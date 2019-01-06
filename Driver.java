package word;





import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;



public class Driver extends Application  {
	private Button buttonA;	
	private Stage window;
	private DocGen gen;
	 private Desktop desktop = Desktop.getDesktop();
	 private String mdPath;
	 private String entPath;
	 
	
	 public String getMdPath() {
		 return mdPath;
	 }
	 
	 public String getEntPath() {
		 return entPath;
	 }
   public static void main(String[] args)throws Exception {
	 
	  launch(args);
   }


   @Override
	public void start(Stage primaryStage) throws Exception {
		//main javaFX code
		window = primaryStage;
		window.setTitle("Welcome to Schedule!");
		// closing by hitting X
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
			
		});
		
		buttonA = new Button();
		buttonA.setText("Do Schedule");
		buttonA.setContentDisplay(ContentDisplay.CENTER);
		
		
		
		// Lamda notation
		
		buttonA.setOnAction(e -> {
			gen = new DocGen();
			gen.makeDoc();
		});
		
		
		
		//Layout embodiment top menu
		HBox topMenu = new HBox(30);
		Button button1 = new Button("File");
		Button button2 = new Button("Edit");
		Button button3 = new Button("View");
		topMenu.getChildren().addAll(button1,button2,button3);
		
		/**
		VBox leftMenu = new VBox(30);
		leftMenu.getChildren().addAll(buttonA,topMenu);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topMenu);
		borderPane.setLeft(leftMenu);
		**/
		//Working with grids
				GridPane grid = new GridPane();
				//Offset from edges in pixils
				grid.setPadding(new Insets(10,10,10,10));
				grid.setVgap(8);
				grid.setHgap(10);
				
				FileChooser chooser = new FileChooser();
				
				Label passLabel = new Label("Search mdReport.txt loc:");
				GridPane.setConstraints(passLabel, 0, 0);
				//Md report location
				Button passInput = new Button("Search");
				passInput.setOnAction(e -> {
					File file = chooser.showOpenDialog(window);
                    if (file != null) {
                      mdPath= file.getAbsolutePath();
                      ParseFile.setMdPath(mdPath);
                    	//openFile(file);
                       
                    }
				});
				GridPane.setConstraints(passInput, 1, 0);
				// ENT MD.txt loc
				Label entLabel = new Label("Search ENT MD.txt location:");
				GridPane.setConstraints(passLabel, 0, 1);
				Button entInput = new Button("Search");
				entInput.setOnAction(e -> {
					List<File> list =
	                        chooser.showOpenMultipleDialog(window);
	                    if (list != null) {
	                        for (File file : list) {
	                            //openFile(file);
	                            entPath=file.getAbsolutePath();
	                            ParseFile.dailyShedule.setEntPath(entPath);
	                        }
	                    }
				});
				GridPane.setConstraints(entInput, 1, 1);
				GridPane.setConstraints(buttonA,1, 7);
		
				
				//Add everything to the grid
				grid.getChildren().addAll(passLabel,passInput,entLabel,entInput,buttonA);
				
		//borderPane.autosize();
		
		//StackPane layout = new StackPane();
		//VBox layout = new VBox(30);
		//layout.getChildren().addAll(buttonA,buttonB,buttonC);
		//topMenu.setAlignment(Pos.CENTER);
		//leftMenu.setAlignment(Pos.CENTER);
		
		//Scene scene = new Scene(layout,300,250);
		//Scene scene = new Scene(borderPane,300,250);
		Scene scene = new Scene(grid,500,400);
		
		window.setScene(scene);
		window.show();
	
	}
	
	private void closeProgram() {
		boolean answer = ConfirmBox.display("Close Application", "Are you sure you want to exit?");
		if(true)
			window.close();
	}
	
	private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                Driver.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    


  
}