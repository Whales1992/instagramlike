package com.example.instagramlike.network.models

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type
import java.util.ArrayList

class SingleVideo {

    /**
     * id : -Liz_D1lv0tX3ojO7CHu
     * user_id : -LizTO9UjVC48PJZ129H
     * talent_id : -LizVN0Q78c8dm_OSSeG
     * description : Kevin Everett Recovery Update
     * media_url : https://maudition-bucket.s3.eu-west-2.amazonaws.com/Kevin+Everett+recovery+update.mp4
     * thumbnail_url : https://maudition-bucket.s3.eu-west-2.amazonaws.com/kevin-everett-recovery-update.png
     * created_at : 2019-06-26T00:00:00.000Z
     * user_info : {"fullname":"Stanley Oguazu","username":"stanlololololo","avatar":"https://maudition-bucket.s3.eu-west-2.amazonaws.com/1.jpg"}
     * comments : [{"id":"-Lj7fX3soiFoStxKxpTw","comment":"cool","created_at":"2019-07-06T19:06:23.417Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7ZHPLzqWAtjKEpbRO","comment":"Nicely done","created_at":"2019-07-06T18:34:44.248Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7DmfNEAPp54-RMC9S","comment":"Olamide we dy","created_at":"2019-07-06T17:00:49.240Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7CQbujLH8znc76oSp","comment":"Kindly","created_at":"2019-07-06T16:54:52.666Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7BLVg3SJPkHwGUGSR","comment":"Happy","created_at":"2019-07-06T16:50:09.580Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7BFbyidnrTupvmKo0","comment":"Work","created_at":"2019-07-06T16:49:45.471Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj7ApljgIPTOtIPpaoC","comment":"Was this","created_at":"2019-07-06T16:47:55.503Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj77uB1j5fAyhFvuLUl","comment":"Different","created_at":"2019-07-06T16:35:07.138Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj77q1S0ESqjIpaTclR","comment":"Different","created_at":"2019-07-06T16:34:50.141Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj77I4CnAEXFxegZk7r","comment":"Would","created_at":"2019-07-06T16:32:26.958Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj76vZvusfelOGbRReh","comment":"Sup","created_at":"2019-07-06T16:30:50.683Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj76F9Evvbe8rbHw76L","comment":"Wetin","created_at":"2019-07-06T16:27:52.848Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj75_1gnBEi20HgUoiV","comment":"Up Naija","created_at":"2019-07-06T16:24:56.238Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj6tfLt2U-6SBQ6du2w","comment":"nice work","created_at":"2019-07-06T15:28:34.233Z","commenter_id":"-Lj6tI_QSBTGG3W8hJch","commenter_avatar":null,"commenter_name":"umarkh"},{"id":"-Lj2372ixOJfPg_7Iun7","comment":"What a joke","created_at":"2019-07-05T16:56:07.151Z","commenter_id":"-Lj1hC_jJr8ErqpyeFvp","commenter_avatar":"https://graph.facebook.com/494117841358516/picture?height=150&width=150&migration_overrides=%7Boctober_2012%3Atrue%7D","commenter_name":"Ahmed Umar"},{"id":"-Lj1uk_x430rfdQTqQ78","comment":"Nice one","created_at":"2019-07-05T16:15:11.742Z","commenter_id":"-Lj1hC_jJr8ErqpyeFvp","commenter_avatar":"https://graph.facebook.com/494117841358516/picture?height=150&width=150&migration_overrides=%7Boctober_2012%3Atrue%7D","commenter_name":"Ahmed Umar"},{"id":"-LizhYoCYcNEtl892V06","comment":"Nice post! Love it","created_at":"2019-06-29T00:00:00.000Z","commenter_id":"-LizTSdjDXk8MiVxhCn7","commenter_avatar":"https://maudition-bucket.s3.eu-west-2.amazonaws.com/5.jpg","commenter_name":"binaryman"},{"id":"-LizhYoCYcNEtl892V07","comment":"Please post more stuffs man!","created_at":"2019-06-29T00:00:00.000Z","commenter_id":"-LizTO9UjVC48PJZ129H","commenter_avatar":"https://maudition-bucket.s3.eu-west-2.amazonaws.com/1.jpg","commenter_name":"stanlololololo"},{"id":"-LizhYoCYcNEtl892V05","comment":"This is actually funny man!","created_at":"2019-06-29T00:00:00.000Z","commenter_id":"-LizSHNYb4OEZYrns5Qw","commenter_avatar":"https://maudition-bucket.s3.eu-west-2.amazonaws.com/2.jpg","commenter_name":"mattoranking"}]
     * comment_count : 19
     * like_count : 8
     * post_shares_count : 0
     */

    var id: String? = null
    var user_id: String? = null
    var talent_id: String? = null
    var description: String? = null
    var media_url: String? = null
    var thumbnail_url: String? = null
    var created_at: String? = null
    var user_info: UserInfoBean? = null
    var comment_count: String? = null
    var like_count: String? = null
    var post_shares_count: String? = null
    var comments: List<CommentsBean>? = null

    class UserInfoBean {
        /**
         * fullname : Stanley Oguazu
         * username : stanlololololo
         * avatar : https://maudition-bucket.s3.eu-west-2.amazonaws.com/1.jpg
         */

        var fullname: String? = null
        var username: String? = null
        var avatar: String? = null

        companion object {

            fun objectFromData(str: String): UserInfoBean {

                return Gson().fromJson(str, UserInfoBean::class.java)
            }

            fun arrayUserInfoBeanFromData(str: String): List<UserInfoBean>? {

                val listType = object : TypeToken<ArrayList<UserInfoBean>>() {

                }.type

                return Gson().fromJson<List<UserInfoBean>>(str, listType)
            }
        }
    }

    class CommentsBean {
        /**
         * id : -Lj7fX3soiFoStxKxpTw
         * comment : cool
         * created_at : 2019-07-06T19:06:23.417Z
         * commenter_id : -Lj6tI_QSBTGG3W8hJch
         * commenter_avatar : null
         * commenter_name : umarkh
         */

        var id: String? = null
        var comment: String? = null
        var created_at: String? = null
        var commenter_id: String? = null
        var commenter_avatar: Any? = null
        var commenter_name: String? = null

        companion object {

            fun objectFromData(str: String): CommentsBean {

                return Gson().fromJson(str, CommentsBean::class.java)
            }

            fun arrayCommentsBeanFromData(str: String): List<CommentsBean>? {

                val listType = object : TypeToken<ArrayList<CommentsBean>>() {

                }.type

                return Gson().fromJson<List<CommentsBean>>(str, listType)
            }
        }
    }

    companion object {

        fun objectFromData(str: String): SingleVideo {

            return Gson().fromJson(str, SingleVideo::class.java)
        }

        fun arraySingleVideoFromData(str: String): List<SingleVideo>? {

            val listType = object : TypeToken<ArrayList<SingleVideo>>() {

            }.type

            return Gson().fromJson<List<SingleVideo>>(str, listType)
        }
    }
}