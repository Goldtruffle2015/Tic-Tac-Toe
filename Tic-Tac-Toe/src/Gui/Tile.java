package Gui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Tile extends StackPane {
	// -- Attributes -- //
	private final int size = 200;	// Defines the size of the rectangle
	private Rectangle rect;	// Defines the shape of the rectangle
	private int x;	// Defines the x-coordinate of the rectangle
	private int y;	// Defines the y-coordinate of the rectangle
	private int player;	// Defines the player tile belongs to
	private Text text = new Text();	// This is either X or O
	
	// -- Constructor -- //
	public Tile(int x, int y, int player) {
		this.x = x;	
		this.y = y;
		this.player = player;
		this.rect = new Rectangle(this.size, this.size);
		this.rect.setFill(Color.WHITE);
		this.rect.setStroke(Color.BLACK);
		this.text.setFont(new Font(80));
		super.getChildren().addAll(this.rect, this.text);
	}
	
	// -- Setter -- //
	public void setText(int player) {	// Draws X or O based 
		switch (player) {
			case 0:
				this.text.setText("O");
				break;
			case 1:
				this.text.setText("X");
				break;
			default:
				break;
		}
	}
	
	// -- Getter -- //
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	// -- Methods -- //
	public boolean checkIn(int x, int y) {	// Checks if a coordinate lies within a tile
		if (this.x <= x && x < this.x + 200 && this.y <= y && y < this.y + 200) {
			return true;
		} else {
			return false;
		}
	}
}
