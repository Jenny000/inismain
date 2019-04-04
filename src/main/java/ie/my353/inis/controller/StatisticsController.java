package ie.my353.inis.controller;




import ie.my353.inis.repository.StatisticsRepository;
import ie.my353.inis.service.StatisticsService;
import io.lettuce.core.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 * date 13/01/2019.
 */

@RestController
@RequestMapping("/st")
public class StatisticsController {


    @Autowired
    StatisticsService statisticsService;

    @Autowired
    StatisticsRepository statisticsRepository;

    @GetMapping("/chart")
//    @CrossOrigin("*")
    public Object getChartData() throws RedisException {
        return statisticsService.getChartDataService();
    }

    @GetMapping("/max")
    //@CrossOrigin("*")
    public Object getMaxDate() throws RedisException {
        return statisticsService.getMaxReleasedDateService();
    }


    /**************以下还没有决定是否开始使用********************************/
//    public List<Object[]> allTest(@PathVariable String queryDate){
//
//        String s = queryDate.replaceAll("-", "/");
//
//        System.out.println(s);
//        return statisticsRepository.getAmountOfProcessDateSortByHoursCatTypeOnGivenDate(s);
//    }


    public List<Object[]> countAllSlotsOfEachDayController() {
        return statisticsService.countAllSlotsOfEachDayService();
    }


    public List<Object[]> countAllSlotsOfGivenDayController(String queryDate) {
        return statisticsService.countAllSlotsOfGivenDayService(queryDate);
    }


    public List<Object[]> issueDateDetailGroupByHourCatTypeFullController() {
        return statisticsService.issueDateDetailGroupByHourCatTypeFullService();
    }


    public List<Object[]> issueDateDetailGroupByHourCatTypeOnGivenDateController(String queryDate) {
        return statisticsService.issueDateDetailGroupByHourCatTypeOnGivenDateService(queryDate);
    }


    public List<Object[]> getTotalOfProcessDateController() {
        return statisticsService.getTotalOfProcessDateService();
    }


    public List<Object[]> getTotalOfProcessDayOnGivenDateController(String queryDate) {
        return statisticsService.getTotalOfProcessDayOnGivenDateService(queryDate);
    }


    public List<Object[]> getTotalOfProcessDateSortByHourOnGivenDateController(String queryDate) {
        return statisticsService.getTotalOfProcessDateSortByHourOnGivenDateService(queryDate);
    }


    public List<Object[]> getAmountOfProcessDateSortByHoursCatTypeOnGivenDateController(String queryDate) {
        return statisticsService.getAmountOfProcessDateSortByHoursCatTypeOnGivenDateService(queryDate);
    }


    public List<Object[]> getTotalOfProcessDataSortByCatTypeDateController() {
        return statisticsService.getTotalOfProcessDataSortByCatTypeDateService();
    }


    public List<Object[]> givenDateIssuesAndProcessDateAnalysisController(String queryDate) {
        return statisticsService.givenDateIssuesAndProcessDateAnalysisService(queryDate);
    }


    public List<Object[]> getTotalOfEachCatOnGivenDateController(String queryDate) {
        return statisticsService.getTotalOfEachCatOnGivenDateService(queryDate);
    }


    public List<Object[]> issueDateAndProcessDateTotalOnGivenDateController(String queryDate) {
        return statisticsService.issueDateAndProcessDateTotalOnGivenDate(queryDate);
    }
}
