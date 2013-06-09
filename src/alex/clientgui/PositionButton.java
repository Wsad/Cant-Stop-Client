package alex.clientgui;

import javax.swing.*;
import java.awt.Color;

public class PositionButton extends JButton {
	private int height;
	private int column;
	
	public PositionButton(int col, int h){
		super();
		column = col;
		height = h;
		setRolloverEnabled(false);
		setBackground(Color.white);
	}
	
	public int getColHeight(){
		return height;
	}
	
	public int getColumn(){
		return column;
	}
}
