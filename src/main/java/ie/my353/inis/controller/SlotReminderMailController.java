package ie.my353.inis.controller;

import ie.my353.inis.entity.SlotReminderMail;
import ie.my353.inis.service.SlotReminderMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/**
 * @author Administrator
 * date 2019/3/2 0002.
 */

@RestController
@Slf4j
public class SlotReminderMailController {
    @Autowired
    private SlotReminderMailService reminderMailService;





    @RequestMapping(value = "/addReminder")
    //@CrossOrigin(origins = "*")
    public String addReminder (@Valid SlotReminderMail reminderMail, BindingResult bindingResult) {
        String result;
        if(bindingResult.hasErrors()){
            result= bindingResult.getFieldError().getDefaultMessage();
        }else {
             reminderMailService.addReminder(reminderMail);
             result="reminder info. saved successfully!";
        }
        return result;
    }

    @RequestMapping(value = "/updateActiveCode/{mailKey}")
    //@CrossOrigin(origins = "*")
    public ModelAndView  updateActiveCode (@PathVariable(name = "mailKey") String mailKey){
        Long userId = reminderMailService.getUserId(mailKey);
        ModelAndView mv = new ModelAndView("successfulActive");
        ModelAndView expiredMv = new ModelAndView("activeTimeExpired");

        if(userId == 0l){
            return expiredMv ;
        }else {
            return mv;
        }
    }

    @RequestMapping(value = "/checkMaxRegistered")
    //@CrossOrigin(origins = "*")
    public int checkMacReg(SlotReminderMail mail){
        System.out.println(mail.getReceiverEmail());
       return reminderMailService.checkMaxReg(mail.getReceiverEmail());
    }
/**

    @RequestMapping(value = "/sendMail")
    public void getReceiverEmail(){
       reminderMailService.sendReminderEmail();

    }
**/


    @RequestMapping(value = "/getReminderList")
    //@CrossOrigin(origins = "*")
    public String getReminderListByEmail(SlotReminderMail reminderMail){
        System.out.println("email  " +reminderMail.getReceiverEmail());
        return reminderMailService.findAllByReceiverEmail(reminderMail.getReceiverEmail());
    }

    @RequestMapping(value = "/deleteReminder/{id}")
    //@CrossOrigin(origins = "*")
    public String deleteFromReminderList(@PathVariable("id") Long id){
        if(null != id){
            reminderMailService.deleteFromReminderList(id);
            return "success deleted";
        }else {
            return "deleted error";
        }
    }
}
