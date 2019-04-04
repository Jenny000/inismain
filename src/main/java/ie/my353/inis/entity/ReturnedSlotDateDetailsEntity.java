package ie.my353.inis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 14/02/2019.
 * 每个可用日期的返回第二次返回的结果
 * {"time": "24 July 2018 - 11:00","id": "47C8DA5FA70F5188802582A3003181B6"}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReturnedSlotDateDetailsEntity {
    String time;
    String id;
}
