package doordonote.ui;

import doordonote.common.Task;
import doordonote.logic.Controller;
import doordonote.logic.UIToController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Priyanka
 */
public class UI extends Application {
    
    Text output = new Text("Feedback Message");
    UIToController controller = new Controller();
    
    BorderPane border = new BorderPane();
    
    @Override
    public void start(Stage primaryStage) {
        
        border.setBottom(addVBoxB());
        border.setCenter(addHBox());
        border.setTop(addHeader());

        Scene scene = new Scene(border);  
        primaryStage.setScene(scene);
        primaryStage.setTitle("DoOrDoNote");
        primaryStage.show();
        
    }
    
    protected VBox addVBoxB() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 12, 10, 12));
        vbox.setSpacing(2);
        vbox.setStyle("-fx-background-color: #336699;");
        
        output.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        output.setFill(Color.WHITE);
        
        vbox.getChildren().addAll(output, addHBox2());
        vbox.setAlignment(CENTER);
        return vbox;
        
    }
    protected HBox addHBox2() {
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 12, 5, 12));
        hbox.setSpacing(10);
        //hbox.setStyle("-fx-background-color: #336699;");
        
        Label command = new Label("Command:");
        command.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        command.setTextFill(Color.web("#FFFFFF"));
        TextField commandBox = new TextField();
        commandBox.setPrefWidth(500);
        
        getUserInput(commandBox);  
      
        hbox.getChildren().addAll(command, commandBox);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    protected void getUserInput(TextField commandBox) {
        
        commandBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
    
        @Override
        public void handle(KeyEvent ke)
        {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if(commandBox.getText() != null) {
                    String feedback = controller.parseAndExecuteCommand(commandBox.getText());
		    if (feedback != null) {
			output.setText(feedback);
			border.setCenter(addHBox());		
		    }    
                commandBox.clear();
                }
            }
        }
        });
    }
    
    
    protected HBox addHBox() {
        
        HBox main = new HBox();
        
        main.setPadding(new Insets(40, 25, 30, 25));
        main.setSpacing(40);
        main.setStyle("-fx-background-color: #FFFFFF;");
        

        return displayTasks(main);
    }
    
    protected HBox displayTasks(HBox main) {
        
        List<Task> taskList = controller.getTasks();
        boolean haveEventsOrDeadlines = true;
        boolean haveFloatingTasks = false;
        boolean haveSameDate = true;
        int count = 1;
        int i, j;
        
        VBox vbox1 = new VBox();
        vbox1.setAlignment(TOP_CENTER);
        vbox1.setPadding(new Insets(18, 18, 18, 18));
        vbox1.setSpacing(15);
        vbox1.setPrefWidth(500);
        vbox1.setStyle("-fx-background-color: #E1F5EF;");
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(TOP_CENTER);
        vbox2.setPadding(new Insets(18, 18, 18, 18));
        vbox2.setSpacing(15);
        vbox2.setPrefWidth(500);
        vbox2.setStyle("-fx-background-color: #E1F5EF;");
        
        for(i = 0; i < taskList.size(); i++) {
            if(!(taskList.get(i).getType().equals("FLOATING_TASK"))) {
                Calendar calEnd = DateToCalendar(taskList.get(i).getEndDate());
                String day = getDay(calEnd); 
                String month = getMonth(calEnd);
                int date = calEnd.get(calEnd.DAY_OF_MONTH);
                String timeEnd = getTime(calEnd);
                Text taskDate = new Text(day + ", " + date + " " + month);
                taskDate.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
                taskDate.setTextAlignment(TextAlignment.CENTER);
                taskDate.setFill(Color.web("#0C1847"));
                Text taskDesc;
                if(taskList.get(i).getType().equals("DEADLINE_TASK")) {
                    taskDesc = new Text(count++ + ". " + "[by " + timeEnd + "] " + taskList.get(i).getDescription());
                }
                else {
                    Calendar calStart = DateToCalendar(taskList.get(i).getStartDate());
                    String timeStart = getTime(calStart);
                    taskDesc = new Text(count++ + ". " + "[" + timeStart + "-" + timeEnd + "] " + taskList.get(i).getDescription());
                }
                taskDesc.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                vbox1.getChildren().addAll(taskDate, taskDesc);
                for(j = i+1; j < taskList.size(); j++) {
                   haveSameDate = true;
                   if(!(taskList.get(j).getType().equals("FLOATING_TASK"))) {
                      Calendar calEnd2 = DateToCalendar(taskList.get(j).getEndDate());
                      String month2 = getMonth(calEnd2);
                      int date2 = calEnd2.get(calEnd2.DAY_OF_MONTH);
                      String timeEnd2 = getTime(calEnd2);
                      if((date != date2)||!(month.equals(month2)))
                          haveSameDate = false;
                      else {
                          Text taskDesc2;
                          if(taskList.get(j).getType().equals("DEADLINE_TASK")) {
                             taskDesc2 = new Text(count++ + ". " + "[by " + timeEnd2 + "] " + taskList.get(j).getDescription());
                          }
                          else {
                             Calendar calStart2 = DateToCalendar(taskList.get(j).getStartDate());
                             String timeStart2 = getTime(calStart2);
                             taskDesc2 = new Text(count++ + ". " + "[" + timeStart2 + "-" + timeEnd2 + "] " + taskList.get(j).getDescription());
                          }
                          taskDesc2.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                          vbox1.getChildren().addAll(taskDesc2);
                          i++;
                      }
                   }
                   else {
                       haveEventsOrDeadlines = false;
                   }
                   if(haveEventsOrDeadlines == false || haveSameDate == false) {
                       break;
                   }
                }
            }
            else {
                haveEventsOrDeadlines = false;
            }
      
            if(haveEventsOrDeadlines == false) {
                break;
            }

        }
        
        Text floatingHeader = new Text("Floating Tasks");
        floatingHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
        floatingHeader.setTextAlignment(TextAlignment.CENTER);
        floatingHeader.setFill(Color.web("#0C1847"));
        vbox2.getChildren().add(floatingHeader);
        
        for(i=0; i<taskList.size(); i++) {
        	if(taskList.get(i).getType().equals("FLOATING_TASK")) {
        		haveFloatingTasks = true;
                        String floatingTask = (count++ + ". " + taskList.get(i).getDescription()); 
                        Text floatingDisplay = new Text(floatingTask);
                        floatingDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                        vbox2.getChildren().add(floatingDisplay);
        	}
        }

        if(haveFloatingTasks == false) {
            Text noFloatingTasks = new Text("*none*");
            noFloatingTasks.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
            vbox2.getChildren().add(noFloatingTasks);
        }
        
        main.setAlignment(TOP_CENTER);
        main.getChildren().addAll(vbox1, vbox2);
        
        return main;
        
    }
    
    protected HBox addHeader() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 25, 20, 25));
        hbox.setStyle("-fx-background-color: #001B4D;");
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Text title = new Text("Ongoing Tasks");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        title.setFill(Color.WHITE);
        title.setEffect(ds);
        title.setCache(true);
        title.setX(10.0f);
        title.setY(270.0f);	
        
        hbox.getChildren().add(title);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    protected Calendar DateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
    
    protected String getDay(Calendar cal) {
        String day = null;
        switch(cal.get(cal.DAY_OF_WEEK)) {
                    case 1: day = "Sun";
                            break;
                    case 2: day = "Mon";
                            break;
                    case 3: day = "Tues";
                            break;
                    case 4: day = "Wed";
                            break;
                    case 5: day = "Thurs";
                            break;
                    case 6: day = "Fri";
                            break;
                    case 7: day = "Sat";
                          
                }
        
        return day;
    }
    
    protected String getMonth(Calendar cal) {
        String month = null;
        switch(cal.get(cal.MONTH)) {
                    case 0: month = "Jan";
                            break;
                    case 1: month = "Feb";
                            break;
                    case 2: month = "Mar";
                            break;
                    case 3: month = "Apr";
                            break;
                    case 4: month = "May";
                            break;
                    case 5: month = "Jun";
                            break;
                    case 6: month = "Jul";
                            break;
                    case 7: month = "Aug";
                            break;
                    case 8: month = "Sept";
                            break;
                    case 9: month = "Oct";
                            break;
                    case 10: month = "Nov";
                            break;
                    case 11: month = "Dec";
                }
        
        return month;
    }
    
    protected String getMinutes(Calendar cal) {
        String minutes;
        
        if(cal.get(cal.MINUTE) < 10) {
        	if(cal.get(cal.MINUTE) == 0) {
                minutes = null;
            }
            else {
                minutes = "0" + cal.get(cal.MINUTE);
            }
        }
        else {
            minutes = "" + cal.get(cal.MINUTE);
        }
        
        return minutes;        
    }
    
    protected String getTime(Calendar cal) {
        String time;
        String minutes = getMinutes(cal);
        int hour = cal.get(cal.HOUR_OF_DAY);
        
        if(hour > 12) {
            if(minutes != null) {
                time = (hour - 12) + ":" + minutes + "pm";
            }
            else {
                time = (hour - 12) + "pm";
            }
        }
        else {
        	if(minutes != null) {
            	if(hour == 0) {
            		
                    time = hour+12 + ":" + minutes + "am";
            	}
            	else{
            		time = hour + ":" + minutes + "am";
            	}
            }
            else {
            	if(hour == 0) {
            		time = hour+12 + "am";
            	}
            	else {
                    time = hour + "am";
            	}
            }
        }
        
        return time;  
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	launch(args);
    }
    
}
