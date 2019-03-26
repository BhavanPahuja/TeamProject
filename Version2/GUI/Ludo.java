package LudoGUI;

//Code taken from https://github.com/TaylorTheDeveloper/Ludo-Java/tree/master/Ludo/src

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
class Ludo extends JFrame {
	private static int turnValue;
	private static int displayValue;
	private static int roll;
	private static int numPlayers;
	private static JFrame gameFrame;
	private static JLabel rollView;
	private static Board b;
	private static boolean rollAgain;
	private Menu m;
	private JPanel mContainer;
	private JButton rollButton;
	private JButton nextTurn;

	/*
	 * Ludo Game Constructor
	 */
	public Ludo() {
		// rollAgain is initially false
		rollAgain = false;
		// Set and Initialize Number of Players
		turnValue = 0;
		displayValue = turnValue + 1;
		// numPlayers = 1;
		chooseNumPlayers();

		// Initialize Roll Button
		rollButton = new JButton("Roll Dice");
		rollButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Ludo.roll();
				while (rollAgain == true) {
					rollAgain = false;// Set to false imediatley
					System.outAWTException
							.println("Ludo:rollButton.addActionListener(): Roll again set to false");
					System.out
							.println("Ludo:rollButton.addActionListener(): Rolling Again, automatically");
					System.out
							.println("Ludo:rollButton.addActionListener(): before Roll: "
									+ roll);
					roll += rollAgain();
					System.out
							.println("Ludo:rollButton.addActionListener():  after Roll: "
									+ roll);
				}
				Board.movePlayer(turnValue, roll);
				repaint();
				int res = Board.checkWin();
				if (res > -1) {
					gameHasBeenWon(res);
				}
				rollButton.setEnabled(false);
				nextTurn.setEnabled(true);
			}
		});
		// Initialize Next Turn Button
		nextTurn = new JButton("Next Turn");
		nextTurn.setEnabled(false);
		nextTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				rollButton.setEnabled(true);
				nextTurn.setEnabled(false);
				getNextTurn();

			}
		});

		// Initialize Board and Menu
		b = new Board(numPlayers);
		m = new Menu();
		m.add(rollButton);
		m.add(nextTurn);

		// Game Panel Stuff
		mContainer = new JPanel();
		rollView = new JLabel("Turn: Player " + displayValue
				+ ";    Dice Roll: ");

		rollView.setBorder(new EmptyBorder(5, 5, 5, 5));
		b.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContainer.add(m, "East");
		mContainer.add(rollView, "West");
		add(b, "Center");
		add(mContainer, "South");
		getContentPane().setBackground(new Color(250, 250, 250));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(-900, 200, 725, 850);//Second Monitor Debugging
		pack();
		setBounds(20, 20, 900, 850);// Single Monitor
		setVisible(true);
	}

	public static void gameHasBeenWon(int id) {
		JOptionPane.showMessageDialog(null, "Congrats Player " + id
				+ "!!! You've just won Ludo!");
		// JOptionPane.showMessageDialog(null, "");
		String[] options = { "Play Again!", "I quit!" };
		String x = (String) JOptionPane.showInputDialog(null,
				"Would you like to play a new game or quit?", "Ludo",
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (x == "Play Again!") {
			reset();
		} else {
			System.exit(0);
		}
	}

	/*
	 * Next Turn Returns the index of the next Game Player
	 */
	public static void getNextTurn() {
		// Check Roll Again Condition
		turnValue += 1;// Increment Players
		turnValue %= numPlayers;// Modulo this for infinite loop

		displayValue = turnValue + 1;
		rollView.setText("Turn: Player " + displayValue + ";    Dice Roll: ");
		System.out.println("\n\nLudo:getNextTurn(): Player "
				+ displayValue);AWTException
	}

	/*
	 * Roll Dice. If both Dice are equal, Player can go again
	 */
	public static int roll() {
		int display = turnValue + 1;
		Random diceRoller = new Random();
		roll = diceRoller.nextInt(6) + 1;// Roll first Dice
		int roll2 = diceRoller.nextInt(6) + 1;// Roll second Dice

		if (roll == roll2) {
			// Removed Due to miscalculations 12/3/14
			// rollAgain = true;
			// System.out.println("Ludo:roll(): Roll again set to true");
		}
		rollView.setText("Turn: Player " + display + ";    Dice Roll: "
				+ Integer.toString(roll) + " and " + Integer.toString(roll2));

		System.out.println("Ludo:roll(): Roll value " + roll + " and "
				+ roll2);

		// Now Update
		roll += roll2;
		return roll;
	}

	/*
	 * Roll Dice Again, can only happen once
	 */
	public static int rollAgain() {
		System.out.println("Ludo:rollAgain(): entering this method");
		Random diceRoller = new Random();
		int roll1 = diceRoller.nextInt(6) + 1;// Roll first Dice
		int roll2 = diceRoller.nextInt(6) + 1;// Roll second Dice

		System.out.println("Ludo:rollAgain(): Roll value " + roll1
				+ " and " + roll2);

		// Now Update
		roll1 += roll2;
		return roll1;
	}

	/*
	 * Choose Number of Players with Error Bounds Checking
	 */
	public static void chooseNumPlayers() {
		while (numPlayers > 4 || numPlayers < 1) {
			String[] options = { "1", "2", "3", "4" };
			String x = (String) JOptionPane.showInputDialog(null,
					"How Many Players? (1-4)", "Ludo",
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

			if (x == null) {
				System.exit(0);
			} else {
				numPlayers = Integer.parseInt(x);
			}
		}

	}

	/*
	 * Resets the Game
	 */
	public static void reset() {
		gameFrame.setVisible(false); // you can't see me!
		gameFrame.dispose();
		gameFrame = new Ludo();
	}
	AWTException
	public static void PlayAI() throws AWTException {
		Robot bot = new Robot();
		try {
			Thread.sleep(500); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		bot.mouseMove(520, 845);
		bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
		try {
			Thread.sleep(500); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		bot.keyPress(KeyEvent.VK_ENTER);
		bot.keyRelease(KeyEvent.VK_ENTER);
		try {
			Thread.sleep(500); // 1000 milliseconds is one second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		bot.mouseMove(540, 845);
		bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static Board getBoard() {
		return b;
	}

	/*
	 * Main Game Method
	 */
	public static void main(String[] args) throws AWTException {
		gameFrame = new Ludo();
		/*
		 * Uncomment this code below if you wish for it to run a game
		 * automatically for you. This feature was used while debugging to save
		 * me click time! All you are required to input is the number of players
		 * (1-4)
		 */
		//
		// int res = getBoard().checkWin();
		// while(res < 0){
		// PlayAI();
		// res = getBoard().checkWin();
		// }
	}

}