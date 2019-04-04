package ie.my353.inis.utils;

import ie.my353.inis.entity.WebsiteStatus;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Slf4j
@ConfigurationProperties(prefix = "k-and-p")
public class JsoupGetKAndP {
    @Value("${k-and-p}")
    private String url;

    @Autowired
    private WebsiteStatus status;

    public final String[] getDAndP(){

            String[] KV ={};
        try{
               Document document = Jsoup.connect(url).timeout(5000*2*3).validateTLSCertificates(false).get();

                Element elementK = document.getElementById("k");
                String valueK = elementK.attr("value");
                Element elementP = document.getElementById("p");
                String valueP = elementP.attr("value");
                KV = new String[]{valueK, valueP};


            } catch (IOException e) {
                //log.error("Jsoup k&v IO错误");
                status.setStatus("RED");
                e.printStackTrace();
                log.error(e.getMessage());
                getDAndP();
            }
            status.setStatus("GREEN");
            return KV;
    }


}
