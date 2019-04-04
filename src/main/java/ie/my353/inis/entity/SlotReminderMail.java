package ie.my353.inis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * date 2019/3/2 0002.
 */
@Entity
@Component
@Data
@Table(name = "slot_reminder_mail")
public class SlotReminderMail implements Serializable {
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    //@JSONField(serialize = false)//fastjson
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty(message = "email can not empty")
    @JSONField(name = "email")
    private String receiverEmail;

    @NotEmpty(message = "appointment date can not empty")
    private String appointmentDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String reminderDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private Date createDate;

    private int beforeRemindDays;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String isActive;

    private String cat;


    private String type;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String userIp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String errorMessage;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private int alreadySendEmailCount;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String isFinished;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private int registeredEmailCount;

    public SlotReminderMail() {
    }




}
