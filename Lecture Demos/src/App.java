
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;

public class App extends Application {

	private void createTopTitle(BorderPane bp) {
		Label titleLabel = new Label("Screen Title");
		titleLabel.setFont(new Font(32));
		BorderPane.setAlignment(titleLabel, Pos.CENTER);
		bp.setTop(titleLabel);
	}
	@SuppressWarnings("unchecked")
	private void createLeftButtons(BorderPane bp) {
		VBox box = new VBox();
		box.setSpacing(8);
		box.setPadding(new Insets(16));
		String[] labels = new String[] {"Run", "Hide", "Info", "Quit"};
		EventHandler<ActionEvent>[] events =(EventHandler<ActionEvent>[])new EventHandler[] {
			e -> { new Alert(AlertType.INFORMATION, "This program is running.", ButtonType.OK).show();},
			e -> { bp.setLeft(null);},
			e -> { new Alert(AlertType.INFORMATION, "This application was created for CS400.", ButtonType.OK).show();},
			e -> { Platform.exit();}
		};
		for(int i = 0; i < labels.length; i++) {
		Button button = new Button(labels[i]);
		button.setOnAction(events[i]);
		box.getChildren().add(button);
		}
//		Button button = new Button("label");
//		button.setOnAction(e -> { new Alert(AlertType.INFORMATION, "This button was clicked.", ButtonType.OK).show(); });
//		box.getChildren().add(button);
		
		bp.setLeft(box);
	}
	private void createCenterImage(BorderPane bp) {
		GridPane grid = new GridPane();
		grid.setVgap(16);
		grid.setHgap(16);
		grid.setMaxWidth(632);
		
		//grid.setBackground(new Background(new BackgroundFill(Paint.valueOf("#000088"), null, null)));
		for(int i = 0; i < 6; i++) {
		ImageView imageView = new ImageView();
		imageView.setImage(new Image("file:lion.jpg", 200, 0, true, true));
		GridPane.setColumnIndex(imageView, i%3);
		GridPane.setRowIndex(imageView, i/3);
		grid.getChildren().add(imageView);
		}
//		ImageView imageView = new ImageView();
//		imageView.setImage(new Image("file:lion.jpg", 200, 0, true, true));
//		GridPane.setColumnIndex(imageView, 0);
//		GridPane.setRowIndex(imageView, 0);
//		grid.getChildren().add(imageView);
		bp.setCenter(grid);
	}
	public void start(Stage stage) {
		BorderPane bp = new BorderPane();
		createTopTitle(bp);
		createLeftButtons(bp);
		createCenterImage(bp);
		Scene scene = new Scene(bp, 800, 600);
		
		stage.setScene(scene);
		stage.setTitle("Layout Management");
		stage.show();
	}
	public static void main(String[] args) {
		Application.launch();

	}

}
