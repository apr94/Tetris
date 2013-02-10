import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Game implements Runnable {


	// These are various swing objects that are displayed in the GUI and
	// display dynamic information from the court object.

	static Timer timer;
	JTextArea updatesArea;
	PlayArea court;
	JPanel cards;
	Document doc;
	JButton pButton;
	JButton qButton;

	public void run() {

		final JFrame frame = new JFrame("Tetris");
		frame.setLocation(300, 0);

		court = new PlayArea();
		final JPanel courtPanel = new JPanel();
		courtPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		courtPanel.add(court);

		JPanel startPanel = new JPanel(new GridLayout(5, 1));
		startPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(),
						BorderFactory.createEmptyBorder(100, 20, 100, 10))));

		JButton startButton = new JButton("PLAY!");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.next(cards);
				updatesArea.setText("");
				court.start();
			}
		});
		JButton instructButton = new JButton("INSTRUCTIONS");
		instructButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.last(cards);
			}
		});

		String descWords = "I decided to implement the\n "
				+ "classic computer game: Tetris! Tetris is a puzzle\n "
				+ "game where different tiles called \"Tetrominoes\"\n "
				+ "are dropped on top of each other. The user can control\n "
				+ "the Tetrominoes upto a certain extent so as to ensure that\n "
				+ "the tiles are arranged with no space between them. When an\n "
				+ "entire row contains all tiles, the row is removed, and\n "
				+ "the player gains points for removing a line. The game ends\n "
				+ "when the rows of tetrominoes hit the upper edge of the\n "
				+ "playing field.\n\n";

		String explWords = "The primary input is through the keyboard.\n "
				+ "Use the left and right arrow keys to control the motion\n "
				+ "of the tiles. Use the up arrow key to rotate tiles by 90\n "
				+ "degrees. Finally, use the space key to immidiately drop a tile\n "
				+ "to the lowest possible point. Press the pause button to \n "
				+ "pause the game and the quit button to quit and start\n " +
				"a new game.\n\n";

		String featWords = "This game has a preview feature, where you can view\n "
				+ "the next dropped tile. It has constantly updated information\n "
				+ "showing lines cleared, scores won and combos achieved. Also\n "
				+ "an update box show developments in the game. Features like\n " +
				"hard-drop, rotation and handling of wall kicks are implemented.\n" +
				" Mouse handling by many buttons in the GUI are also present.";

		JTextPane desc = new JTextPane();
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setBold(set, true);
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(set, 36);
		desc.setCharacterAttributes(set, true);
		desc.setText(" INSTRUCTIONS \n\n");

		set = new SimpleAttributeSet();
		StyleConstants.setBold(set, true);
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 20);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), "Description\n", set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 12);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), descWords, set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		set = new SimpleAttributeSet();
		StyleConstants.setBold(set, true);
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 20);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), "Explanation\n", set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 12);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), explWords, set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		set = new SimpleAttributeSet();
		StyleConstants.setBold(set, true);
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 20);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), "Features\n", set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		set = new SimpleAttributeSet();
		StyleConstants.setAlignment(set, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontSize(set, 12);
		doc = desc.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), featWords, set);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		JLabel startTitle = new JLabel("TETRIS", JLabel.CENTER);
		startTitle.setFont(new Font("Serif", Font.BOLD, 48));

		startPanel.add(startTitle);
		startPanel.add(new JPanel());
		startPanel.add(startButton);
		startPanel.add(new JPanel());
		startPanel.add(instructButton);

		JPanel instructPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		instructPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(),
						BorderFactory.createEmptyBorder(0, 0, 0, 0))));

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.first(cards);
			}
		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		instructPanel.add(desc, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(20, 90, 0, 90);
		instructPanel.add(backButton, c);

		cards = new JPanel(new CardLayout());
		cards.add(startPanel, "Start Screen");
		cards.add(courtPanel, "Game Screen");
		cards.add(instructPanel, "Instructions Screen");

		frame.add(cards, BorderLayout.WEST);

		final JPanel controlPanel = new JPanel();
		final previewArea prev = new previewArea();
		prev.reset(court.choice);

		final JLabel scoreLabel = new JLabel("SCORE:  ");
		final JLabel updatesLabel = new JLabel("UPDATES");
		final JLabel pieceLabel = new JLabel("NEXT PIECE");
		final JLabel linesLabel = new JLabel("LINES:");
		final JLabel combosLabel = new JLabel("COMBOS:");
		final JLabel score = new JLabel();
		final JLabel lines = new JLabel();
		final JLabel combos = new JLabel();
		updatesArea = new JTextArea("No Updates");

		JButton qButton = new JButton("Quit");
		pButton = new JButton("Pause");

		qButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.gameOver = true;
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.first(cards);
				court = new PlayArea();
				courtPanel.removeAll();
				courtPanel.add(court);
			}
		});

		pButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (court.timer.isRunning()) {
					pButton.setText("Resume");
					court.timer.stop();
				} else {
					pButton.setText("Pause");
					court.timer.start();
				}
			}
		});

		controlPanel.setLayout(new GridLayout(8, 2));
		controlPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(),
						BorderFactory.createEmptyBorder(0, 20, 5, 20))));
		controlPanel.add(updatesLabel);
		controlPanel.add(new JPanel());
		controlPanel.add(updatesArea);
		controlPanel.add(new JPanel());
		controlPanel.add(pieceLabel);
		controlPanel.add(new JPanel());
		controlPanel.add(prev);
		controlPanel.add(new JPanel());
		controlPanel.add(scoreLabel);
		controlPanel.add(score);
		controlPanel.add(linesLabel);
		controlPanel.add(lines);
		controlPanel.add(combosLabel);
		controlPanel.add(combos);
		controlPanel.add(qButton);
		controlPanel.add(pButton);

		frame.add(controlPanel, BorderLayout.EAST);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		/*
		 * EXPLANATION: SCORE DISPLAY
		 *
		 *  A method that continuously polls the court object for information
		 *  of lines removed, combos achieved and points won. This information
		 *  is then updated in the JLabels in the control panel. Also, it is
		 *  checked to see if the game is over so as to switch to the main
		 *  screen and possibly start another game.
		 */

		timer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (court.gameOver) {
					JOptionPane.showMessageDialog(
							frame,
							"Game Over! You have cleared " + lines.getText()
									+ " lines and have scored "
									+ score.getText() + " points.");
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.first(cards);
					court = new PlayArea();
					courtPanel.removeAll();
					courtPanel.add(court);
					updatesArea.setText("Game Over!");
				}

				score.setText(Integer.toString(court.score));
				lines.setText(Integer.toString(court.lines));
				combos.setText(Integer.toString(court.combos));
				prev.reset(court.choice);

			}
		});
		timer.start();

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());

	}

}
