package ie.my353.inis.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.utils.JsoupGetReentryVisaInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReentryVisaInfoService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JsoupGetReentryVisaInfo reentryVisaInfo;

    @Scheduled(cron = "0 30 09 * * ?" )
    public void saveReentryVisaInfo(){
        redisTemplate.expire("reentry",24, TimeUnit.HOURS);

        redisTemplate.opsForValue().set("reentry", reentryVisaInfo.getReentryVisa().getReentryNotice());

    }

    public String getReentryVisaInfo() {

        String visaInfo;

        if(redisTemplate.hasKey("reentry")) {
                visaInfo = (String) redisTemplate.opsForValue().get("reentry");
                log.info("reentry visa read from redis!!");
                return visaInfo;
        }else {
            redisTemplate.opsForValue().set("reentry", reentryVisaInfo.getReentryVisa().getReentryNotice());
        }

        return null;
    }
}
