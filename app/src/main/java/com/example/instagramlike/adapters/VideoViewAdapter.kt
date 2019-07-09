package com.example.instagramlike.adapters

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.instagramlike.R
import com.example.instagramlike.network.models.SingleVideo
import com.example.instagramlike.viewpagers.DataStates
import com.squareup.picasso.Picasso

public class VideoViewAdapter (private val list: ArrayList<SingleVideo>, private val dataStatesInterface : DataStates) : RecyclerView.Adapter<VideoViewAdapter.VideoViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return VideoViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
            val singleVideo : SingleVideo = list[position]
            holder.bind(singleVideo, dataStatesInterface)
        }

        override fun getItemCount(): Int = list.size


    class VideoViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.video_view_fragment, parent, false)) {

        private var loading : ProgressBar? = null
        private var videoview: VideoView? = null
        private var thumb: ImageView? = null
        private var tagger_img: ImageView? = null
        private var tagger: TextView? = null
        private var description: TextView? = null
        private var like_text: TextView? = null
        private var comment_text: TextView? = null
        private var share_text: TextView? = null
        private var media_controller: FrameLayout? = null

        init {
            loading = itemView.findViewById(R.id.loading)
            videoview = itemView.findViewById(R.id.videoview)
            thumb = itemView.findViewById(R.id.thumb)
            tagger = itemView.findViewById(R.id.tagger)
            tagger_img = itemView.findViewById(R.id.tagger_img)
            description = itemView.findViewById(R.id.description)
            like_text = itemView.findViewById(R.id.like_text)
            comment_text = itemView.findViewById(R.id.comment_text)
            share_text = itemView.findViewById(R.id.share_text)
            media_controller = itemView.findViewById(R.id.media_controller)
        }

        fun bind(singleVideo : SingleVideo, dataStatesInterface : DataStates) {
            like_text!!.text = ""+singleVideo.like_count
            comment_text!!.text = ""+singleVideo.comment_count
            share_text!!.text = ""+singleVideo.post_shares_count
            tagger!!.text = "@"+singleVideo.user_info!!.username
            description!!.text = ""+singleVideo.description


            loadVideosThumbNails(thumb, singleVideo.thumbnail_url)
            loadTaggerThumbNails(tagger_img, singleVideo.user_info!!.avatar)

            dataStatesInterface.loadVideo(singleVideo, videoview, thumb, loading, media_controller)
        }

        private fun loadTaggerThumbNails(tagger_img: ImageView?, avatar: String?) {
            if(avatar!=null){
                Picasso.get().load(avatar).centerCrop().fit().into(tagger_img, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        //TODO::
                    }

                    override fun onError(e: Exception) {
                        tagger_img!!.setBackgroundResource(R.drawable.sampleuser)
                    }
                })
            }
        }

        private fun loadVideosThumbNails(thumb: ImageView?, thumbnail_url: String?) {
            Picasso.get().load(thumbnail_url).centerCrop().fit().into(thumb, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    loading!!.visibility = View.GONE

//                    val loadVideos = LoadVideos()
//                    loadVideos.execute(""+videolink.media_url)
//                    loadVideos(""+videolink.media_url)
                }

                override fun onError(e: Exception) {
                    showMessage(""+e.message, thumb!!.context)
                    loading!!.visibility = View.GONE
                }
            })
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
}