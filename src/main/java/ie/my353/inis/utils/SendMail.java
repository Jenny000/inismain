package ie.my353.inis.utils;

import ie.my353.inis.entity.SlotReminderMail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Administrator
 * date 2019/3/3 0003.
 */

@Component
@Slf4j

public class SendMail {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine template;
    @Autowired
    SlotReminderMail reminderMail;



    public void sendMail(String from, String[] to, String subject,String emailType,Context context){

        //true表示需要创建一个multipart message
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setBcc(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            String content = template.process(emailType,context);
            helper.setText(content,true);
            mailSender.send(message);
            //log.info("email send");
        } catch (MessagingException e) {
            e.printStackTrace();
            reminderMail.setErrorMessage(e.getMessage());
            log.error("email send error ！", e.getMessage());
        }
    }
}
