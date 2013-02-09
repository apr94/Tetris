import java.awt.Color;
import java.util.ArrayList;

//The tetromino class. It implements the move, set coords and set velocity method.
// The paint method is implemented by the subclasses.

public abstract class Tetromino {

	ArrayList<point> blocks;
	int vX, vY;
	int miny = 0;
	Color c;
	Boolean isDown = false;
	Boolean modify = false;
	int width;
	int height;
	int x;
	int type;
	int y;
	boolean noLeft = false;
	int o;
	boolean noRight = false;

	public Tetromino(int vx, int vy, Color c, int startX, int startY, int w,
			int h, int t, int or) {
		this.c = c;
		blocks = new ArrayList<point>();
		width = w;
		height = h;
		x = startX;
		y = startY;
		type = t;
		o = or;

	}

	public void move(int xBound, int yBound) {

		for (point p : blocks) {

			if (p.dPos == 1) {
				if (p.getY() + vY >= yBound) {
					modify = true;
					isDown = true;
					break;
				}

			}

			if (x + vX < 0) {
				vX = 0;
			}

			if (x + width + vX > 16) {
				vX = 0;
			}

		}
		int diff = 0;

		for (point p : blocks) {
			if (p.dPos == 1) {
				diff = Math.max(diff, p.y);
			}
		}

		for (point p : blocks) {
			if (modify) {
				if (p.y == diff) {
					p.setX(Math.max(Math.min(p.getX() + vX, xBound - 1), 0));
					p.setY(yBound - 1);

				} else {
					p.setX(Math.max(Math.min(p.getX() + vX, xBound - 1), 0));
					p.setY(yBound - 1 - (diff - p.y));
				}
			} else {
				p.setX(Math.max(Math.min(p.getX() + vX, xBound - 1), 0));
				p.setY(Math.max(Math.min(p.getY() + vY, yBound - 1), 0));
			}
		}

		setCoords();

	}

	public void setCoords() {
		int tx = blocks.get(0).getX();
		int ty = blocks.get(0).getY();

		for (point p : blocks) {
			tx = Math.min(tx, p.getX());
			ty = Math.min(ty, p.getY());
		}

		x = tx;
		y = ty;
	}

	public void setVelocity(int vx, int vy) {
		vX = vx;
		vY = vy;
	}


	public ArrayList<point> getComponents() {
		return blocks;
	}

	public void draw(Blocks[][] b) {

	}

}
