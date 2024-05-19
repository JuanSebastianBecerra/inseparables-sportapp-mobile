package com.example.inseparables_sportapp_mobile.comunes

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class VolleyBroker constructor(context: Context) {
    val instance: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object{
        fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener, headersMap: HashMap<String, String>): StringRequest {
            val getRequest: StringRequest = object : StringRequest(
                Method.GET, path,
                responseListener,
                errorListener
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headersMap
                }
            }
            return getRequest
        }
        fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
            return  JsonObjectRequest(Request.Method.POST, path, body, responseListener, errorListener)
        }

        fun postRequestWithHeaders(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener, headersMap: HashMap<String, String> ):JsonObjectRequest{
            val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST, path, body, responseListener, errorListener) {
                /** Passing some request headers*  */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headersMap
                }
            }
            return  jsonObjReq
        }

        fun getRequestWithHeaders(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener, headersMap: HashMap<String, String> ):JsonObjectRequest{
            val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
                Method.GET, path, body, responseListener, errorListener) {
                /** Passing some request headers*  */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headersMap
                }
            }
            return  jsonObjReq
        }

        fun deleteRequestWithHeaders(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener, headersMap: HashMap<String, String> ):JsonObjectRequest{
            val jsonObjReq: JsonObjectRequest = object : JsonObjectRequest(
                Method.DELETE, path, body, responseListener, errorListener) {
                /** Passing some request headers*  */
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    return headersMap
                }
            }
            return  jsonObjReq
        }

    }

}