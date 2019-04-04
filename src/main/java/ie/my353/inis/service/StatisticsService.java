package ie.my353.inis.service;


import ie.my353.inis.repository.StatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * date 13/01/2019.
 */
@Service
@Slf4j
public class StatisticsService {

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private final String redisChartDataKeyStr = "chartData";

    public Object getChartDataService() {

        try {

            if (redisTemplate.hasKey("chartData")) {
                return redisTemplate.opsForValue().get(redisChartDataKeyStr);
            } else {
                List<Object[]> collect = statisticsRepository.chartDataResult()
                        .stream()
                        .map(data ->
                                new Object[]{data[0], data[1].toString().substring(0, 10), data[2]}
                        ).collect(Collectors.toList());
                redisTemplate.opsForValue().set(redisChartDataKeyStr, collect);
                return collect;

            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("NOAUTH")) {

                log.error(e.getMessage());
            } else {
                log.error(e.getMessage());
            }
        }

        return "null";
    }


    @Scheduled(cron = "0 1 0/1 * * ?")
    public void chartDataScTask() {
        List<Object[]> collect = statisticsRepository.chartDataResult()
                .stream()
                .map(data ->
                        new Object[]{data[0], data[1].toString().substring(0, 10), data[2]}
                )
                .collect(Collectors.toList());
        try {

            if (redisTemplate.hasKey(redisChartDataKeyStr)) {

                redisTemplate.delete(redisChartDataKeyStr);
                log.info("del old Key for chart Data" + LocalDateTime.now());
                redisTemplate.opsForValue().set(redisChartDataKeyStr, collect);
                log.info("Set New Key for chart Data" + LocalDateTime.now());
            } else {
                redisTemplate.opsForValue().set(redisChartDataKeyStr, collect);
                log.info("Create New Key for chart Data" + LocalDateTime.now());
            }
        } catch (Exception e) {

            log.error(e.getMessage());
        }

    }

    public Object getMaxReleasedDateService() {

        if (redisTemplate.hasKey("maxReleasedDate")) {

            return redisTemplate.opsForValue().get("maxReleasedDate");
        } else {
            String maxReleasedDate = statisticsRepository.getMaxReleasedDate();
            redisTemplate.opsForValue().set("maxReleasedDate", maxReleasedDate, 30, TimeUnit.MINUTES);

            return maxReleasedDate;
        }

    }

    public List<Object[]> countAllSlotsOfEachDayService() {
        return statisticsRepository.countAllSlotsOfEachDay();
    }

    public List<Object[]> countAllSlotsOfGivenDayService(String queryDate) {
        return statisticsRepository.countAllSlotsOfGivenDay(queryDate);
    }

    public List<Object[]> issueDateDetailGroupByHourCatTypeFullService() {
        return statisticsRepository.issueDateDetailGroupByHourCatTypeFull();
    }

    public List<Object[]> issueDateDetailGroupByHourCatTypeOnGivenDateService(String queryDate) {
        return statisticsRepository.issueDateDetailGroupByHourCatTypeOnGivenDate(queryDate);
    }

    public List<Object[]> getTotalOfProcessDateService() {
        return statisticsRepository.getTotalOfProcessDate();
    }

    public List<Object[]> getTotalOfProcessDayOnGivenDateService(String queryDate) {
        return statisticsRepository.getTotalOfProcessDayOnGivenDate(queryDate);
    }


    public List<Object[]> getTotalOfProcessDateSortByHourOnGivenDateService(String queryDate) {
        return statisticsRepository.getTotalOfProcessDateSortByHourOnGivenDate(queryDate);
    }

    public List<Object[]> getAmountOfProcessDateSortByHoursCatTypeOnGivenDateService(String queryDate) {
        return statisticsRepository.getAmountOfProcessDateSortByHoursCatTypeOnGivenDate(queryDate);
    }

    public List<Object[]> getTotalOfProcessDataSortByCatTypeDateService() {
        return statisticsRepository.getTotalOfProcessDataSortByCatTypeDate();
    }

    public List<Object[]> givenDateIssuesAndProcessDateAnalysisService(String queryDate) {
        return statisticsRepository.givenDateIssuesAndProcessDateAnalysis(queryDate);
    }

    public List<Object[]> getTotalOfEachCatOnGivenDateService(String queryDate) {
        return statisticsRepository.getTotalOfEachCatOnGivenDate(queryDate);
    }

    public List<Object[]> issueDateAndProcessDateTotalOnGivenDate(String queryDate) {
        return statisticsRepository.issueDateAndProcessDateTotalOnGivenDate(queryDate);
    }


}
