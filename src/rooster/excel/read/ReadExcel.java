/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster.excel.read;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import rooster.api.RoosterRunner;

 
public class ReadExcel {
    
    /**
     * 从excel当中读取bean类
     * @param path
     * @return 
     */
    public static ArrayList<String[]> getBeanFromExcel(String path)
    {
        ArrayList<String[]> tdaList = new ArrayList<String[]>();
        String result ="";
         try   {
             File a = new File(path);
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
            Workbook book  =  Workbook.getWorkbook(a);
             //  获得第一个工作表对象
            Sheet sheet  =  book.getSheet( 0 );
            //获取一共有多少列
            int columns = sheet.getColumns();
            //获取一共有多少行
            int rows = sheet.getRows();
            //遍历行信息          
            for(int j=1;j<rows;j++)
            {
                String[] beanValue;
                //遍历列信息
                StringBuffer lines = new StringBuffer();
                for(int i=0;i<columns;i++)
                {
                      
                       Cell cell  =  sheet.getCell( i ,  j );
                     
                       if(cell.getType() == CellType.DATE)
                       {
                           DateCell dc = (DateCell) cell;
                           Date date = dc.getDate();
                            String sDate = sdf.format(date);
                            result = sDate;
                            
                       }else{
                          result  =  cell.getContents();
                          if(result == null || "".equals(result))
                          {
                              result = "noValues";
                          }
                          if(i==3)
                          {
                             String sDate = formateDate(result);
                             result = sDate;
                          }
                       }
                      if(i!=columns-1)
                      {
                         lines.append(result).append("#ms#"); 
                      }else{
                         lines.append(result); 
                      }
                }
                beanValue = lines.toString().split("#ms#");
                tdaList.add(beanValue);
            }
            book.close();
        }   catch  (Exception e)  {
            System.out.println("exception = "+ result);
            System.out.println(e);
            return null;
        }
         return tdaList;
    }
    
    public static String formateDate(String time)
    {
        String result = null;
        DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
        Date date;
         try {
             date = df.parse(time);
             result = df.format(date);
         } catch (ParseException ex) {
             Logger.getLogger(RoosterRunner.class.getName()).log(Level.SEVERE, null, ex);
         }  
        return result;
    }
}
