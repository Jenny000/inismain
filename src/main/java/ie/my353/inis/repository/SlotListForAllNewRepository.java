package ie.my353.inis.repository;
import ie.my353.inis.entity.SlotListForAllNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * date 2019/3/23 0023.
 */

@Repository
public interface SlotListForAllNewRepository extends JpaRepository<SlotListForAllNew,Long> {

    SlotListForAllNew save(SlotListForAllNew slotListForAllNew);

    @Query(value = "select id from slot_list_for_all_new where id = (select max(id) from slot_list_for_all_new)", nativeQuery = true)
    SlotListForAllNew findByLastId();

}
