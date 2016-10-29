/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster.internal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rooster.internal.bean.t_daily_attendance;

public class RoosterDao extends Dao{
    public PreparedStatement getDailyAttendance_ps = null;
    public PreparedStatement getDailyAttendanceByDate_ps = null;
    public PreparedStatement addDailyAttendance_ps = null;
    public PreparedStatement batchAddDailyAttendance_ps = null;
    public PreparedStatement deleteRepeateData_ps = null;
    public PreparedStatement getWorkDays_ps = null;
    public PreparedStatement getStaffNames_ps = null;
    
    /**
     * 从数据库里面获取数据
     * @return 
     */
    public ArrayList<t_daily_attendance> getDailyAttendance(){
        ArrayList<t_daily_attendance> result = new ArrayList<t_daily_attendance>();
        this.getConnection();
        String execute_sql = RoosterDaoSql.getDailyAttendance_sql;
        ResultSet rs = null;
        try {
            this.getDailyAttendance_ps = this.conn.prepareStatement(execute_sql);
            rs = this.getDailyAttendance_ps.executeQuery();
            while(rs.next())
            {
//            "select department,staff_name,accord_no,accord_date,machine_no,"
//           + "record_no,record_type,card_no from t_daily_attendance";
               t_daily_attendance tda = new t_daily_attendance();
               tda.department = rs.getString("department");
               tda.staff_name = rs.getString("staff_name");
               tda.accord_no = rs.getString("accord_no");
               tda.accord_date = rs.getString("accord_date");
               tda.machine_no = rs.getString("machine_no");
               tda.record_no = rs.getString("record_no");
               tda.record_type = rs.getString("record_type");
               tda.card_no = rs.getString("card_no");
               result.add(tda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            free(rs,this.getDailyAttendance_ps,conn);
        }
        return result;
    }
    
    public ArrayList<t_daily_attendance> getDailyAttendanceByDate(String beginDate,String endDate){
        ArrayList<t_daily_attendance> result = new ArrayList<t_daily_attendance>();
        this.getConnection();
        String execute_sql = RoosterDaoSql.getDailyAttendanceByDate_sql;
        ResultSet rs = null;
        try {
            this.getDailyAttendanceByDate_ps = this.conn.prepareStatement(execute_sql);
            //System.out.println("beginDate="+beginDate+" endDate="+endDate);
            this.getDailyAttendanceByDate_ps.setString(1, beginDate);
            this.getDailyAttendanceByDate_ps.setString(2, endDate);
            rs = this.getDailyAttendanceByDate_ps.executeQuery();
            while(rs.next())
            {
//            "select department,staff_name,accord_no,accord_date,machine_no,"
//           + "record_no,record_type,card_no from t_daily_attendance";
               t_daily_attendance tda = new t_daily_attendance();
               tda.department = rs.getString("department");
               tda.staff_name = rs.getString("staff_name");
               tda.accord_no = rs.getString("accord_no");
               tda.accord_date = rs.getString("accord_date");
               tda.machine_no = rs.getString("machine_no");
               tda.record_no = rs.getString("record_no");
               tda.record_type = rs.getString("record_type");
               tda.card_no = rs.getString("card_no");
               result.add(tda);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            free(rs,this.getDailyAttendanceByDate_ps,conn);
        }
        return result;
    }
    
     public ArrayList<String> getWorkDateByDate(String beginDate,String endDate){
        ArrayList<String> result = new ArrayList<String>();
        this.getConnection();
        String execute_sql = RoosterDaoSql.getWorkDays_sql;
        ResultSet rs = null;
        try {
            this.getWorkDays_ps = this.conn.prepareStatement(execute_sql);
            //System.out.println("beginDate="+beginDate+" endDate="+endDate);
            this.getWorkDays_ps.setString(1, beginDate);
            this.getWorkDays_ps.setString(2, endDate);
            rs = this.getWorkDays_ps.executeQuery();
            while(rs.next())
            {
               String workDate =  rs.getString("work_date");
               result.add(workDate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            free(rs,this.getWorkDays_ps,conn);
        }
        return result;
    }
    
     //getStaffNames_sql
        public ArrayList<String> getStaffNamesByDate(String beginDate,String endDate){
        ArrayList<String> result = new ArrayList<String>();
        this.getConnection();
        String execute_sql = RoosterDaoSql.getStaffNames_sql;
        ResultSet rs = null;
        try {
            this.getStaffNames_ps = this.conn.prepareStatement(execute_sql);
            //System.out.println("beginDate="+beginDate+" endDate="+endDate);
            this.getStaffNames_ps.setString(1, beginDate);
            this.getStaffNames_ps.setString(2, endDate);
            rs = this.getStaffNames_ps.executeQuery();
            while(rs.next())
            {
               String workDate =  rs.getString("staff_name");
               result.add(workDate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            free(rs,this.getStaffNames_ps,conn);
        }
        return result;
    }
     
    
    /**
     * 添加数据到数据库当中
     * @param tdaList 
     */
    public void addDailyAttendance( ArrayList<t_daily_attendance> tdaList){
        if(tdaList.size()<=1)
        {
            return;
        }
        try {
            this.getConnection();
            String execute_sql = RoosterDaoSql.addDailyAttendance_sql;
            this.addDailyAttendance_ps = this.conn.prepareStatement(execute_sql);
            int index = 0;
            int size = 10;

            for(int i=1;i<tdaList.size();i++)
            {
                t_daily_attendance a = tdaList.get(i);
//          "insert into t_daily_attendance(department,staff_name,accord_no,accord_date,machine_no,"
//           + "record_no,record_type,card_no) values(?,?,?,?,?,?,?,?)";
                if(a.department == null||"noValues".equals(a.department)||"部门".equals(a.department)||"".equals(a.department))
                {
                    break;
                }
                this.addDailyAttendance_ps.setString(1, a.department);
                this.addDailyAttendance_ps.setString(2, a.staff_name);
                this.addDailyAttendance_ps.setString(3, a.accord_no);
                this.addDailyAttendance_ps.setString(4, a.accord_date);
                this.addDailyAttendance_ps.setString(5, a.machine_no);
                 this.addDailyAttendance_ps.setString(6, a.record_no);
                this.addDailyAttendance_ps.setString(7, a.record_type);
                this.addDailyAttendance_ps.setString(8, a.card_no);
                addDailyAttendance_ps.addBatch();
                index = index +1;
                if(index%size == 0)
                {
                   addDailyAttendance_ps.executeBatch();
                }
            }
            addDailyAttendance_ps.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           free(null,addDailyAttendance_ps,conn);
        }
        
    }
    
    /**
     * 批量添加数据到数据库当中
     * @param tdaList 
     */
     public void batchAddDailyAttendance( ArrayList<String[]> tdaList){
        try {
            this.getConnection();
            String execute_sql = RoosterDaoSql.batchAddDailyAttendance_sql;
            this.batchAddDailyAttendance_ps = this.conn.prepareStatement(execute_sql);
            int index = 0;
            int size = 10;
            for(String[] values:tdaList)
            {
//           "insert into t_daily_attendance(department,staff_name,accord_no,accord_date,machine_no,record_no,"
//           + "record_type,card_no) values(?,?,?,?,?,?,?,?)";
                for(int i=0;i<values.length;i++)
                {
                   this.batchAddDailyAttendance_ps.setString(i+1,values[i]); 
                }
                batchAddDailyAttendance_ps.addBatch();
                index = index +1;
                if(index%size == 0)
                {
                   batchAddDailyAttendance_ps.executeBatch();
                }
            }
            batchAddDailyAttendance_ps.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           free(null,batchAddDailyAttendance_ps,conn);
        }
        
    }
     /**
      * 删除重复数据
      */
     public void deleteRepeateData(String tableName){
         this.getConnection();
         String execute_sql = null;
         if(tableName != null ||"t_daily_attendance".equals(tableName.toLowerCase()))
         {
           execute_sql  = RoosterDaoSql.deleteRepeatLines_sql;
         }else{
             return;
         } 
        try {
            this.deleteRepeateData_ps = this.conn.prepareStatement(execute_sql);
            deleteRepeateData_ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RoosterDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
           free(null,deleteRepeateData_ps,conn);   
        }
     }
    
     public static void free(ResultSet rs,Statement st,Connection conn){
			try {
				if(rs !=null)
				{
					rs.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if(st != null)
					{
						st.close();
					}
				}catch(SQLException e1){
					e1.printStackTrace();
				}finally{
					try {
						if(conn != null)
						{
							conn.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}
	   }
//     public static void main(String args[])
//     {
//         
//         SystemLoad.init();
//         ArrayList<String[]> mm = ReadExcel.getBeanFromExcel("sheet/test.xls");
//
//         RoosterDao a = new RoosterDao();
//         a.batchAddDailyAttendance(mm);
//        ArrayList<t_daily_attendance> m = a.getDailyAttendance();
//        for(t_daily_attendance t:m)
//        {
//             System.out.println(t.department+" "+t.staff_name+" "+t.accord_no+" "
//                     +t.accord_date+"  "+t.machine_no+"  "+t.record_no+"   "+t.record_type+"  "+t.card_no);
//        }
//     }
}
