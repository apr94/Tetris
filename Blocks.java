import java.awt.Color;
import java.awt.Graphics;

// The block class that can paint itself.
public class Blocks {

	int x;
	int y;
	int width;
	int height;
	Color color;

	public Blocks(int x, int y, int w, int h, Color c) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.color = c;
	}

	public void draw(Graphics g) {

		g.setColor(color);
		g.fill3DRect(x, y, width, height, false);

	}

}
