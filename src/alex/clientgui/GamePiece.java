package alex.clientgui;

import javax.swing.*;
import java.awt.*;

public class GamePiece extends JLabel {
	public static final int USER=1;
	public static final int OPPONENT=2;
	private ImageIcon userIcon = new ImageIcon("userPiece.jpg");
	private ImageIcon opponentIcon = new ImageIcon("opponentPiece.jpg");
	private ImageIcon userFinalIcon = new ImageIcon("userFinalPiece.jpg");
	private ImageIcon opponentFinalIcon = new ImageIcon("opponentFinalPiece.jpg");
	private int colNum;
	private int xPos;
	private int yPos;
	
	public GamePiece(int playerIn){
		super();
		if (playerIn == USER){
			this.setIcon(userIcon);
		}else if (playerIn == OPPONENT){
			this.setIcon(opponentIcon);
		}
	}
	
	public GamePiece(int playerIn, int col, int height){
		super();
		colNum = col;
		if (playerIn == USER){
			this.setIcon(userIcon);
		}else if (playerIn == OPPONENT){
			this.setIcon(opponentIcon);
		}
		xPos = 107+(col-2)*(47);
		yPos = 520-(height-1)*(35);
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	public void setYPixels(int pixels){
		yPos=pixels;
	}
	
	public void setY(int height){
		yPos = 520-(height-1)*(35);
	}
	public int getCol(){
		return colNum;
	}
	
	public void setFinal(int PLAYER){
		if (PLAYER == USER)
			this.setIcon(userFinalIcon);
		else if (PLAYER == OPPONENT)
			this.setIcon(opponentFinalIcon);
	}
}
