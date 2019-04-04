package ie.my353.inis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.entity.SlotReminderMail;
import ie.my353.inis.repository.SlotReminderMailRepository;
import ie.my353.inis.repository.SlotReminderMailRepositoryImpl;
import ie.my353.inis.utils.DateConverter;
import ie.my353.inis.utils.IPUtils;
import ie.my353.inis.utils.MapWithExpiredTime;
import ie.my353.inis.utils.SendMail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * date 2019/3/2 0002.
 */

@Component
@Slf4j
@ConfigurationProperties(prefix = "active-reminder-email")
public class SlotReminderMailService {

    @Autowired
    private SlotReminderMailRepositoryImpl mailRepository;
    @Autowired
    private SlotReminderMailRepository slotReminderMailRepository;
    @Autowired
    private DateConverter converter;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private SendMail sendMail;
    @Autowired
    private IPUtils ipUtils;
    @Autowired
    private HttpServletRequest httpRequest;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate redisTemplate;




    @Value("${spring.mail.username}")
    private String from;
    @Value("${confirmation-mail-subject}")
    private String confirmationMailSubject;
    @Value("${active-reminder-email}")
    private String activeReminderEmail;
    @Value("${appointment-subject}")
    private String appointmentSubject;
    @Value("${reminder-email}")
    private String reminderEmail;



    private Long id;
    private String cat;
    private String type;
    private String appointmentDate;
    private int beforeDays;
    private String userIdInRedis;
    MapWithExpiredTime <String,Long> userIdMap = new MapWithExpiredTime<>();

    List <SlotReminderMail> receiverList = new ArrayList<>();
    List<SlotReminderMail> sl;
    List<SlotReminderMail> sll;


    public void addReminder (SlotReminderMail reminderMail)  {
        reminderMail.setCreateDate(reminderMail.getCreateDate());
        try {
            Date date = converter.stringToDate(reminderMail.getAppointmentDate());
            appointmentDate = converter.dateFormatConverter(date);
            beforeDays = reminderMail.getBeforeRemindDays();
            String reminderDate = converter.reminderDate(appointmentDate,beforeDays);
            reminderMail.setReminderDate(reminderDate );
            reminderMail.setIsActive("N");
            reminderMail.setCreateDate(new Date());
            reminderMail.setCat("All");
            reminderMail.setIsFinished("N");
            cat = reminderMail.getCat();
            type=reminderMail.getType();
            reminderMail.setUserIp(ipUtils.getIpAddr(httpRequest));

            if(mailRepository.getRegEmailCountAfterActive(reminderMail.getReceiverEmail()) <3){
                mailRepository.saveReminderMailinfo(reminderMail);
                id = reminderMail.getId();
                String[] to;
                to = new String[]{reminderMail.getReceiverEmail()};
                sendConfirmationEmail(to);
            }else {
                log.info(reminderMail.getReminderDate() + " Your email has registered 3 times！！");
            }


        } catch (ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public int checkMaxReg(String email){
        return mailRepository.getRegEmailCountAfterActive(email);
    }

    public void sendConfirmationEmail(String[] to){

        Context context = new Context();
        userIdInRedis = RandomStringUtils.randomAlphanumeric(20);
        userIdMap.put(userIdInRedis,id,60*1000*30);
        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
        jedisConnectionFactory.setDatabase(1);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);




        context.setVariable("mailKey", userIdInRedis);
        context.setVariable("cat",cat);
        context.setVariable("type",type);
        context.setVariable("appointDate", appointmentDate);
        context.setVariable("reminderDay",beforeDays);
        sendMail.sendMail(from,to,confirmationMailSubject,activeReminderEmail,context);
        log.info("confirmation email sent!");
    }


    public Long getUserId(String mailKey){
        System.out.println("map size"  + userIdMap.size());
            if(userIdMap.containsKey(mailKey)) {
                id = userIdMap.get(mailKey);
                String email = mailRepository.getReceiverEmailbyId(id);
                if (mailRepository.getRegEmailCountAfterActive(email) < 3) {
                    mailRepository.updateActiveCode(id);
                    userIdMap.remove(mailKey);
                    //System.out.println("id   " + id);
                    return id;
                } else {
                    log.error("The user has active 3 times ");
                }

            }else  {
                log.info("your active service is expired, please register again! ");
                return 0l;
            }
        return null;
    }

    @Scheduled(cron = "0 0 0,7 * * ?" )
    public void sendReminderEmail(){

    //获取maxdate 就是reminder的日子
        String reminderDate = (statisticsService.getMaxReleasedDateService()).toString();
        //String reminderDate = "25/04/2019";
        Context context = new Context();
        sl = mailRepository.getEmailListByReminderDate(reminderDate);

        if(0 != sl.size()){
            receiverList.addAll(sl);
            System.out.println("sl   size  " +receiverList.size());
            sl.clear();
        }

        sll = mailRepository.getEmailListBySentMailCountAndIsFinished();
        if(0 != sll.size()){
            receiverList.addAll(sll);
            System.out.println("sll size   " +sll.size());
            sl.clear();
        }

        List<SlotReminderMail> newSrmList = receiverList;

        System.out.println("receiver list   size  " +receiverList.size());


        if(0 != newSrmList.size()){
            String appointmentDate = newSrmList.get(0).getAppointmentDate();
            String[] to = new String[newSrmList.size()];
            for(int i=0; i<newSrmList.size(); i++){
                to[i] = newSrmList.get(i).getReceiverEmail();
            }
            System.out.println(newSrmList);
            context.setVariable("maxDate",reminderDate);
            context.setVariable("appointDate",appointmentDate);
            sendMail.sendMail(from, to,appointmentSubject,reminderEmail,context);


            for (SlotReminderMail srm : newSrmList) {
               Long id = srm.getId();
                //System.out.println("id   "+id);
                mailRepository.updateReminderMailSendInfo(id);
                //System.out.println("sent mail count  "+srm.getAlreadySendEmailCount());
                mailRepository.updateIsFinished();
                log.info("data updated!!!" );

            }
            newSrmList.clear();
            //System.out.println("receiverList.size()  "+ receiverList.size());

        }else {
            log.info("no reminder email need to send!! ");
        }

    }

    public String findAllByReceiverEmail(String email){
        List<SlotReminderMail> list = mailRepository.findAllByReceiverEmail(email);
        String listJson = null;
        try {
            listJson =objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listJson;
    }

    public void deleteFromReminderList(Long id){
        slotReminderMailRepository.deleteById(id);
    }









}
