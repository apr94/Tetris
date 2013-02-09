import java.awt.*;

//It previews the next tile that will be dropped and draws it in a  4x4 block 
// array
import javax.swing.*;

@SuppressWarnings("serial")
public class previewArea extends JComponent {

	Tetromino pq;

	Blocks[][] preview = new Blocks[4][4];

	final int COURTWIDTH = 4;
	final int COURTHEIGHT = 4;

	public previewArea() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		for (int i = 0; i < 4; i++) {
			{
				for (int j = 0; j < 4; j++) {

					preview[i][j] = (new Blocks(i * 20, j * 20, 20, 20,
							Color.WHITE));

				}

			}

		}
	}

	public void reset(int choice) {

		clear();

		if (choice == -1) {

		}

		else if (choice == 0) {
			preview[1][1].color = Color.cyan;
			preview[1][2].color = Color.cyan;
			preview[2][1].color = Color.cyan;
			preview[2][2].color = Color.cyan;
		}

		else if (choice == 1) {
			preview[0][1].color = Color.orange;
			preview[1][1].color = Color.orange;
			preview[2][1].color = Color.orange;
			preview[3][1].color = Color.orange;
		}

		else if (choice == 2) {
			preview[0][1].color = Color.yellow;
			preview[1][1].color = Color.yellow;
			preview[2][1].color = Color.yellow;
			preview[1][2].color = Color.yellow;

		}

		else if (choice == 3) {
			preview[1][2].color = Color.red;
			preview[2][2].color = Color.red;
			preview[2][1].color = Color.red;
			preview[3][1].color = Color.red;
		}

		else {
			preview[0][1].color = Color.green;
			preview[0][2].color = Color.green;
			preview[1][2].color = Color.green;
			preview[2][2].color = Color.green;
		}

		repaint();

	}

	public void clear() {
		for (int i = 0; i < 4; i++) {
			{
				for (int j = 0; j < 4; j++) {

					preview[i][j].color = Color.white;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < 4; i++) {
			{
				for (int j = 0; j < 4; j++) {

					preview[i][j].draw(g);

				}
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(80, 80);
	}
}
