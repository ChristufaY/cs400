import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.application.Platform;
public class JavaFXApp extends Application {

    @Override
    public void start(Stage stage) {

	Label label = new Label("This text should be visible.");
	Button button = new Button("Click Me!");
	button.setLayoutX(500);
	button.setOnAction(event -> {
		label.setText(label.getText() + "\nButton Clicked");
	    });
	Button button2 = new Button("Exit");
	button2.setLayoutX(580);
	button2.setOnAction(e -> { Platform.exit(); } );
	Group group = new Group(label, button, button2);
	Scene scene = new Scene(group, 640, 480);
	stage.setScene(scene);

	stage.setTitle("First JavaFXApp");
	stage.show();
    }

    public static void main(String[] args) {
	Application.launch();
    }
}
