package ie.my353.inis.QuatzScheduleTask;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;
/**
@Service
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        return super.createJobInstance(bundle);
    }
}
**/