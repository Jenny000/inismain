package ie.my353.inis.utils;


import ie.my353.inis.entity.InisHomePageImpotNotice;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@ConfigurationProperties(prefix = "home-page-notice")
@Slf4j

public class JsoupInisHomePageImportNotice {

    @Autowired
    private InisHomePageImpotNotice homePageImportNotice;
    @Value("${home-page-notice}")
    private String noticeUrl;


    public InisHomePageImpotNotice getHomePageImportNotice(){
        try {
            Document imptNotice = Jsoup.connect(noticeUrl).get();
            Elements importantNotice = imptNotice.getElementsByClass("top-div");
            if(null == importantNotice){
                log.error("No important notice!!");
                return null;
            }else{

                String imNotice = String.valueOf(importantNotice);
                homePageImportNotice.setImportantNotice(imNotice);
                homePageImportNotice.setCreateDate(new Date());
                System.out.println(imNotice);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        return new InisHomePageImpotNotice(homePageImportNotice.getImportantNotice(),homePageImportNotice.getCreateDate());



    }

}
