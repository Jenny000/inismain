package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * @author Administrator
 * date 2019/3/23 0023.
 */


@Data
@Entity
@Component
@Table(name = "slot_list_for_all_renew")
public class SlotListForAllRenew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="slot_for_all_renew")
    private List<SlotForAllRenew> slotsList;






    public SlotListForAllRenew() {
    }

    public SlotListForAllRenew(List<SlotForAllRenew> slotsList) {
        this.slotsList = slotsList;
    }
}
