package ie.my353.inis.utils;


import ie.my353.inis.entity.ReentryVisaInfo;
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
@ConfigurationProperties(prefix = "reentry-visa")
@Slf4j
public class JsoupGetReentryVisaInfo {
    @Autowired
    private ReentryVisaInfo reentryVisaInfo;
    @Value("${reentry-visa}")
    private String reentryUrl;

    public ReentryVisaInfo getReentryVisa() {

        try {
            Document reentryNotice = Jsoup.connect(reentryUrl).get();
            Elements reEntryNotice = reentryNotice.getElementsByClass("roadmap1");
            String entryNotice = String.valueOf(reEntryNotice);
            reentryVisaInfo.setReentryNotice(entryNotice);
            reentryVisaInfo.setCreatTime(new Date());
            //System.out.println(entryNotice);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return new ReentryVisaInfo(reentryVisaInfo.getReentryNotice(), reentryVisaInfo.getCreatTime());


    }
}