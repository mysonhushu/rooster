/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rooster;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.scenicview.ScenicView;
import rooster.api.RoosterModel;
import rooster.api.RoosterRunner;
import rooster.internal.bean.Result;
import rooster.internal.util.StringUtility;

 
public class Rooster extends Application {
    RoosterRunner roosterRunner = new RoosterRunner();
    TextField   filePath;
    CheckBox    isHistoryCheckBox;
//    TextField   excludeDate;
    DatePicker beginDatePicker;
    DatePicker endDatePicker;
    public static TextArea   logsText;
    Button btn;
    Color color = Color.color(0.66, 0.67, 0.69);
    StringConverter converter = new StringConverter<LocalDate>() {
             DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };       
    @Override
    public void start(Stage primaryStage) { 
        Text title = new Text(265,12, "广力早到考勤统计V1.0.0");
        title.setTextOrigin(VPos.TOP);
        title.setFill(Color.web("#131021"));
        title.setFont(Font.font("SansSerif", FontWeight.BOLD, 20));
        
        Text beginDateText = new Text(40,70,"开始日期：");
        beginDateText.setTextOrigin(VPos.TOP);
        beginDateText.setFill(Color.web("#131021"));
        beginDateText.setFont(Font.font("SansSerif", FontWeight.BOLD, 18));
        
        
        beginDatePicker = new DatePicker();
        beginDatePicker.setLayoutX(130);
        beginDatePicker.setLayoutY(70);
        beginDatePicker.setMinSize(250, 20);
       // acModel.beginDate.bindBidirectional(beginDatePicker.getProperties());
        beginDatePicker.setEditable(false);
        beginDatePicker.setShowWeekNumbers(true);
        beginDatePicker.setStyle("-fx-base: rgb(255,255,255)");
        beginDatePicker.setConverter(converter);
        beginDatePicker.setPromptText("yyyy-MM-dd".toLowerCase());
        
        Text endDateText = new Text(400, 70, "结束日期：");
        endDateText.setTextOrigin(VPos.TOP);
        endDateText.setFont(Font.font("SanSerif", FontWeight.BOLD, 18));
        endDateText.setFill(Color.web("#131021"));
        
        endDatePicker = new DatePicker();
        endDatePicker.setLayoutX(490);
        endDatePicker.setLayoutY(70);
        endDatePicker.setMinSize(250, 20);
        endDatePicker.setEditable(false);
        endDatePicker.setShowWeekNumbers(true);
        endDatePicker.setStyle("-fx-base: rgb(255,255,255)");
        endDatePicker.setConverter(converter); 
        
//        Text excludeDateText = new Text(530, 70, "调整日期：");
//        excludeDateText.setTextOrigin(VPos.TOP);
//        excludeDateText.setFont(Font.font("SanSerif", FontWeight.BOLD, 18));
//        excludeDateText.setFill(Color.web("#131021"));
//        
//        excludeDate = new TextField();
//        excludeDate.setLayoutX(620);
//        excludeDate.setLayoutY(70);
//        excludeDate.setMaxSize(140, 20);
        
        Text filePathText = new Text(40, 110, "文件路径：");
        filePathText.setTextOrigin(VPos.TOP);
        filePathText.setFont(Font.font("SanSerif", FontWeight.BOLD, 18));
        filePathText.setFill(Color.web("#131021"));
       
        filePath  = new TextField();
        filePath.setLayoutX(130);
        filePath.setLayoutY(110);
        filePath.setMinSize(430, 20);

        isHistoryCheckBox = new CheckBox("历史数据");
        isHistoryCheckBox.setLayoutY(114);
        isHistoryCheckBox.setLayoutX(570);
        isHistoryCheckBox.setStyle("-fx-base: white;");
        
        btn = new Button("统计");
        btn.setLayoutX(660);
        btn.setLayoutY(110);
        btn.setPrefWidth(80);
        btn.setStyle("-fx-base: white;");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                process();
            }
        });

        logsText = new TextArea();
        logsText.setId("logs");
        logsText.setLayoutX(10);
        logsText.setLayoutY(170);
        logsText.setMinSize(760, 160);
        logsText.setStyle("-fx-base: white;");
        logsText.textProperty().bindBidirectional(RoosterModel.logs);
        Stop[] stops = new Stop[]{new Stop(0, Color.web("0xAEBBCC")), new Stop(1, Color.web("0x6D84A3"))};
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        
        Rectangle rectangle = new Rectangle(0, 0, 780, 45);
        rectangle.setFill(linearGradient);

        Rectangle rectangle2 = new Rectangle(0, 43, 780, 320);
//        rectangle2.setFill(Color.rgb(199, 206, 213));
//         rectangle2.setFill(Color.WHITE);
        rectangle2.setFill(Color.rgb(199, 206, 213));  
        rectangle2.setId("rectangle2");

        Rectangle rectangle3 = new Rectangle(10, 54, 760, 90);
        rectangle3.setArcHeight(20);
        rectangle3.setArcWidth(20);
        rectangle3.setFill(Color.WHITE);
        rectangle3.setStroke(color);
        rectangle3.setId("rectangle3");
        
        Line line1 = new Line(10, 100, 770, 100);
        line1.setStroke(color);
        line1.setId("line1");
        
        Line line2 = new Line(10, 141, 770, 141);
        line2.setFill(color);
        line1.setId("line2");
        
        Group group = new Group(rectangle, title, rectangle2, rectangle3,
                beginDateText,
                beginDatePicker,
                endDateText,
                endDatePicker, 
               // excludeDateText,
              //  excludeDate,
                line1,
                filePathText,
                filePath,
                isHistoryCheckBox,
                btn,
                logsText);
        
        Scene scene = new Scene(group, 780, 370);
//        ScenicView.show(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("广力早到考勤统计V1.0.0");
        primaryStage.setMaxWidth(795);
        primaryStage.setMaxHeight(380);
        Image  img=new Image("file:images/rooster.bmp");
        primaryStage.getIcons().add(img);
        primaryStage.show();
    }
    /**
     * 开始处理
     */
    private void process(){
        logsText.setText("");
        if(!StringUtility.stringHasValue(getBeginDate())){
            logsText.setText("请选择开始日期！"); 
            return;
        }
        if(!StringUtility.stringHasValue(getEndDate())){
            logsText.setText("请选择结束日期！"); 
            return;
        }
        
//        if(!StringUtility.stringHasValue(getExcludeDate())){
//            String tips = "请填写不需要统计的当月日期号！\r\n";
//            tips+="默认只统计星期一到星期五的天数\r\n";
//            tips+="如果遇到节假日和加班特殊情况\r\n";
//            tips+="比如当天是星期天，是这个月10号，需要考勤统计，那么就填写+10\r\n";
//            tips+="又比如当天是8号星期五，但是是中秋节，放假，那么就填写-8\r\n";
//            tips+="多天之间用逗号隔开，比如上面的情况就可以表示为：\r\n";
//            tips+="  -8,+10";
//            logsText.setText(tips); 
//            return;
//        }
        
        if(!StringUtility.stringHasValue(getFilePath())){
            if(!isHistoryCheckBox())
            {
              logsText.setText("既然你没有选择统计数据库的历史数据，那么请填写你要导入的excel文件路径！\r\n"); 
              return; 
            }
           
        }else{
              if(!checkFileExist(getFilePath()))
             {
                 logsText.setText("填写文件路径有误，找不到文件！\r\n"); 
                 return;
             }
        }
        
        if(!isBeginLessThanEnd(getBeginDate(),getEndDate())){
             logsText.setText("开始时间大于结束时间！\r\n"); 
             return;
        }
        boolean isHistory = isHistoryCheckBox();
        HashSet<String> days = getWorkDays(getBeginDate(),getEndDate());
        Result result = new Result();
        // public void process(boolean isHistory,String filePath,String beginDate,String endDate,Result result)
        roosterRunner.process(isHistory,getFilePath(),getBeginDate(), getEndDate(),result);
         logsText.setText("生成文件完毕，请在程序目录下面查看excel文件！"); 
//        String ms ="";
//        for(String m:days)
//        {
//           ms+=m+"\r\n";
//        }
//        logsText.setText(ms); 
    }
    /**
     * 获得开始日期
     * @return 
     */
    private String getBeginDate(){
        return  (this.beginDatePicker.getValue() == null)?null:this.beginDatePicker.getValue().toString();
    }
    /**
     * 获得结束日期
     * @return 
     */
    private String getEndDate(){
        return  (this.endDatePicker.getValue() == null)?null:this.endDatePicker.getValue().toString();
    }
    /**
     * 获取是否是历史数据
     * @return 
     */
    private boolean isHistoryCheckBox(){
        return this.isHistoryCheckBox.isSelected();
    }
    
    /**
     * 判断开始日期是否小于结束日期
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 如果开始日期小于结束日期，返回true，如果开始日期大于结束日期，返回false。
     */
    private boolean isBeginLessThanEnd(String beginDate,String endDate){
        boolean isOk = true;
         DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd"); 
        try {
            Date begin = dateFormat.parse(beginDate);
            Calendar start = Calendar.getInstance();
            start.setTime(begin);
            
            Date end = dateFormat.parse(endDate);
            Calendar stop  = Calendar.getInstance();
            stop.setTime(end);
            
            long diff = stop.getTimeInMillis()- start.getTimeInMillis();
            if(diff <= 0)
            {
                return false;
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(Rooster.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return isOk;
        
    }
//    /**
//     * 获取修订的日期字符串
//     * @return 
//     */
//    private String getExcludeDate(){
//        return  (this.excludeDate.getText() == null)?null:this.excludeDate.getText().toString();
//    }
    /**
     * 获取导入文件的路径
     * @return 
     */
    private String getFilePath(){
        return  (this.filePath.getText() == null)?null:this.filePath.getText().toString();
    }
    /**
     * 判断文件是否存在
     * @param filePath
     * @return 
     */
     private  boolean checkFileExist(String filePath)
    {
        boolean isExist = true;
        try{
            File file =new File(filePath);    
           //如果文件 
           if(!file.exists())    
           {    
               isExist=false;
           }     
        }catch(Exception e){
             isExist=false;
        }
        return isExist;
    }
    
     private HashSet<String> getWorkDays(String beginDate,String endDate)
     {
         HashSet<String> workDays = new HashSet<String>();
         DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd"); 
        try {
             Date  begin = dateFormat.parse(beginDate);
             Calendar start = Calendar.getInstance();
             start.setTime(begin);
             Date end = dateFormat.parse(endDate);
             Calendar stop = Calendar.getInstance();
             stop.setTime(end);
              while((start.getTimeInMillis() - stop.getTimeInMillis())<=0)
              {
                    int weekDay = start.get(Calendar.DAY_OF_WEEK);
                    //  System.out.println("weekDay="+weekDay+" DAY_OF_MONTH="+start.get(Calendar.DAY_OF_MONTH));
                    if(weekDay == 2 || weekDay == 3||weekDay == 4||weekDay == 5||weekDay==6)
                    {
                         workDays.add(dateFormat.format(start.getTime()));
                        // System.out.println(dateFormat.format(start.getTime()));
                    }
                    start.add(Calendar.DATE, 1);
              }
        } catch (ParseException ex) {
            Logger.getLogger(Rooster.class.getName()).log(Level.SEVERE, null, ex);
        }
         return workDays;
     }
     
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
