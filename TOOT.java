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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TOOT extends Parent {
	private final static int TILE_SIZE = 100;
	private final int ROWS = 4;
	private final int COLUMNS = 6;
	private boolean TootToMove = true;
	private boolean isT;
	private Disc[][] grid = new Disc[COLUMNS][ROWS];
	private Pane discRoot = new Pane();
	public boolean isPlayWithBot = true;
	
	private int TofP1 = 6;
	private int OofP1 = 6;
	private int TofP2 = 6;
	private int OofP2 = 6;
	private Text remainedT1 = new Text("P1_T: ");
	private Text remainedO1 = new Text("P1_O: ");
	private Text remainedT2 = new Text("P2_T: ");
	private Text remainedO2 = new Text("P2_O: ");

	public TOOT() {
//		remainedDisc.getChildren().addAll(reminder1, reminder2);
//		getChildren().add(remainedDisc);
		getChildren().add(discRoot);
		getChildren().add(makeBoard());
		getChildren().addAll(makeColumns2());
	}
	
	public boolean getisT() {
		return isT;
	}
	
	private class Disc extends Parent{
		private boolean isT;
		private Text type;
		private Shape disc;
		
		public Disc(boolean isT) {
			this.isT = isT;
			
			disc = new Circle(TILE_SIZE / 2, Color.BLACK);
			disc = Shape.subtract(disc, new Circle(TILE_SIZE / 2 - 7));
			if(isT)
				type = new Text("T");
			else
				type = new Text("O");
			type.setFont(Font.font(72));
			type.setTranslateX(-20);
			if(!isT)
				type.setTranslateX(-27);
			type.setTranslateY(27);
			this.getChildren().addAll(disc, type);
		}
	}
	
	private void playWithBot(int col, boolean isPlaceT) {
		if(isPlaceT && TofP1 > 0) {
			placeDisc(new Disc(isPlaceT), col);
			easyBotMove2();
		}
		else if(!isPlaceT && OofP1 > 0){
			placeDisc(new Disc(isPlaceT), col);
			easyBotMove2();
		}
	}

	private void playWithHuman(int col, boolean isPlaceT) {
		if(isPlaceT && TofP1 > 0)
			placeDisc(new Disc(isPlaceT), col);
		else if(!isPlaceT && OofP1 > 0)
			placeDisc(new Disc(isPlaceT), col);
	}

	private void easyBotMove2() {
		int col = (int) (Math.random() * COLUMNS);
		while(getDisc(col, 0).isPresent()) {
			col = (int) (Math.random() * COLUMNS);
		}
		// choose T or O randomly
		boolean isPlaceT = Math.random() < 0.5 ? true : false;
		if(isPlaceT && TofP2 <= 0)
			isPlaceT = !isPlaceT;
		else if(!isPlaceT && OofP2 <= 0)
			isPlaceT = !isPlaceT;
		placeDisc(new Disc(isPlaceT), col);
	}
	
	private Shape makeBoard() {
		Shape board = new Rectangle(640, 560);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(j * (TILE_SIZE + 5) + 10);
				circle.setTranslateY(i * (TILE_SIZE + 18) + 60);

				board = Shape.subtract(board, circle);
			}
		}
		Light.Distant light = new Light.Distant();
		light.setAzimuth(30);
		light.setElevation(60);
		
		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(3.0);
		
		board.setFill(Color.CORNFLOWERBLUE);
		board.setEffect(lighting);
		
		return board;
	}
	
	private List<Rectangle> makeColumns2() {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < COLUMNS; i++) {
			Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE + 60);
			rect.setTranslateX(i * (TILE_SIZE + 5) + 10);
			rect.setFill(Color.TRANSPARENT);

			rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 200, 0.3)));
			rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

			final int col = i;
			rect.setOnMouseClicked(e -> {
				// left click
				if(e.getButton() == MouseButton.PRIMARY) {
					if(!getDisc(col, 0).isPresent()) {
						if(isPlayWithBot)
							playWithBot(col, true);
						else
							playWithHuman(col, true);
					}
				}
				// right click
				else if(e.getButton() == MouseButton.SECONDARY) {
					if(!getDisc(col, 0).isPresent()) {
						if(isPlayWithBot)
							playWithBot(col, false);
						else
							playWithHuman(col, false);
					}
				}
			});

			list.add(rect);
		}
		return list;
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
		disc.setTranslateX(col * (TILE_SIZE + 5) + 60);
		discRoot.getChildren().add(disc);
		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.2), disc);
		animation.setToY(row * (TILE_SIZE + 18) + 110);

		final int currentRow = row;

		animation.setOnFinished(e -> {
			if (gameisOver(col, currentRow)) {
				gameOver();
			}
			if(TootToMove) {
				if(disc.isT)
					this.TofP1--;
				else
					this.OofP1--;
			}
			else {
				if(disc.isT)
					this.TofP2--;
				else
					this.OofP2--;
			}
			System.out.println("Player" + (TootToMove ? "P1" : "P2") + " move:");
			System.out.println("TofP1: " + TofP1 + ", OofP1: " + OofP1);
			System.out.println("TofP2: " + TofP2 + ", OofP2: " + OofP2);
			System.out.println();
			TootToMove = !TootToMove;
		});
		
		animation.play();
	}

	private void gameOver() {
		project_main.gameMenu.showWinner();
	}

	public void restart() {
		this.TofP1 = 6;
		this.TofP2 = 6;
		this.OofP1 = 6;
		this.OofP2 = 6;
		grid = new Disc[COLUMNS][ROWS];
		while (!discRoot.getChildren().isEmpty())
			discRoot.getChildren().remove(0);
	}

	private boolean gameisOver(int col, int row) {
		// check draw case
		int count = 0;
		for(int i = 0; i < COLUMNS; i++) {
			if(getDisc(i, 0).isPresent()) {
				count++;
				//System.out.println("column " + i + " occupied");
			}
		}
		if(count == COLUMNS) {
			//System.out.println(count);
			project_main.gameMenu.isDraw = true;
			return true;
		}
		// IntStream, point2D
		List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
				.mapToObj(r -> new Point2D(col, r))
				.collect(Collectors.toList());
		
		List<Point2D> horizontal = IntStream.rangeClosed(col - 3, col + 3)
				.mapToObj(c -> new Point2D(c, row))
				.collect(Collectors.toList());

		Point2D topLeft1 = new Point2D(col - 3, row - 3);
		List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
				.mapToObj(i -> topLeft1.add(i, i))
				.collect(Collectors.toList());

		Point2D botLeft1 = new Point2D(col - 3, row + 3);
		List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
				.mapToObj(i -> botLeft1.add(i, -i))
				.collect(Collectors.toList());
		
		return checkRange(vertical) || checkRange(horizontal)
				|| checkRange(diagonal1) || checkRange(diagonal2);
	}

	private boolean checkRange(List<Point2D> points) {
		int count = 0;
		for(int i = 0; i < points.size(); i++) {
			count = 0;
			int col = (int) points.get(i).getX();
			int row = (int) points.get(i).getY();
			//System.out.println("checking from col: " + col + ", row: " + row);
			boolean isT;
			if(!getDisc(col, row).isPresent())
				continue;
			else {
				isT = getDisc(col, row).get().isT;
				this.isT = isT; // pass the found pattern value
			}
			
//			if(isT)
//				System.out.println("isT: " +isT);
			// check 2nd
			if(i + 1 < points.size()) {
				col = (int) points.get(i + 1).getX();
				row = (int) points.get(i + 1).getY();
				if(!getDisc(col, row).isPresent())
					continue;
				else if(getDisc(col, row).get().isT == !isT)
					count++;
			}
			// check 3rd
			if(i + 2 < points.size()) {
				col = (int) points.get(i + 2).getX();
				row = (int) points.get(i + 2).getY();
				if(!getDisc(col, row).isPresent())
					continue;
				else if(getDisc(col, row).get().isT == !isT)
					count++;
			}
			// check 4th
			if(i + 3 < points.size()) {
				col = (int) points.get(i + 3).getX();
				row = (int) points.get(i + 3).getY();
				if(!getDisc(col, row).isPresent())
					continue;
				else if(getDisc(col, row).get().isT == isT)
					count++;
			}
			
			if(count == 3)
				return true;
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

