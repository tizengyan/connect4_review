package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

// create connect4
public class Connect4 extends Parent {
	private final static int TILE_SIZE = 80;
	private final int ROWS = 6;
	private final int COLUMNS = 7;
	public boolean redToMove;
	private Disc[][] grid = new Disc[COLUMNS][ROWS];
	private Pane discRoot = new Pane();
	public boolean isPlayWithBot = true;

	public Connect4() {
		getChildren().add(discRoot);
		getChildren().add(makeBoard());
		getChildren().addAll(makeColumns1());
	}

	// create disc class
	private class Disc extends Circle {

		private final boolean color;

		public Disc(boolean color) {
			super(TILE_SIZE / 2, color ? Color.RED : Color.YELLOW);
			this.color = color;
			setCenterX(TILE_SIZE / 2);
			setCenterY(TILE_SIZE / 2);
		}
	}

	private void playWithBot(int col) {
		placeDisc(new Disc(redToMove), col);
		easyBotMove();
	}

	private void playWithHuman(int col) {
		placeDisc(new Disc(redToMove), col);
	}

	private void easyBotMove() {
		int col = (int) (Math.random() * COLUMNS);
		
		while(getDisc(col, 0).isPresent()) {
			col = (int) (Math.random() * COLUMNS);
		}
		
		placeDisc(new Disc(!redToMove), col);
	}

	private List<Rectangle> makeColumns1() {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < COLUMNS; i++) {
			Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
			rect.setTranslateX(i * (TILE_SIZE + 5) + 20);
			rect.setFill(Color.TRANSPARENT);

			rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 200, 0.3)));
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

			final int col = i;

			rect.setOnMouseClicked(e -> {
				if(!getDisc(col, 0).isPresent()) {
					if (isPlayWithBot)	
						playWithBot(col);
					else
						playWithHuman(col);
				}
			});

			list.add(rect);
		}
		return list;
	}

	private Shape makeBoard() {
		Shape board = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 1) * TILE_SIZE);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(j * (TILE_SIZE + 5) + 20);
				circle.setTranslateY(i * (TILE_SIZE + 5) + 20);

				board = Shape.subtract(board, circle);
			}
		}

		Light.Distant light = new Light.Distant();
		light.setAzimuth(30);
		light.setElevation(70);
		
		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(2);
		
		board.setFill(Color.CORNFLOWERBLUE);
		board.setEffect(lighting);
		
		return board;
	}
	
	private void placeDisc(Disc disc, int col) {
		int row = ROWS - 1;
		
		while (row >= 0) {
			if (!getDisc(col, row).isPresent())
				break;
			row--; // move up if that row occupied
		}
		if (row < 0)
			return;
		grid[col][row] = disc;
		disc.setTranslateX(col * (TILE_SIZE + 5) + 20);
		discRoot.getChildren().add(disc);
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.2), disc);
		animation.setToY(row * (TILE_SIZE + 5) + 20);

		final int currentRow = row;

		animation.setOnFinished(e -> {
			if (gameisOver(col, currentRow)) {
				gameOver();
			}
			redToMove = !redToMove;
		});
		
		animation.play();
	}

	private void gameOver() {
		System.out.println("Game Over. " + (redToMove ? "Red" : "Yellow") + " player wins. ");
		
		project_main.gameMenu.showWinner();
	}

	public void restart() {
		grid = new Disc[COLUMNS][ROWS];
		while (!discRoot.getChildren().isEmpty())
			discRoot.getChildren().remove(0);
	}

	private boolean gameisOver(int col, int row) {
		int count = 0;
		for(int i = 0; i < COLUMNS; i++) {
			if(getDisc(i, 0).isPresent())
				count++;
		}
		if(count == COLUMNS) {
			project_main.gameMenu.isDraw = true;
			return true;
		}
		// IntStream?? point2D??
		List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3).mapToObj(r -> new Point2D(col, r))
				.collect(Collectors.toList());
		List<Point2D> horizontal = IntStream.rangeClosed(col - 3, col + 3).mapToObj(c -> new Point2D(c, row))
				.collect(Collectors.toList());

		Point2D topLeft = new Point2D(col - 3, row - 3);
		List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6).mapToObj(i -> topLeft.add(i, i))
				.collect(Collectors.toList());

		Point2D botLeft = new Point2D(col - 3, row + 3);
		List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6).mapToObj(i -> botLeft.add(i, -i))
				.collect(Collectors.toList());

		return checkRange(vertical) || checkRange(horizontal) || checkRange(diagonal1) || checkRange(diagonal2);
	}

	private boolean checkRange(List<Point2D> points) {
		int count = 0;
		for (Point2D p : points) {
			int col = (int) p.getX();
			int row = (int) p.getY();

			Disc disc = getDisc(col, row).orElse(new Disc(!redToMove));
			if (disc.color == redToMove) {
				count++;
				if (count == 4) {
					return true;
				}
			} else
				count = 0;
		}

		return false;
	}

	private Optional<Disc> getDisc(int col, int row) {
		if (col < 0 || col >= COLUMNS || row < 0 || row >= ROWS) {
			return Optional.empty();
		}
		return Optional.ofNullable(grid[col][row]);
	}


}
