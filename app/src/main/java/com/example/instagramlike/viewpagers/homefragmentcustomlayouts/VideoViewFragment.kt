package com.example.instagramlike.viewpagers.homefragmentcustomlayouts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
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
    private var uri: Uri? = null
    private val isContinuously = false

    companion object {
        fun newInstance() = VideoViewFragment()
    }

    fun preloadVideo(videolink: SingleVideo, position: Int){
        Log.e("IN!@@@@", "==== "+videolink)
        this.videolink = videolink
        this.position = position
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val ll = inflater.inflate(R.layout.video_view_fragment, container, false) as ConstraintLayout

        vv = ll.findViewById(R.id.videoview) as VideoView
        thumb = ll.findViewById(R.id.thumbnail_holder) as ImageView

        loading = ll.findViewById(R.id.loading) as ProgressBar

        showHide()

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

        return ll
    }

    private fun loadVideos(url : String) {
        object : AsyncTask<Void, Void, Void>() {
            public override fun doInBackground(vararg voids: Void): Void? {
                Handler(getMainLooper()).post{
                    val path : String? = downloadVideoAndSaveLocally(url)
                    if(path!=null){
                        thumb!!.visibility = GONE
                        uri = Uri.parse(path)
                        vv!!.setVideoURI(uri)
                        vv!!.requestFocus()
                        vv!!.start()

                        vv!!.setOnCompletionListener {
                            if (isContinuously) {
                                vv!!.start()
                            }
                        }
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