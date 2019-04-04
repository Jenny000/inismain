package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class WebsiteStatus {
    private String status;
    private Date createDate;

    public WebsiteStatus(String status, Date createDate) {
        this.status = status;
        this.createDate = createDate;
    }

    public WebsiteStatus() {
    }
}
