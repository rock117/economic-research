package org.e.util.http

import okhttp3.Response

type StatusCode = 200|201|300|301|302|400|401|403|404|500|501|502|503

case class HttpResp(status:Int, data:Array[Byte], headers:Map[String, String]){
    def verifyOk():HttpResp = {
        if(this.status != 200){
            throw new RuntimeException(s"not 200 status:$status")
        }
        this
    }
    def stringData(encoding:String = "UTF-8"):String = new String(data, encoding)
}

object HttpResp {
    def fromOkResponse(resp:Response): HttpResp = {
        val code = resp.code()
        val data = resp.body().bytes()
        val names = resp.headers().names()
        val map = scala.collection.mutable.Map.empty[String, String]
        names.forEach(name => {
            map += (name -> resp.header(name))
        })
        HttpResp(status = code, data = data, headers = map.toMap)
    }
}


def fromInt(code:Int):Option[StatusCode] = {
    code match {
        case 200|201|300|301|302|400|401|403|404|500|501|502|503  => Some(code.asInstanceOf[StatusCode])
    }
}