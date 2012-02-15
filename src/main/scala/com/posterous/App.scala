package com.posterous

import java.net.InetSocketAddress;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.http.Http;
import com.twitter.util.Future;
import com.twitter.util.FutureEventListener;
import com.twitter.util.FutureTransformer;

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
  }

}
