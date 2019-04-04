package ie.my353.inis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import ie.my353.inis.QuatzScheduleTask.SchedulerAllJob;
import ie.my353.inis.Aop.AnnotationInterfaces.ProcessTimeDuration;
import ie.my353.inis.entity.*;
import ie.my353.inis.rabbitmq.SlotCatAndTypeSender;
import ie.my353.inis.repository.SlotListForAllNewRepositoryImpl;
import ie.my353.inis.repository.SlotListForAllRenewRepositoryImpl;
import ie.my353.inis.repository.SlotsRepositoryImpl;
import ie.my353.inis.utils.GetAvailSlots;

import ie.my353.inis.utils.GetKAndP;
import ie.my353.inis.utils.HttpClientGet;
import ie.my353.inis.utils.IPUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
@Service
public class SlotService {

    @Autowired
    private GetAvailSlots availSlots;


    @Autowired
    private SlotsRepositoryImpl slotsRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WebPageVisitorIpService ipService;


    @Autowired
    private GetKAndP kAndP;

    @Autowired
    private KAndP kp;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SlotCatAndTypeMessageContent messageContent;

    @Autowired
    private SlotCatAndTypeSender sender;

    @Autowired
    private SlotList slotList;

    @Autowired
    private HttpServletRequest servletRequest;
    @Autowired
    private IPUtils ipUtils;


    List<Slots> listForAll = new ArrayList<>();


    AtomicInteger count = new AtomicInteger(0);

    public void chromeAppValidator(String body) {

        Map map = null;
        try {
            map = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String messageType = map.get("messageType").toString();

        final String messageType = Optional.ofNullable(map.get("messageType")).orElse("no").toString();


        System.out.println(messageType);
        switch (messageType) {
            case "kp": {
                ClientKpEntity clientKpEntity = objectMapper.convertValue(map, ClientKpEntity.class);
                if (null != clientKpEntity.getK() || null != clientKpEntity.getP()) {
                    setKPToRedis(clientKpEntity.getK(), clientKpEntity.getP());

                }
                break;

            }
            case "ct": {

                List<Slots> sl = new ArrayList<>();
                ClientSlotEntity clientSlotEntity = objectMapper.convertValue(map, ClientSlotEntity.class);
                if (null != clientSlotEntity) {
                    Set<String> fullCatAndType = new HashSet<>();
                    fullCatAndType.add(clientSlotEntity.getCat() + " " + clientSlotEntity.getTyp());
                    messageContent.setCatAndType(fullCatAndType);
                    sender.sendSlotCatAndTypeToQueue(messageContent.getCatAndType());
                    //log.info(clientSlotEntity.toString());

                }

                Slots s = new Slots();
                if (null != clientSlotEntity.getData()) {
                    for (int i = 0; i < clientSlotEntity.getData().length; i++) {
                        s.setSlotTime(clientSlotEntity.getData()[i].getTime());
                        s.setSlotId(clientSlotEntity.getData()[i].getId());
                        s.setCreateTime(new Date());
                        //s.setServerId("webClient");
                        s.setServerId("ip " + ipUtils.getIpAddr(servletRequest));

                        sl.add(new Slots(new Date(), clientSlotEntity.getDate(), clientSlotEntity.getCat(), clientSlotEntity.getTyp(),
                                s.getSlotTime(), s.getSlotId(), s.getServerId()));
                        System.out.println(s.getServerId());

                    }
                }
                slotList.setSlotsList(sl);
                redisTemplate.opsForValue().set("slotsKey", slotList.getSlotsList());
                slotsRepository.saveSlots(new SlotList(slotList.getSlotsList()));
                break;

            }
            case "no": {
                break;
            }
        }


    }

    public void setKPToRedis(String k, String p) {
        Map<String, String> kp = new HashMap<>();
        kp.put("k", k);
        kp.put("p", p);
        redisTemplate.opsForHash().putAll("kp1", kp);


        redisTemplate.expire("kp1", 25, TimeUnit.MINUTES);
//            Map kp2 = reactiveRedisTemplate.opsForValue().get("kp2").block();
//            System.out.println(kp2);


    }

    public void setKPRedis() {
        log.info("read kp from redis....");
        Long expireTime;
        if (redisTemplate.hasKey("kp1")) {
            kp.setK(redisTemplate.opsForHash().get("kp1", "k").toString());
            kp.setP(redisTemplate.opsForHash().get("kp1", "p").toString());
            expireTime = redisTemplate.getExpire("kp1", TimeUnit.MINUTES);
            log.info("kp expire time  " + expireTime + " minutes");
            kp.setKpCount(expireTime.intValue());
        } else {
            kAndP.getKandP();

        }

    }


    public void getKPCount() {

        if (count.get() == 0) {

            setKPRedis();


        }
        if (count.get() >= kp.getKpCount() - 2) {
            setKPRedis();
            count.set(0);
        }

        count.addAndGet(1);

    }


    //Long expireTime;


    //@Scheduled(fixedRate = 60 * 1000)
    //8-14点一分钟一次
    @Scheduled(cron = "0 0/1 10 * * ?")
    public void saveSlotsMultiThread() {

        log.info(" four threads start................");

        getKPCount();

        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {

            service.submit(() -> {

                //saveSlotsForAllRenew();
                listForAll.addAll(saveSlotsForAllRenew());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //saveSlotsForAllNew();
                listForAll.addAll(saveSlotsForAllNew());
                redisTemplate.opsForValue().set("slotsKey",listForAll);
                log.info("list size for 4 threads  " +listForAll.size());
                listForAll.clear();
            });
            try {
                Thread.sleep(1000 * 15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();

    }


    //@Scheduled(cron = "0 31-32 14 * * ? ") 每天14点31 - 14点32 每一分钟一次
    @Scheduled(cron = "0 * 11-23 * * ?") //14-0点一分钟一次
    @Scheduled(cron = "0 * 0-9 * * ?")  //0-9点一分钟一次
    public void saveSlotsSingleThread() {
        log.info("single thread start................");
        getKPCount();
        saveSlotsForAllUrls();
    }

    public void saveSlotsForAllUrls() {

        listForAll = availSlots.getAvailSlotForAll();
        redisTemplate.opsForValue().set("slotsKey", listForAll);
        slotsRepository.saveSlots(new SlotList(listForAll));

    }

    public List<Slots> saveSlotsForAllRenew() {
        List<Slots> listForAllRenew;

        listForAllRenew = availSlots.getAvailSlotForAllRenew();
        log.info(" listForAllRenew size  " + listForAllRenew.size());
        //redisTemplate.opsForValue().set("slotsKey", listForAllRenew);
        slotsRepository.saveSlots(new SlotList(listForAllRenew));
        return listForAllRenew;


    }

    public List<Slots> saveSlotsForAllNew() {
        List<Slots> listForAllNew;


        listForAllNew = availSlots.getAvailSlotForAllNew();
        log.info(" listForAllNew size  " + listForAllNew.size());
        //redisTemplate.opsForValue().set("slotsKey", listForAllNew);
        slotsRepository.saveSlots(new SlotList(listForAllNew));
        return listForAllNew;


    }


    public String getSlots() {
        //获取ip
        ipService.getVisitorIp();
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {


            if (redisTemplate.hasKey("slotsKey")) {

                json = objectMapper.writeValueAsString(redisTemplate.opsForValue().get("slotsKey"));
                log.info(" redis  json " + json);
                return json;

            } else {
                final SlotList byLastId = slotsRepository.findByLastId();
                redisTemplate.opsForValue().set("slotsKey",byLastId.getSlotsList() );
                log.info("read from mysql!!");

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return null;


    }
}




