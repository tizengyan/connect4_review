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

// create menu button class
public class MenuButton0 extends StackPane {
	private Text text;

	public MenuButton0(String name) {
		text = new Text(name);
		text.getFont();
		text.setFont(Font.font(20));
		text.setFill(Color.WHITE);

		Rectangle bg = new Rectangle(250, 30);
		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		this.setAlignment(Pos.CENTER_LEFT);
		this.setRotate(-0.5);
		this.getChildren().addAll(bg, text);

		this.setOnMouseEntered(e -> {
			bg.setTranslateX(10);
			text.setTranslateX(10);
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});

		this.setOnMouseExited(e -> {
			bg.setTranslateX(0);
			text.setTranslateX(0);
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());

		this.setOnMousePressed(e -> setEffect(drop));
		this.setOnMouseReleased(e -> setEffect(null));
	}
}

