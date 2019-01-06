package word;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
public class ConfirmBox {
	//bool holding user's response
	static boolean answer;
	
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		// do not allow interaction with other windows until this one is closed
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		
		//Create 2 buttons
		Button yesButton = new Button("YES");
		Button noButton = new Button("NO");
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label,yesButton,noButton);
		//Setting alighment
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		// blocks any user interaction until this is closed
		window.showAndWait();
		
		return answer;
	}
}
