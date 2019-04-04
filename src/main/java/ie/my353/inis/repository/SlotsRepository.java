package ie.my353.inis.repository;



import ie.my353.inis.entity.SlotList;
import ie.my353.inis.entity.SlotListForAllNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlotsRepository extends JpaRepository<SlotList,Long> {
    SlotList save(SlotList list);
/**
    @Query(value = "select id from slot_list where id = (select max(id) from slot_list)", nativeQuery = true)
    SlotList findByLastId();
**/
    @Query(value = "select id from slot_list where id = (select max(id) from slot_list)", nativeQuery = true)
    SlotList findByLastId();









}
