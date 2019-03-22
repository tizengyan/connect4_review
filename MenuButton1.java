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

// central text button
public class MenuButton1 extends StackPane {
	private Text text;

	public MenuButton1(String name) {
		setText(new Text(name));
		getText().getFont();
		getText().setFont(Font.font(20));
		getText().setFill(Color.WHITE);

		Rectangle bg = new Rectangle(150, 30);
		bg.setOpacity(0.6);
		bg.setFill(Color.BLACK);
		bg.setEffect(new GaussianBlur(3.5));
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(bg, getText());

		this.setOnMouseEntered(e -> {
			bg.setTranslateY(-4);
			getText().setTranslateY(-4);
			bg.setFill(Color.WHITE);
			getText().setFill(Color.BLACK);
		});

		this.setOnMouseExited(e -> {
			bg.setTranslateY(0);
			getText().setTranslateY(0);
			bg.setFill(Color.BLACK);
			getText().setFill(Color.WHITE);
		});

		DropShadow drop = new DropShadow(50, Color.WHITE);
		drop.setInput(new Glow());

		this.setOnMousePressed(e -> setEffect(drop));
		this.setOnMouseReleased(e -> setEffect(null));
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
}
