package ie.my353.inis.utils;

import ie.my353.inis.Aop.AnnotationInterfaces.ProcessTimeDuration;
import ie.my353.inis.entity.KAndP;
import ie.my353.inis.entity.Slots;
import ie.my353.inis.service.SlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/2/26 0026.
 */
@Component
@Slf4j
public class GetKAndP {



    @Autowired
    private Slots slots;

    @Autowired
    private JsoupGetKAndP getKAndP;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KAndP kAndP;

    @Autowired
    private SlotService slotService;

    //@ProcessTimeDuration
    //@Scheduled(cron = "0 0/14 * * * ? " )
    public void getKandP(){

            String[] KP = getKAndP.getDAndP();
            log.info("执行 get k p 方法");
            slotService.setKPToRedis(KP[0],KP[1]);

            kAndP.setK(KP[0]);
            kAndP.setP(KP[1]);



/**
        JSONObject datesForKandP = null;
        try {
            datesForKandP = new JSONObject(httpClientGet.responseEntity(dateUrls.getDateUrls().get(0).getSlotUrl()));
        } catch (JSONException e) {
            e.printStackTrace();
            //log.error(e.getMessage());
        }
        if(datesForKandP.toString().contains("error")){
            //log.error("error message");

            //System.out.println("get k p ");
        }


**/
    }
}
