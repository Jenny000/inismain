package ie.my353.inis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.entity.WebsiteStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebsiteStatusService {
    @Autowired
    private WebsiteStatus status;
    public String getStatus(){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;

        try {
            json = mapper.writeValueAsString(status.getStatus());
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return json;
    }
}
