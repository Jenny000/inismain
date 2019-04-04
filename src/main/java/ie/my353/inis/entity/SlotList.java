package ie.my353.inis.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Component
@Table(name = "slot_list")
public class SlotList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="slots")
    private List<Slots> slotsList;


    public SlotList() {
    }


    public SlotList(List<Slots> slotsList) {
        this.slotsList = slotsList;
    }



}
