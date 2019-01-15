package socket.java

import java.net.{DatagramPacket, DatagramSocket}

import scala.annotation.tailrec

// Don't do this at home
object Main extends App {

  val maxBytesInMessage = 2048

  val port = 6543
  val receiveData = new Array[Byte](maxBytesInMessage)

  val serverSocket = new DatagramSocket(port)
  val receivePacket = new DatagramPacket(receiveData, receiveData.length)

  @tailrec
  def listen(): Unit = {
    serverSocket.receive(receivePacket)
    val data = new String(receivePacket.getData, 0, receivePacket.getLength)
    println(data)
    listen()
  }

  listen()
}
