package ie.my353.inis.repository;

import ie.my353.inis.entity.RequestCountEntityMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * date 2019/3/4 0004.
 */
@Component
public class RequestCountRepositoryImpl {
    @Autowired
    private RequestCountRepository requestCountRepository;

    public void save (RequestCountEntityMQ entityMQ){
        requestCountRepository.save(entityMQ);
    }
}
