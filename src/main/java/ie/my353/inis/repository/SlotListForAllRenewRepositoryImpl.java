package ie.my353.inis.repository;

import ie.my353.inis.entity.SlotListForAllNew;
import ie.my353.inis.entity.SlotListForAllRenew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/3/23 0023.
 */
@Component
public class SlotListForAllRenewRepositoryImpl {
    @Autowired
    private SlotListForAllRenewRepository slotListForAllRenewRepository;

    public SlotListForAllRenew saveListForAllRenew(SlotListForAllRenew slotListForAllRenew){
        return slotListForAllRenewRepository.save(slotListForAllRenew);
    }

    public SlotListForAllRenew findByLastId(){
        return slotListForAllRenewRepository.findByLastId();

    }
}
