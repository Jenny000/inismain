package ie.my353.inis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.my353.inis.entity.ClientKpEntity;
import ie.my353.inis.entity.WebsiteStatus;
import ie.my353.inis.service.SlotService;
import ie.my353.inis.service.WebsiteStatusService;
import ie.my353.inis.utils.GetAvailSlots;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
//@RequestMapping("/inis")
public class SlotsController {


    @Autowired
    private SlotService slotService;
    @Autowired
    private WebsiteStatusService statusService;

    //todo 转移到service中
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/getSlotsJson")
    public String getSlotsJson() {

        return slotService.getSlots();
    }

    @GetMapping(value = "/getStatus")
    public String getWebsiteStatus() {

        return statusService.getStatus();
    }


    @PostMapping(value = "/validator")
    public void getKP(@RequestBody String body,HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.getRemoteAddr());
        slotService.chromeAppValidator(body);

    }

}
