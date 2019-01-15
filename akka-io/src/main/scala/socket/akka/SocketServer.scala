package socket.akka

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorSystem, _}
import akka.io.{IO, Udp}
import akka.util.ByteString

import scala.io.StdIn

object Main extends App {

  val system: ActorSystem = ActorSystem("socket-server")

  val address = new InetSocketAddress("localhost", 9876)
  val server = system.actorOf(SockerServer.props(address))

  try {
    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  }
  finally {
    println("Socket Server exiting...")
    system.terminate()
  }
}

class SockerServer(address: InetSocketAddress) extends Actor {

  import context.system

  IO(Udp) ! Udp.Bind(self, address)

  def receive: PartialFunction[Any, Unit] = {
    case Udp.Bound(_) => context.become(ready(sender))
  }

  def ready(socket: ActorRef): Receive = {
    case Udp.Received(data, remote) =>
      println(s"Received message: '${data.utf8String}' from '$remote'")
      socket ! Udp.Send(ByteString("Thank you for your message\n"), remote)
    case Udp.Unbind =>
      socket ! Udp.Unbind
    case Udp.Unbound =>
      context.stop(self)
  }
}

object SockerServer {
  def props(address: InetSocketAddress): Props = Props(classOf[SockerServer], address)
}
