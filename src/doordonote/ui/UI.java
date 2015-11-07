//@@author A0132785Y
package doordonote.ui;

import doordonote.common.Task;
import doordonote.logic.Logic;
import doordonote.logic.UIToLogic;
import doordonote.logic.UIState;
import doordonote.ui.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.lang.StringBuilder;

import org.apache.log4j.BasicConfigurator;
import org.apache.commons.lang.WordUtils;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.TOP_CENTER;
import javafx.geometry.Pos;

import javafx.scene.Node;
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
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.shape.Shape;

/**
 *
 * @author Priyanka
 */
public class UI extends Application {

	private static final String HELP = "help";
	private static final String HELP_ADD = "add";
	private static final String HELP_DELETE = "delete";
	private static final String HELP_UPDATE = "update";
	private static final String HELP_FIND = "find";
	private static final String HELP_FINISH = "finish";
	private static final String HELP_PATH = "path";
	private static final String HELP_RESTORE = "restore";
	private static final String HELP_GET = "get";

	private static final String COMMAND_HOME = "home";
	private static final String COMMAND_DISPLAY_FINISH = "view finished";
	private static final String COMMAND_DISPLAY_DELETE = "view deleted";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";
	
	private static final String MESSAGE_WELCOME = "Welcome to DoOrDoNote! "
			+ "Type in \"help\" for all the help you need!";
	private static final String MESSAGE_TABLE = "Here is a table of all the commands you can use:";
	private static final String MESSAGE_HELP = "Hello! "
			+ "This is the page to provide you with all the help you need for DoOrDoNote";
	
	private static final String TITLE_APP = "DoOrDoNote";
	private static final String TITLE_HOME = "Home";
	private static final String TITLE_HELP = "Help!";
	
	private static final String LABEL_OK = " OK ";

	private Text output = new Text(MESSAGE_WELCOME);
	private Text title = new Text(TITLE_HOME);
	
	private int count;

	private UIToLogic logic = null;
	private DateUtil util;

	public UI() {
		try {
			logic = new Logic();
			util = new DateUtil();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	BorderPane border = new BorderPane();
	Scene scene = new Scene(border);

	@Override
	public void start(Stage primaryStage) {
		UIState state = new UIState();
		border.setBottom(addBottomArea());
		border.setCenter(addMainDisplay(0, state.getDisplayType()));
		border.setTop(addHeader(state.getDisplayType()));

		scene.getStylesheets().add("UiStyleSheet.css");
		
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE_APP);
		primaryStage.getIcons().add(new Image("icon1.jpg"));
		primaryStage.show();
		primaryStage.setMaximized(true);

	}

	protected VBox addBottomArea() {
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 12, 10, 12));
		vBox.setSpacing(5);
		vBox.setStyle("-fx-background-color: #383737;");

		HBox hBox = new HBox();
		hBox.setStyle("-fx-background-color: #F0F0F0;"); //E9EFFD #F5F8FF
		hBox.setAlignment(CENTER);
		hBox.setPadding(new Insets(5, 5, 5, 5));
		output.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
		//output.getStyleClass().add("text");
		output.setFill(Color.web("#00811C")); //use .text from css
		hBox.getChildren().add(output);

		vBox.getChildren().addAll(hBox, addCommandArea());
		vBox.setAlignment(CENTER);
		return vBox;

	}
	protected HBox addCommandArea() {

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(5, 12, 5, 12));
		hBox.setSpacing(10);
		//hbox.setStyle("-fx-background-color: #336699;");

		Label command = new Label("Command:");
		command.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
		command.setTextFill(Color.web("#FFFFFF"));
		TextField commandBox = new TextField();
		commandBox.setPrefWidth(500);

		getUserInput(commandBox);  

		hBox.getChildren().addAll(command, commandBox);
		hBox.setAlignment(CENTER);

		return hBox;
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
							feedback = logic.parseAndExecuteCommand(commandBox.getText());
							UIState state = logic.getState();

							if(state.getHelpBox() == null) {
								output.setText(feedback);
								output.setFill(Color.web("#00811C"));
								border.setCenter(addMainDisplay(state.getIdNewTask()+1, state.getDisplayType()));
								border.setTop(addHeader(state.getDisplayType()));
								if(state.getTitle() != null) {
									title.setText(state.getTitle());
								}
								if(state.getInputBox() == null || state.getInputBox() == "") {
									commandBox.clear();
								}
								else {
									commandBox.setText(state.getInputBox());
									commandBox.positionCaret(state.getInputBox().length() + 1);
								}
							}
							else {
								output.setText(feedback);
								output.setFill(Color.web("#00811C"));

								Stage helpStage;
								switch(state.getHelpBox()) {
								  case HELP_ADD : {
									// Fallthrough
								  }
								  case HELP_DELETE : {
									// Fallthrough
								  }
								  case HELP_FIND : {
									// Fallthrough
								  }
								  case HELP_FINISH : {
									// Fallthrough
								  }
								  case HELP_GET : {
									// Fallthrough
								  }
								  case HELP_PATH : {
									// Fallthrough
								  }
								  case HELP_RESTORE : {
									// Fallthrough
								  }
								  case HELP_UPDATE : {
									helpStage = createHelpCommandWindow(state.getHelpBox());
									break;
								  }
								  default: {
									helpStage = createHelpWindow();
									break;
								  }
								}

								helpStage.show();
								helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent evt) {
										if (evt.getCode().equals(KeyCode.ESCAPE)|| 
												evt.getCode().equals(KeyCode.ENTER)) {
											helpStage.close();
										}
									}
								});
								commandBox.clear();
							}
						}
						catch (Exception e) {
							feedback = e.getMessage();
							UIState state = new UIState();
							border.setCenter(addMainDisplay(0, state.getDisplayType()));
							output.setText(feedback);
							output.setFill(Color.web("#F20505"));
						}
					}
				}

				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					try {
						UIState state = logic.getState();
						feedback = logic.parseAndExecuteCommand(COMMAND_HOME);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
						border.setCenter(addMainDisplay(0, state.getDisplayType()));
						border.setTop(addHeader(state.getDisplayType()));
						if(state.getTitle() != null) {
							title.setText(state.getTitle());
						}
						commandBox.clear();
					}
					catch (Exception e) {
						feedback = e.getMessage();
						output.setText(feedback);
						output.setFill(Color.web("#F20505"));
					}
				}

				if (ke.getCode().equals(KeyCode.Z) && ke.isControlDown()) {
					try {
						UIState state = logic.getState();
						feedback = logic.parseAndExecuteCommand(COMMAND_UNDO);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
						border.setCenter(addMainDisplay(0, state.getDisplayType()));
						border.setTop(addHeader(state.getDisplayType()));
						if(state.getTitle() != null) {
							title.setText(state.getTitle());
						}
						commandBox.clear();
					}
					catch (Exception e) {
						feedback = e.getMessage();
						output.setText(feedback);
						output.setFill(Color.web("#F20505"));
					}
				}

				if (ke.getCode().equals(KeyCode.Y) && ke.isControlDown()) {
					try {
						UIState state = logic.getState();
						feedback = logic.parseAndExecuteCommand(COMMAND_REDO);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
						border.setCenter(addMainDisplay(0, state.getDisplayType()));
						border.setTop(addHeader(state.getDisplayType()));
						if(state.getTitle() != null) {
							title.setText(state.getTitle());
						}
						commandBox.clear();
					}
					catch (Exception e) {
						feedback = e.getMessage();
						output.setText(feedback);
						output.setFill(Color.web("#F20505"));
					}
				}

				if (ke.getCode().equals(KeyCode.D) && ke.isControlDown()) {
					try {
						UIState state = logic.getState();
						feedback = logic.parseAndExecuteCommand(COMMAND_DISPLAY_DELETE);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
						border.setCenter(addMainDisplay(0, state.getDisplayType()));
						border.setTop(addHeader(state.getDisplayType()));
						if(state.getTitle() != null) {
							title.setText(state.getTitle());
						}
						commandBox.clear();
					}
					catch (Exception e) {
						feedback = e.getMessage();
						output.setText(feedback);
						output.setFill(Color.web("#F20505"));
					}
				}

				if (ke.getCode().equals(KeyCode.F) && ke.isControlDown()) {
					try {
						UIState state = logic.getState();
						feedback = logic.parseAndExecuteCommand(COMMAND_DISPLAY_FINISH);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
						border.setCenter(addMainDisplay(0, state.getDisplayType()));
						border.setTop(addHeader(state.getDisplayType()));
						if(state.getTitle() != null) {
							title.setText(state.getTitle());
						}
						commandBox.clear();
					}
					catch (Exception e) {
						feedback = e.getMessage();
						output.setText(feedback);
						output.setFill(Color.web("#F20505"));
					}
				}

				if (ke.getCode().equals(KeyCode.H) && ke.isControlDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(HELP);
						output.setText(feedback);   
						output.setFill(Color.web("#00811C"));
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

	protected Stage createHelpWindow() {
		Stage stage = new Stage();

		HBox hBox = new HBox();
		hBox.setSpacing(15);
		hBox.setAlignment(CENTER);
		Image image1 = new Image("question_mark.png");
		ImageView imv1 = new ImageView(image1);
		imv1.setFitWidth(30);
		imv1.setPreserveRatio(true);
		imv1.setSmooth(true);
		imv1.setCache(true);
		Text helpHeader = new Text(MESSAGE_HELP);
		helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
		helpHeader.setFill(Color.web("#00143E")); //#00143E
		hBox.getChildren().addAll(helpHeader, imv1);

		Text tableHeader = new Text(MESSAGE_TABLE);
		tableHeader.setFont(Font.font("Calibri", FontWeight.NORMAL, 17));
		tableHeader.setFill(Color.web("#00143E"));

		Image image2 = new Image("help_resize.jpg");
		ImageView imv2 = new ImageView(image2);
		imv2.setFitWidth(700);
		imv2.setPreserveRatio(true);
		imv2.setSmooth(true);
		imv2.setCache(true);

		Button button = new Button(LABEL_OK);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(15, 30, 10, 30));
		vBox.setSpacing(10);
		vBox.getChildren().addAll(hBox, tableHeader, imv2, button);
		vBox.setAlignment(TOP_CENTER);
		vBox.setStyle("-fx-background-color: #eff4ff;");
		Scene sc = new Scene(vBox);

		stage.setTitle(TITLE_HELP);
		stage.getIcons().add(image1);
		stage.setScene(sc);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.close();      
			}
		});

		return stage;

	}

	protected Stage createHelpCommandWindow(String commandType) {
		Stage stage = new Stage();
		HBox hBox = new HBox();
		hBox.setSpacing(15);
		hBox.setAlignment(CENTER);
		Image image1 = new Image("question_mark.png");
		ImageView imv1 = new ImageView(image1);
		imv1.setFitWidth(30);
		imv1.setPreserveRatio(true);
		imv1.setSmooth(true);
		imv1.setCache(true);
		
		Text helpHeader = new Text("Hello! Here is a table of the commands you can use for " + 
		                           commandType.toUpperCase() + ":");
		helpHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
		helpHeader.setFill(Color.web("#00143E")); //#00143E
		hBox.getChildren().addAll(helpHeader, imv1);
		
		Image image2;
        switch(commandType) {
          case HELP_ADD : {
        	image2 = new Image("helpadd.jpg");
        	break;
          }
          case HELP_DELETE : {
        	image2 = new Image("helpdelete.jpg");
        	break;
          }
          case HELP_FIND : {
        	image2 = new Image("helpfind.jpg");
        	break;
          }
          case HELP_FINISH : {
        	image2 = new Image("helpfinish.jpg");
        	break;
          }
          case HELP_GET : {
        	image2 = new Image("helpget.jpg");
        	break;
          }
          case HELP_PATH : {
        	image2 = new Image("helppath.jpg");
        	break;
          }
          case HELP_RESTORE : {
        	image2 = new Image("helprestore.jpg");
        	break;
          }
          case HELP_UPDATE : {
        	image2 = new Image("helpupdate.jpg");
          }
          default : {
        	  image2 = new Image("help_resize.jpg");
          }
        }
		
		ImageView imv2 = new ImageView(image2);
		imv2.setFitWidth(700);
		imv2.setPreserveRatio(true);
		imv2.setSmooth(true);
		imv2.setCache(true);

		Button button = new Button(LABEL_OK);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(15, 30, 10, 30));
		vBox.setSpacing(10);
		vBox.getChildren().addAll(hBox, imv2, button);
		vBox.setAlignment(TOP_CENTER);
		vBox.setStyle("-fx-background-color: #eff4ff;");
		Scene sc = new Scene(vBox);

		stage.setTitle(TITLE_HELP);
		stage.getIcons().add(image1);
		stage.setScene(sc);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				stage.close();      
			}
		});

		return stage;

	}

	protected HBox addMainDisplay(int n, doordonote.logic.UIState.ListType listType) {

		HBox main = new HBox();

		main.setPadding(new Insets(40, 25, 30, 25));
		main.setSpacing(40);
		//main.setStyle("-fx-background-color: #FFFFFF;");
		main.setStyle("-fx-background-image: url('whitee.png');" +
		           "-fx-background-size: 100% 100%; " +
		           "-fx-background-repeat: no-repeat;");

		
		return displayTasks(main, n, listType);
	}

	protected HBox displayTasks(HBox main, int n, doordonote.logic.UIState.ListType listType) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		List<Task> taskList = logic.getTasks();
		boolean haveEventsSpanningDays = checkForEventsSpanningDays(taskList, formatter);
		boolean isHome = listType.equals(doordonote.logic.UIState.ListType.NORMAL); 
		System.out.println(isHome);
		count = 1;

		VBox v1 = new VBox();
		v1.setPrefWidth(500);
		v1.setStyle("-fx-background-color: #E1F5EF;");

		ScrollPane sp1 = new ScrollPane();
		VBox.setVgrow(sp1, Priority.ALWAYS);
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

		VBox v2_1 = new VBox();
		v2_1.setPrefWidth(500);
		v2_1.setStyle("-fx-background-color: #FFF3F3;");

		VBox v2_2 = new VBox();
		v2_2.setPrefWidth(500);
		v2_2.setStyle("-fx-background-color: #F9FFC6;");

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
		
		VBox singleDayTasks = displaySingleDayTasks(n, isHome, taskList, formatter);

		sp1.setContent(singleDayTasks);
		v1.getChildren().addAll(sp1);

		if(haveEventsSpanningDays == true) {

			ScrollPane sp3 = new ScrollPane();
			VBox.setVgrow(sp3, Priority.ALWAYS);
			//sp2.setVmax(440);
			sp3.setFitToHeight(true);
			sp3.setFitToWidth(true);
			sp3.setPrefSize(115, 150);
			sp3.setHbarPolicy(ScrollBarPolicy.NEVER);
			sp3.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

			scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent evt) {
					if (evt.getCode().equals(KeyCode.UP)) {
						if (sp3.getVvalue() > sp3.getVmin()) {
							sp3.setVvalue(sp3.getVvalue() - scrollPaneIncrement);
						}
					}
					if (evt.getCode().equals(KeyCode.DOWN)) {
						if (sp3.getVvalue() < sp3.getVmax()) {
							sp3.setVvalue(sp3.getVvalue() + scrollPaneIncrement);
						}
					}
				}
			});
			
			VBox eventsSpanningDays = displayEventsSpanningDays(n, isHome, taskList, formatter);

			sp3.setContent(eventsSpanningDays);
			v2_1.getChildren().addAll(sp3);
		}

        VBox floatingTasks = displayFloatingTasks(n, isHome, taskList);
		sp2.setContent(floatingTasks);
		v2_2.getChildren().addAll(sp2);

		if(haveEventsSpanningDays == true) {
			VBox v2 = new VBox();
			v2.setSpacing(10);
			v2_1.setPrefHeight(235);
			v2_2.setPrefHeight(235);
			v2.getChildren().addAll(v2_1, v2_2);
			main.getChildren().addAll(v1, v2);
		}
		else {
			main.getChildren().addAll(v1, v2_2);
		}

		main.setAlignment(TOP_CENTER);


		return main;

	}
	
	protected boolean checkForEventsSpanningDays(List<Task> taskList, SimpleDateFormat formatter) {
		boolean haveEventsSpanningDays = false;
		
		for(int i = 0; i < taskList.size(); i++) {
			if(taskList.get(i).getType().equals("EVENT_TASK")) {
				
					String startDate = formatter.format(taskList.get(i).getStartDate());
					String endDate = formatter.format(taskList.get(i).getEndDate());


					if(!(startDate.equals(endDate))) {
						haveEventsSpanningDays = true;
						break;
					}
			}
		}
		
		return haveEventsSpanningDays;		
	}
	
	protected VBox displaySingleDayTasks(int n, boolean isHome, List<Task> taskList,
			                             SimpleDateFormat formatter) {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #E1F5EF;");
		
		boolean haveEventsOrDeadlines = true;
		boolean haveSameDate = true;

		for(int i = 0; i < taskList.size(); i++) {
			if(!(taskList.get(i).getType().equals("FLOATING_TASK"))) {
				if(taskList.get(i).getType().equals("EVENT_TASK")) {
					String startDate = formatter.format(taskList.get(i).getStartDate());
					String endDate = formatter.format(taskList.get(i).getEndDate());


					if(!(startDate.equals(endDate))) {
						continue;
					}
				}

				Calendar calEnd = DateToCalendar(taskList.get(i).getEndDate());
				String day = getDay(calEnd); 
				String month = getMonth(calEnd);
				int date = calEnd.get(calEnd.DAY_OF_MONTH);
				String timeEnd = getTime(calEnd);
				Text taskDate;
				if(checkForToday(taskList.get(i).getEndDate())) {
					taskDate = new Text("Today, " + day + ", " + date + " " + month);
				}
				else {
					taskDate = new Text(day + ", " + date + " " + month);
				}
				taskDate.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
				taskDate.setTextAlignment(TextAlignment.CENTER);
				taskDate.setFill(Color.web("#0C1847"));

				HBox dates = new HBox();
				dates.setAlignment(TOP_CENTER);
				dates.getChildren().add(taskDate);

				Text taskDesc;
				String task ;
				if(taskList.get(i).getType().equals("DEADLINE_TASK")) {
					task = count++ + ". " + "[by " + timeEnd + "] " + taskList.get(i).getDescription();
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
					FillTransition colour;
					if(checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
						taskDesc.setFill(Color.RED);
						colour = changeColour(taskDesc, Color.RED);
					}
					else {
						colour = changeColour(taskDesc, Color.BLACK);
					}
					if(count == n+1) {
						Timeline blinker = createBlinker(taskDesc);
						SequentialTransition blink = new SequentialTransition(
								taskDesc,
								blinker
								);

						blink.play();
						colour.play();
					}
				}
				else {
					Calendar calStart = DateToCalendar(taskList.get(i).getStartDate());
					String timeStart = getTime(calStart);
					task = count++ + ". " + "[" + timeStart + "-" + timeEnd + "] " + 
					       taskList.get(i).getDescription();
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
					FillTransition colour;
					if(checkForOngoing(taskList.get(i).getStartDate(), taskList.get(i).getEndDate()) 
							&& isHome) {
						taskDesc.setFill(Color.web("#0F6F00"));
						colour = changeColour(taskDesc, Color.web("#0F6F00"));
					}
					else if(checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
						taskDesc.setFill(Color.RED);
						colour = changeColour(taskDesc, Color.RED);
					}
					else {
						colour = changeColour(taskDesc, Color.BLACK);
					}
					if(count == n+1) {
						Timeline blinker = createBlinker(taskDesc);
						SequentialTransition blink = new SequentialTransition(
								taskDesc,
								blinker
								);

						blink.play();
						colour.play();
					}
				}

				taskDesc.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
				vBox.getChildren().addAll(dates, taskDesc);
				for(int j = i+1; j < taskList.size(); j++) {
					haveSameDate = true;
					if(!(taskList.get(j).getType().equals("FLOATING_TASK"))) {
						if(taskList.get(j).getType().equals("EVENT_TASK")) {
							String startDate = formatter.format(taskList.get(j).getStartDate());
							String endDate = formatter.format(taskList.get(j).getEndDate());


							if(!(startDate.equals(endDate))) {
								continue;
							}
						}
						Calendar calEnd2 = DateToCalendar(taskList.get(j).getEndDate());
						String month2 = getMonth(calEnd2);
						int date2 = calEnd2.get(calEnd2.DAY_OF_MONTH);
						String timeEnd2 = getTime(calEnd2);
						if((date != date2)||!(month.equals(month2)))
							haveSameDate = false;
						else {
							Text taskDesc2;
							FillTransition colour;
							if(taskList.get(j).getType().equals("DEADLINE_TASK")) {
								task = count++ + ". " + "[by " + timeEnd2 + "] " + taskList.get(j).getDescription();
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));

								if(checkForOverdue(taskList.get(j).getEndDate()) && isHome) {
									taskDesc2.setFill(Color.RED);
									colour = changeColour(taskDesc2, Color.RED);
								}
								else {
									colour = changeColour(taskDesc2, Color.BLACK);
								}
							}
							else {
								Calendar calStart2 = DateToCalendar(taskList.get(j).getStartDate());
								String timeStart2 = getTime(calStart2);
								task = count++ + ". " + "[" + timeStart2 + "-" + timeEnd2 + "] " + 
								       taskList.get(j).getDescription();
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));
								if(checkForOngoing(taskList.get(j).getStartDate(), taskList.get(j).getEndDate()) 
										&& isHome) {
									taskDesc2.setFill(Color.web("#0F6F00"));
									colour = changeColour(taskDesc2, Color.web("#0F6F00"));
								}
								else if(checkForOverdue(taskList.get(j).getEndDate())&& isHome) {
									taskDesc2.setFill(Color.RED);
									colour = changeColour(taskDesc2, Color.RED);
								}
								else {
									colour = changeColour(taskDesc2, Color.BLACK);
								}
							}
							taskDesc2.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
							if(count == n+1) {
								Timeline blinker = createBlinker(taskDesc2);                       
								SequentialTransition blink = new SequentialTransition(
										taskDesc2,
										blinker
										);

								blink.play();
								colour.play();
							}
							vBox.getChildren().addAll(taskDesc2);
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
		
		return vBox;
	}
	
	protected VBox displayEventsSpanningDays(int n, boolean isHome, List<Task> taskList,
			                                 SimpleDateFormat formatter) {

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #FFF3F3;");

		HBox events = new HBox();
		events.setAlignment(Pos.TOP_CENTER);
		Text eventsHeader = new Text("Events Spanning Days");
		eventsHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		eventsHeader.setTextAlignment(TextAlignment.CENTER);
		eventsHeader.setFill(Color.web("#560000"));
		events.getChildren().add(eventsHeader);
		vBox.getChildren().add(events);

		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getType().equals("EVENT_TASK")) {
				if(taskList.get(i).getType().equals("EVENT_TASK")) {
					String start = formatter.format(taskList.get(i).getStartDate());
					String end = formatter.format(taskList.get(i).getEndDate());


					if(!(start.equals(end))) {
						Calendar calStart = DateToCalendar(taskList.get(i).getStartDate());
						String startDay = getDay(calStart); 
						String startMonth = getMonth(calStart);
						String startTime = getTime(calStart);
						int startDate = calStart.get(calStart.DAY_OF_MONTH);

						Calendar calEnd = DateToCalendar(taskList.get(i).getEndDate());
						String endDay = getDay(calEnd); 
						String endMonth = getMonth(calEnd);
						String endTime = getTime(calEnd);
						int endDate = calEnd.get(calEnd.DAY_OF_MONTH);

						String eventTask = (count++ + ". " + "[" + startDay + ", " + startDate + " " + startMonth + ", " + startTime + " - " + endDay + ", " + endDate + " " + endMonth + ", " + endTime + "] " + taskList.get(i).getDescription());
						Text eventDisplay = new Text(WordUtils.wrap(eventTask, 62, "\n", true));
						eventDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
						FillTransition colour;
						if(checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
							eventDisplay.setFill(Color.RED);
							colour = changeColour(eventDisplay, Color.RED);
						}
						else if(checkForOngoing(taskList.get(i).getStartDate(), taskList.get(i).getEndDate()) 
								&& isHome) {
							eventDisplay.setFill(Color.web("#0F6F00"));
							colour = changeColour(eventDisplay, Color.web("#0F6F00"));
						}
						else {
							colour = changeColour(eventDisplay, Color.BLACK);
						}
						if(count == n+1) {
							Timeline blinker = createBlinker(eventDisplay);

							SequentialTransition blink = new SequentialTransition(
									eventDisplay,
									blinker
									);

							blink.play();
							colour.play();
						}
						vBox.getChildren().add(eventDisplay);
					}
				}
			}

		}
		
		return vBox;
	}
	
	protected VBox displayFloatingTasks(int n, boolean isHome, List<Task> taskList) {
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #F9FFC6;");
		
		boolean haveFloatingTasks = false;
		
		HBox floating = new HBox();
		floating.setAlignment(TOP_CENTER);
		Text floatingHeader = new Text("Floating Tasks");
		floatingHeader.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		floatingHeader.setTextAlignment(TextAlignment.CENTER);
		floatingHeader.setFill(Color.web("#3C220A"));
		floating.getChildren().add(floatingHeader);
		vBox.getChildren().add(floating);

		for(int i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getType().equals("FLOATING_TASK")) {
				haveFloatingTasks = true;
				String floatingTask = (count++ + ". " + taskList.get(i).getDescription());

				Text floatingDisplay = new Text(WordUtils.wrap(floatingTask, 62, "\n", true));
				floatingDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
				if(count == n+1) {
					Timeline blinker = createBlinker(floatingDisplay);
					FillTransition colour = changeColour(floatingDisplay, Color.BLACK);
					SequentialTransition blink = new SequentialTransition(
							floatingDisplay,
							blinker
							);

					blink.play();
					colour.play();
				}
				vBox.getChildren().add(floatingDisplay);
			}
		}

		if(haveFloatingTasks == false) {
			Text noFloatingTasks = new Text("*none*");
			noFloatingTasks.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
			vBox.getChildren().add(noFloatingTasks);
		}

		
		return vBox;
	}

	

	protected HBox addHeader(doordonote.logic.UIState.ListType listType) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(20, 25, 20, 25));
		if(listType.equals(doordonote.logic.UIState.ListType.NORMAL)) {
			hbox.setStyle("-fx-background-color: #0D0D0D;");
		}
		else if(listType.equals(doordonote.logic.UIState.ListType.FINISHED)){
			hbox.setStyle("-fx-background-color: #000E54;");
		}
		else {
			hbox.setStyle("-fx-background-color: #560202;");
		}

		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(4.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));


		title.setFont(Font.font("Aharoni", FontWeight.BOLD, 30));
		title.setFill(Color.WHITE);
		title.setEffect(shadow);
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
		case 1 : day = "Sunday";
		break;
		case 2 : day = "Monday";
		break;
		case 3 : day = "Tuesday";
		break;
		case 4 : day = "Wednesday";
		break;
		case 5 : day = "Thursday";
		break;
		case 6 : day = "Friday";
		break;
		case 7 : day = "Saturday";

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

	protected static String getMinutes(Calendar cal) {
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

	protected static String getTime(Calendar cal) {
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

	protected static String getFirstWord(String input) {
		String commandTypeString = input.trim().split("\\s+")[0];
		return commandTypeString.toLowerCase();
	}

	protected static boolean checkForToday(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		boolean isToday = dateFormat.format(date).equals(dateFormat.format(today));
		return isToday;
	}

	protected static boolean checkForOverdue(Date date) {
		Date today = new Date();
		boolean isOverdue = date.before(today);
		return isOverdue;

	}

	protected static boolean checkForOngoing(Date start, Date end) {
		Date today = new Date();

		boolean isOngoing = (!today.before(start)) && (!today.after(end));
		return isOngoing;
	}

	protected Timeline createBlinker(Node node) {
		Timeline blink = new Timeline(
				new KeyFrame(
						Duration.seconds(0),
						new KeyValue(
								node.opacityProperty(), 
								1, 
								Interpolator.DISCRETE
								)
						),
				new KeyFrame(
						Duration.seconds(0.25),
						new KeyValue(
								node.opacityProperty(), 
								0, 
								Interpolator.DISCRETE
								)
						),
				new KeyFrame(
						Duration.seconds(0.5),
						new KeyValue(
								node.opacityProperty(), 
								1, 
								Interpolator.DISCRETE
								)
						)
				);
		blink.setCycleCount(3);

		return blink;
	}

	protected FillTransition changeColour(Shape shape, Color color) {
		FillTransition fill = new FillTransition(Duration.seconds(5), shape, Color.web("#DFCA00"), color);
		return fill;
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
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

	protected Stage createHelpUpdateWindow() {
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

	protected Stage createHelpFindWindow() {
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

	protected Stage createHelpFinishWindow() {
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

	protected Stage createHelpPathWindow() {
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

	protected Stage createHelpRestoreWindow() {
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

	protected Stage createHelpGetWindow() {
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
	
	protected HBox displayDeletedOrFinishedTasks(HBox main) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		List<Task> taskList = logic.getTasks();
		boolean haveEventsOrDeadlines = true;
		boolean haveFloatingTasks = false;
		boolean haveSameDate = true;
		boolean haveEventsSpanningDays = false;
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

		VBox v2_1 = new VBox();
		v2_1.setPrefWidth(500);
		v2_1.setStyle("-fx-background-color: #FFF3F3;");

		VBox v2_2 = new VBox();
		v2_2.setPrefWidth(500);
		v2_2.setStyle("-fx-background-color: #F9FFC6;");

		VBox vbox2 = new VBox();
		vbox2.setAlignment(TOP_CENTER);
		vbox2.setPadding(new Insets(18, 18, 18, 18));
		vbox2.setSpacing(15);
		vbox2.setPrefWidth(500);
		vbox2.setStyle("-fx-background-color: #F9FFC6;");

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
				if(taskList.get(i).getType().equals("EVENT_TASK")) {
					String startDate = formatter.format(taskList.get(i).getStartDate());
					String endDate = formatter.format(taskList.get(i).getEndDate());


					if(!(startDate.equals(endDate))) {
						haveEventsSpanningDays = true;
						continue;
					}
				}

				Calendar calEnd = DateToCalendar(taskList.get(i).getEndDate());
				String day = getDay(calEnd); 
				String month = getMonth(calEnd);
				int date = calEnd.get(calEnd.DAY_OF_MONTH);
				String timeEnd = getTime(calEnd);
				Text taskDate;
				if(checkForToday(taskList.get(i).getEndDate())) {
					taskDate = new Text("Today, " + day + ", " + date + " " + month);
				}
				else {
					taskDate = new Text(day + ", " + date + " " + month);
				}
				taskDate.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
				taskDate.setTextAlignment(TextAlignment.CENTER);
				taskDate.setFill(Color.web("#0C1847"));
				Text taskDesc;
				String task ;
				if(taskList.get(i).getType().equals("DEADLINE_TASK")) {
					task = count++ + ". " + "[by " + timeEnd + "] " + taskList.get(i).getDescription();
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
				}
				else {
					Calendar calStart = DateToCalendar(taskList.get(i).getStartDate());
					String timeStart = getTime(calStart);
					task = count++ + ". " + "[" + timeStart + "-" + timeEnd + "] " + taskList.get(i).getDescription();
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
				}

				taskDesc.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
				vbox1.getChildren().addAll(taskDate, taskDesc);
				for(j = i+1; j < taskList.size(); j++) {
					haveSameDate = true;
					if(!(taskList.get(j).getType().equals("FLOATING_TASK"))) {
						if(taskList.get(j).getType().equals("EVENT_TASK")) {
							String startDate = formatter.format(taskList.get(j).getStartDate());
							String endDate = formatter.format(taskList.get(j).getEndDate());


							if(!(startDate.equals(endDate))) {
								haveEventsSpanningDays = true;
								continue;
							}
						}
						Calendar calEnd2 = DateToCalendar(taskList.get(j).getEndDate());
						String month2 = getMonth(calEnd2);
						int date2 = calEnd2.get(calEnd2.DAY_OF_MONTH);
						String timeEnd2 = getTime(calEnd2);
						if((date != date2)||!(month.equals(month2)))
							haveSameDate = false;
						else {
							Text taskDesc2;
							if(taskList.get(j).getType().equals("DEADLINE_TASK")) {
								task = count++ + ". " + "[by " + timeEnd2 + "] " + taskList.get(j).getDescription();
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));
							}
							else {
								Calendar calStart2 = DateToCalendar(taskList.get(j).getStartDate());
								String timeStart2 = getTime(calStart2);
								task = count++ + ". " + "[" + timeStart2 + "-" + timeEnd2 + "] " + taskList.get(j).getDescription();
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));
							}
							taskDesc2.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
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

		if(haveEventsSpanningDays == true) {

			VBox vbox3 = new VBox();
			vbox3.setAlignment(TOP_CENTER);
			vbox3.setPadding(new Insets(18, 18, 18, 18));
			vbox3.setSpacing(15);
			vbox3.setPrefWidth(500);
			vbox3.setStyle("-fx-background-color: #FFF3F3;");

			ScrollPane sp3 = new ScrollPane();
			VBox.setVgrow(sp3, Priority.ALWAYS);
			//sp2.setVmax(440);
			sp3.setFitToHeight(true);
			sp3.setFitToWidth(true);
			sp3.setPrefSize(115, 150);
			sp3.setHbarPolicy(ScrollBarPolicy.NEVER);
			sp3.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

			scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent evt) {
					if (evt.getCode().equals(KeyCode.UP)) {
						if (sp3.getVvalue() > sp3.getVmin()) {
							sp3.setVvalue(sp3.getVvalue() - scrollPaneIncrement);
						}
					}
					if (evt.getCode().equals(KeyCode.DOWN)) {
						if (sp3.getVvalue() < sp3.getVmax()) {
							sp3.setVvalue(sp3.getVvalue() + scrollPaneIncrement);
						}
					}
				}
			});

			Text eventsHeader = new Text("Events Spanning Days");
			eventsHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
			eventsHeader.setTextAlignment(TextAlignment.CENTER);
			eventsHeader.setFill(Color.web("#560000"));
			vbox3.getChildren().add(eventsHeader);

			for(i=0; i<taskList.size(); i++) {
				if(taskList.get(i).getType().equals("EVENT_TASK")) {
					if(taskList.get(i).getType().equals("EVENT_TASK")) {
						String start = formatter.format(taskList.get(i).getStartDate());
						String end = formatter.format(taskList.get(i).getEndDate());


						if(!(start.equals(end))) {
							Calendar calStart = DateToCalendar(taskList.get(i).getStartDate());
							String startDay = getDay(calStart); 
							String startMonth = getMonth(calStart);
							String startTime = getTime(calStart);
							int startDate = calStart.get(calStart.DAY_OF_MONTH);

							Calendar calEnd = DateToCalendar(taskList.get(i).getEndDate());
							String endDay = getDay(calEnd); 
							String endMonth = getMonth(calEnd);
							String endTime = getTime(calEnd);
							int endDate = calEnd.get(calEnd.DAY_OF_MONTH);

							String eventTask = (count++ + ". " + "[" + startDay + ", " + startDate + " " + startMonth + ", " + startTime + " - " + endDay + ", " + endDate + " " + endMonth + ", " + endTime + "] " + taskList.get(i).getDescription());
							Text eventDisplay = new Text(WordUtils.wrap(eventTask, 62, "\n", true));
							eventDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
							vbox3.getChildren().add(eventDisplay);
						}
					}
				}

			}

			sp3.setContent(vbox3);
			v2_1.getChildren().addAll(sp3);
		}

		Text floatingHeader = new Text("Floating Tasks");
		floatingHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 22));
		floatingHeader.setTextAlignment(TextAlignment.CENTER);
		floatingHeader.setFill(Color.web("#3C220A"));
		vbox2.getChildren().add(floatingHeader);

		for(i=0; i<taskList.size(); i++) {
			if(taskList.get(i).getType().equals("FLOATING_TASK")) {
				haveFloatingTasks = true;
				String floatingTask = (count++ + ". " + taskList.get(i).getDescription());

				Text floatingDisplay = new Text(WordUtils.wrap(floatingTask, 62, "\n", true));
				floatingDisplay.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
				vbox2.getChildren().add(floatingDisplay);
			}
		}

		if(haveFloatingTasks == false) {
			Text noFloatingTasks = new Text("*none*");
			noFloatingTasks.setFont(Font.font("Calibri", FontWeight.NORMAL, 16));
			vbox2.getChildren().add(noFloatingTasks);
		}

		sp2.setContent(vbox2);
		v2_2.getChildren().addAll(sp2);

		if(haveEventsSpanningDays == true) {
			VBox v2 = new VBox();
			v2.setSpacing(10);
			v2_1.setPrefHeight(235);
			v2_2.setPrefHeight(235);
			v2.getChildren().addAll(v2_1, v2_2);
			main.getChildren().addAll(v1, v2);
		}
		else {
			main.getChildren().addAll(v1, v2_2);
		}

		main.setAlignment(TOP_CENTER);


		return main;

	}

	protected static String wrapText(String text) {
    	StringBuilder sb = new StringBuilder(text);

    	int x = 0;
    	while (x + 50 < sb.length() && (x = sb.lastIndexOf(" ", x + 50)) != -1) {
    	    sb.replace(x, x + 1, "\n");
    	}
    	return sb.toString();
    }	 

}
