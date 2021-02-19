package org.e.util.http

import okhttp3.Request.Builder
import okhttp3.{OkHttpClient, Request, RequestBody}

import java.util.concurrent.TimeUnit
import scala.collection.mutable.{Map => MutMap}

class Http(val client:OkHttpClient) {
    private var url:String = null
    private var data:Array[Byte] = null
    private val headers:MutMap[String, String] = MutMap[String,String]()
    
    def url(url:String):Http = {
        this.url = url
        this
    }
    def data(data:String | Array[Byte]):Http = {
        data match {
            case data: String => this.data = data.getBytes("UTF-8")
            case data: Array[Byte] => this.data = data
        }
        this
    }
    def addHeader(name:String, value:String):Http = {
        this.headers += (name -> value)
        this
    }
    def addHeaders(headers:Map[String, String]):Http = {
        this.headers ++= headers
        this
    }
    def get(): HttpResp ={
       request(GET)
    }
    def post(): HttpResp ={
        request(POST)
    }
    private def request(method: HttpMethod): HttpResp ={
        val builder = method match {
            case  GET => new Builder().url(this.url).get()
            case  POST => new Builder().url(this.url).post(RequestBody.create(null, data))
            case  DELETE => new Builder().url(this.url).delete(RequestBody.create(null, data))
            case  PUT => new Builder().url(this.url).put(RequestBody.create(null, data)) 
        } 
        headers.foreach((k, v) =>  builder.addHeader(k, v))
        val resp = client.newCall(builder.build()).execute()
        HttpResp.fromOkResponse(resp)
    }
}

object Http {
    private val client = {
        OkHttpClient.Builder()
          .followRedirects(true)
          .connectTimeout(5, TimeUnit.SECONDS)
          .writeTimeout(5, TimeUnit.SECONDS)
          .readTimeout(300, TimeUnit.SECONDS)
          .followRedirects(false)
          .build();
    }
    def create() = {
        new Http(client)
    }
}