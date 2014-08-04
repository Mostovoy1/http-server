package Server; /**
 * Created with IntelliJ IDEA.
 * User: Мост
 * Date: 26.07.14
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
import Service.ServiceCustomConnection;
import Service.ServiceLogger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import java.lang.instrument.Instrumentation;

import javax.print.attribute.PrintServiceAttributeSet;
import javax.servlet.RequestDispatcher;

import java.net.URL;
import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

import io.netty.handler.traffic.*;

public class HttpHelloWorldServerHandler extends AbstractTrafficShapingHandler {



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
       if (msg instanceof HttpRequest)
            {

                HttpRequest request = (HttpRequest) msg;

                if (request.getUri().equals("/favicon.ico"))
                {
                 ctx.close().addListener(ChannelFutureListener.CLOSE);
                }
                 ServiceLogger.inc_Count_of_open_channels();
                 if (request.getUri().equals("/hello") || request.getUri().contains("/redirect") || request.getUri().contains("/status") )
                 {
                     ServiceLogger.inc_count_unique_hosts(request.headers().get("Host"));
                     ServiceLogger.setCount_query_by_ip(request.headers().get("Host"));
                     ServiceLogger.inc_count();
                     FullHttpResponse response = null;
                     if (request.getUri().equals("/hello"))
                {
                    response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(ServiceLogger.CONTENT));
                    response.headers().set(CONTENT_TYPE, "text/plain");
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e) {
                       exceptionCaught(ctx,e);
                    }
                 }
                     if (request.getUri().contains("/redirect"))
                {
                    QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
                    ServiceLogger.inc_count_url_requests(decoder.parameters().get("url").get(0));
                    String redirect_uri = "http://" + decoder.parameters().get("url").get(0);
                    response = new DefaultFullHttpResponse(HTTP_1_1, MOVED_PERMANENTLY);
                    response.headers().set(CONTENT_LENGTH, 0);
                    response.headers().set(LOCATION, redirect_uri);
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                }
                if (request.getUri().contains("/status"))
                {
                            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(
                            ("count of open connections : " + ServiceLogger.getCount_of_open_channels() + "\n\r" +
                                    "count of requests : " + ServiceLogger.getCount_query() + "\n\r" +
                                    "hosts : " + ServiceLogger.getUnique_hosts() + " " + "\n\r" +
                                    "count of unique requests " + ServiceLogger.getUnique_hosts().size() + "\n\r " +
                                    "url requests : " + "\n\r" +
                                    ServiceLogger.getCount_url_requests() + "\n\r" +
                                    "table of of ip, count of query , last query_date" + ServiceLogger.getCount_query_by_ip() + "\n\r" +
                                    "--------------------------------------------------------------------------------------------------" + "\n\r" +
                                    "Log of last 16 connections " + "\n\r" +
                                    ServiceLogger.getConnections()
                            ).getBytes()));
                        response.headers().set(CONTENT_TYPE, "text/plain");
                        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                        }

                     ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                     ServiceLogger.getConnections().add(new ServiceCustomConnection(request.headers().get("Host"),
                             request.getUri(), new Date().getTime(), request.toString().length(), response.toString().length(),
                             trafficCounter.lastCumulativeTime(), System.currentTimeMillis()));

                 }
                    ServiceLogger.dec_Count_of_open_channels();
                }

            }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
     @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        trafficCounter = new TrafficCounter(this,ctx.executor(), "TC", 1000);
        trafficCounter.start();
        super.channelRegistered(ctx);
           //To change body of overridden methods use File | Settings | File Templates.
    }
     @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        trafficCounter.stop();
        super.channelUnregistered(ctx);
           //To change body of overridden methods use File | Settings | File Templates.
    }
}