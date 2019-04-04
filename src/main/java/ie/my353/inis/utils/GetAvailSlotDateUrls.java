package ie.my353.inis.utils;


import ie.my353.inis.Aop.AnnotationInterfaces.ProcessTimeDuration;
import ie.my353.inis.entity.KAndP;
import ie.my353.inis.entity.Slots;
import ie.my353.inis.entity.SlotsCatAndType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@ConfigurationProperties(prefix = "slot-date-url")
public class GetAvailSlotDateUrls {


    @Autowired
    private SlotsCatAndType catAndType;
    @Autowired
    private Slots slots;
    @Autowired
    private KAndP kAndP;


    @Value("${slot-date-url.dateUrl}")
    private String urlOfDate;


    public List<Slots> getDateUrls() {

        ArrayList<Slots> dateUrlList = new ArrayList<>();


        //get urls
        String[] slotsCat = catAndType.getCat();
        String[] slotsType = catAndType.getType();

        for (int i = 0; i < slotsCat.length; i++) {
            for (int j = 0; j < slotsType.length; j++) {

                String dateUrl = urlOfDate + slotsCat[i] + "&sbcat=All&typ=" + slotsType[j] + "&k=" + kAndP.getK()
                        + "&p=" + kAndP.getP() + "&_=" + (new Date().getTime());

                slots.setSlotCat(slotsCat[i]);
                slots.setSlotType(slotsType[j]);
                slots.setSlotUrl(dateUrl);

                dateUrlList.add(new Slots(slots.getSlotCat(), slots.getSlotType(), slots.getCreateTime(),slots.getSlotUrl()));


            }
        }

        return dateUrlList;

    }
}
