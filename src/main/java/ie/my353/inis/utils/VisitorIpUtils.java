package ie.my353.inis.utils;



import ie.my353.inis.entity.VisitorIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class VisitorIpUtils {
    @Autowired
    private VisitorIp visitorIp;
    @Autowired
    private IPUtils ipUtils;



    public String getVisitorIpAddr(HttpServletRequest request) {
        String ip = ipUtils.getIpAddr(request);

        // ipAddress = this.getRequest().getRemoteAddr();
        //System.out.println("ip:   " + ip);
        visitorIp.setIp(ip);
        visitorIp.setCreate_date(visitorIp.getCreate_date());
        visitorIp.setCreate_time(visitorIp.getCreate_time());


        //visitorIp.setCount(visitorIp.getCount());

        return ip;
    }
}
