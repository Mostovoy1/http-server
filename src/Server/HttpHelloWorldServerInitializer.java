package Server; /**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 26.07.14
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
import Server.HttpHelloWorldServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    public HttpHelloWorldServerInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpHelloWorldServerHandler());
        }
    }