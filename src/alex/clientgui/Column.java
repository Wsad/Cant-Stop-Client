package alex.clientgui;

import javax.swing.*;
import java.awt.*;

public class Column extends JPanel {
	private JButton [] positions;
	private final int columnNum;
	
	public Column(int colNumIn){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		for (int i =0; i <6; i++){
			if (colNumIn == (i+2)){
				positions = new JButton[3+(2*i)];
				break;
			}
		}
		for (int i=6;i<11;i++){
			if (colNumIn == (i+2)){
				positions = new JButton[13-(2*(i-5))];
				break;
			}
		}
		for (JButton pos : positions){
			pos = new JButton();
			pos.setMinimumSize(new Dimension(47,28));
			pos.setMaximumSize(new Dimension(47,40));
			pos.setPreferredSize(new Dimension(47,34));
			//pos.setRolloverEnabled(false);
			pos.setBackground(Color.white);
			this.add(pos);
		}
		columnNum = colNumIn;
	}
	
	public void highLight(int position){
		try {
			positions[position-1].setBackground(Color.yellow);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Unable to highlight column: Array Index out of bounds ["+(position-1)+"]");
			System.out.println(e.getMessage());
		}
	}
}
