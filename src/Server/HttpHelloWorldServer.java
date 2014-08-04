package Server; /**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 26.07.14
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
import Service.ServiceLogger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * An HTTP server that sends back the content of the received HTTP request
 * in a pretty plaintext form.
 */
public final class HttpHelloWorldServer {



    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap b = new ServerBootstrap();
             b.group(bossGroup, workerGroup);
             b.channel(NioServerSocketChannel.class);
             b.childHandler(new HttpHelloWorldServerInitializer());
             Channel ch = b.bind(ServiceLogger.PORT).sync().channel();
             ch.closeFuture().sync();
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}