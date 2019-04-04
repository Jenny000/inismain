package ie.my353.inis.repository;

import ie.my353.inis.entity.SlotReminderMail;
import ie.my353.inis.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 * date 2019/3/2 0002.
 */
@Repository
public interface SlotReminderMailRepository extends JpaRepository<SlotReminderMail, Long> {

    SlotReminderMail save(SlotReminderMail reminderMail);

    @Modifying
    @Transactional
    @Query(value = "update slot_reminder_mail set is_active = 'Y', registered_email_count = 1 where id = ?",nativeQuery = true)
    void updateActiveCode (Long id);


    @Query(value = "select count(registered_email_count) from slot_reminder_mail where receiver_email = ? and is_active = 'Y' and is_finished = 'N'", nativeQuery = true)
    int getRegEmailCountAfterActive (String email);

    @Query(value = "select receiver_email from slot_reminder_mail where id = ?", nativeQuery = true)
    String getReceiverEmailbyId(Long id);

    @Modifying
    @Transactional
    @Query(value = "update slot_reminder_mail set already_send_email_count = already_send_email_count +1 where id = ?",nativeQuery = true)
    void updateReminderMailSendInfo(Long id);

    @Modifying
    @Transactional
    @Query(value = "update slot_reminder_mail set is_finished = 'Y' where already_send_email_count = before_remind_days",nativeQuery = true)
    void updateIsFinished();



    @Query(value = "select * from slot_reminder_mail where reminder_date = ? and is_active = 'Y' and already_send_email_count = 0",nativeQuery = true)
    List<SlotReminderMail> getEmailListByReminderDate (String remindDate);

    @Query(value = "select * from slot_reminder_mail where already_send_email_count <> 0 and is_finished = 'N'",nativeQuery = true)
    List<SlotReminderMail> getEmailListBySentMailCountAndIsFinished();

    //@Query(value = "select * from slot_reminder_mail where receiver_email = ?",nativeQuery = true)

    @Query(value = "select * from slot_reminder_mail where receiver_email = ? and is_active = 'Y' and is_finished = 'N'",nativeQuery = true)
    List<SlotReminderMail> findAllByReceiverEmail(String email);



    void deleteById(Long id);
}
