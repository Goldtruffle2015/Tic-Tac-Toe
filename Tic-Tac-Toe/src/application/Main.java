package application;
	
import Gui.Tile;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	private static final int WIDTH = 600;	// Defines the width of the window
	private static final int HEIGHT = 600;	// Defines the height of the window
	private static Pane root;
	private static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new Pane();
			for (int i=0;i<3;i++) {
				for (int j=0;j<3;j++) {
					root.getChildren().addAll(new Tile(i * 200, j * 200));
				}
			}
			
			scene = new Scene(root, WIDTH, HEIGHT, true);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Tic-Tac-Toe");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
