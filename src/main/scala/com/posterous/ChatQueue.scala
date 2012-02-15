package com.posterous

import scala.collection.mutable.ArrayBuffer

object ChatQueue {

  var queue = new ArrayBuffer[String]

  def push(mesg:String) {
    queue += mesg
  }

  def messagesSince(index:Int):ArrayBuffer[String] = {
    queue.drop(index+1)
  }
}
