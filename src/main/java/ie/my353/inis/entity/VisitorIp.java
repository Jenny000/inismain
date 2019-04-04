package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Component
@Table(name = "visitor_ip")
@Data
public class VisitorIp {

    //private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private String create_date;
    private String create_time;
    private int count;
    private Date lastAccessTime;

    public VisitorIp(String ip, String create_date, String create_time, int count) {
        this.ip = ip;
        this.create_date = create_date;
        this.create_time = create_time;
        this.count = count;
    }

    public VisitorIp() {

    }

    public VisitorIp(String ip, String create_date, String create_time, int count, Date lastAccessTime) {
        this.ip = ip;
        this.create_date = create_date;
        this.create_time = create_time;
        this.count = count;
        this.lastAccessTime = lastAccessTime;
    }

    public String getCreate_date() {


        return create_date;
    }

    public void setCreate_date(String create_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        create_date = sdf.format(new Date());
        this.create_date = create_date;
    }

    public String getCreate_time() {
        return create_time;
    }

    public VisitorIp(String ip, int count) {
        this.ip = ip;
        this.count = count;
    }

    public void setCreate_time(String create_time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        create_time = sdf.format(new Date());
        this.create_time = create_time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "VisitorIp{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", create_date='" + create_date + '\'' +
                ", create_time='" + create_time + '\'' +
                ", count=" + count +
                '}';
    }
}
