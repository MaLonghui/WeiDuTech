
package com.wd.tech.api
import com.wd.tech.bean.UserPublicBean
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

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

    @POST
    @Multipart
    fun headicon(@Url url:String, @HeaderMap headMap:Map<String,String>,@Part image:MultipartBody.Part):Observable<ResponseBody>


}