package com.posterous

import com.twitter.finagle.{Service, SimpleFilter}
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1
import org.jboss.netty.buffer.ChannelBuffers.copiedBuffer
import org.jboss.netty.util.CharsetUtil.UTF_8
import com.twitter.util.Future
import java.net.InetSocketAddress
import com.twitter.finagle.builder.{Server, ServerBuilder}
import com.twitter.finagle.http.Http

/**
 * @author ${user.name}
 */
object App {
  class ChatService extends Service[HttpRequest, HttpResponse] {
    def apply(request: HttpRequest) = {

      val response = new DefaultHttpResponse(HTTP_1_1, OK)
      response.setContent(copiedBuffer("hello world", UTF_8))
      Future.value(response)
    }
  }

  def main(args : Array[String]) {
    val chatService = new ChatService

    val server: Server = ServerBuilder()
      .codec(Http())
      .bindTo(new InetSocketAddress(9090))
      .name("httpserver")
      .build(chatService)

    println("server started on 9090")
  }
}
