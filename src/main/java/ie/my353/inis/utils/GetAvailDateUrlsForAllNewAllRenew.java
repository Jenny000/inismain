package ie.my353.inis.utils;

import ie.my353.inis.entity.KAndP;
import ie.my353.inis.entity.Slots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * date 2019/3/19 0019.
 */
@Component
@ConfigurationProperties(prefix = "slot-date-url-for-all")
public class GetAvailDateUrlsForAllNewAllRenew {

    @Autowired
    private Slots slots;


    @Value("${slot-date-url-for-all.dateUrl}")
    private String urlOfDate;

    public  List<Slots> getDateUrls(){

        ArrayList<Slots> dateUrlList = new ArrayList<>();
        String[] type = new String[]{"New","Renewal"};

        for(int i=0;i< type.length; i++){
            String dateUrl = urlOfDate + "All" + "&sbcat=All&typ=" + type[i] + "&k=" + slots.getK()
                    + "&p=" + slots.getP() + "&_=" + (new Date().getTime());

            slots.setSlotType(type[i]);
            slots.setSlotCat("All");
            slots.setSlotUrl(dateUrl);
            dateUrlList.add(new Slots(slots.getSlotCat(), slots.getSlotType(), slots.getCreateTime(),slots.getSlotUrl()));

        }
        return dateUrlList;

    }
}
