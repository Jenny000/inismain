package ie.my353.inis.utils;

import ie.my353.inis.Aop.AnnotationInterfaces.ProcessTimeDuration;
import ie.my353.inis.entity.*;
import ie.my353.inis.rabbitmq.SlotCatAndTypeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
@ConfigurationProperties(prefix = "slot-url")

public class GetAvailSlotsDateAndTime {

    @Autowired
    private HttpClientGet httpClientGet;
    @Autowired
    private KAndP kAndP;


    @Value("${slot-url.slotUrl}")
    private String urlOfSlot;


    public List<Slots> getAvailSlotsDateAndTime(Slots s) throws JSONException {


        List<Slots> slotList = new ArrayList<>();
        JSONObject slotObj;


            String dateUrl = urlOfSlot + s.getSlotDate() + "&cat=" + s.getSlotCat() + "&sbcat=All&typ="
                    + s.getSlotType() + "&k=" + kAndP.getK() + "&p=" + kAndP.getP() + "&_="
                    + (new Date().getTime());


            slotObj = new JSONObject(httpClientGet.responseEntity(dateUrl));
            if (!slotObj.toString().contains("empty")
                    && !slotObj.toString().contains("TRUE")
                    && !slotObj.toString().contains("[]")
                    && ! slotObj.toString().contains("error")) {
                log.error("date and time  "   +slotObj.toString());

                //List<Slots> slotsListForAll = new ArrayList<>();
                for (int j = 0; j < slotObj.getJSONArray("slots").length(); j++) {
                    //System.out.println(slotObj.toString());
                    JSONObject slotDetails = slotObj.getJSONArray("slots").getJSONObject(j);
                    s.setSlotTime(slotDetails.getString("time"));
                    s.setSlotId(slotDetails.getString("id"));
                    //((Slots) obj).setCreateTime(new Date());
                    s.setServerId("353main");
                    slotList.add(new Slots(new Date(), s.getSlotDate(), s.getSlotCat(), s.getSlotType(), s.getSlotTime(), s.getSlotId(), s.getServerId()));
                    log.info("slots={}", s.toString());
                }

            }



        return slotList;
    }
}

/**
    //@ProcessTimeDuration
    public List<Slots> getAvailSlotsDateAndTime(Slots s ){


        List<Slots> slotList = new ArrayList<>();




        String dateUrl = urlOfSlot + s.getSlotDate() + "&cat=" + s.getSlotCat()+ "&sbcat=All&typ="
                + s.getSlotType()+ "&k=" + s.getK() + "&p=" + s.getP() + "&_="
                + (new Date().getTime());


        try {

            JSONObject slotObj = new JSONObject(httpClientGet.responseEntity(dateUrl));

            if (slotObj.toString().contains("empty") && slotObj.toString().contains("TRUE")) {
                log.error(slotObj.toString());
            }else if(slotObj.toString().contains("[]") || slotObj.toString().contains("empty")) {
                log.info(s.getSlotCat() + "      " + s.getSlotType() + "  no slots!!!!" +"  "+slotObj);
            } else {
                for (int j = 0; j < slotObj.getJSONArray("slots").length(); j++) {
                    //sender.sendSlotCatAndTypeToQueue(s.getSlotCat()+s.getSlotType());
                    JSONObject slotDetails = slotObj.getJSONArray("slots").getJSONObject(j);
                    s.setSlotTime(slotDetails.getString("time"));
                    s.setSlotId(slotDetails.getString("id"));
                    s.setCreateTime(new Date());
                    s.setServerId("353main");
                    slotList.add(new Slots(s.getCreateTime(),s.getSlotDate(),s.getSlotCat(),s.getSlotType(),s.getSlotTime(),s.getSlotId(),s.getServerId()));
                    log.info("slots={}",s.toString());
                }
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //得到的是一个cat一个type的slot list
        //System.out.println(s.getSlotDate() +"  getAvailSlotsDateAndTime size  " +slotList.size());
        return slotList;

    }




**/

