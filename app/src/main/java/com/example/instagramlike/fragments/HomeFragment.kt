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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import android.widget.*

class HomeFragment : Fragment(), DataStates {

    private var adapter : VideoViewAdapter? = null
    private var recyclerview : RecyclerView? = null
    private  var uri: Uri? = null

    private lateinit var meadiaController: MediaController
    private var videoview: VideoView? =null

    val videosList = ArrayList<SingleVideo>()

    internal val resultInterface : DataStates? = this

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val l = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //To make fragment full screen
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)//To make fragment full screen on runtime in the case it resets
//        getLocallyCachedVideos(l)

        findViewsByIds(l)
        setAdapter(videosList)

//        val getVideosSilently = GetVideosInTheBackGround()
//        getVideosSilently.execute(resultInterface)

        return l
    }

    override fun onDetach() {
        super.onDetach()
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
        media_controller: FrameLayout?,
        position: Int
    ) {
        try{
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                this.videoview = videoview

                if(result.media_url!=null){
                    meadiaController = MediaController(context)

                    uri = Uri.parse(result.media_url)
                    this.videoview!!.setVideoURI(uri)

                    this.videoview!!.setOnPreparedListener{
                        this.videoview!!.setMediaController(meadiaController)
                        meadiaController.setAnchorView(media_controller)
                        this.videoview!!.seekTo(1000)
                        this.videoview!!.start()

                        thumb!!.visibility = View.GONE
                        loading!!.visibility = View.GONE
                        this.videoview!!.visibility = View.VISIBLE
                        this.videoview!!.setZOrderOnTop(true)
                    }

                    this.videoview!!.setOnCompletionListener {
                        if(videosList.size-1 > position)
                            recyclerview!!.scrollToPosition(position)
                    }

                    this.videoview!!.setOnInfoListener{player, what, extra ->
                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                            thumb!!.visibility = View.GONE
                            loading!!.visibility = View.GONE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                            loading!!.visibility = View.VISIBLE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                            loading!!.visibility = View.GONE
                        }

                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_NOT_PLAYING){
                            loading!!.visibility = View.VISIBLE
                        }
                        true
                    }
                }else{
                    showMessage("Opps!!!,This video is unavailable for now ...", context!!)
                }
            }, 500)
        }catch (ex :Exception){
            Log.e("EXCEPTION", ""+ex.message)
        }
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