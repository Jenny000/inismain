package ie.my353.inis.utils;

import ie.my353.inis.entity.SlotReminderMail;
import ie.my353.inis.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Component
public class DateConverter {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private SlotReminderMail reminderMail;

    public String CurrnetDateconvert(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());
        return todayDate;
    }

    public Date stringToDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println("date formate  " +  dateFormat.parse(date));
        return dateFormat.parse(date);

    }

    public String dateFormatConverter(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);

    }



    public String reminderDate(String appointDate, int beforeDays){
        //String maxReleasedDate = (statisticsService.getMaxReleasedDateService()).toString();
        //System.out.println(maxReleasedDate);
        //过去max release date的前几天
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(appointDate,new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        //把日期设置成maxReleasedDate
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE, - beforeDays);
        Date beforeRemindDate =calendar.getTime();
        String result = sdf.format(beforeRemindDate);
        System.out.println("resutl   " + result);

        return result;


    }
    public String addOneDay(String sendMailDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = new Date(dateFormat.parse(sendMailDate).getTime() + 24*3600*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    /**
     *
     * @param date
     * @return 使用near api 的时候把日期（14 September 2019 - 14:00）转换成 14/09/2019
     */
    public String nearApiDateConverter(String date){
        //String date="14 September 2019 - 14:00";
        String newDate= date.substring(0,date.indexOf("-")-1);
        //System.out.println(newDate);

        String dd = LocalDate.parse(newDate, DateTimeFormatter
                .ofPattern("d MMMM yyyy", Locale.ENGLISH))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy",Locale.ENGLISH));
        //System.out.println(dd);
        return dd;
    }





}
