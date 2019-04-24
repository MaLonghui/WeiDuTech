
package com.wd.tech.api
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
    @POST
    @Multipart
    fun headicon(@Url url:String, @HeaderMap headMap:Map<String,String>,@Part image:MultipartBody.Part):Observable<ResponseBody>

    //多图上传
    @POST
    @Multipart
    fun releasePost(@Url url: String, @HeaderMap headerMap: Map<String, Any>, @FieldMap parms:Map<String,Any>, @Part image: MultipartBody.Part)

}