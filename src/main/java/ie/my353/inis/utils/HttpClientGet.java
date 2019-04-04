package ie.my353.inis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class HttpClientGet {
    @Autowired
    private HttpClientUtils httpClientUtils;


    public String responseEntity(String url) {

        CloseableHttpClient httpClient = httpClientUtils.getHttpClient();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);

        // 获取客户端连接对象
        //CloseableHttpClient httpClient = httpClientConfig.getHttpClient();

        // 创建GET请求对象
        /**
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        //httpGet.setHeader("Referer", "https://burghquayregistrationoffice.inis.gov.ie/Website/AMSREG/AMSRegWeb.nsf/AppSelect?OpenForm");
        httpGet.setHeader("Referer","https://burghquayregistrationoffice.inis.gov.ie/Website/AMSREG/AMSRegWeb.nsf/AppSelect?OpenForm");
        httpGet.setHeader("Host", "burghquayregistrationoffice.inis.gov.ie");
        httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
         **/

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        httpGet.setHeader("Referer","https://burghquayregistrationoffice.inis.gov.ie/Website/AMSREG/AMSRegWeb.nsf/AppSelect?OpenForm");
        httpGet.setHeader("Host","burghquayregistrationoffice.inis.gov.ie");
        httpGet.setHeader("X-Requested-With","MLHttpRequest");


        CloseableHttpResponse response = null;
        String responseJsonString = null;
        try{

            response = httpClient.execute(httpGet);

            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 获取响应信息
            responseJsonString = EntityUtils.toString(entity,"UTF-8");

        } catch (ClientProtocolException e) {
            //System.err.println("协议错误");
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (IOException e) {
            //System.err.println("IO错误");
            e.printStackTrace();
            log.error(e.getMessage());
        }catch (ParseException e){
            //System.err.println("解析错误");
            e.printStackTrace();
            log.error(e.getMessage());
        }finally {
            if(null != response){
                try{
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }

        }
        return  responseJsonString;
    }






}

