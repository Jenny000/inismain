package ie.my353.inis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 * date 2019/3/26 0026.
 */
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class SlotsEntityList {
    private List<SlotsEntity> slotsList;
}
