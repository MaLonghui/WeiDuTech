
package com.wd.tech.api
import com.wd.tech.bean.HeadBean
import com.wd.tech.bean.UserPublicBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.HeaderMap
import retrofit2.http.Url
import retrofit2.http.Multipart
import retrofit2.http.POST



@JvmSuppressWildcards
interface ApiService {
    @GET
    fun get(@Url url:String, @HeaderMap headerMap: Map<String,Any>, @QueryMap parms:Map<String,Any>):Observable<ResponseBody>
    @POST
    @FormUrlEncoded
    fun post(@Url url: String,@HeaderMap headerMap: Map<String,Any>,@FieldMap parms:Map<String,Any>):Observable<ResponseBody>

    @PUT
    fun put(@Url url: String, @HeaderMap headerMap: Map<String, Any>, @QueryMap parms: Map<String, Any>):Observable<ResponseBody>

    @DELETE
    fun delete(@Url url: String, @HeaderMap headerMap: Map<String, Any>, @QueryMap parms: Map<String, Any>):Observable<ResponseBody>
    //多图上传
    @POST
    @Multipart
    fun sendCommunity(@Url url: String, @HeaderMap headerMap: Map<String, Any>, @Query("content") content : String, @Part file: ArrayList<MultipartBody.Part>): Observable<UserPublicBean>

    //上传用户头像
    @POST
    @Multipart
    fun headicon(@Url url: String, @HeaderMap headMap: Map<String, Any>, @Part image: MultipartBody.Part): Observable<HeadBean>

}