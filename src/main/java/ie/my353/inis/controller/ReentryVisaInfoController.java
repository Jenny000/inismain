package ie.my353.inis.controller;


import ie.my353.inis.service.ReentryVisaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/inis")
public class ReentryVisaInfoController {
    @Autowired
    private ReentryVisaInfoService reentryVisaInfoService;

    @RequestMapping("/getReentryNotice")
    public String getReentryInfo(){
      return reentryVisaInfoService.getReentryVisaInfo();

    }

}
