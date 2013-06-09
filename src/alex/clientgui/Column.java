package alex.clientgui;

import javax.swing.*;
import java.awt.*;

public class Column extends JPanel {
	public static final int USER = 1;
	public final int OPPONENT = 2;
	private boolean conquered;
	private PositionButton [] positions;
	private final int columnNum;
	private int tempHeight, finalHeight;
	private int opponentTemp, opponentFinal;
	
	public Column(int colNumIn){
		columnNum = colNumIn;
		tempHeight = 0;
		finalHeight = 0;
		opponentTemp = 0;
		opponentFinal =0;
		conquered = false;
		//set up number of buttons on column.
		for (int i =0; i <6; i++){
			if (colNumIn == (i+2)){
				positions = new PositionButton[3+(2*i)];
				this.setLayout(new GridLayout(3+(2*i),1));
				break;
			}
		}
		for (int i=6;i<11;i++){
			if (colNumIn == (i+2)){
				positions = new PositionButton[13-(2*(i-5))];
				this.setLayout(new GridLayout(13-(2*(i-5)),1));
				break;
			}
		}		
		//initialize buttons on column.
		for (int i=0;i< positions.length; i++){
			PositionButton pos = new PositionButton(columnNum,(positions.length-i));
			/*pos.setMinimumSize(new Dimension(47,28));
			pos.setMaximumSize(new Dimension(47,40));
			pos.setPreferredSize(new Dimension(47,34));
			pos.setRolloverEnabled(false);
			pos.setBackground(Color.white);*/
			positions[(positions.length-1)-i] = pos;//start from bottom of column
			this.add(pos);
		}
		
	}
	
	public PositionButton getPosButton(int height){
		return positions[height-1];
	}
	
	public int getColNum(){
		return columnNum;
	}
	
	public boolean getConquered(){
		return conquered;
	}
	
	public void setConquered(boolean value){
		conquered = value;
	}
	
	public boolean hasTemp(int PLAYER){
		if ((PLAYER == USER)&&(tempHeight > 0)){
			return true;
		}else if ((PLAYER == OPPONENT)&&(opponentTemp > 0)){
			return true;
		}else
			return false;
	}
	
	public boolean hasFinal(int PLAYER){
		if ((PLAYER == USER)&&(finalHeight > 0)){
			return true;
		}else if ((PLAYER == OPPONENT)&&(opponentFinal > 0)){
			return true;
		}else
			return false;
	}
	
	public int getTemp(int PLAYER){
		if (PLAYER == USER){
			return tempHeight;
		}else if (PLAYER == OPPONENT){
			return opponentTemp;
		}else
			return 0;
	}
	
	public int getFinal(int PLAYER){
		if (PLAYER == USER){
			return finalHeight;
		}else if (PLAYER == OPPONENT){
			return opponentFinal;
		}else
			return 0;
	}
	
	public void highLight(int position){
		try {
			positions[position-1].setBackground(Color.yellow);
			positions[position-1].setRolloverEnabled(true);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Unable to highlight column: Array Index out of bounds ["+(position-1)+"]");
			System.out.println(e.getMessage());
		}
	}
	
	public void removeHighlight(){
		for (PositionButton p : positions){
			p.setBackground(Color.white);
			p.setRolloverEnabled(false); 
		}
	}
}
