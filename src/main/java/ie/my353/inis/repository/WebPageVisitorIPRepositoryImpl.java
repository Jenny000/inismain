package ie.my353.inis.repository;

import ie.my353.inis.entity.VisitorIp;
import ie.my353.inis.utils.VisitorIpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class WebPageVisitorIPRepositoryImpl {

    @Autowired
    private VisitorIp visitorIp;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VisitorIpUtils visitorIpUtils;
    @Autowired
    private WebPageVisitorIPRepository visitorIPRepository;



    public VisitorIp saveIp(VisitorIp vIp) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = dateFormat.format(new Date());
        String ip = visitorIpUtils.getVisitorIpAddr(request);
        Long id = findByCreate_date(todayDate,ip);
        if(null != ip){
            if(null != id){
                //int count = visitorIp.getCount();
                //log.info("count   " +count);
                visitorIPRepository.update(new Date(), id);
                //log.info("ip count update successfully");
            }else {
                visitorIPRepository.save(new VisitorIp(visitorIp.getIp(), visitorIp.getCreate_date(), visitorIp.getCreate_time(), visitorIp.getCount()));
                //log.info("ip saved successfully");

            }

        }

        return null;
    }




    public Long findByCreate_date(String date, String ip) {
        return visitorIPRepository.findByCreate_date(date,ip);
    }


}
