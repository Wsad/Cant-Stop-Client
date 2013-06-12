/**@author Nigel Gauvin RulesPanel to display the rules of the game Can't Stop */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;



public class RulesPanel extends JFrame implements ActionListener {
	private Container contentPane;
	private JPanel mainPanel;
	private JButton mainMenu;
	private ClientConnection connection;
	


	/**Constructor RulesPanel creates the panel and loads in onto the contentPane
	 * @param	Container contentPaneIn
	 * @param 	ClientConnection connectionIn*/
	public RulesPanel(Container contentPaneIn, ClientConnection connectionIn){
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		connection = connectionIn;
		JPanel border = new JPanel();
		border.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Game Rules."));

		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));

		JTextField rules = new JTextField();
		rules.setEditable(false);
		

 		JPanel innerPanel = new JPanel();
 		innerPanel.setLayout(new BoxLayout(innerPanel,BoxLayout.PAGE_AXIS));
 

		JTextPane rules = new JTextPane();
		rules.setContentType("text/html");
 		rules.setEditable(false);
		rules.setText("GameRules.txt");
		rules.setText("<html>GameRules<br><html><br>Goal: To be the first player to reach the top of three columns." +
				"<br>Playing: To start your turn, roll all four dice. Look your roll over carefully. " +
				"<br>Then split your roll in half in any way you wish, and add the two dice in each half." +
				"<br>The pair of numbers you choose to create represents the two columns into which you must now place game pieces." +
				"<br>In this game you may roll more than once on a single turn. On each additional roll, you also create a pair of " +
				"<br>numbers in the same way." +
				"<br>a) Lets say you roll again and create a pair that includes a number you've already chosen. When this happens," +
				"<br>move the game piece up one space in that numbers column.<br> b) Lets say you roll again and decide to " +
				"<br>create a pair with a new number. If you have another game piece left, you must place it into the new game pieces column." +
				"<br>You may continue to roll as long as your last roll allowed you either to place a game piece or to move one up. If you prefer" +
				"<br>, you may stop your turn whenever you wish. To stop, simply replace each temporary game piece with one of your final game pieces." +
				"<br>a) If you choose a column that does not already have one of your colored pieces in it, place the game piece onto the space at the bottom of that column." +
				"<br>b) If you choose a column that does already have one of your colored game pieces in it, place the game piece onto the space directly above your colored square." +
				"<br>c) You may place a game piece onto a space that's already occupied by an opponent's piece.<br> d) If you can place a piece on your roll,you must." +
				"<br>Craping Out When your roll will not allow you either to place a game piece or to move one up, you've crapped out and must end your turn." +
				"<br>Remove all of the pieces that you've placed, but leave all of your colored squares that are already on the board." +
				"<br>Remember:As soon as you've placed all three pieces on your turn, each additional roll on that turn must allow you to move up at least one of the pieces." +
				"<br>Otherwise you've crapped out and your turn ends." +
				"<br>Winning a Column.You win a column as soon as you place one of your game pieces onto the number at the top of that column. " +
				"<br>If any of your opponents already has a colored square in a column that you win, he or she must remove that square immediately.</html>");
 		
 		mainMenu = new JButton("Return to main");
 		mainMenu.setVisible(true);

		mainMenu = new JButton("Return to main");
		mainMenu.setVisible(true);
		mainMenu.setMaximumSize(new Dimension(200,50));
		mainMenu.setMinimumSize(new Dimension(200,50));
		mainMenu.setPreferredSize(new Dimension(200,50));
		mainMenu.setAlignmentX(Container.CENTER_ALIGNMENT);
		mainMenu.addActionListener(this);

		innerPanel.add(rules);
		innerPanel.add(mainMenu);
		innerPanel.setPreferredSize(new Dimension(400,400));
		innerPanel.setMaximumSize(new Dimension(400,400));
		innerPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

		mainPanel.add(innerPanel);
		mainPanel.setPreferredSize(new Dimension(450,450));

		mainPanel.setMaximumSize(new Dimension(450,450));

		border.add(mainPanel);

		contentPane.add(border);
		contentPane.revalidate();
	}

	/**Method actionPerformed adds action to the mainMenu button to load MainMenuFrame onto the content pane
	 * @param ActionEvent e 
	 * @return void	*/
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == mainMenu){
			MainMenuFrame m = new MainMenuFrame(contentPane,connection);
		}	
		}
	
	}

