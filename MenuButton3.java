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

// big central text button
public class MenuButton3 extends StackPane {
	private Text text;

	public MenuButton3(String name) {
		text = new Text(name);
		text.getFont();
		text.setFont(Font.font(35));
		text.setFill(Color.WHITE);

		Rectangle bg = new Rectangle(250, 60);
		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(bg, text);

		this.setOnMouseEntered(e -> {
			bg.setTranslateY(-4);
			text.setTranslateY(-4);
			bg.setFill(Color.WHITE);
			text.setFill(Color.BLACK);
		});

		this.setOnMouseExited(e -> {
			bg.setTranslateY(0);
			text.setTranslateY(0);
			bg.setFill(Color.BLACK);
			text.setFill(Color.WHITE);
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());

		this.setOnMousePressed(e -> setEffect(drop));
		this.setOnMouseReleased(e -> setEffect(null));
	}
}

