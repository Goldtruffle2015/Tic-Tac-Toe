package application;
	
import java.io.IOException;

import Gui.Tile;
import Network.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	private static final int WIDTH = 600;	// Defines the width of the window
	private static final int HEIGHT = 600;	// Defines the height of the window
	private static Tile[] tiles = new Tile[9];	// Stores the tiles
	private static Pane root;
	private static Scene scene;
	private static Client client;
	private static int player;	// Identifies the player number
	
	@Override
	public void start(Stage primaryStage) {
		try {
			client = new Client("192.168.1.71");
			player = client.getPlayer();
			System.out.println(player);
			
			// Initialize Tiles //
			root = new Pane();
			for (int i=0;i<3;i++) {
				for (int j=0;j<3;j++) {
					root.getChildren().addAll(new Tile(200*i, 200*j, player));
				}
			}
			
			scene = new Scene(root, WIDTH, HEIGHT, true);
			
			// Mouse Listener //
			scene.setOnMousePressed(event -> {
				try {
					client.sendToServer(new int[] {(int) event.getSceneX(), (int) event.getSceneY(), player});
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			// Initialize Window //
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
