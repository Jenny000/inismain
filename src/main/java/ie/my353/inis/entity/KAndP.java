package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/3/24 0024.
 */
@Component
@Data
public class KAndP {
    private String k;
    private String p;
    private int kpCount;

    public KAndP() {
    }
}
