package doordonote.ui;

import doordonote.common.Task;
import doordonote.logic.Controller;
import doordonote.logic.UIToLogic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
	
	private static final String STATE_UPDATE = "Update";
	private static final String STATE_DISPLAY = "Display";
	private static final String STATE_HELP = "Help";
	private static final String STATE_HELP_ADD = "Helpadd";
	private static final String STATE_HELP_DELETE = "Helpdelete";
	private static final String STATE_HELP_UPDATE = "Helpupdate";
	private static final String STATE_HELP_FIND = "Helpfind";
	private static final String STATE_HELP_FINISH = "Helpfinish";
	private static final String STATE_HELP_PATH = "Helppath";
	private static final String STATE_HELP_RESTORE = "Helprestore";
	private static final String STATE_HELP_GET = "Helpget";
	private static final String STATE_FIND = "Find";
	private static final String STATE_HOME = "Home";
	private static final String STATE_DISPLAY_FINISH = "Display finish";
	private static final String STATE_DISPLAY_DELETE = "Display delete";
    
    Text output = new Text("Feedback Message");
    UIToLogic controller = new Controller();
    
    BorderPane border = new BorderPane();
    Scene scene = new Scene(border);
    
    @Override
    public void start(Stage primaryStage) {
        
        border.setBottom(addVBoxB());
        border.setCenter(addHBox());
        border.setTop(addHeader());
  
        primaryStage.setScene(scene);
        primaryStage.setTitle("DoOrDoNote");
//        primaryStage.getIcons().add(new Image("icon.jpg"));
        primaryStage.show();
        
    }
    
    protected VBox addVBoxB() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 12, 10, 12));
        vbox.setSpacing(5);
        vbox.setStyle("-fx-background-color: #336699;");
        
        HBox hb = new HBox();
        hb.setStyle("-fx-background-color: #F5F8FF;"); //E9EFFD
        hb.setAlignment(CENTER);
        hb.setPadding(new Insets(5, 5, 5, 5));
        output.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
        output.setFill(Color.web("#00811C"));
        hb.getChildren().add(output);
        
        vbox.getChildren().addAll(hb, addHBox2());
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
         String feedback;
        @Override
        public void handle(KeyEvent ke)
        {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if(commandBox.getText() != null) {
                    try {
                    	feedback = controller.parseAndExecuteCommand(commandBox.getText());
                    	String state = controller.getState();
                    	switch(state) {
                    	case STATE_HELP: {
                    	   Stage helpStage = createHelpWindow();
 	                       helpStage.show();
 	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
 	                          @Override
 	                          public void handle(KeyEvent evt) {
 	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
 	                                helpStage.close();
 	                            }
 	                          }
 	                       });
 	                      commandBox.clear();
 	                       break;
                    	}
                    	case STATE_HELP_ADD: {
                    		Stage helpStage = createHelpAddWindow();
  	                       helpStage.show();
  	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
  	                          @Override
  	                          public void handle(KeyEvent evt) {
  	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
  	                                helpStage.close();
  	                            }
  	                          }
  	                       });
  	                     commandBox.clear();
  	                     
  	                       break;
                    	}
                    	case STATE_HELP_DELETE: {
                    		Stage helpStage = createHelpDeleteWindow();
   	                       helpStage.show();
   	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
   	                          @Override
   	                          public void handle(KeyEvent evt) {
   	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
   	                                helpStage.close();
   	                            }
   	                          }
   	                       });
   	                     commandBox.clear();
   	                     
   	                       break;
                    	}
                    	case STATE_HELP_UPDATE: {
                    		Stage helpStage = createHelpUpdateWindow();
    	                       helpStage.show();
    	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	                          @Override
    	                          public void handle(KeyEvent evt) {
    	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
    	                                helpStage.close();
    	                            }
    	                          }
    	                       });
    	                     commandBox.clear();
    	                     
    	                       break;
                    	}
                    	case STATE_HELP_FIND: {
                    		Stage helpStage = createHelpFindWindow();
 	                       helpStage.show();
 	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
 	                          @Override
 	                          public void handle(KeyEvent evt) {
 	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
 	                                helpStage.close();
 	                            }
 	                          }
 	                       });
 	                     commandBox.clear();
 	                     
 	                       break;
                    	}
                    	case STATE_HELP_FINISH: {
                    		Stage helpStage = createHelpFinishWindow();
  	                       helpStage.show();
  	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
  	                          @Override
  	                          public void handle(KeyEvent evt) {
  	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
  	                                helpStage.close();
  	                            }
  	                          }
  	                       });
  	                     commandBox.clear();
  	                     
  	                       break;
                    	}
                    	case STATE_HELP_PATH: {
                    		Stage helpStage = createHelpPathWindow();
   	                       helpStage.show();
   	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
   	                          @Override
   	                          public void handle(KeyEvent evt) {
   	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
   	                                helpStage.close();
   	                            }
   	                          }
   	                       });
   	                     commandBox.clear();
   	                     
   	                       break;
                    	}
                    	case STATE_HELP_RESTORE: {
                    		Stage helpStage = createHelpRestoreWindow();
    	                       helpStage.show();
    	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	                          @Override
    	                          public void handle(KeyEvent evt) {
    	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
    	                                helpStage.close();
    	                            }
    	                          }
    	                       });
    	                     commandBox.clear();
    	                     
    	                       break;
                    	}
                    	case STATE_HELP_GET: {
                    		Stage helpStage = createHelpGetWindow();
 	                       helpStage.show();
 	                       helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
 	                          @Override
 	                          public void handle(KeyEvent evt) {
 	                            if (evt.getCode().equals(KeyCode.ESCAPE)|| evt.getCode().equals(KeyCode.ENTER)) {
 	                                helpStage.close();
 	                            }
 	                          }
 	                       });
 	                     commandBox.clear();
 	                     
 	                       break;
                    	}
                    	case STATE_DISPLAY_FINISH: {
                    		border.setCenter(addHBox());
                    		border.setTop(addCompleteHeader());
		                    commandBox.clear();
		                    output.setText("");
		                    break;
                    	}
                    	case STATE_DISPLAY_DELETE: {
                    		border.setCenter(addHBox());
                    		border.setTop(addDeleteHeader());
		                    commandBox.clear();
		                    output.setText("");
		                    break;
                    	}
                    	case STATE_FIND: {
                    		border.setCenter(addHBox());
                    		border.setTop(addFindHeader());
		                    commandBox.clear();
		                    output.setText(feedback);
		                    break;         		
                    	}
                    	case STATE_DISPLAY: {
                    		output.setText(feedback);   
		                	output.setFill(Color.web("#00811C"));
				            border.setCenter(addHBox());
		                    commandBox.clear();
		                    break;
                    	}
                    	case STATE_HOME: {
                    		output.setText(feedback);   
		                	output.setFill(Color.web("#00811C"));
				            border.setCenter(addHBox());
                    		border.setTop(addHeader());
                    		commandBox.clear();
                    		break;
                    	}
                    	case STATE_UPDATE: {
                    	    commandBox.setText(controller.getTaskToBeUpdated());
                    	    commandBox.positionCaret(controller.getTaskToBeUpdated().length() + 1);
                    	    output.setText(feedback);
                    	    output.setFill(Color.web("#00811C"));
                    	    border.setCenter(addHBox());
                    		border.setTop(addHeader());
                    	    break;
                    	}
                    	}
                    }
                    catch (Exception e) {
                    	feedback = e.getMessage();
                    	output.setText(feedback);
                    	output.setFill(Color.web("#F20505"));
                    }
		            
                }
            }
                
             if (ke.getCode().equals(KeyCode.ESCAPE)) {
                	try {
                    	feedback = controller.parseAndExecuteCommand("home");
                    	output.setText(feedback);   
	                	output.setFill(Color.web("#00811C"));
			            border.setCenter(addHBox());
                		border.setTop(addHeader());
                		commandBox.clear();
                	}
                	catch (Exception e) {
                    	feedback = e.getMessage();
                    	output.setText(feedback);
                    	output.setFill(Color.web("#F20505"));
                    }
                }
        }
        });
    }
    
    public Stage createHelpWindow() {
    	Stage stage = new Stage();
    	
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! This is the page to provide you with all the help you need for DoOrDoNote");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Text tableHeader = new Text("Here is a table of all the commands you can use:");
        tableHeader.setFont(Font.font("Calibri", FontWeight.NORMAL, 17));
        tableHeader.setFill(Color.web("#00143E"));
        
        Image image2 = new Image("help_resize.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, tableHeader, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpAddWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is a table of the commands you can use for ADD:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpadd.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpDeleteWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is the command you can use for DELETE:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpdelete.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpUpdateWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is the command you can use for UPDATE:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpupdate.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpFindWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is a table of the commands you can use for FIND:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpfind.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpFinishWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is the command you can use for FINISH:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpfinish.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpPathWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here the command you can use for PATH:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helppath.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpRestoreWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is the command you can use for RESTORE:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helprestore.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
    }
    
    public Stage createHelpGetWindow() {
    	Stage stage = new Stage();
    	HBox hb = new HBox();
    	hb.setSpacing(15);
    	hb.setAlignment(CENTER);
        Image image1 = new Image("question_mark.png");
        ImageView imv1 = new ImageView(image1);
        imv1.setFitWidth(30);
        imv1.setPreserveRatio(true);
        imv1.setSmooth(true);
        imv1.setCache(true);
        Text helpHeader = new Text("Hello! Here is the command you can use for GET:");
        helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        helpHeader.setFill(Color.web("#00143E")); //#00143E
        hb.getChildren().addAll(helpHeader, imv1);
        
        Image image2 = new Image("helpget.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setFitWidth(700);
        imv2.setPreserveRatio(true);
        imv2.setSmooth(true);
        imv2.setCache(true);
        
        Button bt = new Button(" OK! ");
       
        VBox vb = new VBox();
        vb.setPadding(new Insets(15, 30, 10, 30));
        vb.setSpacing(10);
        vb.getChildren().addAll(hb, imv2, bt);
        vb.setAlignment(TOP_CENTER);
        vb.setStyle("-fx-background-color: #eff4ff;");
        Scene sc = new Scene(vb);
        
        stage.setTitle("Help!");
        stage.getIcons().add(image1);
        stage.setScene(sc);
        
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            stage.close();      
            }
        });
        
        return stage;
    	
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
        
        VBox v1 = new VBox();
        v1.setPrefWidth(500);
        v1.setStyle("-fx-background-color: #E1F5EF;");
        
        VBox vbox1 = new VBox();
        vbox1.setAlignment(TOP_CENTER);
        vbox1.setPadding(new Insets(18, 18, 18, 18));
        vbox1.setSpacing(15);
        vbox1.setPrefWidth(500);
        vbox1.setStyle("-fx-background-color: #E1F5EF;");
        
        ScrollPane sp1 = new ScrollPane();
        VBox.setVgrow(sp1, Priority.ALWAYS);
        //sp1.setVmax(440);
        sp1.setFitToHeight(true);
        sp1.setFitToWidth(true);
        sp1.setPrefSize(115, 150);
        sp1.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp1.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        double scrollPaneIncrement = 0.2;
        
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                if (evt.getCode().equals(KeyCode.UP)) {
                	if (sp1.getVvalue() > sp1.getVmin()) {
                        sp1.setVvalue(sp1.getVvalue() - scrollPaneIncrement);
                    }
                }
                if (evt.getCode().equals(KeyCode.DOWN)) {
                	if (sp1.getVvalue() < sp1.getVmax()) {
                        sp1.setVvalue(sp1.getVvalue() + scrollPaneIncrement);
                    }
                }
            }
        }); 
        
        VBox v2 = new VBox();
        v2.setPrefWidth(500);
        v2.setStyle("-fx-background-color: #E1F5EF;");
        
        VBox vbox2 = new VBox();
        vbox2.setAlignment(TOP_CENTER);
        vbox2.setPadding(new Insets(18, 18, 18, 18));
        vbox2.setSpacing(15);
        vbox2.setPrefWidth(500);
        vbox2.setStyle("-fx-background-color: #E1F5EF;");
        
        ScrollPane sp2 = new ScrollPane();
        VBox.setVgrow(sp2, Priority.ALWAYS);
        //sp2.setVmax(440);
        sp2.setFitToHeight(true);
        sp2.setFitToWidth(true);
        sp2.setPrefSize(115, 150);
        sp2.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp2.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent evt) {
                if (evt.getCode().equals(KeyCode.UP)) {
                	if (sp2.getVvalue() > sp2.getVmin()) {
                        sp2.setVvalue(sp2.getVvalue() - scrollPaneIncrement);
                    }
                }
                if (evt.getCode().equals(KeyCode.DOWN)) {
                	if (sp2.getVvalue() < sp2.getVmax()) {
                        sp2.setVvalue(sp2.getVvalue() + scrollPaneIncrement);
                    }
                }
            }
        });
        
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
        
        sp1.setContent(vbox1);
        v1.getChildren().addAll(sp1);
        
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
        
        sp2.setContent(vbox2);
        v2.getChildren().addAll(sp2);
        
        main.setAlignment(TOP_CENTER);
        main.getChildren().addAll(v1, v2);
        
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
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 26));
        title.setFill(Color.WHITE);
        title.setEffect(ds);
        title.setCache(true);
        title.setX(10.0f);
        title.setY(270.0f);	
        
        hbox.getChildren().add(title);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    protected HBox addCompleteHeader() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 25, 20, 25));
        hbox.setStyle("-fx-background-color: #001B4D;");
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Text title = new Text("Finished Tasks");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 26));
        title.setFill(Color.WHITE);
        title.setEffect(ds);
        title.setCache(true);
        title.setX(10.0f);
        title.setY(270.0f);	
        
        hbox.getChildren().add(title);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    protected HBox addDeleteHeader() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 25, 20, 25));
        hbox.setStyle("-fx-background-color: #001B4D;");
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Text title = new Text("Deleted Tasks");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 26));
        title.setFill(Color.WHITE);
        title.setEffect(ds);
        title.setCache(true);
        title.setX(10.0f);
        title.setY(270.0f);	
        
        hbox.getChildren().add(title);
        hbox.setAlignment(CENTER);
        
        return hbox;
    }
    
    protected HBox addFindHeader() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(20, 25, 20, 25));
        hbox.setStyle("-fx-background-color: #001B4D;");
        
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Text title = new Text("Ongoing Tasks Found");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 26));
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
        else if (hour < 12){
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
        else {
        	if(minutes != null) {
                time = hour + ":" + minutes + "pm";
            }
            else {
                time = hour + "pm";
            }
        }
        
        return time;  
        
    }
    
    public static String getFirstWord(String input) {
		String commandTypeString = input.trim().split("\\s+")[0];
		return commandTypeString.toLowerCase();
	}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	launch(args);
    }
    
}
