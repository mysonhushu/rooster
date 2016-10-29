package rooster.excel.read;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WritableFont;
import rooster.config.SystemLoad;
import rooster.internal.bean.Result;
import rooster.internal.bean.t_daily_attendance;
import rooster.internal.dao.RoosterDao;

public class WriteExcel {
    

    /**
     * 
     * @param tdaList 所有记录
     * @param beLateList  迟到记录
     * @param noSignList  没有打卡记录
     * @param result 
     */
    public static void exportExcel(ArrayList<t_daily_attendance> tdaList, Result result){
        //姓名	迟到日期	迟到分钟数 迟到次数累计
        result.result = "F-writeExcel：create excel error!";
        result.describle = "导出excel表错误！";
         String excelName = "考勤统计结果_"+getNowDate()+".xls"; 
        try {  
            File excelFile = new File(excelName);  
            // 如果文件存在就删除它  
            if (excelFile.exists())  
            {
               excelFile.delete();  
            }
            // 打开文件  
            WritableWorkbook book = Workbook.createWorkbook(excelFile);  
            // 生成名为“第一页”的工作表，参数0表示这是第一页  
            WritableSheet sheet = book.createSheet(" 考勤结果统计 ", 0);  
           // 合并单元格  
           // sheet.mergeCells(5, 5, 6, 6);  
           sheet.setColumnView(0,20);
           sheet.setColumnView(1,30);
           sheet.setColumnView(2,30);
           sheet.setColumnView(3,30);
            // 文字样式  
            WritableFont wfc = new WritableFont(  
              WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,  
              UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);  

            WritableCellFormat wcfFC = new WritableCellFormat(wfc);  
            
            // 设置单元格样式  
            wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色  
            wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中  
            
            // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)  
            // 以及单元格内容为  
            Label name = new Label(0, 0, "姓名", wcfFC);  
            // 将定义好的单元格添加到工作表中  
            sheet.addCell(name);  
            
            Label lateDate = new Label(1, 0, "迟到日期", wcfFC);  
            sheet.addCell(lateDate);  
            
            Label lateMinutes = new Label(2, 0, "迟到分钟数", wcfFC);  
            sheet.addCell(lateMinutes);  
            
            Label lateTimes = new Label(3, 0, "迟到次数累计", wcfFC);  
            sheet.addCell(lateTimes);  
             int j = 0;
            for(int i=0;i<tdaList.size();i++)
            {
                if(!tdaList.get(i).isLate)
                {
                 continue;   
                }else{
                    Label nameLabel = new Label(0,j+1,tdaList.get(i).staff_name);
                    sheet.addCell(nameLabel);
                    Label lateDateLabel = new Label(1,j+1,tdaList.get(i).accord_date);
                    sheet.addCell(lateDateLabel);
                    Label lateMinute = new Label(2,j+1,tdaList.get(i).lateRemark);
                    sheet.addCell(lateMinute);
                    j = j+1;
                }
                
            }
            // 写入数据并关闭文件  
            book.write();  
            book.close();  
            result.result = "0";
            result.describle = "Excel创建成功！";
            System.out.println("Excel创建成功");  
           } catch (Exception e) {  
            System.out.println(e);  
           }  
    }
    public static String getNowDate()
    {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
            Date dt = new Date();
            String  returnTime ="";
            try {
                    returnTime = sdf.format((dt));
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return returnTime;
    }
    public static void main(String args[])
    {
        SystemLoad.init();
        ArrayList<String[]> mm = ReadExcel.getBeanFromExcel("sheet/test.xls");
        for(String[] m:mm)
        {
            for(int i=0;i<m.length;i++)
            {
                System.out.print(m[i]+" ");
            }
            System.out.println();
        }

         RoosterDao dao = new RoosterDao();
         dao.batchAddDailyAttendance(mm);
         
//        Result result = new Result();
////        RoosterDao dao = new RoosterDao();
//        ArrayList<t_daily_attendance> tdaList = dao.getDailyAttendance();
////        
//        for(t_daily_attendance a :tdaList)
//        {
//            a.isLate = RoosterRunner.judgeSignIn(a.accord_date, 8, 31, 0);
//            if(a.isLate)
//            {
//             a.lateRemark = RoosterRunner.caculateSingIn(a.accord_date, 8, 31, 0);
//            }
//        }
//        WriteExcel.exportExcel(tdaList, result);
    }
}
