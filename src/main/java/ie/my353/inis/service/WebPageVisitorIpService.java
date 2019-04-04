package ie.my353.inis.service;



import ie.my353.inis.entity.VisitorIp;
import ie.my353.inis.repository.WebPageVisitorIPRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WebPageVisitorIpService {

    @Autowired
    private WebPageVisitorIPRepositoryImpl webPageVisitorIP;
    @Autowired
    private VisitorIp visitorIp;

    public void getVisitorIp(){
        webPageVisitorIP.saveIp(visitorIp);
    }
}
