package application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuBG extends Parent {
	public ImageView connect4BG;
	public ImageView tootBG;
	
	public MenuBG() {
		this.getChildren().add(loadImg());
	}

	private Pane loadImg() {
		InputStream is;
		Pane root = new Pane();
		Text title = new Text("Made by Tizeng Yan.");
		title.setTranslateX(30);
		title.setTranslateY(525);
		title.setFont(Font.font(25));
		try {
			is = Files.newInputStream(Paths.get("images/Connect-4.jpg"));
			Image im1 = new Image(is);
			is = Files.newInputStream(Paths.get("images/TOOT1.png"));
			Image im2 = new Image(is);
			is.close();
			connect4BG = new ImageView(im1);
			connect4BG.setFitWidth(640);
			connect4BG.setFitHeight(560);
			tootBG = new ImageView(im2);
			tootBG.setFitWidth(640);
			tootBG.setFitHeight(560);
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.getChildren().addAll(connect4BG, tootBG, title);
		connect4BG.setVisible(false);
		tootBG.setVisible(false);
		return root;
	}
}
/*
  Running time: less than 0.1s
*/