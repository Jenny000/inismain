package ie.my353.inis.repository;



import ie.my353.inis.entity.SlotList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * date 13/01/2019.
 */

@Repository
public interface StatisticsRepository extends JpaRepository<SlotList, Integer> {

    /***
     * 1 过去7天 每小时的有号数量统计 不含当天
     * 不含当天发布数量
     * @return
     */

    @Query(value = "select convert(date_format(create_time,'%H'),SIGNED),date_format(create_time,'%d/%m/%Y %H') as dates ,count(distinct slot_id)from slots where date_format(create_time,'%Y-%m-%d')  between DATE_SUB(DATE(now()),INTERVAL 7 DAY ) and curdate() group by dates order by date_format(create_time,'%Y-%m-%d') desc ", nativeQuery = true)
    List<Object[]> chartDataResult();


    /***
     * 获得目前最远的日子
     * @return
     */

    @Query(value = "select date_format(MAX(str_to_date(slot_date,'%d/%m/%Y')),'%d/%m/%Y') from slots;", nativeQuery = true)
    String getMaxReleasedDate();

    /*************************************************发号日分析*******************************************************************/
    /**
     * 每天发号的总数 全库
     *
     * @return
     */

    @Query(value = "select count(distinct slot_id) as total ,date_format(create_time,'%d/%m/%Y') as issueDay from slots group by issueDay;", nativeQuery = true)
    List<Object[]> countAllSlotsOfEachDay();

    /***
     * 给定日期的发号总数
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */

    @Query(value = "select count(distinct id) as total,date_format(create_time ,'%d/%m/%Y') as issueDay from slots where date_format(create_time,'%d/%m/%Y')=:queryDate group by issueDay;", nativeQuery = true)
    List<Object[]> countAllSlotsOfGivenDay(@Param("queryDate") String queryDate);

    /***
     * 每天都发了什么号 分别是哪天的 以小时 cat type 分组（全库版本）
     * @return
     */

    @Query(value = "select count(distinct id) as total,date_format(create_time,'%d/%m/%Y %H') as issueDay,date_format(str_to_date(time,'%d %M %Y - %H'),'%d/%m/%Y %H') AS processDay,cat,type from slots group by issueDay,str_to_date(time,'%d %M %Y - %H'),cat,type", nativeQuery = true)
   List<Object[]> issueDateDetailGroupByHourCatTypeFull();

    /***
     *  给定日期都发了什么号 发了哪天的号
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select count(distinct id) as total,date_format(date_and_time,'%Y/%m/%d %H') as issueDay,cat,type,date_format(str_to_date(time,'%d %M %Y - %H'),'%Y/%m/%d %H') AS processDay from slot_entity_s where date_format(date_and_time,'%d/%m/%Y')=:queryDate group by issueDay,str_to_date(time,'%d %M %Y - %H'),cat,type", nativeQuery = true)
    List<Object[]> issueDateDetailGroupByHourCatTypeOnGivenDate(@Param("queryDate") String queryDate);

    /***
     * 给定日期的发号细节 只统计办理日总数 eg 18/01/2019 发了 69 张 19/01/2019 的号
     * @param queryDate dd/mm/yyyy
     * @return
     */
    @Query(value = "select date_format(date_and_time,'%d/%m/%Y') as issueDay,count(distinct id)as total,date_format(str_to_date(time,'%d %M %Y'),'%d/%m/%Y') AS processDay from slot_entity_s where date_format(date_and_time,'%d/%m/%Y')=:queryDate group by issueDay,str_to_date(time,'%d %M %Y')", nativeQuery = true)
    List<Object[]> issueDateAndProcessDateTotalOnGivenDate(@Param("queryDate") String queryDate);


    /***********************************************办理日分析****************************************************************/
    /**
     * 实际办理当天的总数 全库
     * group by str_to_date(date,'%d/%m/%Y') 看上去很没必要 实际上需要这个group把string 变成date 并可以排序 否则纯String 是无法按时间舒徐排de
     *
     * @return
     */
    @Query(value = "select COUNT(distinct id),date from slot_entity_s group by str_to_date(date,'%d/%m/%Y')", nativeQuery = true)
    List<Object[]> getTotalOfProcessDate();

    /***
     * 给定日期 办理总数
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select COUNT(distinct id),date from slot_entity_s where date=:queryDate", nativeQuery = true)
    List<Object[]> getTotalOfProcessDayOnGivenDate(@Param("queryDate") String queryDate);

    /***
     * 给定办理日期 办理人数统计 只按照小时分组
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select count(distinct id) as total,date_format(str_to_date(time,'%d %M %Y - %H'),'%d/%m/%Y %H' )as processday from slot_entity_s where date=:queryDate group by str_to_date(time,'%d %M %Y - %H')", nativeQuery = true)
    List<Object[]> getTotalOfProcessDateSortByHourOnGivenDate(@Param("queryDate") String queryDate);

    /***
     *  给定日期 实际办理日的 人数，cat,type，小时分组统计
     *  @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select count(distinct id)as total,date_format(str_to_date(time,'%d %M %Y - %H'),'%d/%m/%Y %H' )as processday ,cat,type from slot_entity_s where date=:queryDate group by  str_to_date(time,'%d %M %Y - %H'),cat,type", nativeQuery = true)
    List<Object[]> getAmountOfProcessDateSortByHoursCatTypeOnGivenDate(@Param("queryDate") String queryDate);


    /***
     * 实际办理日 每天办理的数 并按照cat type 分组 全库
     * @return
     */
    @Query(value = "select count(distinct id),cat,type,date from slot_entity_s group by str_to_date(time,'%d %M %Y'),cat,type", nativeQuery = true)
    List<Object[]> getTotalOfProcessDataSortByCatTypeDate();


    /***
     * 给定日期 办理的各类别总数
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select count(distinct id),cat,type,date from slot_entity_s where date=:queryDate group by cat,type", nativeQuery = true)
    List<Object[]> getTotalOfEachCatOnGivenDate(@Param("queryDate") String queryDate);

    /***
     * 给定的日期 各个小时 发布的新号的统计 按发布时间，受理日期，种类，类型
     * 给定的日期 每小时都发了什么号，都是哪天的
     * @param queryDate 格式 dd/mm/yyyy
     * @return
     */
    @Query(value = "select count(distinct id),date_format(date_and_time,'%d/%m/%Y %H') as dtes,cat,type,  str_to_date(time,'%d %M %Y - %H') AS dates  from slot_entity_s where date_format(date_and_time,'%d/%m/%Y')=:queryDate group by dtes,dates,cat,type", nativeQuery = true)
    List<Object[]> givenDateIssuesAndProcessDateAnalysis(@Param("queryDate") String queryDate);

    /**
     todo 1 过去7天 每小时的有号数量统计 done
     todo 目前最远的日子 done
     todo 每天发号的总数
     todo 给定日期的发号总数
     todo 每天个小时发号数量 种类 以及实际办理日统计 全库
     todo 给定日期每小时 每天个小时发号数量 种类 以及实际办理日统计
     todo 实际办理日期办理总数
     todo 实际办理日期个小时 各个种类的的统计 全库
     todo 给定日期实际办理日期个小时 各个种类的的统计
     */

    /**
     * #给定日期和小时 查看各个时间段 cat type 的分布状况 done
     * select count(distinct id),cat,type,str_to_date(time,'%d %M %Y - %H') as theday from slot_entity_s where date='05/03/2019' group by theday,cat,type
     *
     * #给定日期每小时办理总数 done
     * select count(distinct id),str_to_date(time,'%d %M %Y - %H') as theday from slot_entity_s where date='05/03/2019' group by theday
     *
     * #给定日期每小时办理的类型与数量 done
     * select count(distinct id),str_to_date(time,'%d %M %Y - %H') as theday ,cat,type from slot_entity_s where date='05/03/2019' group by theday,cat,type
     *
     * #办理的各类别总数 全库版 done
     * select count(distinct id),cat,type,date from slot_entity_s group by str_to_date(time,'%d %M %Y'),cat,type
     * #给定日期 办理的各类别总数 done
     * select count(distinct id),cat,type,date from slot_entity_s where date='05/03/2019' group by cat,type
     *
     * #办理总数的全库版 done
     * select COUNT(distinct id),date from slot_entity_s group by str_to_date(date,'%d/%m/%Y')
     * #给定日期 办理总数 done
     * select COUNT(distinct id),date from slot_entity_s where date='05/03/2019'
     */

}
