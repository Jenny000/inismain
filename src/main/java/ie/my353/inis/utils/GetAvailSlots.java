package ie.my353.inis.utils;



import com.alibaba.fastjson.JSONPObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.entity.*;
import ie.my353.inis.rabbitmq.SlotCatAndTypeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@ConfigurationProperties(prefix = "url-near-api")
public class GetAvailSlots {

    @Autowired
    private HttpClientGet httpClientGet;

    @Autowired
    private GetAvailSlotsDateAndTime availSlotsDateAndTime;
    @Autowired
    private SlotCatAndTypeSender sender;
    @Autowired
    private GetAvailSlotDateUrls dateUrls;
    @Autowired
    private SlotCatAndTypeMessageContent messageContent;
    @Autowired
    private KAndP kAndP;
    @Autowired
    private SlotForAllNew slotForAllNew;
    @Autowired
    private SlotForAllRenew slotForAllRenew;
    @Autowired
    private DateConverter dateConverter;

    @Autowired
    private SlotList slotList;


    @Value("${url-near-api.allNew}")
    private String urlAllNewNear;

    @Value("${url-near-api.allRenew}")
    private String urlAllRenewNear;


    private Set<String> fullCatAndType = new HashSet<>();



    public List<Slots> getAvailSlotForAllNew() {
        List<Slots> listFlorAllNew = new ArrayList<>();
        getList(slotForAllNew,listFlorAllNew,(urlAllNewNear + kAndP.getK() + "&p=" + kAndP.getP() + "&_=" + (new Date().getTime())));
        return listFlorAllNew;
    }

    public List<Slots> getAvailSlotForAllRenew() {

        List<Slots> listForAllRenew = new ArrayList<>();
        getList(slotForAllRenew,  listForAllRenew ,(urlAllRenewNear + kAndP.getK() + "&p=" + kAndP.getP() + "&_=" + (new Date().getTime())));
        return listForAllRenew;
    }

    public List<Slots> getAvailSlotForAll() {
        List<Slots> slotForAllTypeSlots = new ArrayList<>();
        dateUrls.getDateUrls().forEach(slots ->

                getList(slots, slotForAllTypeSlots,slots.getSlotUrl()));


        return slotForAllTypeSlots;
    }
/**

    public List<SlotForAllNew> getAvailSlotForAllNew() {
        List<SlotForAllNew> listFlorAllNew = new ArrayList<>();
        getList(slotForAllNew, listFlorAllNew, (urlAllNewNear + kAndP.getK() + "&p=" + kAndP.getP() + "&_=" + (new Date().getTime())));
        return listFlorAllNew;
    }


    public List<SlotForAllRenew> getAvailSlotForAllRenew() {
        List<SlotForAllRenew> listForAllRenew = new ArrayList<>();
        getList(slotForAllRenew, listForAllRenew, (urlAllRenewNear + kAndP.getK() + "&p=" + kAndP.getP() + "&_=" + (new Date().getTime())));

        return listForAllRenew;
    }


    public List<Slots> getAvailSlotForAll() {
        List<Slots> slotForAllTypeSlots = new ArrayList<>();
        dateUrls.getDateUrls().forEach(slots ->

                getList(slots, slotForAllTypeSlots, slots.getSlotUrl()));


        return slotForAllTypeSlots;
    }

**/
    public void sendCatAndTypeToQueue() {
        if (0 != fullCatAndType.size()) {
            sender.sendSlotCatAndTypeToQueue(messageContent.getCatAndType());
            fullCatAndType.clear();
        }
    }

    public JSONArray getDateArray(String dateUrl) {
        JSONArray datesArray = null;

        try {
            JSONObject datesObj = new JSONObject(httpClientGet.responseEntity(dateUrl));
            if (!datesObj.toString().contains("[]")
                    && !datesObj.toString().contains("empty")
                    && !datesObj.toString().contains("error")
                    && !datesObj.toString().contains("TRUE")) {
                log.error(datesObj.toString());
                datesArray = datesObj.getJSONArray("slots");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return datesArray;

    }



    public List<Slots> getList(Object obj, List<Slots> returnList,String dateUrl) {

        JSONArray datesArray = getDateArray(dateUrl);

        try{


            if(obj instanceof Slots) {
                if (null != datesArray) {
                    for (int i = 0; i < datesArray.length(); i++) {
                        //System.out.println("all  slots======");
                        ((Slots) obj).setSlotDate(datesArray.get(i).toString());
                        obj = new Slots(((Slots) obj).getSlotCat(), ((Slots) obj).getSlotType(), ((Slots) obj).getSlotDate());
                        //sl = availSlotsDateAndTime.getAvailSlotsDateAndTime(obj);
                        returnList.addAll(availSlotsDateAndTime.getAvailSlotsDateAndTime((Slots) obj));
                        if (0 != returnList.size()) {
                            returnList.forEach(
                                    slot -> fullCatAndType.add(slot.getSlotCat() + " " + slot.getSlotType()));
                        }
                        messageContent.setCatAndType(fullCatAndType);
                        sendCatAndTypeToQueue();

                    }
                }
            }if(obj instanceof SlotForAllRenew) {
                log.info("all  renew======");
                if (null != datesArray) {

                    for (int i = 0; i < datesArray.length(); i++) {
                        fullCatAndType.add("All" + " " + "Renewal");
                        messageContent.setCatAndType(fullCatAndType);
                        sendCatAndTypeToQueue();

                        JSONObject slotDetails = datesArray.getJSONObject(i);

                        ((SlotForAllRenew) obj).setSlotDate(dateConverter.nearApiDateConverter(slotDetails.getString("time")));
                        ((SlotForAllRenew) obj).setSlotTime(slotDetails.getString("time"));
                        ((SlotForAllRenew) obj).setSlotId(slotDetails.getString("id"));
                        ((SlotForAllRenew) obj).setServerId("353mainNearAPI");
                        ((SlotForAllRenew) obj).setSlotCat("All");
                        ((SlotForAllRenew) obj).setSlotType("Renewal");

                        returnList.add(new Slots(new Date(), ((SlotForAllRenew) obj).getSlotDate(), ((SlotForAllRenew) obj).getSlotCat(), ((SlotForAllRenew) obj).getSlotType(),
                                ((SlotForAllRenew) obj).getSlotTime(), ((SlotForAllRenew) obj).getSlotId(), ((SlotForAllRenew) obj).getServerId()));

                    }
                }
            }if(obj instanceof SlotForAllNew){
                log.info("all  new ===");
                if (null != datesArray) {

                    for (int i = 0; i < datesArray.length(); i++) {
                        fullCatAndType.add("All" + " " + "New");
                        messageContent.setCatAndType(fullCatAndType);
                        sendCatAndTypeToQueue();
                        JSONObject slotDetails = datesArray.getJSONObject(i);
                        ((SlotForAllNew) obj).setSlotDate(dateConverter.nearApiDateConverter(slotDetails.getString("time")));
                        ((SlotForAllNew) obj).setSlotTime(slotDetails.getString("time"));
                        ((SlotForAllNew) obj).setSlotId(slotDetails.getString("id"));
                        ((SlotForAllNew) obj).setServerId("353mainNearAPI");
                        ((SlotForAllNew) obj).setSlotCat("All");
                        ((SlotForAllNew) obj).setSlotType("New");

                        returnList.add(new Slots(new Date(), ((SlotForAllNew) obj).getSlotDate(), ((SlotForAllNew) obj).getSlotCat(), ((SlotForAllNew) obj).getSlotType(),
                                ((SlotForAllNew) obj).getSlotTime(), ((SlotForAllNew) obj).getSlotId(), ((SlotForAllNew) obj).getServerId()));

                    }
                }
            }



        }catch (JSONException e) {
            e.printStackTrace();
            }
        //slotList.setSlotsList(returnList);


        return returnList;

    }

}




/**


    public List<Slots> getList(Object obj, List<Slots> returnList, String dateUrl) {
        //List<?> sl;
        JSONArray datesArray = getDateArray(dateUrl);
        System.out.println(datesArray+"=======================================");
        try {
            if (obj instanceof Slots) {
                if (null != datesArray) {
                    for (int i = 0; i < datesArray.length(); i++) {
                        try {
                            System.out.println("all   slots=========");

                            ((Slots) obj).setSlotDate(datesArray.get(i).toString());
                            obj = new Slots(((Slots) obj).getSlotCat(), ((Slots) obj).getSlotType(), ((Slots) obj).getSlotDate());
                            //sl = availSlotsDateAndTime.getAvailSlotsDateAndTime(obj);
                           // returnList.addAll(sl);
                            returnList.addAll(availSlotsDateAndTime.getAvailSlotsDateAndTime((Slots) obj));
                            if (0 != returnList.size()) {
                                returnList.forEach(
                                        slot -> fullCatAndType.add(slot.getSlotCat() + " " + slot.getSlotType()));
                            }
                            messageContent.setCatAndType(fullCatAndType);
                            sendCatAndTypeToQueue();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    fullCatAndType.addAll(fullCatAndType);
                    messageContent.setCatAndType(fullCatAndType);
                    sendCatAndTypeToQueue();
                }
            }
            if (obj instanceof SlotForAllRenew) {

                if (null != datesArray) {
                    System.out.println("all  renew=========");
                    for (int i = 0; i < datesArray.length(); i++) {
                        fullCatAndType.add("All" + " " + "Renewal");
                        messageContent.setCatAndType(fullCatAndType);
                        sendCatAndTypeToQueue();


                        JSONObject slotDetails = datesArray.getJSONObject(i);
                        //System.out.println(slotDetails);

                        ((SlotForAllRenew) obj).setSlotDate(dateConverter.nearApiDateConverter(slotDetails.getString("time")));
                        ((SlotForAllRenew) obj).setSlotTime(slotDetails.getString("time"));
                        ((SlotForAllRenew) obj).setSlotId(slotDetails.getString("id"));
                        //((SlotForAllRenew) obj).setCreateTime(new Date());
                        ((SlotForAllRenew) obj).setServerId("353main");
                        ((SlotForAllRenew) obj).setSlotCat("All");
                        ((SlotForAllRenew) obj).setSlotType("Renewal");


                        returnList.add(new Slots(new Date(),((SlotForAllRenew) obj).getSlotDate(),((SlotForAllRenew) obj).getSlotCat(),((SlotForAllRenew) obj).getSlotType(),
                        ((SlotForAllRenew) obj).getSlotTime(),((SlotForAllRenew) obj).getSlotId(),((SlotForAllRenew) obj).getServerId()));
                        //returnList.add(new SlotForAllRenew(((SlotForAllRenew) obj).getCreateTime(), ((SlotForAllRenew) obj).getSlotDate(),
                                //((SlotForAllRenew) obj).getSlotCat(), ((SlotForAllRenew) obj).getSlotType(), ((SlotForAllRenew) obj).getSlotTime(), ((SlotForAllRenew) obj).getSlotId(), ((SlotForAllRenew) obj).getServerId()));

                    }
                }
            }
            if (obj instanceof SlotForAllNew) {
                if (null != datesArray) {
                    for (int i = 0; i < datesArray.length(); i++) {

                        System.out.println("all  new ==============");
                        fullCatAndType.add("All" + " " + "New");
                        messageContent.setCatAndType(fullCatAndType);
                        sendCatAndTypeToQueue();
                        //System.out.println("slots for all new");

                        JSONObject slotDetails = datesArray.getJSONObject(i);
                        //System.out.println(slotDetails);


                        ((SlotForAllNew) obj).setSlotDate(dateConverter.nearApiDateConverter(slotDetails.getString("time")));
                        ((SlotForAllNew) obj).setSlotTime(slotDetails.getString("time"));
                        ((SlotForAllNew) obj).setSlotId(slotDetails.getString("id"));
                        //((SlotForAllNew) obj).setCreateTime(new Date());
                        ((SlotForAllNew) obj).setServerId("353main");
                        ((SlotForAllNew) obj).setSlotCat("All");
                        ((SlotForAllNew) obj).setSlotType("New");

                        //returnList.add(new Slots(((SlotForAllNew) obj).getCreateTime(),((SlotForAllNew) obj).getSlotDate(),
                        //((SlotForAllNew) obj).getSlotCat(),((SlotForAllNew) obj).getSlotType(),((SlotForAllNew) obj).getSlotTime(),((SlotForAllNew) obj).getServerId(),
                        //((SlotForAllNew) obj).getSlotId()));


                        returnList.add(new Slots(new Date(),((SlotForAllNew) obj).getSlotDate(),((SlotForAllNew) obj).getSlotCat(),((SlotForAllNew) obj).getSlotType(),
                        ((SlotForAllNew) obj).getSlotTime(),((SlotForAllNew) obj).getSlotId(),((SlotForAllNew) obj).getServerId()));


                        //returnList.add(new SlotForAllNew(((SlotForAllNew) obj).getCreateTime(), ((SlotForAllNew) obj).getSlotDate(),
                                //((SlotForAllNew) obj).getSlotCat(), ((SlotForAllNew) obj).getSlotType(), ((SlotForAllNew) obj).getSlotTime(), ((SlotForAllNew) obj).getSlotId(), ((SlotForAllNew) obj).getServerId()));


                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
        return returnList;
    }

}

 **/


















