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
import java.util.Date;

/**
 * @author Administrator
 * date 2019/3/25 0025.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotsEntity {


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
    private String serverId;


}


