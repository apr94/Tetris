import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public class PlayArea extends JComponent {

	Tetromino sq, fq, sq1;

	ArrayList<Tetromino> tetros = new ArrayList<Tetromino>();

	boolean gameOver = false;
	
	/* 
	 * EXPLANATION: DATASTRUCTURE USE
	 * 
	 * The board is represented as a two dimensional 16x30 array that contain 
	 * blocks. Blocks have the ability to paint themselves, given the coordinates
	 * and width and height. When a title passed over a block, one of the four
	 * points on the tile acts as a "remote" on the block and changes its colour.
	 * When the tile leaves, the block's colour is turned back to white, which
	 * signifies that no tile is occupying the block. The objectColors array stores
	 * the colours of the blocks, which are used when the block array is refreshed.
	 */

	Blocks[][] objects = new Blocks[16][30];
	Color[][] objectColors = new Color[16][30];

	private int interval = 100;
	boolean noRotate = false;
	Timer timer;

	final int COURTWIDTH = 16;
	final int COURTHEIGHT = 30;

	int score = 0;
	int combos = 0;
	int choice = -1;
	int lines = 0;

	/* 
	 * EXPLANATION: GAME LOGIC
	 * 
	 * This will explained through out the code. The gist is listening for key
	 * commands, and editing the state of the tiles based on it. The tick method
	 * continuously repaints the blocks based on the updated information. Before 
	 * the blocks are updated, the values are checked to make sure that they are
	 * within the boundaries and maintain the invariant. Also, the event of a
	 * game finish is monitored by the tick command.
	 */
	
	public PlayArea() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);

		for (int i = 0; i < 16; i++) {
			{
				for (int j = 0; j < 30; j++) {

					objects[i][j] = (new Blocks(i * 20, j * 20, 20, 20,
							Color.WHITE));
					objectColors[i][j] = Color.WHITE;

				}

			}
			
			/* 
			 * EXPLANATION: KEYBOARD CONTROLS
			 * 
			 * These are the keyboard controls. The left and right arrows control
			 * the direction of the tile. The up key rotates the tiles counter-
			 * clockwise. The shift key hard-drops the tile. When the other keys
			 * are pressed and released, some of the settings dictating the 
			 * behaviour of the tiles are reset.
			 */

			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						if (!sq.noLeft) {
							sq.setVelocity(-1, 1);
							sq.noLeft = true;
							sq.noRight = false;
							noRotate = false;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (!sq.noRight) {
							sq.setVelocity(1, 1);
							sq.noRight = true;
							noRotate = false;
							sq.noLeft = false;
						}
					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						sq.noRight = false;
						sq.noLeft = false;
						noRotate = false;
					}

					else if (e.getKeyCode() == KeyEvent.VK_UP) {

						sq.noRight = false;
						sq.noLeft = false;
						sq.setCoords();
						clear();
						if (sq.y > 1 && sq.y < 28) {
							Rotate(sq);
						}
						if (fq != null) {
							fq.setCoords();
							if (fq.x >= 0 && fq.x + fq.width < 16 && fq.y >= 0
									&& fq.y + fq.height < 40 && check(fq)) {
								sq = fq;
							}
						}
						fq = null;
						noRotate = true;

					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						completeDrop(sq);

						sq.noLeft = false;
						sq.noRight = false;
						noRotate = false;
					} else {

						sq.noLeft = false;
						sq.noRight = false;
						noRotate = false;

					}

				}

				public void keyReleased(KeyEvent e) {
					sq.setVelocity(0, 1);
					sq.noLeft = false;
					sq.noRight = false;
					noRotate = false;
				}

			});
		}

		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!gameOver) {
					tick();
				}

				else {
					timer.stop();

				}

			}
		});

	}
    
	// Generate a random tile from one of the five tiles.
	
	public void start() {

		
		choice = 0 + (int) (Math.random() * ((4 - 0) + 1));

		if (choice == 0) {
			sq = new Square(0, 1, Color.CYAN, COURTWIDTH / 2 - 1, 0, 2, 2, 0, 1);
		}

		else if (choice == 1) {
			sq = new Rectangle(0, 1, Color.ORANGE, COURTWIDTH / 2 - 1, 0, 4, 1,
					1, 1);
		}

		else if (choice == 2) {
			sq = new Tee(0, 1, Color.YELLOW, COURTWIDTH / 2 - 1, 0, 3, 2, 2, 1);
		}

		else if (choice == 3) {
			sq = new Zee(0, 1, Color.RED, COURTWIDTH / 2 - 1, 0, 3, 2, 3, 1);
		}

		else if (choice == 4) {
			sq = new Lee(0, 1, Color.GREEN, COURTWIDTH / 2 - 1, 0, 3, 2, 4, 1);
		}

		sq.setVelocity(0, 1);
		sq.setCoords();
		requestFocusInWindow();

		
		choice = 0 + (int) (Math.random() * ((4 - 0) + 1));

		if (choice == 0) {
			sq1 = new Square(0, 1, Color.CYAN, COURTWIDTH / 2 - 1, 0, 2, 2, 0,
					1);
		}

		else if (choice == 1) {
			sq1 = new Rectangle(0, 1, Color.ORANGE, COURTWIDTH / 2 - 1, 0, 4,
					1, 1, 1);
		}

		else if (choice == 2) {
			sq1 = new Tee(0, 1, Color.YELLOW, COURTWIDTH / 2 - 1, 0, 3, 2, 2, 1);
		}

		else if (choice == 3) {
			sq1 = new Zee(0, 1, Color.RED, COURTWIDTH / 2 - 1, 0, 3, 2, 3, 1);
		}

		else if (choice == 4) {
			sq1 = new Lee(0, 1, Color.GREEN, COURTWIDTH / 2 - 1, 0, 3, 2, 4, 1);
		}

		sq1.setVelocity(0, 1);
		sq1.setCoords();
		requestFocusInWindow();

		timer.start();

	}

	public void pause() {
		timer.stop();
	}

	public void resume() {
		timer.start();
	}
	
	// The old tile has fallen, create a new tile.

	public void reset() {

		sq = sq1;

		
		choice = 0 + (int) (Math.random() * ((4 - 0) + 1));

		if (choice == 0) {
			sq1 = new Square(0, 1, Color.CYAN, COURTWIDTH / 2 - 1, 0, 2, 2, 0,
					1);
		}

		else if (choice == 1) {
			sq1 = new Rectangle(0, 1, Color.ORANGE, COURTWIDTH / 2 - 1, 0, 4,
					1, 1, 1);
		}

		else if (choice == 2) {
			sq1 = new Tee(0, 1, Color.YELLOW, COURTWIDTH / 2 - 1, 0, 3, 2, 2, 1);
		}

		else if (choice == 3) {
			sq1 = new Zee(0, 1, Color.RED, COURTWIDTH / 2 - 1, 0, 3, 2, 3, 1);
		}

		else if (choice == 4) {
			sq1 = new Lee(0, 1, Color.GREEN, COURTWIDTH / 2 - 1, 0, 3, 2, 4, 1);
		}

		sq1.setVelocity(0, 1);
		sq1.setCoords();
		requestFocusInWindow();

	}
	
	// the background method that repaints the tiles and
	// checks for game completion.

	public void tick() {

		clear();
		sq.move(COURTWIDTH, COURTHEIGHT);
		sq.draw(objects);
		repaint();

		if (sq.isDown) {
			sq.setCoords();
			if (sq.y <= 0) {
				gameOver = true;
			} else {
				updateColors(sq);
				tetros.add(sq);
				checkCompletion();
				reset();
			}
		}

	}
	
	//update the board based on the color of the tiles.

	public void updateColors(Tetromino t) {
		for (point p : t.blocks) {
			objectColors[p.getX()][p.getY()] = sq.c;
		}
	}
	
	//clear the board of unwanted trails from moving tiles.

	public void clear() {
		for (int i = 0; i < 16; i++) {
			{
				for (int j = 0; j < 30; j++) {

					objects[i][j].color = objectColors[i][j];
				}
			}
		}
	}
	
	//This is the method that hard-drops the tile. It checks if there
	// exists a tile in the path between this tile and the bottom. If there is
	// none, the tile is placed on the bottom. If there is one, then the tile is
	// placed on top of the other tile.

	public void completeDrop(Tetromino t) {

		int my = 29;
		boolean present = false;
		for (int i = t.x; i < t.x + t.width; i++) {
			for (int j = t.y + t.height; j < COURTHEIGHT; j++) {
				if (objects[i][j].color != Color.white) {
					my = Math.min(my, j);
					present = true;
				}
			}
		}
		if (present) {

			int dif = 0;

			for (point p : sq.blocks) {
				if (p.dPos == 1) {
					dif = Math.max(dif, p.y);
				}
			}

			for (point p : sq.blocks) {

				p.setY(my - 2 - (dif - p.y));

			}
		}

		else {

			int dif = 0;

			for (point p : sq.blocks) {
				if (p.dPos == 1) {
					dif = Math.max(dif, p.y);
				}
			}
			for (point p : sq.blocks) {

				p.setY(29 - (dif - p.y));

			}
		}

	}
	
	//Checks to see if the rows are completed. If they are, then all the tiles
	// on the top are moved down by one, and the score, lines and combo values
	// are updated.

	public void checkCompletion() {
		boolean isComplete = true;
		boolean[] indexes = new boolean[30];

		for (int i = 0; i < 30; i++) {
			isComplete = true;
			for (int j = 0; j < 16; j++) {
				if (objects[j][i].color == Color.white) {
					isComplete = false;
				}

			}

			if (isComplete) {
				indexes[i] = true;
			}
		}

		for (int i = 0; i < 30; i++) {
			if (indexes[i] == true) {
				for (int j = 0; j < 16; j++) {
					objects[j][i].color = Color.white;
					objectColors[j][i] = Color.white;
				}

				for (int k = i; k > 0; k--) {

					for (int j = 0; j < 16; j++) {
						objects[j][k].color = objects[j][k - 1].color;
						objectColors[j][k] = objectColors[j][k - 1];
					}

				}

				for (int j = 0; j < 16; j++) {
					objects[j][0].color = Color.white;
				}

			}

		}

		int newLines = 0;

		for (int i = 0; i < 30; i++) {
			if (indexes[i] == true) {
				newLines++;
			}
		}
		
		/* 
		 * EXPLANATION: SCORE DISPLAY
		 * 
		 * These lines update the score, lines and combo. The method to calculate
		 * the score is (lines removed + 9)*lines removed. This means that each 
		 * extra line removed adds one extra point for each line removed. So removing
		 * one line gets 10 points, removing 2 lines gets 11 points per line and
		 * so on. So combos are rewarded.
		 */

		lines = lines + newLines;
		if (newLines > 1) {
			combos = combos + newLines;
		}
		score = score + ((newLines + 9) * newLines);

	}
	
	// This rotates the tile counterclockwise. A custom tile exists for every 
	// orientation, and this tile is placed at the center of the old tile. If 
	// the new tile is outside the border or collides with other tiles, then 
	// the tile is tried to be placed elsewhere. If this does not work, then
	// the original tile is not rotated.

	private void Rotate(Tetromino sq) {

		if (!noRotate) {
			if (sq.type == 1) {
				fq = new fRectangle(0, 1, Color.ORANGE, (sq.x + sq.width / 2),
						sq.y, 1, 4, 12, 3);
				fq.width = 1;
				fq.height = 4;
			}

			else if (sq.type == 12) {
				fq = new Rectangle(0, 1, Color.ORANGE, (sq.x + sq.width / 2),
						(sq.y + sq.height/2), 4, 1, 1, 1);
				fq.width = 4;
				fq.height = 1;
			}

			else if (sq.type == 2) {
				fq = new lTee(0, 1, Color.YELLOW, (sq.x + sq.width / 2), sq.y,
						2, 3, 22, 1);
				fq.width = 2;
				fq.height = 3;
			}

			else if (sq.type == 22) {
				fq = new dTee(0, 1, Color.YELLOW, (sq.x + sq.width / 2), sq.y,
						3, 2, 23, 1);
				fq.width = 3;
				fq.height = 2;
			}

			else if (sq.type == 23) {
				fq = new rTee(0, 1, Color.YELLOW, (sq.x + sq.width / 2), sq.y,
						2, 3, 24, 1);
				fq.width = 2;
				fq.height = 3;
			}

			else if (sq.type == 24) {
				fq = new Tee(0, 1, Color.YELLOW, (sq.x + sq.width / 2), sq.y,
						3, 2, 2, 1);
				fq.width = 3;
				fq.height = 2;
			} else if (sq.type == 3) {
				fq = new lZee(0, 1, Color.RED, (sq.x + sq.width / 2), sq.y, 2,
						3, 32, 1);
				fq.width = 2;
				fq.height = 3;
			}

			else if (sq.type == 32) {
				fq = new Zee(0, 1, Color.RED, (sq.x + sq.width / 2), sq.y, 3,
						2, 3, 1);
				fq.width = 3;
				fq.height = 2;
			}

			else if (sq.type == 4) {
				fq = new lLee(0, 1, Color.GREEN, (sq.x + sq.width / 2), sq.y,
						2, 3, 42, 1);
				fq.width = 2;
				fq.height = 3;
			}

			else if (sq.type == 42) {
				fq = new uLee(0, 1, Color.GREEN, (sq.x + sq.width / 2), sq.y,
						3, 2, 43, 1);
				fq.width = 3;
				fq.height = 2;
			}

			else if (sq.type == 43) {
				fq = new rLee(0, 1, Color.GREEN, (sq.x + sq.width / 2), sq.y,
						2, 3, 44, 1);
				fq.width = 2;
				fq.height = 3;
			}

			else if (sq.type == 44) {
				fq = new Lee(0, 1, Color.GREEN, (sq.x + sq.width / 2), sq.y, 3,
						2, 4, 1);
				fq.width = 3;
				fq.height = 2;
			}

		}
	}
	
	//check for tile conflicts
	
	public boolean check(Tetromino t){
		for(point p: t.blocks){
			if(objects[p.getX()][p.getY()].color != Color.white){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < 16; i++) {
			{
				for (int j = 0; j < 30; j++) {

					objects[i][j].draw(g);

				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(320, 600);
	}
}
