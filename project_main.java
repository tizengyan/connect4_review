package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

public class project_main extends Application {

	public static GameMenu gameMenu;
	public static Connect4 connect4;
	public static TOOT toot;
	public static MenuBG menubg;

	private Parent createGame() {
		Pane root = new Pane();
		root.setPrefSize(640, 560);

		connect4 = new Connect4();
		toot = new TOOT();
		gameMenu = new GameMenu();
		menubg = new MenuBG();
		root.getChildren().addAll(menubg, connect4, toot, gameMenu);

		return root;
	}

	private Scene sceneEffect(Scene scene) {
		gameMenu.setVisible(true);
		connect4.setVisible(false);
		toot.setVisible(false);
		menubg.setVisible(false);
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE && !gameMenu.holdMenu) {
				if (!gameMenu.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(0);
					ft.setToValue(1);
					gameMenu.setVisible(true);
					ft.play();
				} else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(e1 -> gameMenu.setVisible(false));
					ft.play();
				}
			}
		});
		return scene;
	}

	@Override
	public void start(Stage primaryStage) {
		// Scene scene = new Scene(createMenu());
		Scene scene = new Scene(createGame());

		scene = sceneEffect(scene);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Connect4 & TOOT");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
/*
  Running time: less than 0.1s
*/