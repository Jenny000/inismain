package ie.my353.inis.service;


import ie.my353.inis.utils.JsoupInisHomePageImportNotice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class InisHomePageImpotNoticeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JsoupInisHomePageImportNotice homePageImportNotice;

    @Scheduled(cron = "0 40 09 * * ?" )
    public void saveHomePageImportNotice(){
        redisTemplate.expire("homePageNotice",24, TimeUnit.HOURS);

        redisTemplate.opsForValue().set("homePageNotice", homePageImportNotice.getHomePageImportNotice());

    }

    public String getHomePageImportNotice(){

        Object imptInfo;

        if(redisTemplate.hasKey("homePageNotice")) {
            imptInfo =  redisTemplate.opsForValue().get("homePageNotice");
            log.info("home page notice read from redis!!");
            return imptInfo.toString();
        }else {
            redisTemplate.opsForValue().set("homePageNotice", homePageImportNotice.getHomePageImportNotice());
        }
        return null;
    }
}

