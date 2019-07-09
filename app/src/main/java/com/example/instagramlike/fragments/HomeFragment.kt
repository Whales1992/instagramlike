package com.example.instagramlike.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramlike.R
import com.example.instagramlike.network.Apicalls
import com.example.instagramlike.network.ResponseCallback
import com.example.instagramlike.network.models.SingleVideo
import com.example.instagramlike.network.models.VideoModel
import com.example.instagramlike.viewpagers.homefragmentcustomlayouts.CustomAdapter
import com.example.instagramlike.viewpagers.homefragmentcustomlayouts.FetchVideoState
import kotlinx.android.synthetic.main.home_fragment.view.*
import org.json.JSONArray
import org.json.JSONException
import com.google.gson.reflect.TypeToken as TypeToken1

class HomeFragment : Fragment(), FetchVideoState {
    internal var adapter : CustomAdapter? = null
    internal var downloadedLinks : ArrayList<VideoModel>? = null
    internal var videosLink : ArrayList<String>? = null

    internal val resultInterface : FetchVideoState ? = this
    internal var l : ConstraintLayout? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val l = inflater.inflate(R.layout.home_fragment, container, false) as ConstraintLayout
        this.l = l
//        getLocallyCachedVideos(l)

        val getVideosSilently = GetVideosSilently()
        getVideosSilently.execute(resultInterface)

        return l
    }

    private fun initViewPager(videosLink: ArrayList<SingleVideo>) {
        this.l!!.viewpagerr.adapter = CustomAdapter(fragmentManager, videosLink.size, videosLink)

        this.l!!.viewpagerr.setPageTransformer(true, this.l!!.viewpagerr.PageTransformer())

        this.l!!.viewpagerr.setCurrentItem(0, true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun finishFetchingVideos(result: String) {
        val video= SingleVideo
        val videosList = ArrayList<SingleVideo>()

        try{
            val json = JSONArray(result)
            for (i in 0 until json.length()) {
                val item = json.getJSONObject(i)
                videosList.add(video.objectFromData(item.toString()))
            }

            initViewPager(videosList)
        }catch (jx : JSONException){
            Log.e("EXCEPTION", jx.message+"")
        }
    }

    internal class GetVideosSilently : AsyncTask<FetchVideoState, String, String>(){

        override fun doInBackground(vararg params: FetchVideoState?): String? {

            val apiCalls = Apicalls(60, 60, 60)
            apiCalls.getVideos(object : ResponseCallback {

                override fun onSuccess(res: String?): String? {
                    if(res!=null)
                        params.get(0)!!.finishFetchingVideos(res)
                    return null
                }

                override fun onFailure(res: String?): String? {
                    return null
                }

            })

            return null
        }
    }
}