package ie.my353.inis.utils;

import ie.my353.inis.entity.WebsiteStatus;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


@Component
@Slf4j
public class HttpClientUtils {
    @Autowired
    private WebsiteStatus status;


    public CloseableHttpClient getHttpClient() {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (KeyStoreException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        /**
         * 测出超时重试机制为了防止超时不生效而设置
         *  如果直接放回false,不重试
         *  这里会根据情况进行判断是否重试
         */

        HttpRequestRetryHandler retry = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext httpContext) {
                if(executionCount >= 3){// 如果已经重试了3次，就放弃
                    //log.error("连接重复了3次，放弃连接", exception);
                    log.error(exception.getMessage());
                    status.setStatus("RED");
                    return false;
                }
                if(exception instanceof NoHttpResponseException){
                    // 如果服务器丢掉了连接，那么就重试
                    //log.error("服务器丢掉了连接", exception);
                    log.error(exception.getMessage());
                    status.setStatus("RED");
                    return true;
                }
                if(exception instanceof SSLHandshakeException){
                    // 不要重试SSL握手异常
                    //log.error("SSL握手异常", exception);
                    log.error(exception.getMessage());
                    return false;
                }
                if(exception instanceof InterruptedIOException){
                    // 超时
                    //log.error("IO 错误",exception);
                    log.error(exception.getMessage());
                    status.setStatus("RED");
                    return true;
                }
                if(exception instanceof UnknownHostException){
                    // 目标服务器不可达
                    //log.error("目标服务器不可达", exception);
                    log.error(exception.getMessage());
                    status.setStatus("RED");
                    return false;
                }
                if(exception instanceof ConnectTimeoutException){
                    // 连接被拒绝
                    //log.error("连接被拒绝,连接超时", exception);
                    log.error(exception.getMessage());
                    status.setStatus("RED");
                    return true;
                }
                if(exception instanceof SSLException){
                    // ssl握手异常
                    //log.error("ssl握手异常");
                    log.error(exception.getMessage());
                    return false;
                }
                status.setStatus("GREEN");
                return false;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .setRetryHandler(retry)
                .build();

        return httpClient ;

    }

}
