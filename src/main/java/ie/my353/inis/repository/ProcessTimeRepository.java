package ie.my353.inis.repository;

import ie.my353.inis.entity.ProcessTimeEntityMQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * date 2019/3/4 0004.
 */
@Repository
public interface ProcessTimeRepository extends JpaRepository<ProcessTimeEntityMQ,Long> {
    ProcessTimeEntityMQ save (ProcessTimeEntityMQ processTimeEntityMQ);

}
