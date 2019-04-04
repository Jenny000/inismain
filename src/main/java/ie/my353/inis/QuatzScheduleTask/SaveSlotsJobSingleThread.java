package ie.my353.inis.QuatzScheduleTask;

import ie.my353.inis.service.SlotService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
@Component
@Slf4j
public class SaveSlotsJobSingleThread implements Job {
    @Autowired
    private SlotService slotService;

    public SaveSlotsJobSingleThread(){}


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //需要执行的任务
        jobExecutionContext.getJobInstance();
        slotService.saveSlotsSingleThread();
        System.out.println("单线程执行。。。" +new Date());

    }
}
**/