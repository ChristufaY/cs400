import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.geometry.Pos;
import java.util.Random;

public class DessertGame extends Application {

	private int score = 0;
    @Override
    public void start(final Stage stage) {
        // Step 1 & 2
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 640, 480);
        stage.setTitle("Dessert in the Desert JavaFX Game");
        
        // Step 3
        Label scoreLabel = new Label("Score: 0");
        borderPane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.TOP_LEFT);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            Platform.exit();
        });
        borderPane.setBottom(exitButton);
        BorderPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        
        // Step 4
        Pane pane = new Pane();
        borderPane.setCenter(pane);
        BorderPane.setAlignment(pane, Pos.CENTER);

        // TODO: Step 5-8
        Button[] buttons = new Button[8];
        Random rand = new Random();
        buttons[0] = new Button("Dessert");
        buttons[0].setOnAction(e -> {score += 1; borderPane.setTop(new Label("Score: " + score)); 
        randomizeButtonPositions(rand, buttons);
        exitButton.requestFocus();});
        for(int i = 1; i < 8; i++) {
        	buttons[i] = new Button("Desert");
        	buttons[i].setOnAction(e -> {score -= 1; borderPane.setTop(new Label("Score: " + score)); 
        	randomizeButtonPositions(rand, buttons);
        	exitButton.requestFocus();});
        }
        for(int j = 0; j < 8; j++ ) {
        	pane.getChildren().add(buttons[j]);
        }
        randomizeButtonPositions(rand, buttons);
        stage.setScene(scene);
        stage.show();
    }

    private void randomizeButtonPositions(Random rand, Button[] buttons) {
    	for(int i = 0; i < buttons.length; i++) {
    		buttons[i].setLayoutX(rand.nextInt(601));
    		buttons[i].setLayoutY(rand.nextInt(401));
    	}
    }
    public static void main(String[] args) {
        Application.launch();
    }
}