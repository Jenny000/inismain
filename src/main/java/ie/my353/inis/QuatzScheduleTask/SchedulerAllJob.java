package ie.my353.inis.QuatzScheduleTask;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
@Component
@Slf4j

public class SchedulerAllJob {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
     // 该方法用来启动所有的定时任务

    public void scheduleJobs(){

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduleJobSingleThread(scheduler);
        scheduleJobMultiThread(scheduler);



    }

    private void scheduleJobSingleThread(Scheduler scheduler){
        JobDetail jobDetail = JobBuilder.newJob(SaveSlotsJobSingleThread.class). withIdentity("single thread job", "group1").build();
        //CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/60 * * * * ?");
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 19 12 * * ? ");

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder)

                .build();
        try {
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }


    }

    private void scheduleJobMultiThread(Scheduler scheduler){
        JobDetail jobDetail = JobBuilder.newJob(SaveSlotsJobMultiThread.class). withIdentity("job2", "group2").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 12 12 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2") .withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.error(e.getMessage());

        }


    }

}
**/