package rooster.internal.bean;

 
public class t_daily_attendance {
    //部门
    public String department;
    //姓名
    public String staff_name;
    //考勤号码
    public String accord_no;
    //考勤日期
    public String accord_date;
    //机器号
    public String machine_no;
    //编号
    public String record_no;
    //对比方式
    public String record_type;
    //卡号
    public String card_no;
    //是否迟到
    public boolean isLate;
    //迟到时间计算
    public String lateRemark;
    //是否加班
    public boolean isOverTime;
    //加班时间计算
    public String overTimeRemark;
    
}
