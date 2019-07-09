package com.example.instagramlike.network

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class ResponseUtil {

    public companion object{
        val OK = "200"
        val BAD_REQUEST = "400" //EXISTING USER RETURNING

        public fun isSuccessfull(response : String) : Boolean{
            try {
                var json = JSONObject(response)
                if(json.has("response_code"))
                    return json.get("response_code").equals(OK)
            }catch (ex : JSONException){
                Log.e("Exception", ex.message+"")
            }

            return false
        }
    }
}