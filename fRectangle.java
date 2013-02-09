import java.awt.*;

//The Rectangle tile. Checks for conflict while repainting.

public class fRectangle extends Tetromino {

	int a;

	public fRectangle(int vx, int vy, Color c, int startX, int startY, int w,
			int h, int t, int or) {
		super(vx, vy, c, startX, startY, w, h, t, or);
		blocks.add(new point(startX, startY - 1, 0, 1, 1));
		blocks.add(new point(startX, startY + 0, 0, 1, 1));
		blocks.add(new point(startX, startY + 1, 0, 1, 1));
		blocks.add(new point(startX, startY + 2, 1, 1, 1));
	}

	public void draw(Blocks[][] b) {

		a = 0;

		for (point p : blocks) {
			if (b[p.getX()][p.getY()].color != Color.white) {
				a = 1;

			}

		}

		if (a == 0) {

			for (point p : blocks) {

				b[p.getX()][p.getY()].color = c;

				if (vX == 0) {

					if (b[Math.max(0, p.getX())][Math.min(29, p.getY() + 1)].
							color != Color.white
							&& p.dPos == 1) {
						isDown = true;
					}
				}

				else if (vX == -1) {
					if (b[Math.max(0, p.getX() - 1)][Math.min(29, p.getY() + 1)].
							color != Color.white
							&& p.lPos == 1) {
						vX = 0;
					}
				}

				else {
					if (b[Math.min(15, p.getX() + 1)][Math
							.min(29, p.getY() + 1)].color != Color.white
							&& p.rPos == 1) {
						vX = 0;
					}
				}

			}

		}

		else {

			for (point p : blocks) {
				p.x = p.x - vX;
				p.y = p.y - vY;
			}
			if (vX == 0) {
				isDown = true;
			} else {
				vX = 0;
			}
		}

	}
}
