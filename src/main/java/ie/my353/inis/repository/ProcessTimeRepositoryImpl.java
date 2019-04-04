package ie.my353.inis.repository;

import ie.my353.inis.entity.ProcessTimeEntityMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/3/4 0004.
 */
@Component
public class ProcessTimeRepositoryImpl {
    @Autowired
    private ProcessTimeRepository processTimeRepository;

    public void saveProcessTime(ProcessTimeEntityMQ processTimeEntityMQ){
        processTimeRepository.save(processTimeEntityMQ);
    }
}
