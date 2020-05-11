package Gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Pane {
	// -- Attributes -- //
	private final int size = 200;	// Defines the size of the rectangle
	private Rectangle rect;	// Defines the shape of the rectangle
	private int x;	// Defines the x-coordinate of the rectangle
	private int y;	// Defines the y-coordinate of the rectangle
	
	// -- Constructor -- //
	public Tile(int x, int y) {
		this.x = x;	
		this.y = y;
		this.rect = new Rectangle(this.size, this.size, Color.WHITE);
		this.rect.setTranslateX(this.x);
		this.rect.setTranslateY(this.y);
		this.rect.setStroke(Color.BLACK);
		super.getChildren().addAll(this.rect);
		
	}
	
	// -- Setter -- //
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	// -- Getter -- //
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Rectangle getRect() {
		return this.rect;
	}
	
	// -- Methods -- //
}
