package ie.my353.inis.QuatzScheduleTask;

import ie.my353.inis.service.SlotService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
/**
public class SaveSlotsJobMultiThread implements Job {
    @Autowired
    private SlotService slotService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //需要执行的任务
        //slotService.saveSlotsMultiThread();
        System.out.println("多线程执行。。。" +new Date());

    }
}
 **/