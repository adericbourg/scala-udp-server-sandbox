package socket.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.channel.{ChannelInitializer, _}
import io.netty.util.CharsetUtil

object Main extends App {

  val workerGroup = new NioEventLoopGroup()

  try {
    val bootstrap = new Bootstrap()
    bootstrap.group(workerGroup)
      .channel(classOf[NioDatagramChannel])
      .handler(new ChannelInitializer[NioDatagramChannel] {
        override def initChannel(ch: NioDatagramChannel): Unit = {
          ch.pipeline().addLast(new ConsolePrintHandler)
        }
      })

    val channelFuture = bootstrap.bind(8765).sync()
    println("Server running...")
    channelFuture.channel().closeFuture().sync()
  }
  finally {
    println("Shutting down server")
    workerGroup.shutdownGracefully()
  }
}

class ConsolePrintHandler extends SimpleChannelInboundHandler[DatagramPacket] {
  override def channelRead0(ctx: ChannelHandlerContext, msg: DatagramPacket): Unit = {
    print(msg.content().toString(CharsetUtil.UTF_8))
  }
}
