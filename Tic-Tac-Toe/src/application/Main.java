package application;
	
import java.io.IOException;

import Gui.Tile;
import Network.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Main extends Application {
	private static final int WIDTH = 600;	// Defines the width of the window
	private static final int HEIGHT = 600;	// Defines the height of the window
	private static Tile[] tiles = new Tile[9];	// Stores the tiles
	private static Pane root;
	private static Scene scene;
	private static Client client;
	private static int player;	// Identifies the player number
	private static Line line;	// Draws a line 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			client = new Client("192.168.1.71");
			player = client.getPlayer();
			
			// Initialize Tiles //
			root = new Pane();
			
			scene = new Scene(root, WIDTH, HEIGHT, true);
			
			line = new Line();
			
			for (int i=0;i<3;i++) {
				for (int j=0;j<3;j++) {
					tiles[3*i + j] = new Tile(200*i, 200*j, player);
					tiles[3*i + j].setTranslateX(200*i);
					tiles[3*i + j].setTranslateY(200*j);
				}
			}
			
			// Mouse Listener //
			scene.setOnMousePressed(event -> {
				try {
					client.sendToServer(new int[] {(int) event.getSceneX(), (int) event.getSceneY(), player});
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			// Listen for Input //
			(new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							int[] data = client.getFromServer();
							for (Tile current : tiles) {
								if (current.checkIn(data[0], data[1])) {
									current.setText(data[2]);
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			})).start();
			
			// Check for combos //
			(new Thread(new Runnable() {
				@Override
				public void run() {
					boolean run = true;
					while (run) {
						// Check rows //
						for (int row=0;row<3;row++){
							if (tiles[row].getText() == tiles[row + 3].getText() &&
									tiles[row].getText() == tiles[row + 6].getText() &&
									tiles[row].getText() != "") {
								createLine(tiles[row].getCenterX(), tiles[row].getCenterY(), 
										tiles[row + 6].getCenterX(), tiles[row+6].getCenterY());
								run = false;
								break;
							} 
						}
						
						// Check columns //
						for (int col=0;col<3;col++) {
							if (tiles[col].getText() == tiles[col + 1].getText() &&
									tiles[col].getText() == tiles[col + 2].getText() &&
									tiles[col].getText() != "") {
								createLine(tiles[col].getCenterX(), tiles[col].getCenterY(), 
										tiles[col + 2].getCenterX(), tiles[col + 2].getCenterY());
								run = false;
								break;
							}
						}
						
						// Diagonals //
						if (tiles[0].getText() == tiles[4].getText() &&
								tiles[0].getText() == tiles[8].getText() &&
								tiles[0].getText() != "") {
							createLine(tiles[0].getCenterX(), tiles[0].getCenterY(),
									tiles[8].getCenterX(), tiles[8].getCenterY());
							run = false;
						}
						if (tiles[2].getText() == tiles[4].getText() &&
							tiles[2].getText() == tiles[6].getText() &&
							tiles[2].getText() != "") {
							createLine(tiles[2].getCenterX(), tiles[2].getCenterY(),
									tiles[6].getCenterX(), tiles[6].getCenterY());
							run = false;
						}
					}
				}
			})).start();
			
			// Initialize Window //
			for (int i=0;i<9;i++) {
				root.getChildren().addAll(tiles[i]);
			}
			root.getChildren().addAll(line);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Tic-Tac-Toe");
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void createLine(int startX, int startY, int endX, int endY) {
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
	}
}
