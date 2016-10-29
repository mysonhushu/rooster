/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster.internal.dao;

/**
 *
 * @author zg
 */
public class RoosterDaoSql {
   public static final String getDailyAttendance_sql = 
            "select department,staff_name,accord_no,accord_date,machine_no,"
           + "record_no,record_type,card_no from t_daily_attendance ";
   public static final String  addDailyAttendance_sql = 
           "insert into t_daily_attendance(department,staff_name,accord_no,accord_date,machine_no,record_no,"
           + "record_type,card_no) values(?,?,?,?,?,?,?,?)";
   public static final String  batchAddDailyAttendance_sql = 
           "insert into t_daily_attendance(department,staff_name,accord_no,accord_date,machine_no,record_no,"
           + "record_type,card_no) values(?,?,?,?,?,?,?,?)";
   public static final String deleteRepeatLines_sql = 
           "delete from t_daily_attendance where rowid not in(select max(rowid) from t_daily_attendance group by staff_name,accord_date)";
   public static final String getDailyAttendanceByDate_sql =
           "select department,staff_name,accord_no,accord_date,machine_no,record_no,record_type,card_no from t_daily_attendance \n" +
            "  where DATE(accord_date)>=date(?)  and DATE(accord_date)<=date(?) order by staff_name";
   
   public static final String getWorkDays_sql =
           "select work_date from (\n" +
            " select  date(accord_date) work_date,count(*) as total from t_daily_attendance  \n" +
            "           where DATE(accord_date)>=date(?)  \n" +
            "               and DATE(accord_date)<=date(?)       \n" +
            "                 group by   date(accord_date) )  where total>6";
   
   public static final String getStaffNames_sql = 
           "select distinct staff_name from t_daily_attendance \n" +
"          where DATE(accord_date)>=date(?)  and DATE(accord_date)<=date(?)     ";

}
