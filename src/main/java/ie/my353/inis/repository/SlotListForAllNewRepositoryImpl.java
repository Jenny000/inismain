package ie.my353.inis.repository;

import ie.my353.inis.entity.SlotList;
import ie.my353.inis.entity.SlotListForAllNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/3/23 0023.
 */
@Component
public class SlotListForAllNewRepositoryImpl {
    @Autowired
    private SlotListForAllNewRepository slotListForAllNewRepository;

    public SlotListForAllNew saveListForAllNew(SlotListForAllNew slotListForAllNew){
        return slotListForAllNewRepository.save(slotListForAllNew);
    }

    public SlotListForAllNew findByLastId(){
        return slotListForAllNewRepository.findByLastId();

    }
}
