package alex.clientgui;

import javax.swing.*;

import java.awt.*;
import java.util.Scanner;

public class EndPanel extends JPanel {
	private String userScore;
	private String topScore1;
	private String topScore2;
	private String topScore3;
	
	public EndPanel(boolean won, ClientConnection connectionIn){
		JLabel header = new JLabel();
		if(won){
			header.setText("You Won!");
		}else
			header.setText("You Lost");
		Font font = header.getFont();
		header.setFont(new Font(font.getFontName(), font.getStyle(), 25));
		header.setAlignmentX(Container.CENTER_ALIGNMENT);
		JLabel title1 = new JLabel("Your Stats:" +
				"                                                         ");
		title1.setFont(new Font(font.getFontName(), Font.BOLD, 16));
		
		JPanel userStatsTitle = new JPanel();
		userStatsTitle.setLayout(new GridLayout(1,3));
		
		String userResponse = connectionIn.read();
		Scanner scan = new Scanner(userResponse).useDelimiter(",");
		userStatsTitle.add(new JLabel("wins"));
		userStatsTitle.add(new JLabel("losses"));
		userStatsTitle.add(new JLabel("points"));
		
		JPanel userStats = new JPanel();
		userStats.setLayout(new GridLayout(1,3));
		userStats.add(new JLabel(""+scan.nextInt()));
		userStats.add(new JLabel(""+scan.nextInt()));
		userStats.add(new JLabel(""+scan.nextInt()));
		
		JPanel userBox = new JPanel();
		userBox.setLayout(new BoxLayout(userBox,BoxLayout.PAGE_AXIS));
		userBox.add(userStatsTitle);
		userBox.add(userStats);
		
		
		JPanel topStatsTitle = new JPanel();
		topStatsTitle.setLayout(new GridLayout(1,4));
		topStatsTitle.add(new JLabel("username"));
		topStatsTitle.add(new JLabel("wins"));
		topStatsTitle.add(new JLabel("games played"));
		topStatsTitle.add(new JLabel("points"));
		
		String topResponse = connectionIn.read();
		scan = new Scanner(topResponse).useDelimiter(",");
		
		JPanel topStats = new JPanel();
		topStats.setLayout(new GridLayout(1,4));
		topStats.add(new JLabel(scan.next()));
		topStats.add(new JLabel(""+scan.nextInt()));
		topStats.add(new JLabel(""+scan.nextInt()));
		topStats.add(new JLabel(""+scan.nextInt()));
		
		JPanel topStats2 = new JPanel();
		topStats2.setLayout(new GridLayout(1,4));
		topStats2.add(new JLabel(scan.next()));
		topStats2.add(new JLabel(""+scan.nextInt()));
		topStats2.add(new JLabel(""+scan.nextInt()));
		topStats2.add(new JLabel(""+scan.nextInt()));
		
		JPanel topStats3 = new JPanel();
		topStats3.setLayout(new GridLayout(1,4));
		topStats3.add(new JLabel(scan.next()));
		topStats3.add(new JLabel(""+scan.nextInt()));
		topStats3.add(new JLabel(""+scan.nextInt()));
		topStats3.add(new JLabel(""+scan.nextInt()));
		
		JPanel topBox = new JPanel();
		topBox.setLayout(new BoxLayout(topBox,BoxLayout.PAGE_AXIS));
		topBox.add(topStatsTitle);
		topBox.add(topStats);
		topBox.add(topStats2);
		topBox.add(topStats3);
		
		JLabel title2 = new JLabel("Top 3 Players:              " +
				"                                     ");
		title2.setFont(new Font(font.getFontName(), Font.BOLD, 16));
		title2.setAlignmentX(Container.RIGHT_ALIGNMENT);
		title1.setAlignmentX(Container.RIGHT_ALIGNMENT);
		JPanel mainBox = new JPanel();
		mainBox.setLayout(new BoxLayout(mainBox, BoxLayout.PAGE_AXIS));
		mainBox.add(header);
		mainBox.add(Box.createRigidArea(new Dimension (20,20)));
		mainBox.add(title1);
		mainBox.add(Box.createRigidArea(new Dimension (10,10)));
		mainBox.add(userBox);
		mainBox.add(Box.createRigidArea(new Dimension (20,20)));
		mainBox.add(title2);
		mainBox.add(Box.createRigidArea(new Dimension (10,10)));
		mainBox.add(topBox);
		mainBox.add(Box.createRigidArea(new Dimension (20,20)));
		mainBox.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel borderBox = new JPanel();
		borderBox.add(mainBox);
		borderBox.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0), 150));
		
		this.add(borderBox);
	}
}
