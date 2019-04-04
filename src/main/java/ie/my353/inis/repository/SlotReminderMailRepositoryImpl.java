package ie.my353.inis.repository;

import ie.my353.inis.entity.SlotReminderMail;
import ie.my353.inis.utils.DateConverter;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 * date 2019/3/2 0002.
 */
@Component
public class SlotReminderMailRepositoryImpl {
    @Autowired
    private SlotReminderMailRepository reminderMailRepository;
    @Autowired
    private DateConverter dateConverter;


    public void saveReminderMailinfo(SlotReminderMail reminderMail) {
        reminderMailRepository.save(reminderMail);
    }

    public void updateActiveCode(Long id) {
        reminderMailRepository.updateActiveCode(id);
    }

    public List<SlotReminderMail> getEmailListByReminderDate(String remindDate) {
        return reminderMailRepository.getEmailListByReminderDate(remindDate);
    }

    public int getRegEmailCountAfterActive(String email) {

        return reminderMailRepository.getRegEmailCountAfterActive(email);


    }

    public String getReceiverEmailbyId(Long id) {
        return reminderMailRepository.getReceiverEmailbyId(id);
    }

    public void updateReminderMailSendInfo(Long id) {

        reminderMailRepository.updateReminderMailSendInfo(id);

    }

    public List<SlotReminderMail> getEmailListBySentMailCountAndIsFinished() {
        return reminderMailRepository.getEmailListBySentMailCountAndIsFinished();
    }

    public void updateIsFinished() {
        reminderMailRepository.updateIsFinished();
    }

    public List<SlotReminderMail> findAllByReceiverEmail(String email) {
        return reminderMailRepository.findAllByReceiverEmail(email);
    }
}