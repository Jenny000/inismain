package ie.my353.inis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * date 27/03/2019.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSlotEntity {
    String cat;
    String typ;
    String date;
    ReturnedSlotDateDetailsEntity[] data;


}
