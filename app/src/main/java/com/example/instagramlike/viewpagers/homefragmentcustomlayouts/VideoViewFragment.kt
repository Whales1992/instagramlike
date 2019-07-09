package com.example.instagramlike.viewpagers.homefragmentcustomlayouts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper.getMainLooper
import android.os.StrictMode
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.example.instagramlike.R
import com.example.instagramlike.network.models.SingleVideo
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class VideoViewFragment : Fragment(), VideoState {
    var videolink = SingleVideo()
    var position : Int = 0

    internal val videoState : VideoState ? = this
    internal var loading : ProgressBar ? = null

    private var vv: VideoView? = null
    private var thumb: ImageView? = null
    private var tagger_img: ImageView? = null
    private var tager: TextView? = null
    private var description: TextView? = null
    private var like_text: TextView? = null
    private var comment_text: TextView? = null
    private var share_text: TextView? = null
    private var media_controller: FrameLayout? = null

    private var uri: Uri? = null
    private val isContinuously = false

    private var playbackCallbackPosition = 0
    private lateinit var meadiaController: MediaController

    companion object {
        fun newInstance() = VideoViewFragment()
    }

    fun preloadVideo(videolink: SingleVideo, position: Int){
        this.videolink = videolink
        this.position = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val ll = inflater.inflate(R.layout.video_view_fragment, container, false) as ConstraintLayout
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //To make fragment full screen
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)//To make fragment full screen on runtime in the case it resets

        //Init media controller
        meadiaController = MediaController(context)

        setFindViewsByid(ll)

        showHide()

        loadTaggerImage()
        loadTaggerContents()
        loadVideosThumbNails()

        loadVideos(videolink.media_url)

        return ll
    }

    /*
    * Note that Appending of empty text is to make sure @widget TextView does'nt get
    * an empty String value which will definitely cause Runtime crash that even Kotlin
    * cannot handle
    * */
    private fun loadTaggerContents() {
        like_text!!.text = ""+videolink.like_count
        comment_text!!.text = ""+videolink.comment_count
        share_text!!.text = ""+videolink.post_shares_count
        tager!!.text = "@"+videolink.user_info!!.username
        description!!.text = ""+videolink.description
    }

    override fun onPause() {
        super.onPause()
        vv!!.stopPlayback()
        playbackCallbackPosition = vv!!.currentPosition
    }

    override fun onStop() {
        super.onStop()
        vv!!.stopPlayback()
    }

    private fun loadTaggerImage() {
        Picasso.get().load(videolink.user_info!!.avatar).centerCrop().fit().into(tagger_img, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                //TODO::
            }

            override fun onError(e: Exception) {
                tagger_img!!.setBackgroundResource(R.drawable.sampleuser)
            }
        })
    }

    private fun loadVideosThumbNails() {
        Picasso.get().load(videolink.thumbnail_url).centerCrop().fit().into(thumb, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                loading!!.visibility = GONE

//                    val loadVideos = LoadVideos()
//                    loadVideos.execute(""+videolink.media_url)
//                    loadVideos(""+videolink.media_url)
            }

            override fun onError(e: Exception) {
                showMessage(""+e.message)
                loading!!.visibility = GONE
            }
        })
    }

    private fun setFindViewsByid(ll: ConstraintLayout) {
        vv = ll.findViewById(R.id.videoview) as VideoView
        thumb = ll.findViewById(R.id.thumbnail_holder) as ImageView
        loading = ll.findViewById(R.id.loading) as ProgressBar
        tager = ll.findViewById(R.id.tager) as TextView
        description = ll.findViewById(R.id.description) as TextView
        tagger_img = ll.findViewById(R.id.tagger_img) as ImageView

        like_text = ll.findViewById(R.id.like_text) as TextView
        comment_text = ll.findViewById(R.id.comment_text) as TextView
        share_text = ll.findViewById(R.id.share_text) as TextView
        media_controller = ll.findViewById(R.id.media_controller) as FrameLayout
    }

    private fun loadVideos(url : String ?) {
        object : AsyncTask<Void, Void, Void>() {
            public override fun doInBackground(vararg voids: Void): Void? {
                Handler(getMainLooper()).post{
                    if(url!=null){

                        loading!!.visibility = VISIBLE

                        val uri = Uri.parse(url)
                        vv!!.setVideoURI(uri)

                        vv!!.setOnPreparedListener{
                            vv!!.setMediaController(meadiaController)
                            meadiaController.setAnchorView(media_controller)
                            vv!!.seekTo(100)
                            vv!!.start()
                            vv!!.pause()

                            vv!!.visibility = VISIBLE
                            vv!!.setZOrderOnTop(true)

                            loading!!.visibility = GONE
                        }
                    }else{
                        showHide()
                        showMessage("We're sorry, video not available ...")
                    }


                    vv!!.setOnInfoListener{player, what, extra ->
                        if(what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                            thumb!!.visibility = GONE
                            loading!!.visibility = GONE
                        }
                        true
                    }
                }
                return null
            }
        }.doInBackground()
    }

    fun downloadVideoAndSaveLocally(media_link: String): String? {
        try {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val destinationFile = "VID_$timeStamp.mp4"

            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val url = URL(media_link)
            val iss = url.openStream()

            val filePath = context!!.filesDir.path + destinationFile
            val file = File(filePath)

            FileOutputStream(file).use { output ->
                    iss.copyTo(output)
                output.flush()
            }

            iss.close()

            return if (file.exists()) {
                file.absolutePath
            } else
                null
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    class LoadVideos : AsyncTask<String, Void, Void>(){
        override fun doInBackground(vararg params: String?): Void? {
                params[0]?.let { VideoViewFragment.newInstance().videoState!!.showVideo(it) }
            return null
        }
    }

    override fun showVideo(result: String) {
        Log.e("URL", ""+result)

            Handler(getMainLooper()).postDelayed({
                thumb!!.visibility = GONE
                uri = Uri.parse(result)
                vv!!.setVideoURI(uri)
                vv!!.requestFocus()
                vv!!.start()

                vv!!.setOnCompletionListener {
                    if (isContinuously) {
                        vv!!.start()
                    }
                }
            }, 2000)
    }

    fun showMessage(msg : String){
        try{
            val handler = Handler(getMainLooper())
            handler.post {
                AlertDialog.Builder(this@VideoViewFragment.context)
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

    fun showHide(){
            loading!!.visibility = if (loading!!.visibility == VISIBLE){
                View.INVISIBLE
            } else{
                VISIBLE
            }
    }
}