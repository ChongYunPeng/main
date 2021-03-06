//@@author A0132785Y
package doordonote.ui;

import doordonote.common.Task;
import doordonote.logic.Logic;
import doordonote.logic.UIToLogic;
import doordonote.logic.UIState;
import doordonote.ui.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;
import java.lang.StringBuilder;
import java.util.logging.*;

import org.apache.commons.lang.WordUtils;

import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Interpolator;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    
	/** String objects for state of helpBox from UIState */
	private static final String HELP = "help";
	private static final String HELP_ADD = "add";
	private static final String HELP_DELETE = "delete";
	private static final String HELP_UPDATE = "update";
	private static final String HELP_FIND = "find";
	private static final String HELP_FINISH = "finish";
	private static final String HELP_SAVE = "save";
	private static final String HELP_RESTORE = "restore";
	private static final String HELP_READFROM = "readfrom";
	private static final String HELP_VIEW = "view";
    
	/** String objects for commands */
	private static final String COMMAND_HOME = "home";
	private static final String COMMAND_DISPLAY_FINISH = "view finished";
	private static final String COMMAND_DISPLAY_DELETE = "view deleted";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";
    
	/** String objects for different messages displayed to the user */
	private static final String MESSAGE_WELCOME = "Welcome to DoOrDoNote! "
			+ "Type in \"help\" for all the help you need!";
	private static final String MESSAGE_COMMAND_TABLE = "Table for Commands: ";
	private static final String MESSAGE_SHORTCUT_TABLE = "Table for Keyboard Shortcuts: ";
	private static final String MESSAGE_COLOUR_TABLE = "Table for Colour Coding of Tasks: ";
	private static final String MESSAGE_HELP = "Hello! "
			+ "This is the page to provide you with all the help you need for DoOrDoNote";
	private static final String MESSAGE_HELP_COMMAND = "Hello! Here is a table of the commands "
			+ "you can use for ";
	private static final String MESSAGE_NO_INPUT = "Try typing one of our commands. "
			+ "For help, just type \"help\"!";
    
	/** String objects for titles displayed */
	private static final String TITLE_APP = "DoOrDoNote";
	private static final String TITLE_HOME = "Home";
	private static final String TITLE_HELP = "Help!";
	private static final String TITLE_FLOATING = "Floating Tasks";
	private static final String TITLE_EVENTS = "Events Spanning Tasks";
    
	/** String objects for text for labels */
	private static final String LABEL_OK = " OK ";
	private static final String LABEL_COMMAND = "Command:";
    
	/** String objects for types of tasks */
	private static final String TYPE_EVENT = "EVENT_TASK";
	private static final String TYPE_DEADLINE = "DEADLINE_TASK";
	private static final String TYPE_FLOATING = "FLOATING_TASK";
    
	/** String objects for different fonts */
	private static final String FONT_CALIBRI = "Calibri";
	private static final String FONT_TAHOMA = "Tahoma";
	private static final String FONT_AHARONI = "Aharoni";
	
	/** String objects for logging warning messages */
	private static final String EXECUTION_ERROR_LOGGING = "processing execution error";
	private static final String FILE_ERROR_LOGGING = "processing file error";
	
	/** double value for the scroll change during up/down keys navigation */
	private static final double SCROLL_INCREMENT = 0.2;
    
	/** Text object for the feedback area containing feedback to be displayed */
	private static Text output = new Text(MESSAGE_WELCOME);
	
	/** Text object for the header area containing header to be displayed */
	private static Text title = new Text(TITLE_HOME);
	
	/** BorderPane object for the Scene to be displayed in the main display*/
	private static BorderPane border = new BorderPane();
	
	/** Scene object for the Stage to be displayed in the main display */
	private static Scene scene = new Scene(border);
    
	/** integer for the Task ID of the tasks displayed */
	private static int count;
    
	/** Logic object to which input is to be passed and data for display is to be retrieved */
	private static UIToLogic logic = null;
	
	/** Obtains a suitable logger for class UI */
	private static Logger logger = Logger.getLogger("UI");
    
	/** Constructor for UI */
	public UI() {
		logic = new Logic();
		count = 1;
	}

	/**
	 * The main entry point for all JavaFX applications. 
	 * The start method is invoked once the application is opened by the user, 
	 * and after the system is ready for the application to begin running
	 *
	 * @param primaryStage     the primary stage for this application, 
	 *                         onto which the application scene can be set. 
	 */
	@Override
	public void start(Stage primaryStage) {
		
		UIState state = new UIState();
		
		border.setBottom(addBottomArea());
		border.setCenter(addMainDisplay(0, state.getDisplayType()));
		border.setTop(addHeader(state.getDisplayType()));

		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE_APP);
		primaryStage.getIcons().add(new Image("icon1.jpg"));
		primaryStage.show();
		// ensures application enters full screen by default
		primaryStage.setMaximized(true);

	}
    
	/**
	 * Return a vertical box containing elements of the bottom 
	 * section of the display
	 *
	 * @return     vertical box VBox.
	 */
	public static VBox addBottomArea() {
		
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 12, 10, 12));
		vBox.setSpacing(5);
		vBox.setStyle("-fx-background-color: #383737;");

		HBox hBox = new HBox();
		hBox.setStyle("-fx-background-color: #F0F0F0;"); 
		hBox.setAlignment(Pos.CENTER);
		hBox.setPadding(new Insets(5, 5, 5, 5));
		
		output.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
		output.setFill(Color.web("#00811C"));
		
		hBox.getChildren().add(output);

		vBox.getChildren().addAll(hBox, addCommandArea());
		vBox.setAlignment(Pos.CENTER);
		
		return vBox;

	}
	
	/**
	 * Return a horizontal box containing elements of the  
	 * command section of the display
	 *
	 * @return     horizontal box HBox.
	 */
	public static HBox addCommandArea() {

		HBox hBox = new HBox();
		hBox.setPadding(new Insets(5, 12, 5, 12));
		hBox.setSpacing(10);

		Label command = new Label(LABEL_COMMAND);
		command.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
		command.setTextFill(Color.web("#FFFFFF"));
		
		TextField commandBox = new TextField();
		commandBox.setPrefWidth(500);

		handleUserInput(commandBox);

		hBox.getChildren().addAll(command, commandBox);
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}
	
	/**
	 * Handles user input given in commandBox.
	 * Passes the user input to Logic and
	 * displays feedback returned as output.
	 * Also calls Logic's getState() to get UIState
	 * object for information regarding display
	 *
	 * @param commandBox  TextField where user can enter input      
	 */
	public static void handleUserInput(TextField commandBox) {

		commandBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			String feedback;

			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					if (commandBox.getText() != null && ! commandBox.getText().trim().isEmpty()) {
						try { 
							feedback = logic.parseAndExecuteCommand(commandBox.getText());
							
							UIState state = logic.getState();

							if (state.getHelpBox() == null) {
								handleNonHelpCommand(feedback, state, commandBox);
							} else {
								handleHelpCommand(feedback, state, commandBox);
							}
						} catch (Exception e) {
							handleExecutionException(e.getMessage());
						}
					} 
					// if user presses enter without giving a command
					else {
						feedback = MESSAGE_NO_INPUT;
						output.setText(feedback);
						output.setFill(Color.web("#00811C"));
					}
				}
                
				// keyboard shortcut for home
				if (ke.getCode().equals(KeyCode.ESCAPE)) {
					try {
						feedback = logic.parseAndExecuteCommand(COMMAND_HOME);
						UIState state = logic.getState();

						handleNonHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}
                
				// keyboard shortcut for undo
				if (ke.getCode().equals(KeyCode.Z) && ke.isShortcutDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(COMMAND_UNDO);
						UIState state = logic.getState();
						
						handleNonHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}
                
				// keyboard shortcut for redo
				if (ke.getCode().equals(KeyCode.Y) && ke.isShortcutDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(COMMAND_REDO);
						UIState state = logic.getState();
						
						handleNonHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}
                
				// keyboard shortcut for view deleted
				if (ke.getCode().equals(KeyCode.D) && ke.isShortcutDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(COMMAND_DISPLAY_DELETE);
						UIState state = logic.getState();
						
						handleNonHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}
                
				// keyboard shortcut for view finished
				if (ke.getCode().equals(KeyCode.F) && ke.isShortcutDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(COMMAND_DISPLAY_FINISH);
						UIState state = logic.getState();
						
						handleNonHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}
                
				// keyboard shortcut for help
				if (ke.getCode().equals(KeyCode.H) && ke.isShortcutDown()) {
					try {
						feedback = logic.parseAndExecuteCommand(HELP);
						UIState state = logic.getState();
						
						handleHelpCommand(feedback, state, commandBox);
					} catch (Exception e) {
						handleExecutionException(e.getMessage());
					}
				}

			}
		});
	}
    
	/**
	 * Handles exceptions thrown from logic while parsing and executing user
	 * command 
	 *
	 * @param feedback  String message for the exception to be displayed as  
	 *                  feedback in the Text object output
	 */
	public static void handleExecutionException(String feedback) {
		
		//log a message at WARNING level
		logger.log(Level.WARNING, EXECUTION_ERROR_LOGGING, feedback);
		
		UIState state = logic.getState();
		
		border.setCenter(addMainDisplay(0, state.getDisplayType()));
		
		output.setText(feedback);
		// sets the colour of feedback displayed as output as red
		output.setFill(Color.web("#F20505"));
		
	}
	
	/**
	 * Handles the display of UI after user-entered non-help commands 
	 * by gathering information from Logic's UIState
	 *
	 * @param feedback     String message to be displayed as output after
	 *                     successful execution of command
	 * @param state        UIState object giving information about the display
	 *                     (title, list type, Task ID for highlighting, input Box)
	 * @param commandBox   TextField object where for the user input, to be 
	 *                     manipulated according to UIState
	 */
	public static void handleNonHelpCommand(String feedback, UIState state, 
			                                TextField commandBox) {
		
		output.setText(feedback);
		output.setFill(Color.web("#00811C"));

		border.setCenter(addMainDisplay(state.getIdNewTask() + 1, state.getDisplayType()));
		border.setTop(addHeader(state.getDisplayType()));
		
		if (state.getTitle() != null) {
			title.setText(state.getTitle());
		}
		if (state.getInputBox() == null || state.getInputBox() == "") {
			commandBox.clear();
		} else {
			commandBox.setText(state.getInputBox());
			// to position the caret at the end of the line of text
			commandBox.positionCaret(state.getInputBox().length() + 1);
		}
		
	}
	
	/**
	 * Handles the display of UI after user-entered help command 
	 * by gathering information from Logic's UIState
	 *
	 * @param feedback     String message to be displayed as output after
	 *                     successful execution of command
	 * @param state        UIState object giving information about the type 
	 *                     of help display
	 * @param commandBox   TextField object for the user input, to be 
	 *                     cleared after help command
	 */
	public static void handleHelpCommand(String feedback, UIState state, 
			                             TextField commandBox) {
		
		output.setText(feedback);
		output.setFill(Color.web("#00811C"));

		Stage helpStage;
		switch (state.getHelpBox()) {
		case HELP_ADD: {
			// Fallthrough
		}
		case HELP_DELETE: {
			// Fallthrough
		}
		case HELP_FIND: {
			// Fallthrough
		}
		case HELP_FINISH: {
			// Fallthrough
		}
		case HELP_READFROM: {
			// Fallthrough
		}
		case HELP_SAVE: {
			// Fallthrough
		}
		case HELP_RESTORE: {
			// Fallthrough
		}
		case HELP_VIEW: {
			// Fallthrough
		}
		case HELP_UPDATE: {
			helpStage = createHelpCommandWindow(state.getHelpBox());
			break;
		}
		default: {
			helpStage = createHelpWindow();
			break;
		}
		}

		helpStage.show();
		
		// closes help window if user presses Enter or Esc
		helpStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent evt) {
				if (evt.getCode().equals(KeyCode.ESCAPE)
						|| evt.getCode().equals(KeyCode.ENTER)) {
					helpStage.close();
				}
			}
		});
		
		commandBox.clear();
	}
	
	/**
	 * Returns the Stage for the Help window 
	 * box with all useful info for the user.
	 *  
	 * @return            Help window Stage
	 */
	public static Stage createHelpWindow() {
		
		Stage stage = new Stage();

		HBox hBox1 = new HBox();
		hBox1.setSpacing(15);
		hBox1.setAlignment(Pos.CENTER);
		
		Image image1 = new Image("question_mark.png");
		ImageView imv1 = new ImageView(image1);
		imv1.setFitWidth(30);
		imv1.setPreserveRatio(true);
		imv1.setSmooth(true);
		imv1.setCache(true);
		
		Text helpHeader = new Text(MESSAGE_HELP);
		helpHeader.setFont(Font.font(FONT_CALIBRI, FontWeight.BOLD, 18));
		helpHeader.setFill(Color.web("#00143E")); // #00143E
		
		hBox1.getChildren().addAll(helpHeader, imv1);
		
		HBox hBox2 = new HBox();
		hBox2.setSpacing(15);
		hBox2.setAlignment(Pos.CENTER);
		
		// for the table of commands
		VBox vBox1 = new VBox();
	    vBox1.setSpacing(5);
	    vBox1.setAlignment(Pos.TOP_CENTER);
        
		Text commandHeader = new Text(MESSAGE_COMMAND_TABLE);
		commandHeader.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 15));
		commandHeader.setFill(Color.web("#00143E"));

		Image image2 = new Image("help.png");
		ImageView imv2 = new ImageView(image2);
		imv2.setFitWidth(650);
		imv2.setPreserveRatio(true);
		imv2.setSmooth(true);
		imv2.setCache(true);
		
		vBox1.getChildren().addAll(commandHeader, imv2);
		
		// for the table of shortcuts
		VBox vBox2_1 = new VBox();
		vBox2_1.setSpacing(5);
		vBox2_1.setAlignment(Pos.TOP_CENTER);
		
		Text shortcutHeader = new Text(MESSAGE_SHORTCUT_TABLE);
		shortcutHeader.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 15));
		shortcutHeader.setFill(Color.web("#00143E"));
		
		Image image3 = new Image("helpshortcut.png");
		ImageView imv3 = new ImageView(image3);
		imv3.setFitWidth(550);
		imv3.setPreserveRatio(true);
		imv3.setSmooth(true);
		imv3.setCache(true);
		
		// for the table of colour codes
		vBox2_1.getChildren().addAll(shortcutHeader, imv3);
		
		VBox vBox2_2 = new VBox();
		vBox2_2.setSpacing(5);
		vBox2_2.setAlignment(Pos.TOP_CENTER);
		
		Text colourCodeHeader = new Text(MESSAGE_COLOUR_TABLE);
		colourCodeHeader.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 15));
		colourCodeHeader.setFill(Color.web("#00143E"));
		
		Image image4 = new Image("helpcolour.png");
		ImageView imv4 = new ImageView(image4);
		imv4.setFitWidth(550);
		imv4.setPreserveRatio(true);
		imv4.setSmooth(true);
		imv4.setCache(true);

		vBox2_2.getChildren().addAll(colourCodeHeader, imv4);
		
		Button button = new Button(LABEL_OK);
        
		VBox vBox2 = new VBox();
		vBox2.setSpacing(15);
		vBox2.setAlignment(Pos.TOP_CENTER);
		vBox2.getChildren().addAll(vBox2_1, vBox2_2, button);
		
		hBox2.getChildren().addAll(vBox1, vBox2);

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10, 20, 0, 20));
		vBox.setSpacing(10);
		vBox.getChildren().addAll(hBox1, hBox2);
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setStyle("-fx-background-color: #eff4ff;");
		
		Scene sc = new Scene(vBox);

		stage.setTitle(TITLE_HELP);
		stage.getIcons().add(image1);
		stage.setScene(sc);
        
		// if pressed, button OK closes the window
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});

		return stage;
	}
    
	/**
	 * Returns the Stage for the Help window 
	 * box for a particular command type
	 * with all useful info about that command 
	 * type for the user.
	 *  
	 * @param commandType String for the type of command for which 
	 *                    help is to be displayed            
	 * @return            Help window Stage for specific command type
	 */
	public static Stage createHelpCommandWindow(String commandType) {
		
		Stage stage = new Stage();
		
		HBox hBox = new HBox();
		hBox.setSpacing(15);
		hBox.setAlignment(Pos.CENTER);
		
		Image image1 = new Image("question_mark.png");
		ImageView imv1 = new ImageView(image1);
		imv1.setFitWidth(30);
		imv1.setPreserveRatio(true);
		imv1.setSmooth(true);
		imv1.setCache(true);

		Text helpHeader = new Text(MESSAGE_HELP_COMMAND + 
				commandType.toUpperCase() + ":");
		helpHeader.setFont(Font.font(FONT_CALIBRI, FontWeight.BOLD, 18));
		helpHeader.setFill(Color.web("#00143E")); // #00143E
		
		hBox.getChildren().addAll(helpHeader, imv1);

		Image image2 = getTableImage(commandType);

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
		vBox.setAlignment(Pos.TOP_CENTER);
		vBox.setStyle("-fx-background-color: #eff4ff;");
		
		Scene sc = new Scene(vBox);

		stage.setTitle(TITLE_HELP);
		stage.getIcons().add(image1);
		stage.setScene(sc);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});

		return stage;
	}
	
	/**
	 * Returns the Image object for the Help window 
	 * box displaying help table for a particular
	 * command type with all useful info about that 
	 * command type for the user.
	 *  
	 * @param commandType String for the type of command for which 
	 *                    image is to be returned            
	 * @return            Image object for specific command type
	 */
	public static Image getTableImage(String commandType) {
		
		Image image;
		
		switch (commandType) {
		case HELP_ADD: {
			image = new Image("helpadd.png");
			break;
		}
		case HELP_DELETE: {
			image = new Image("helpdelete.jpg");
			break;
		}
		case HELP_FIND: {
			image = new Image("helpfind.png");
			break;
		}
		case HELP_FINISH: {
			image = new Image("helpfinish.jpg");
			break;
		}
		case HELP_READFROM: {
			image = new Image("helpreadfrom.png");
			break;
		}
		case HELP_SAVE: {
			image = new Image("helpsave.png");
			break;
		}
		case HELP_RESTORE: {
			image = new Image("helprestore.jpg");
			break;
		}
		case HELP_UPDATE: {
			image = new Image("helpupdate.png");
			break;
		}
		case HELP_VIEW: {
			image = new Image("helpview.png");
			break;
		}
		default: {
			// main help box with all commands as default
			image = new Image("help.png");
		}
		}
		
		return image;
	}
	
	/**
	 * Returns the horizontal box for the main window 
	 * displaying all the tasks inside their boxes
	 *  
	 * @param taskId      id of the task to be highlighted after update or add command
	 * @param listType    type of task lists to be displayed (home, deleted, finished)
	 * @return            horizontal box HBox for main display
	 */
	public static HBox addMainDisplay(int taskId, doordonote.logic.UIState.ListType listType) {
		
		HBox main = new HBox();

		main.setPadding(new Insets(40, 25, 30, 25));
		main.setSpacing(40);
		main.setStyle("-fx-background-image: url('whitee.png');" + "-fx-background-size: 100% 100%; "
				+ "-fx-background-repeat: no-repeat;");


		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		List<Task> taskList;
		
		try {
			taskList = logic.getTasks();
		}
		catch (Exception e) {
			//log a message at WARNING level
			logger.log(Level.WARNING, FILE_ERROR_LOGGING, e);
			
			/* if exception thrown by logic, displays exception 
			 * message as feedback in red and initialises taskList
			 * as an empty List 
			 */
			taskList = new ArrayList<Task>();
			output.setText(e.getMessage());
			output.setFill(Color.web("#F20505"));
			
		}

		boolean haveEventsSpanningDays = checkForEventsSpanningDays(taskList, formatter);
		boolean isHome = listType.equals(doordonote.logic.UIState.ListType.NORMAL);

		count = 1;

		VBox singleDayBox = new VBox();
		singleDayBox.setPrefWidth(500);
		singleDayBox.setStyle("-fx-background-color: #E1F5EF;");

		VBox singleDayTasks = displaySingleDayTasks(taskId, isHome, taskList, formatter);
		ScrollPane scroll1 = createScroll();
		scroll1.setContent(singleDayTasks);
		singleDayBox.getChildren().addAll(scroll1);

		VBox multipleDayBox = new VBox();
		multipleDayBox.setPrefWidth(500);
		multipleDayBox.setStyle("-fx-background-color: #FFF3F3;");

		if (haveEventsSpanningDays == true) {

			ScrollPane scroll3 = createScroll();
			VBox eventsSpanningDays = displayEventsSpanningDays(taskId, isHome, taskList, formatter);

			scroll3.setContent(eventsSpanningDays);
			multipleDayBox.getChildren().addAll(scroll3);
		}
        
		VBox floatingBox = new VBox();
		floatingBox.setPrefWidth(500);
		floatingBox.setStyle("-fx-background-color: #F9FFC6;");

		ScrollPane scroll2 = createScroll();
		VBox floatingTasks = displayFloatingTasks(taskId, taskList);
		scroll2.setContent(floatingTasks);
		floatingBox.getChildren().addAll(scroll2);

		if (haveEventsSpanningDays == true) {
			VBox leftSideBoxes = new VBox();
			leftSideBoxes.setSpacing(10);
			multipleDayBox.setPrefHeight(235);
			floatingBox.setPrefHeight(235);
			leftSideBoxes.getChildren().addAll(multipleDayBox, floatingBox);
			main.getChildren().addAll(singleDayBox, leftSideBoxes);
		} else {
			main.getChildren().addAll(singleDayBox, floatingBox);
		}

		main.setAlignment(Pos.TOP_CENTER);

		return main;

	}
	
	/**
	 * Customizes and returns a ScrollPane object for a
	 * task list box
	 *  
	 * @return     customized ScrollPane object for VBox
	 */
	public static ScrollPane createScroll() {
		
		ScrollPane scroll = new ScrollPane();
		VBox.setVgrow(scroll, Priority.ALWAYS);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		scroll.setPrefSize(115, 150);
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		// for scrolling using up/down arrow keys
		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent evt) {
				if (evt.getCode().equals(KeyCode.UP)) {
					if (scroll.getVvalue() > scroll.getVmin()) {
						scroll.setVvalue(scroll.getVvalue() - SCROLL_INCREMENT);
					}
				}
				if (evt.getCode().equals(KeyCode.DOWN)) {
					if (scroll.getVvalue() < scroll.getVmax()) {
						scroll.setVvalue(scroll.getVvalue() + SCROLL_INCREMENT);
					}
				}
			}
		});
		
		return scroll;
		
	}
    
	/**
	 * Returns the boolean value corresponding to the check for events spanning days
	 * in the task list
	 *  
	 * @param taskList    the list of all tasks to be displayed
	 * @param formatter   SimpleDateFormat formatter for formatting dates
	 * @return            boolean value to check for events spanning days
	 */
	public static boolean checkForEventsSpanningDays(List<Task> taskList, SimpleDateFormat formatter) {
		boolean haveEventsSpanningDays = false;

		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getType().equals(TYPE_EVENT)) {

				String startDate = formatter.format(taskList.get(i).getStartDate());
				String endDate = formatter.format(taskList.get(i).getEndDate());

				if (!(startDate.equals(endDate))) {
					haveEventsSpanningDays = true;
					break;
				}
			}
		}

		return haveEventsSpanningDays;
	}

	/**
	 * Returns the vertical box for the display 
	 * of dated tasks NOT spanning multiple days
	 *  
	 * @param taskId      id of the task to be highlighted after update or add command
	 * @param isHome      boolean value: true if display is of home type, else false
	 *                    lets method know if tasks are to be colour coded
	 * @param taskList    list of all tasks to be displayed
	 * @param formatter   SimpleDateFormat object for formatting of task dates
	 *                
	 * @return            vertical box VBox for display of single day tasks
	 */
	public static VBox displaySingleDayTasks(int taskId, boolean isHome, List<Task> taskList,
			SimpleDateFormat formatter) {
		
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #E1F5EF;");

		boolean haveEventsOrDeadlines = true;
		boolean haveSameDate = true;

		for (int i = 0; i < taskList.size(); i++) {
			if (!(taskList.get(i).getType().equals(TYPE_FLOATING))) {
				if (taskList.get(i).getType().equals(TYPE_EVENT)) {
					// if task encountered is multiple day event, doesn't display
					if (DateUtil.checkIfMultipleDayEvent(taskList.get(i), formatter)) {
						continue;
					}
				}

				String dateString = getDateToBeDisplayedString(taskList.get(i));
				Text taskDate = new Text(dateString);
				taskDate.setFont(Font.font(FONT_TAHOMA, FontWeight.BOLD, 18));
				taskDate.setTextAlignment(TextAlignment.CENTER);
				taskDate.setFill(Color.web("#0C1847"));

				HBox dates = new HBox();
				dates.setAlignment(Pos.TOP_CENTER);
				dates.getChildren().add(taskDate);

				Text taskDesc;
				String task;
				if (taskList.get(i).getType().equals(TYPE_DEADLINE)) {
					task = getDeadlineString(taskList.get(i));
					// uses apache's WordUtils class to wrap text for long tasks
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
					
					FillTransition colour;
					
					// if task is overdue and display type is home
					if (DateUtil.checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
						taskDesc.setFill(Color.RED);
						colour = changeColour(taskDesc, Color.RED);
					} else {
						colour = changeColour(taskDesc, Color.BLACK);
					}
					
					// if count i.e. Task ID of task corresponds to value of taskId passed, task is highlighted 
					if (count == taskId + 1) {
						Timeline blinker = createBlinker(taskDesc);
						SequentialTransition blink = 
								new SequentialTransition(taskDesc, blinker, colour);

						blink.play();
					}
				} else {
					task = getSingleDayEventString(taskList.get(i));
					taskDesc = new Text(WordUtils.wrap(task, 62, "\n", true));
					
					FillTransition colour;
					if (DateUtil.checkForOngoing(taskList.get(i).getStartDate(), 
							taskList.get(i).getEndDate()) && isHome) {
						taskDesc.setFill(Color.web("#0F6F00"));
						colour = changeColour(taskDesc, Color.web("#0F6F00"));
					} else if (DateUtil.checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
						taskDesc.setFill(Color.RED);
						colour = changeColour(taskDesc, Color.RED);
					} else {
						colour = changeColour(taskDesc, Color.BLACK);
					}
					
					if (count == taskId + 1) {
						Timeline blinker = createBlinker(taskDesc);
						SequentialTransition blink = new SequentialTransition(taskDesc, blinker, colour);

						blink.play();
					}
				}

				taskDesc.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
				vBox.getChildren().addAll(dates, taskDesc);
				for (int j = i + 1; j < taskList.size(); j++) {
					haveSameDate = true;
					if (!(taskList.get(j).getType().equals(TYPE_FLOATING))) {
						if (taskList.get(j).getType().equals(TYPE_EVENT)) {
							if (DateUtil.checkIfMultipleDayEvent(taskList.get(j), formatter)) {
								continue;
							}
						}

						if (DateUtil.checkForSameDay(taskList.get(i), taskList.get(j))) {
							haveSameDate = false;
						}
						// if same date, task is displayed under that day
						else {
							Text taskDesc2;
							
							FillTransition colour;
							if (taskList.get(j).getType().equals(TYPE_DEADLINE)) {
								task = getDeadlineString(taskList.get(j));
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));

								if (DateUtil.checkForOverdue(taskList.get(j).getEndDate()) 
										&& isHome) {
									taskDesc2.setFill(Color.RED);
									colour = changeColour(taskDesc2, Color.RED);
								} else {
									colour = changeColour(taskDesc2, Color.BLACK);
								}
							} else {
								task = getSingleDayEventString(taskList.get(j));
								taskDesc2 = new Text(WordUtils.wrap(task, 62, "\n", true));
								if (DateUtil.checkForOngoing(taskList.get(j).getStartDate(),
										taskList.get(j).getEndDate()) && isHome) {
									taskDesc2.setFill(Color.web("#0F6F00"));
									colour = changeColour(taskDesc2, Color.web("#0F6F00"));
								} else if (DateUtil.checkForOverdue(taskList.get(j).getEndDate()) 
										&& isHome) {
									taskDesc2.setFill(Color.RED);
									colour = changeColour(taskDesc2, Color.RED);
								} else {
									colour = changeColour(taskDesc2, Color.BLACK);
								}
							}
							
							taskDesc2.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
							
							if (count == taskId + 1) {
								Timeline blinker = createBlinker(taskDesc2);
								SequentialTransition blink = 
										new SequentialTransition(taskDesc2, blinker, colour);

								blink.play();
							}
							vBox.getChildren().addAll(taskDesc2);
							i++;
						}
					}
					// if floating task encountered, doesn't display
					else {
						haveEventsOrDeadlines = false;
					}
					if (haveEventsOrDeadlines == false || haveSameDate == false) {
						break;
					}
				}
			} else {
				haveEventsOrDeadlines = false;
			}

			if (haveEventsOrDeadlines == false) {
				break;
			}

		}

		return vBox;
	}
	
	/**
	 * Returns the String for the Date to be displayed in the single day events
	 * box (today(if true), day, date and month)
	 *  
	 * @param task      Task whose date is to be displayed
	 *                
	 * @return          String for date of task
	 */
	public static String getDateToBeDisplayedString(Task task) {

		Calendar calEnd = DateUtil.dateToCalendar(task.getEndDate());
		String day = DateUtil.getDay(calEnd);
		String month = DateUtil.getMonth(calEnd);
		int date = calEnd.get(Calendar.DAY_OF_MONTH);
		
		String dateString;
		if (DateUtil.checkForToday(task.getEndDate())) {
			dateString = "Today, " + day + ", " + date + " " + month;
		} else {
			dateString = day + ", " + date + " " + month;
		}
		
		return dateString;
	}
	
	/**
	 * Returns the customized String for the deadline task to be displayed 
	 * in the single day events box (end time and description)
	 *  
	 * @param task      DeadlineTask which is to be displayed as String
	 *                
	 * @return          String for deadline task
	 */
	public static String getDeadlineString(Task task) {
		
		Calendar calEnd = DateUtil.dateToCalendar(task.getEndDate());
		String timeEnd = DateUtil.getTime(calEnd);
		
		String taskString = count++ + ". " + "[by " + timeEnd + "] " + task.getDescription();
		
		return taskString;
		
	}
	
	/**
	 * Returns the customized String for the event task to be displayed 
	 * in the single day events box (start time-end time and description)
	 *  
	 * @param task      EventTask which is to be displayed as String
	 *                
	 * @return          String for single day event task
	 */
	public static String getSingleDayEventString(Task task) {
		
		Calendar calStart = DateUtil.dateToCalendar(task.getStartDate());
		String timeStart = DateUtil.getTime(calStart);
		
		Calendar calEnd = DateUtil.dateToCalendar(task.getEndDate());
		String timeEnd = DateUtil.getTime(calEnd);
		
		String taskString = count++ + ". " + "[" + timeStart + "-" + timeEnd + "] " 
		                    + task.getDescription();
		
		return taskString;
	}

	/**
	 * Returns the vertical box for the display 
	 * of event tasks spanning multiple days
	 *  
	 * @param taskId      id of the task to be highlighted after update or add command
	 * @param isHome      boolean value: true if display is of home type, else false
	 *                    lets method know if tasks are to be colour coded
	 * @param taskList    list of all tasks to be displayed
	 * @param formatter   SimpleDateFormat object for formatting of task dates
	 *                
	 * @return            vertical box VBox for display of multiple day events
	 */
	public static VBox displayEventsSpanningDays(int taskId, boolean isHome, 
			List<Task> taskList, SimpleDateFormat formatter) {

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #FFF3F3;");

		HBox events = new HBox();
		events.setAlignment(Pos.TOP_CENTER);
		Text eventsHeader = new Text(TITLE_EVENTS);
		eventsHeader.setFont(Font.font(FONT_TAHOMA, FontWeight.BOLD, 20));
		eventsHeader.setTextAlignment(TextAlignment.CENTER);
		eventsHeader.setFill(Color.web("#560000"));
		events.getChildren().add(eventsHeader);
		vBox.getChildren().add(events);

		for (int i = 0; i < taskList.size(); i++) {

			if (taskList.get(i).getType().equals(TYPE_EVENT)) {
				String start = formatter.format(taskList.get(i).getStartDate());
				String end = formatter.format(taskList.get(i).getEndDate());

				if (!(start.equals(end))) {
					String eventTask = getMultipleDayEventString(taskList.get(i));
					Text eventDisplay = new Text(WordUtils.wrap(eventTask, 62, "\n", true));
					eventDisplay.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
					
					FillTransition colour;
					
					if (DateUtil.checkForOverdue(taskList.get(i).getEndDate()) && isHome) {
						eventDisplay.setFill(Color.RED);
						colour = changeColour(eventDisplay, Color.RED);
					} else if (DateUtil.checkForOngoing(taskList.get(i).getStartDate(), 
							taskList.get(i).getEndDate()) && isHome) {
						eventDisplay.setFill(Color.web("#0F6F00"));
						colour = changeColour(eventDisplay, Color.web("#0F6F00"));
					} else {
						colour = changeColour(eventDisplay, Color.BLACK);
					}
					
					if (count == taskId + 1) {
						Timeline blinker = createBlinker(eventDisplay);

						SequentialTransition blink = 
								new SequentialTransition(eventDisplay, blinker, colour);

						blink.play();
					}
					
					vBox.getChildren().add(eventDisplay);
				}
			}
		}

		return vBox;
	}
	
	/**
	 * Returns the customized String for the event task to be displayed 
	 * in the multiple day events box (start date and time-end date and time 
	 * and description)
	 *  
	 * @param task      EventTask which is to be displayed as String
	 *                
	 * @return          String for multiple day event task
	 */
	public static String getMultipleDayEventString(Task task) {
		
		Calendar calStart = DateUtil.dateToCalendar(task.getStartDate());
		String startDay = DateUtil.getDay(calStart);
		String startMonth = DateUtil.getMonth(calStart);
		String startTime = DateUtil.getTime(calStart);
		int startDate = calStart.get(Calendar.DAY_OF_MONTH);

		Calendar calEnd = DateUtil.dateToCalendar(task.getEndDate());
		String endDay = DateUtil.getDay(calEnd);
		String endMonth = DateUtil.getMonth(calEnd);
		String endTime = DateUtil.getTime(calEnd);
		int endDate = calEnd.get(Calendar.DAY_OF_MONTH);

		String taskString = count++ + ". " + "[" + startDay + ", " + startDate + " " 
		                     + startMonth + ", " + startTime + " - " + endDay + 
		                     ", " + endDate + " " + endMonth + ", " + endTime + "] "
				             + task.getDescription();
		
		return taskString;
	}
    
	/**
	 * Returns the vertical box for the display 
	 * of floating tasks
	 *  
	 * @param taskId      id of the task to be highlighted after update or add command
	 * @param taskList    list of all tasks to be displayed
	 *                
	 * @return            vertical box VBox for display of floating tasks
	 */
	public static VBox displayFloatingTasks(int taskId, List<Task> taskList) {
		
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.TOP_LEFT);
		vBox.setPadding(new Insets(18, 18, 18, 18));
		vBox.setSpacing(15);
		vBox.setPrefWidth(500);
		vBox.setStyle("-fx-background-color: #F9FFC6;");

		boolean haveFloatingTasks = false;

		HBox floating = new HBox();
		floating.setAlignment(Pos.TOP_CENTER);
		Text floatingHeader = new Text(TITLE_FLOATING);
		floatingHeader.setFont(Font.font(FONT_TAHOMA, FontWeight.BOLD, 20));
		floatingHeader.setTextAlignment(TextAlignment.CENTER);
		floatingHeader.setFill(Color.web("#3C220A"));
		floating.getChildren().add(floatingHeader);
		vBox.getChildren().add(floating);

		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getType().equals(TYPE_FLOATING)) {
				haveFloatingTasks = true;
				String floatingTask = (count++ + ". " + taskList.get(i).getDescription());

				Text floatingDisplay = new Text(WordUtils.wrap(floatingTask, 62, "\n", true));
				floatingDisplay.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
				
				if (count == taskId + 1) {
					Timeline blinker = createBlinker(floatingDisplay);
					FillTransition colour = changeColour(floatingDisplay, Color.BLACK);
					SequentialTransition blink = new SequentialTransition(floatingDisplay, blinker, colour);

					blink.play();
				}
				vBox.getChildren().add(floatingDisplay);
			}
		}

		if (haveFloatingTasks == false) {
			Text noFloatingTasks = new Text("*none*");
			noFloatingTasks.setFont(Font.font(FONT_CALIBRI, FontWeight.NORMAL, 16));
			vBox.getChildren().add(noFloatingTasks);
		}

		return vBox;
	}
    
	/**
	 * Return a horizontal box customizing of the top
	 * (header) section of the display according to listType
	 * (normal, finished, deleted)
	 *
	 * @param listType      type of list to be displayed as instructed by 
	 *                      Logic's UIState       
	 * @return              horizontal box HBox for the header area
	 */
	public static HBox addHeader(doordonote.logic.UIState.ListType listType) {
		
		HBox hBox = new HBox();
		hBox.setPadding(new Insets(20, 25, 20, 25));
		if (listType.equals(doordonote.logic.UIState.ListType.NORMAL)) {
			hBox.setStyle("-fx-background-color: #0D0D0D;");
		} else if (listType.equals(doordonote.logic.UIState.ListType.FINISHED)) {
			hBox.setStyle("-fx-background-color: #000E54;");
		} else {
			hBox.setStyle("-fx-background-color: #560202;");
		}

		setTitleEffects();

		hBox.getChildren().add(title);
		hBox.setAlignment(Pos.CENTER);

		return hBox;
	}
	
	/**
	 * Sets shadow effects for the title Text object of the header area 
	 * of the display
	 * 
	 */
	public static void setTitleEffects() {
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(4.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));

		title.setFont(Font.font(FONT_AHARONI, FontWeight.BOLD, 30));
		title.setFill(Color.WHITE);
		title.setEffect(shadow);
		title.setCache(true);
		title.setX(10.0f);
		title.setY(270.0f);
	}

	/**
	 * Creates a Timeline object for the blinker, to be played 
	 * when a task is to be higlighted
	 * 
	 * @param node       The node to which the blinking effect is to be added
	 *                   In this class, text object
	 * 
	 * @return Timeline  Timeline object for the blinker
	 */
	public static Timeline createBlinker(Node node) {
		
		KeyValue keyValue1 = new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE);
		KeyValue keyValue2 = new KeyValue(node.opacityProperty(), 0, Interpolator.DISCRETE);
		KeyValue keyValue3 = new KeyValue(node.opacityProperty(), 1, Interpolator.DISCRETE);
		
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0), keyValue1);
		KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.25), keyValue2);
		KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(0.5), keyValue3);
		
		Timeline blink = new Timeline(keyFrame1, keyFrame2, keyFrame3);
		
		blink.setCycleCount(3);

		return blink;
	}
    
	/**
	 * Creates a FillTransition object for changing colour purposes, 
	 * to be played when a task is to be higlighted
	 * 
	 * @param shape            the shape whose colour is to be changed
	 *                         In this class, a Text object
	 * @param                  the final colour to be set to shape
	 *                         after colour change 
	 * 
	 * @return                 the FillTransition object for changing 
	 *                         colour of the shape passed
	 */
	public static FillTransition changeColour(Shape shape, Color color) {
		
		FillTransition fill = new FillTransition(Duration.seconds(5), shape, 
				Color.web("#DFCA00"), color);
		
		return fill;
	}

	/**
	 * @param args  the command line arguments
	 *            
	 */
	public static void main(String[] args) {
		
		launch(args);
	}

	//@@author A0132785Y-unused
    /* This method was unused in the final product as we used org.apache.commons.lang's
     * WordUtil class' wrap() method for wrapping text of long tasks
     */
	public static String wrapText(String text) {
		StringBuilder sb = new StringBuilder(text);

		int x = 0;
		while (x + 50 < sb.length() && (x = sb.lastIndexOf(" ", x + 50)) != -1) {
			sb.replace(x, x + 1, "\n");
		}
		return sb.toString();
	}

}
