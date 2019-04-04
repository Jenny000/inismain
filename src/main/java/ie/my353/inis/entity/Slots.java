package ie.my353.inis.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Embeddable
@Data
@AllArgsConstructor

public class Slots implements Serializable {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    @Column(name = "slot_id")
    private String slotId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    @Transient
    private String slotUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    @Transient
    private String k;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    @Transient
    private String p;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    @Column(name = "create_time")
    private Date createTime;
    //private LocalDateTime createTime;

    @Column(name = "slot_cat")
    private String slotCat;
    @Column(name = "slot_type")
    private String slotType;
    @Column(name = "slot_date")
    private String slotDate;
    @Column(name = "slot_time")
    private String slotTime;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
    @JSONField(serialize = false)//fastjson
    private String serverId = "353main";



    public Slots() {

    }

    public Slots(String slotCat, String slotType, String slotDate) {
        this.slotCat = slotCat;
        this.slotType = slotType;
        this.slotDate = slotDate;
    }

    public String getServerId() {
        return serverId;
    }


    public Slots(Date createTime, String slotDate, String slotCat, String slotType, String slotTime, String slotId, String serverId) {
        this.createTime = createTime;
        this.slotCat = slotCat;
        this.slotType = slotType;
        this.slotDate = slotDate;
        this.slotTime = slotTime;
        this.slotId = slotId;
        this.serverId = serverId;
    }

    public Slots(String slotCat, String slotType, String slotDate, String k, String p) {
        this.slotCat = slotCat;
        this.slotType = slotType;
        this.slotDate = slotDate;
        this.k = k;
        this.p = p;
    }

    public Slots(String slotCat, String slotType, Date createTime, String slotUrl) {
        this.slotCat = slotCat;
        this.slotType = slotType;
        this.createTime = createTime;
        this.slotUrl = slotUrl;

    }

    @Override
    public String toString() {
        return "Slots{" +
                "slotId='" + slotId + '\'' +
                ", slotUrl='" + slotUrl + '\'' +
                ", k='" + k + '\'' +
                ", p='" + p + '\'' +
                ", createTime=" + createTime +
                ", slotCat='" + slotCat + '\'' +
                ", slotType='" + slotType + '\'' +
                ", slotDate='" + slotDate + '\'' +
                ", slotTime='" + slotTime + '\'' +
                ", serverId='" + serverId + '\'' +
                '}';
    }
}

