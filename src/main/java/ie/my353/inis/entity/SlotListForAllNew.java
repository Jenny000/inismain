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
@Table(name = "slot_list_for_all_new")
public class SlotListForAllNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="slot_for_all_new")
    private List<SlotForAllNew> slotsList;


    public SlotListForAllNew() {
    }

    public SlotListForAllNew(List<SlotForAllNew> slotsList) {
        this.slotsList = slotsList;
    }
}
