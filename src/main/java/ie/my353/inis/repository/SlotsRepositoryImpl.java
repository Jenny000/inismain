package ie.my353.inis.repository;


import ie.my353.inis.entity.SlotList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class SlotsRepositoryImpl  {
    @Autowired
    private SlotsRepository slotsRepository;




    public SlotList saveSlots(SlotList slotList) {
        return slotsRepository.save(slotList);
    }


    public SlotList findByLastId(){
        return slotsRepository.findByLastId();




    }


}
