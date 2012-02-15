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
    val uiTemplate = scala.io.Source.fromFile("index.html").mkString

    def The404Template = {
      <h1>404</h1>
    }

    def frontpageRoute = {
      val response = new DefaultHttpResponse(HTTP_1_1, OK)
      response.setContent(copiedBuffer(uiTemplate.toString(), UTF_8))
      Future.value(response)
    }

    def The404Route = {
      val response = new DefaultHttpResponse(HTTP_1_1, OK)
      response.setContent(copiedBuffer(The404Template.toString(), UTF_8))
      Future.value(response)
    }

    def Post404Route = {
      val response = new DefaultHttpResponse(HTTP_1_1, OK)
      response.setContent(copiedBuffer("errorrrrrr", UTF_8))
      Future.value(response)
    }


    def getRoutes(request: HttpRequest) = {
      request.getUri match {
        case "/" => frontpageRoute
        case _ => The404Route
      }
    } 


    def postRoutes(request: HttpRequest) = {
      request.getUri match {
        case "/" => frontpageRoute
        case _ => Post404Route
      }
    }

    def apply(request: HttpRequest) = {
      request.getMethod.getName match {
        case "GET" => getRoutes(request)
        case "POST" => postRoutes(request)
        case _ => getRoutes(request)
      }
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
