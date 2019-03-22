package application;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// effect remained menu button
public class MenuButton2 extends StackPane {
	private Text text;
	private boolean isPressed = false;
	Rectangle bg = new Rectangle(120, 30);
	
	public void setisPressed(boolean t) {
		this.isPressed = t;
	}

	public MenuButton2(String name) {
		text = new Text(name);
		text.getFont();
		text.setFont(Font.font(20));
		text.setFill(Color.WHITE);

		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(bg, text);

		this.setOnMouseEntered(e -> {
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});

		this.setOnMouseExited(e -> {
			if (!isPressed) {
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			}
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());

		this.setOnMousePressed(e -> {
			setEffect(drop);
			isPressed = true;
		});

		this.setOnMouseReleased(e -> setEffect(null));
	}

	public void reverseColor(boolean isLightened) {
		if (isLightened) {
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		} else {
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		}
	}
}

