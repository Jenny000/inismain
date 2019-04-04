package ie.my353.inis.repository;

import ie.my353.inis.entity.SlotListForAllNew;
import ie.my353.inis.entity.SlotListForAllRenew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * date 2019/3/23 0023.
 */
@Repository
public interface SlotListForAllRenewRepository extends JpaRepository<SlotListForAllRenew,Long> {

    SlotListForAllRenew save(SlotListForAllRenew slotListForAllRenew);

    @Query(value = "select id from slot_list_for_all_renew where id = (select max(id) from slot_list_for_all_renew)", nativeQuery = true)
    SlotListForAllRenew findByLastId();

}
