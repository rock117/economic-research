import org.e.util.http.Http

object Main {

  def main(args: Array[String]): Unit = {
      println(Http.create().url("http://baidu.com").get().stringData())
  }

  def msg = "I was compiled by dotty :)"

}
