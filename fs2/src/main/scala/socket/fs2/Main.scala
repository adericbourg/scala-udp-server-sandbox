package socket.fs2

import java.net.InetSocketAddress

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2._
import fs2.io.udp.{Socket, _}

object Main extends IOApp {

  implicit val socketGroup: AsynchronousSocketGroup = AsynchronousSocketGroup()

  // NOT WORKING YET
  def run(args: List[String]): IO[ExitCode] = {
    try {
      val address = new InetSocketAddress("localhost", 7654)
      val program = Stream.resource(Socket[IO](address))
        .flatMap { serverSocket =>
          serverSocket
            .reads()
            .evalMap { packet =>
              println(packet.bytes.toString())
              serverSocket.write(packet)
            }
            .drain
        }
      program.compile.drain
        .as(ExitCode.Success)
    }
    finally {
      socketGroup.close()
    }
  }
}
