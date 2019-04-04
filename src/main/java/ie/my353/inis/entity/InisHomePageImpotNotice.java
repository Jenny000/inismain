package ie.my353.inis.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Component
public class InisHomePageImpotNotice {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private int id;
    @Column(name = "important_notice")
    private String importantNotice;
    @Column(name = "create_date")
    private Date createDate;

    public InisHomePageImpotNotice() {

    }

    public InisHomePageImpotNotice(String importantNotice, Date createDate) {
        this.importantNotice = importantNotice;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImportantNotice() {
        return importantNotice;
    }

    public void setImportantNotice(String importantNotice) {
        this.importantNotice = importantNotice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "InisHomePageImpotNotice{" +
                "id=" + id +
                ", importantNotice='" + importantNotice + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
