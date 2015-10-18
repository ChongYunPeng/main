package doordonote.ui;

import doordonote.storage.Task;
import doordonote.logic.Logic;

import java.text.SimpleDateFormat;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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
    UIjava logic = new UIjava();
    
    BorderPane border = new BorderPane();
    
    @Override
    public void start(Stage primaryStage) {
        
        border.setBottom(addVBoxB());
        border.setCenter(addVBox());
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
        command.setTextFill(Color.web("#ffffff"));
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
                    String feedback = logic.parseAndExecuteCommand(commandBox.getText());
		    if (feedback != null) {
			output.setText(feedback);
			border.setCenter(addVBox());		
		    }    
                commandBox.clear();
                }
            }
        }
        });
    }
    
    
    protected VBox addVBox() {
        
        VBox main = new VBox();
        
        main.setPadding(new Insets(40, 25, 30, 25));
        main.setSpacing(40);
        main.setStyle("-fx-background-color: #FFFEE8;");
        

        return displayTasks(main);
    }
    
    protected VBox displayTasks(VBox main) {
        
        List<Task> taskList = logic.getTaskList();
        SimpleDateFormat ft = new SimpleDateFormat ("EEE, MMM d, hh:mm");
        boolean haveEvents = false;
        boolean haveDeadlines = false;
        boolean haveFloatingTasks = false;
        int count = 1;
        
        VBox vbox1 = new VBox();
        vbox1.setAlignment(CENTER);
        
        Text eventsHeader = new Text("Events");
        eventsHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
        eventsHeader.setTextAlignment(TextAlignment.CENTER);
        vbox1.getChildren().add(eventsHeader);

        for(int i=0; i<taskList.size(); i++) {
        	if(taskList.get(i).getTaskType() == 2) {
        		haveEvents = true;
                        String eventTask = count++ + ". " + taskList.get(i).getTaskDesc() + " from " + ft.format(taskList.get(i).getStartDate()) + " to " + ft.format(taskList.get(i).getEndDate()); 
                        Text eventDisplay = new Text(eventTask);
                        eventDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                        vbox1.getChildren().add(eventDisplay);
        	}
        }

        if(haveEvents == false) {
            Text noEvent = new Text("*none*");
            vbox1.getChildren().add(noEvent);
        }
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(CENTER);
        
        Text deadlineHeader = new Text("Deadlines");
        deadlineHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
        deadlineHeader.setTextAlignment(TextAlignment.CENTER);
        vbox2.getChildren().add(deadlineHeader);

        for(int i=0; i<taskList.size(); i++) {
        	if(taskList.get(i).getTaskType() == 1) {
        		haveDeadlines = true;
                        String deadlineTask = count++ + ". " + taskList.get(i).getTaskDesc() + " by " + ft.format(taskList.get(i).getEndDate()); 
                        Text deadlineDisplay = new Text(deadlineTask);
                        deadlineDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                        vbox2.getChildren().add(deadlineDisplay);
        	}
        }

        if(haveDeadlines == false) {
            Text noDeadline = new Text("*none*");
            vbox2.getChildren().add(noDeadline);
        }
        
        VBox vbox3 = new VBox();
        vbox3.setAlignment(CENTER);
        
        Text floatingHeader = new Text("Floating Tasks");
        floatingHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
        floatingHeader.setTextAlignment(TextAlignment.CENTER);
        vbox3.getChildren().add(floatingHeader);

        for(int i=0; i<taskList.size(); i++) {
        	if(taskList.get(i).getTaskType() == 0) {
        		haveFloatingTasks = true;
                        String floatingTask = (count++ + ". " + taskList.get(i).getTaskDesc()); 
                        Text floatingDisplay = new Text(floatingTask);
                        floatingDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 18));
                        vbox3.getChildren().add(floatingDisplay);
        	}
        }

        if(haveFloatingTasks == false) {
            Text noFloatingTasks = new Text("*none*");
            vbox3.getChildren().add(noFloatingTasks);
        }
        
        main.setAlignment(TOP_CENTER);
        main.getChildren().addAll(vbox1, vbox2, vbox3);
        
        return main;
        
    }
    
    protected HBox addHeader() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 25, 20, 25));
        hbox.setStyle("-fx-background-color: #001B4D;");
        
        Text title = new Text("Ongoing Tasks");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        title.setFill(Color.WHITE);
        
        hbox.getChildren().add(title);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
