package ie.my353.inis.controller;

import ie.my353.inis.service.InisHomePageImpotNoticeService;
import ie.my353.inis.utils.JsoupInisHomePageImportNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/inis")
public class InisHomePageImportNoticeController {
    @Autowired
    private InisHomePageImpotNoticeService noticeService;




    @RequestMapping("/getHomeNotice")
    public void getHomePageNotice(){
        noticeService.getHomePageImportNotice();
    }
}
