package ie.my353.inis.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

//@Entity
@Component
//@Table(name = "reentry_visa_info")
public class ReentryVisaInfo {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reentry_notice")
    private String reentryNotice;
    @Column(name = "create_time")
    private Date creatTime;

    public ReentryVisaInfo() {
    }

    public ReentryVisaInfo(String reentryNotice) {
        this.reentryNotice = reentryNotice;
    }

    public ReentryVisaInfo(String reentryNotice, Date creatTime) {
        this.reentryNotice = reentryNotice;
        this.creatTime = creatTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReentryNotice() {
        return reentryNotice;
    }

    public void setReentryNotice(String reentryNotice) {
        this.reentryNotice = reentryNotice;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "ReentryVisaInfo{" +
                "id=" + id +
                ", reentryNotice='" + reentryNotice + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}
