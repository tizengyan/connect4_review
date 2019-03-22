package application;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

// create menu
public class GameMenu extends Parent {
	private Text winner1 = new Text("Red Player Wins!");
	private Text winner2 = new Text("Yellow Player Wins!");
	private Text youWin = new Text("You Win!");
	private Text youLose = new Text("You Lose!");
	private Text tootFound = new Text("TOOT FOUND!");
	private Text ottoFound = new Text("OTTO FOUND!");
	private Text draw = new Text("BOARD FULL!");
	private Text confirm = new Text("Are you sure?");
	private boolean isChoseConnect4 = false;

	VBox mainMenu = new VBox(10); // Vertical box
	VBox optionMenu = new VBox(10);
	VBox winnerMenu = new VBox(10);
	HBox winnerBtns = new HBox(10); // Horizontal box
	HBox modeBtns = new HBox(10);
	HBox gameSelectMenu = new HBox(10);
	VBox confirmMenu = new VBox(10);
	
	final int offSet = 600;
	
	public boolean isDraw = false;
	public boolean holdMenu = false;

	public GameMenu() {
		winner1.setFont(Font.font(36));
		winner1.setFill(Color.WHITE);
		winner2.setFont(Font.font(36));
		winner2.setFill(Color.WHITE);
		youWin.setFont(Font.font(40));
		youWin.setFill(Color.WHITE);
		youLose.setFont(Font.font(40));
		youLose.setFill(Color.WHITE);
		tootFound.setFont(Font.font(40));
		tootFound.setFill(Color.WHITE);
		ottoFound.setFont(Font.font(40));
		ottoFound.setFill(Color.WHITE);
		draw.setFont(Font.font(40));
		draw.setFill(Color.WHITE);
		confirm.setFont(Font.font(40));
		confirm.setFill(Color.RED);

		gameSelectMenu.setTranslateX(65);
		gameSelectMenu.setTranslateY(260);
		gameSelectMenu.setVisible(true);
		
		mainMenu.setTranslateX(100);
		mainMenu.setTranslateY(170);
		mainMenu.setVisible(false);
		optionMenu.setTranslateX(100);
		optionMenu.setTranslateY(200);
		optionMenu.setTranslateX(offSet);
		
		// connect4 outcome menu
		winnerMenu.setTranslateX(165);
		winnerMenu.setTranslateY(230);
		winnerMenu.setVisible(false);
		
		MenuButton2 mode1 = new MenuButton2("1 VS BOT");
		MenuButton2 mode2 = new MenuButton2("1 VS 1");
		MenuButton0 restart0 = new MenuButton0("RESTART");
		MenuButton1 restart2 = new MenuButton1("RESTART");
		MenuButton0 resume = new MenuButton0("RESUME");
		MenuButton0 start = new MenuButton0("START");
		MenuButton0 diff = new MenuButton0("DIFFICULTY");
		MenuButton0 exit0 = new MenuButton0("EXIT");
		MenuButton1 returnToMain2 = new MenuButton1("RETURN TO MENU");
		returnToMain2.getText().setFont(Font.font(17));
		MenuButton0 back = new MenuButton0("BACK");
		MenuButton0 returnToMain = new MenuButton0("RETURN TO MENU");
		//returnToMain.text.setFont(Font.font(18));
		MenuButton0 easy = new MenuButton0("EASY");
		MenuButton0 hard = new MenuButton0("HARD");
		MenuButton0 selectGame = new MenuButton0("SELECT GAME");
		MenuButton3 connect4Btn = new MenuButton3("Connect4");
		MenuButton3 tootBtn = new MenuButton3("TOOT & OTTO");

		mode1.reverseColor(false);
		mode1.setOnMouseClicked(e -> {
			System.out.println("mode1");
			//if(isChoseConnect4)
				project_main.connect4.isPlayWithBot = true;
			//else
				project_main.toot.isPlayWithBot = true;
			mode2.reverseColor(true);
			mode2.setisPressed(false);
		});
		mode2.setOnMouseClicked(e -> {
			System.out.println("mode2");
			//if(isChoseConnect4)
			project_main.connect4.isPlayWithBot = false;
			//else
			project_main.toot.isPlayWithBot = false;
			mode1.reverseColor(true);
			mode1.setisPressed(false);
		});

		restart0.setOnMouseClicked(e -> {
			holdMenu = false;
			isDraw = false;
			if(isChoseConnect4)
				project_main.connect4.restart();
			else
				project_main.toot.restart();
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);

			ft.setOnFinished(e1 -> this.setVisible(false));
			
			ft.play();
		});

		restart2.setOnMouseClicked(e -> {
			holdMenu = false;
			isDraw = false;
			if(isChoseConnect4)
				project_main.connect4.restart();
			else
				project_main.toot.restart();
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);

			ft.setOnFinished(e1 -> {
				this.setVisible(false);
				mainMenu.setVisible(true);
//				while(winnerMenu.getChildren().size() != 1)
//					winnerMenu.getChildren().remove(0);
				winnerMenu.getChildren().removeAll(draw, tootFound, ottoFound);
				winnerMenu.setVisible(false);
			});
			ft.play();
		});

		resume.setOnMouseClicked(e -> {
			fadeAway(this);
		});
		
		returnToMain.setOnMouseClicked(e -> {
			holdMenu = false;
			mainMenu.getChildren().removeAll(resume, restart0, returnToMain);
			mainMenu.getChildren().add(0, selectGame);
			mainMenu.getChildren().add(0, start);
			if(isChoseConnect4)
				fadeAway(project_main.connect4);
			else
				fadeAway(project_main.toot);
			showUp(project_main.menubg);
		});

		start.setOnMouseClicked(e -> {
			holdMenu = false;
			fadeAway(project_main.menubg);
			if(isChoseConnect4) {
				project_main.connect4.restart();
				showUp(project_main.connect4);
			}
			else {
				project_main.toot.restart();
				showUp(project_main.toot);
			}
			
			FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
			ft.setFromValue(1);
			ft.setToValue(0);

			ft.setOnFinished(e1 -> {
				setVisible(false);
				mainMenu.getChildren().removeAll(start, selectGame);
				mainMenu.getChildren().add(0, returnToMain);
				mainMenu.getChildren().add(0, restart0);
				mainMenu.getChildren().add(0, resume);
			});
			ft.play();
			
			
		});

		diff.setOnMouseClicked(e -> {
			getChildren().add(optionMenu);
			TranslateTransition tt0 = new TranslateTransition(Duration.seconds(0.25), mainMenu);
			tt0.setToX(mainMenu.getTranslateX() - offSet);
			TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), optionMenu);
			tt1.setToX(mainMenu.getTranslateX());

			tt0.play();
			tt1.play();

			tt0.setOnFinished(e1 -> getChildren().remove(mainMenu));
		});

		exit0.setTranslateY(35);
		exit0.setOnMouseClicked(e -> Platform.exit());

		returnToMain2.setOnMouseClicked(e -> {
			holdMenu = false;
			mainMenu.getChildren().removeAll(resume, restart0, returnToMain);
			mainMenu.getChildren().add(0, selectGame);
			mainMenu.getChildren().add(0, start);
			if(isChoseConnect4)
				fadeAway(project_main.connect4);
			else
				fadeAway(project_main.toot);
			showUp(mainMenu);
			winnerMenu.setVisible(false);
			showUp(project_main.menubg);
		});

		back.setOnMouseClicked(e -> {
			getChildren().add(mainMenu);
			TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), optionMenu);
			tt1.setToX(optionMenu.getTranslateX() + offSet);
			TranslateTransition tt0 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
			tt0.setToX(optionMenu.getTranslateX());

			tt0.play();
			tt1.play();

			tt1.setOnFinished(e1 -> getChildren().remove(optionMenu));
		});

		easy.setOnMouseClicked(e -> {
			getChildren().add(mainMenu);
			TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), optionMenu);
			tt1.setToX(optionMenu.getTranslateX() + offSet);
			TranslateTransition tt0 = new TranslateTransition(Duration.seconds(0.5), mainMenu);
			tt0.setToX(optionMenu.getTranslateX());

			tt0.play();
			tt1.play();

			tt1.setOnFinished(e1 -> getChildren().remove(optionMenu));
		});
		
		selectGame.setOnMouseClicked(e -> {
			fadeAway(project_main.menubg);
			fadeAway(mainMenu);
			showUp(gameSelectMenu);
		});
		
		connect4Btn.setOnMouseClicked(e -> {
			this.isChoseConnect4 = true;
			fadeAway(gameSelectMenu);
			project_main.menubg.connect4BG.setVisible(true);
			project_main.menubg.tootBG.setVisible(false);
			showUp(project_main.menubg);
			showUp(mainMenu);
		});
		
		tootBtn.setOnMouseClicked(e -> {
			this.isChoseConnect4 = false;
			fadeAway(gameSelectMenu);
			project_main.menubg.tootBG.setVisible(true);
			project_main.menubg.connect4BG.setVisible(true);
			showUp(project_main.menubg);
			showUp(mainMenu);
		});

		gameSelectMenu.getChildren().addAll(connect4Btn, tootBtn);
		
		modeBtns.getChildren().addAll(mode1, mode2);
		mainMenu.getChildren().addAll(start, selectGame, modeBtns, diff, exit0);

		optionMenu.getChildren().addAll(back, easy, hard);

		winnerBtns.getChildren().addAll(restart2, returnToMain2);
		winnerMenu.getChildren().add(winnerBtns);

		Rectangle bg = new Rectangle(800, 600);
		bg.setFill(Color.GRAY);
		bg.setOpacity(0.8);

		this.getChildren().addAll(bg, gameSelectMenu, mainMenu, winnerMenu);
	}
	
	private void fadeAway(Node n) {
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), n);
		ft.setFromValue(1);
		ft.setToValue(0);

		ft.setOnFinished(e1 -> n.setVisible(false));
		ft.play();
	}
	
	private void showUp(Node n) {
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), n);
		ft.setFromValue(0);
		ft.setToValue(1);

		n.setVisible(true);
		ft.play();
	}

	public void showWinner() {
		winnerMenu.getChildren().removeAll(youWin, youLose, winner1, winner2, tootFound, ottoFound, draw);
		if(isDraw) {
			winnerMenu.getChildren().add(0, draw);
			// make the winning reminder center
			isDraw = false;
			winnerMenu.getChildren().get(0).setTranslateX(40);
		}
		// if playing connect4
		else if(isChoseConnect4) {
			if (project_main.connect4.redToMove) {
				if (project_main.connect4.isPlayWithBot) {
					winnerMenu.getChildren().add(0, youLose);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(70);
				} 
				else {
					winnerMenu.getChildren().add(0, winner1);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(25);
				}
			}
			else {
				if(project_main.connect4.isPlayWithBot) {
					winnerMenu.getChildren().add(0, youWin);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(70);
				}
				else
					winnerMenu.getChildren().add(0, winner2);
			}
		}
		// if playing TOOT
		else {
			if (project_main.toot.getisT()) {
				if (project_main.toot.isPlayWithBot) {
					winnerMenu.getChildren().add(0, tootFound);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(40);
				} 
				else {
					winnerMenu.getChildren().add(0, tootFound);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(40);
				}
			} 
			else {
				if(project_main.toot.isPlayWithBot) {
					winnerMenu.getChildren().add(0, ottoFound);
					// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(40);
				}
				else
					winnerMenu.getChildren().add(0, ottoFound);
				// make the winning reminder center
					winnerMenu.getChildren().get(0).setTranslateX(40);
			}
		}
		
		// show the winner menu
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
		ft.setFromValue(0);
		ft.setToValue(1);

		mainMenu.setVisible(false);
		setVisible(true);
		winnerMenu.setVisible(true);
		ft.play();

		holdMenu = true;
	}
}
