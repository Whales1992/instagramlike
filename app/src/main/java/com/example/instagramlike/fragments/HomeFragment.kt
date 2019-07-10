package com.example.instagramlike.fragments

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.instagramlike.R
import com.example.instagramlike.adapters.VideoViewAdapter
import com.example.instagramlike.network.Apicalls
import com.example.instagramlike.network.ResponseCallback
import com.example.instagramlike.network.models.SingleVideo
import com.example.instagramlike.viewpagers.DataStates
import org.json.JSONArray
import org.json.JSONException
import java.lang.Exception
import com.google.gson.reflect.TypeToken as TypeToken1
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.*
import com.example.instagramlike.viewpagers.PageStates

class HomeFragment : Fragment(), DataStates, PageStates {

    private var adapter : VideoViewAdapter? = null
    private var recyclerview : RecyclerView? = null
    private  var uri: Uri? = null

    private lateinit var meadiaController: MediaController
    private var videoview: VideoView? =null
    private var position: Int? = null

    val videosList = ArrayList<SingleVideo>()

    internal val resultInterface : DataStates? = this
    var args : Bundle ? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val l = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //To make fragment full screen
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)//To make fragment full screen on runtime in the case it resets
//        getLocallyCachedVideos(l)

        if(savedInstanceState!=null){
            args = savedInstanceState.getBundle("key")
            if(args != null){
                position = args!!.getInt("position")
            }
        }

        findViewsByIds(l)
        setAdapter(videosList)

//        val getVideosSilently = GetVideosInTheBackGround()
//        getVideosSilently.execute(resultInterface)

        return l
    }

    override fun pageStateChange(position: Int) {
        Log.e("AA", "..."+position)

        if(this.position!! != position)
            if(videoview!=null)
                videoview!!.stopPlayback()
    }

    override fun onDetach() {
        super.onDetach()
        if(videoview!=null)
            videoview!!.stopPlayback()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(videoview!=null)
            videoview!!.stopPlayback()
    }

    override fun onPause() {
        super.onPause()

        if(videoview!=null)
            videoview!!.stopPlayback()
    }

    override fun onStop() {
        super.onStop()
        if(videoview!=null)
            videoview!!.stopPlayback()
    }

    private fun findViewsByIds(l: ConstraintLayout) {
        recyclerview = l.findViewById(R.id.recyclerview)
    }

    override fun finishFetchingFeedFromServer(result: String) {
        val video= SingleVideo
        videosList.clear()

        try{
            val json = JSONArray(result)
            for (i in 0 until json.length()) {
                val item = json.getJSONObject(i)
                videosList.add(video.objectFromData(item.toString()))
            }

            adapter!!.notifyDataSetChanged()
        }catch (jx : JSONException){
            Log.e("EXCEPTION", jx.message+"")
        }
    }

    private fun setAdapter(videosList: ArrayList<SingleVideo>) {
        adapter = VideoViewAdapter(videosList, this)
        recyclerview!!.setAdapter(adapter)
        val layoutmanager = LinearLayoutManager(context!!)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        recyclerview!!.layoutManager = layoutmanager

        adapter!!.notifyDataSetChanged()

        //Simply uncomment to see beautiful Indicator Decoration ;)
//        recyclerview!!.addItemDecoration(LinePagerIndicatorDecoration())

        // This is the whole magic that makes recyclerview act like a view pager
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerview)

        fetchDataFromServer()
    }

    private fun fetchDataFromServer(){
        try{
            val apiCalls = Apicalls(60, 60, 60)
            apiCalls.getVideos(object : ResponseCallback {

                override fun onSuccess(res: String?): String? {
                    if(res!=null)
                        resultInterface!!.finishFetchingFeedFromServer(res)
                    return null
                }

                override fun onFailure(res: String?): String? {
                    return null
                }
            })

        }catch (ex : Exception){
            Log.e("Exception", ""+ex.message);
        }
    }

    override fun loadVideo(
        result: SingleVideo,
        videoview: VideoView?,
        thumb: ImageView?,
        loading: ProgressBar?,
        play_btn: ImageView?,
        pause_btn: ImageView?,
        position: Int
    ) {
        try{
            this.videoview = videoview

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({

                if(result.media_url!=null){
                    loading!!.visibility = VISIBLE
                    thumb!!.visibility = VISIBLE

                    uri = Uri.parse(result.media_url)
                    videoview!!.setVideoURI(uri)

                    videoview.setOnPreparedListener{
                        videoview.seekTo(1000)
                        videoview.start()

                        thumb.visibility = GONE
                        loading.visibility = GONE
                        videoview.visibility = VISIBLE
                    }

                    videoview.setOnCompletionListener {l ->
                        l.seekTo(1000)

                        if(videosList.size-1 > position)
                            this.recyclerview!!.scrollToPosition(position+1)
                            this.recyclerview!!.animate()
                    }

                    videoview.setOnInfoListener{player, what, extra ->
                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                            thumb.visibility = GONE
                            loading.visibility = GONE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                            loading.visibility = VISIBLE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                            loading.visibility = GONE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_NOT_PLAYING){
                            loading.visibility = VISIBLE
                        }
                        true
                    }


//                    val fadeIn = AlphaAnimation(0f, 1f)
//                    fadeIn.interpolator = DecelerateInterpolator() //add this
//                    fadeIn.duration = 1000
//
//                    val fadeOut = AlphaAnimation(1f, 0f)
//                    fadeOut.interpolator = AccelerateInterpolator() //and this
//                    fadeOut.startOffset = 1000
//                    fadeOut.duration = 2000
//
//                    val animation = AnimationSet(false) //change to false
//                    animation.addAnimation(fadeIn)
//                    animation.addAnimation(fadeOut)

//                    videoview.setOnTouchListener(View.OnTouchListener { v, event ->
//                        if(event.action==MotionEvent.ACTION_UP){
//                            if(videoview.isPlaying){
//                                videoview.pause()
//                                showPlayBtn(play_btn!!, pause_btn!!)
//
//                                val handler = Handler(Looper.getMainLooper())
//                                handler.postDelayed({
//                                    play_btn.animation = fadeOut
//                                }, 1000)
//                            }else{
//                                videoview.resume()
//                                showPauseBtn(play_btn!!, pause_btn!!)
//
//                                val handler = Handler(Looper.getMainLooper())
//                                handler.postDelayed({
//                                    pause_btn.animation = fadeOut
//                                }, 1000)
//                            }
//                        }
//
//                        v?.onTouchEvent(event) ?: true
//                    })
                }else{
                    showMessage("Opps!!!,This video is unavailable for now ...", context!!)
                }
            }, 100)
        }catch (ex :Exception){
            showMessage("Opps!!!, Something when wrong ...", context!!)
            Log.e("EXCEPTION", ""+ex.message)
        }
    }

    private fun showPlayBtn(play : ImageView, pause : ImageView){
        play.visibility = VISIBLE
        pause.visibility = GONE
    }

    private fun showPauseBtn(play : ImageView, pause : ImageView){
        play.visibility = GONE
        pause.visibility = VISIBLE
    }

    private fun showMessage(msg : String, context : Context){
        try{
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
                    .show()
            }
        }catch (ex : Exception){
            Log.e("EXCEPTION", ""+ex.message)
        }
    }

}