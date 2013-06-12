package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
//import java.awt.Image;
import javax.swing.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.ArrayList;

public class BoardFrame extends JFrame implements ActionListener, MouseListener {
	private JLayeredPane boardPane;
	private Column[] columns;
	private JLabel[] colLabels;
	private JLabel dialogue;
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	private ClientConnection connection;
	private JPanel buttonPanel, backlight, GamePiecePanel;
	private JButton roll;
	private JButton stop;
	private JLabel dice1, dice2, dice3, dice4;
	//game logic
	private int numPieces, numOpPieces;
	private String move;
	private int moveNum, moveChoice;
	private int d1,d2,d3,d4;
	private int alpha;
	private boolean lightOn;
	private Timer turnTimer, fadeInTimer, fadeOutTimer;
	private GamePiece [] tempUserPieces, tempOpPieces;
	private ArrayList<GamePiece> finalUserPieces, finalOpPieces;
	private PositionButton doubleMove;
	
	public BoardFrame(Container contentPaneIn, ClientConnection connectionIn, int playerNum){
		connection = connectionIn;
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		contentPane.setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel centrePanel = new JPanel();
		
		//set up diceAndButton (contains dice panel and button panel)
		JPanel diceAndButton = new JPanel();
		diceAndButton.setLayout(new BoxLayout(diceAndButton,BoxLayout.PAGE_AXIS));
		diceAndButton.setPreferredSize(new Dimension(200,360));
		diceAndButton.setMaximumSize(new Dimension(200,360));
		diceAndButton.setMinimumSize(new Dimension(200,360));
		diceAndButton.setBorder(BorderFactory.createEmptyBorder(35, 10, 35, 5));
		
		//set up dice panel
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new GridLayout(2,2));
		dice1 = new JLabel(new ImageIcon("1.jpg"));
		dice2 = new JLabel(new ImageIcon("1.jpg"));
		dice3 = new JLabel(new ImageIcon("1.jpg"));
		dice4 = new JLabel(new ImageIcon("1.jpg"));
		dice1.setBorder(BorderFactory.createRaisedBevelBorder());
		dice2.setBorder(BorderFactory.createRaisedBevelBorder());
		dice3.setBorder(BorderFactory.createRaisedBevelBorder());
		dice4.setBorder(BorderFactory.createRaisedBevelBorder());
		dicePanel.add(dice1);
		dicePanel.add(dice2);
		dicePanel.add(dice3);
		dicePanel.add(dice4);
		dicePanel.setPreferredSize(new Dimension(120,120));
		dicePanel.setMaximumSize(new Dimension(120,120));
		dicePanel.setMinimumSize(new Dimension(120,120));
		
		//set up buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
		roll = new JButton("Roll");
		stop = new JButton("Stop");
		roll.addActionListener(this);
		stop.addActionListener(this);
		buttonPanel.add(roll);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,7)));
		buttonPanel.add(stop);
		
		//add to diceAndButton
		diceAndButton.add(Box.createRigidArea(new Dimension(100,100)));
		diceAndButton.add(dicePanel);
		diceAndButton.add(Box.createRigidArea(new Dimension(50,50)));
		diceAndButton.add(buttonPanel);
		buttonPanel.setAlignmentX(Container.CENTER_ALIGNMENT);
		
		//add to left panel
		leftPanel.add(diceAndButton);
		leftPanel.setAlignmentY(Container.CENTER_ALIGNMENT);
		
		//add to right panel
		JPanel EmptyPanel = new JPanel();
		EmptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
		GamePiecePanel = new JPanel();
		GamePiecePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		GamePiecePanel.setLayout(new BoxLayout(GamePiecePanel,BoxLayout.LINE_AXIS));
		if (playerNum == 1){
			for (int i =0; i <3; i++){
				GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
				GamePiecePanel.add(new GamePiece(GamePiece.USER));
			}
			GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
		}
		
		else if (playerNum == 2){
			for (int i =0; i <3 ; i++){
				GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
				GamePiecePanel.add(new GamePiece(GamePiece.OPPONENT));
			}
				GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
		}
		EmptyPanel.add(GamePiecePanel);
		rightPanel.add(EmptyPanel);
		
		

		//create scaled background image and place on pane
		Image scaled = backgroundIcon.getImage().getScaledInstance(691, 600,Image.SCALE_DEFAULT);
		backgroundIcon = new ImageIcon(scaled);
		JLabel background = new JLabel(backgroundIcon);
		background.setBounds(10, 10, 700, 600);
		boardPane = new JLayeredPane();
		boardPane.setPreferredSize(new Dimension(800, 600));
		boardPane.add(background,new Integer(1));
		
		//set up columns
		columns = new Column[12];
		for (int i=2;i<=12;i++){
			columns[i-2] = new Column(i);
			
		}
		
		//place columns on board
		for (int i=0;i<6;i++){
			Column temp = columns[i];
			temp.setBounds(101+i*(47), 550-35*(i*2+3), 35, 35*(i*2+3)+6);
			boardPane.add(temp, new Integer(3));
			
		}		
		for (int i=6;i<11;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(101+i*(47), 95+((i-5)*35*2), 35, 461-((i-5)*35*2));
			boardPane.add(temp, new Integer(3));
		}
		
		//set up column labels
		colLabels = new JLabel[11];
		for (int i=0; i<11;i++){
			JLabel colNum = new JLabel(""+(i+2));
			Font curFont = colNum.getFont();
		    colNum.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 16));
			colNum.setForeground(Color.white);
			colNum.setBounds(110+i*(47), 560, 25, 25);
			boardPane.add(colNum, new Integer(5));
			colLabels[i]= colNum;
		}
		
		centrePanel.add(boardPane);
		
		//some game logic initialization
		tempUserPieces = new GamePiece[3];
		tempOpPieces= new GamePiece[3];
		finalUserPieces = new ArrayList<GamePiece>();
		finalOpPieces = new ArrayList<GamePiece>();
		numPieces = 0;
		numOpPieces = 0;
		fadeInTimer = new Timer(50,this);
		fadeOutTimer = new Timer(50,this);
		turnTimer = new Timer(5,this);
		if (playerNum == 2){
			buttonPanel.setVisible(false);
			turnTimer.start();
			dialogue = new JLabel("You are Player 2: It is now Player 1's turn.");
		}else
			dialogue = new JLabel("You Are Player 1: It is your turn!");
		
		//Dialogue Box
		JPanel dialogueBox = new JPanel();
		dialogueBox.setLayout(new BoxLayout(dialogueBox,BoxLayout.PAGE_AXIS));
		dialogue.setAlignmentX(Container.CENTER_ALIGNMENT);
		dialogue.setAlignmentY(Container.CENTER_ALIGNMENT);
		Font dFont = dialogue.getFont();
		dialogue.setFont(new Font(dFont.getFontName(), dFont.getStyle(), 20));
		dialogueBox.add(dialogue);
		
		//set up content pane
		contentPane.add(leftPanel, BorderLayout.WEST);
		contentPane.add(centrePanel,BorderLayout.CENTER);
		contentPane.add(rightPanel,BorderLayout.EAST);
		contentPane.add(dialogueBox,BorderLayout.SOUTH);
		contentPane.revalidate();
		
		
	}
	
	 public void mouseEntered(MouseEvent e) {
		 Object source = e.getSource();
	     if ((source.getClass()== Column.class) && (moveNum == 0)){
	    	 Column col = (Column)source;
	    	 //backlight(col.getX(),col.getY(),col.getWidth(),col.getHeight());
	       	 int colNum = col.getColNum();
	       	 clearHighlight();
	       	 int[] available = availableColumns(d1,d2,d3,d4,colNum);
	       	 for (int a : available){
	       		 highlightPosition(a);
	       	 }
	       	 contentPane.revalidate();
	     }else if ((source.getClass()== Column.class) && (moveNum == 1)){
	    	 Column col = (Column)source;
	    	 //backlight(col.getX(),col.getY(),col.getWidth(),col.getHeight());
	    	 int colNum = col.getColNum();
	    	 clearHighlight();
	    	 highlightPosition(colNum);
	    	 /*int[] available = availableColumns(d1, d2, d3, d4, moveChoice, colNum);
	    	 for (int a : available) {
	    		highlightPosition(a);
	    	 }*/
	    	 contentPane.revalidate();
	     }
	 }
	 
	public void mouseExited(MouseEvent e) {
		java.awt.Point p = new java.awt.Point(e.getLocationOnScreen());
		SwingUtilities.convertPointFromScreen(p, e.getComponent());
		if (e.getComponent().contains(p)) {
			return;
		}
		Object source = e.getSource();
		if ((source.getClass() == Column.class) && (moveNum == 0)) {
			//clearBacklight();
			clearHighlight();
			int[] available = availableColumns(d1, d2, d3, d4);
			for (int a : available) {
				highlightPosition(a);
			}
			contentPane.revalidate();
		} else if ((source.getClass() == Column.class) && (moveNum == 1)) {
			//clearBacklight();
			clearHighlight();
			int colNum = ((Column) source).getColNum();
			highlightPosition(colNum);
			/*
			 * int[] available = availableColumns(d1, d2, d3, d4,moveChoice);
			 * for (int a : available) { highlightPosition(a); }
			 */
			contentPane.revalidate();
		}
	}
	
	public void clearBacklight(){
		alpha = 255;
		lightOn = false;
		fadeInTimer.stop();
		fadeOutTimer.start();
	}
	
	public void backlight(int xPos, int yPos, int xSize, int ySize){
		alpha = 0;
		lightOn = true;
		fadeOutTimer.stop();
		fadeInTimer.start();
		backlight = new TransparentPanel();
		backlight.setBackground(new Color(255,255,0,alpha));
		backlight.setBounds(xPos -10, yPos - 10, xSize + 20, ySize + 20);
		boardPane.add(backlight, new Integer(2));
		
	}
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == roll){
			move = "";
			moveNum = 0;
			connection.print("roll");
			buttonPanel.setVisible(false);
			String roll = connection.read();
			Scanner rollScan = new Scanner(roll).useDelimiter(",");
			d1 = rollScan.nextInt();
			d2 = rollScan.nextInt();
			d3 = rollScan.nextInt();
			d4 = rollScan.nextInt();
			dice1.setIcon(new ImageIcon(d1+".jpg"));
			dice2.setIcon(new ImageIcon(d2+".jpg"));
			dice3.setIcon(new ImageIcon(d3+".jpg"));
			dice4.setIcon(new ImageIcon(d4+".jpg"));
			int[] availableCol = availableColumns(d1,d2,d3,d4);//add combinations of roll values into list (ignore duplicates) (maybe using sets) 1,2,3,4
			for (int colNum : availableCol)
				highlightPosition(colNum);
			
			clearBacklight();
			
			boolean canMove = canMove(availableCol);
			if (!canMove){
				connection.print("crap");
				for (int i=0; i<numPieces;i++){
					boardPane.remove(tempUserPieces[i]);
					Column col = columns[tempUserPieces[i].getCol()-2];
					col.setTemp(Column.USER, 0);
					tempUserPieces[i] = null;
				}
				numPieces =0;
				//show crap out screen
				dialogue.setText("You cannot make any moves!");
				String response = connection.read();
				while (!response.equals("ack")){
					System.out.println("There was an error crapping out.");
					response = connection.read();
				}
				contentPane.revalidate();
				turnTimer.start();
			}
		}
		else if (source == stop){
			buttonPanel.setVisible(false);
			clearBacklight();
			connection.print("stop");
			//copy temp pieces into final collection
			//setPiecesToFinal(GamePiece.USER);
			for (int i =0; i<numPieces;i++){
				tempUserPieces[i].setFinal(GamePiece.USER);
				boolean changed = false;
				for (GamePiece fin: finalUserPieces){
					if (fin.getCol() == tempUserPieces[i].getCol()){
						fin.setYPixels(tempUserPieces[i].getYPos());
						boardPane.remove(fin);
						fin.setBounds(fin.getXPos(), fin.getYPos(), 15, 28);
						boardPane.add(fin,new Integer(8));
					 	changed = true;
					}
				}
				if (!changed)
					finalUserPieces.add(tempUserPieces[i]);
				boardPane.remove(tempUserPieces[i]);
				for (GamePiece fin: finalUserPieces){
					if (fin.getCol() == tempUserPieces[i].getCol()){
						changed = true;
					 	fin.setBounds(fin.getXPos(),fin.getYPos(), 15,28);
					 	boardPane.add(fin, new Integer(7));
					}
				}
				
				//track pieces on column
				Column col = columns[tempUserPieces[i].getCol()-2];
				col.setFinal(Column.USER, col.getTemp(Column.USER));
				col.setTemp(Column.USER, 0);
				if (col.getFinal(Column.USER) == col.getColHeight()){
					col.setConquered(true);
					JLabel flag = new JLabel(new ImageIcon("userFlag.jpg"));
					flag.setBounds(col.getX()+10, col.getY()-25,30,30);
					boardPane.add(flag, new Integer(7));
					
				}
				tempUserPieces[i]=null;
				boardPane.revalidate();
				
			}
			numPieces =0;
			String response = connection.read();
			if (response.equals("ack")){
				turnTimer.start();
				dialogue.setText("It is your opponent's turn!");
			}
		}
		else if (source.getClass() == PositionButton.class){
			PositionButton button = (PositionButton)source;
			int height = button.getColHeight();
			int col = button.getColumn();
			if (columns[col-2].hasTemp(Column.USER)){
				for (int i=0; i< numPieces; i++){
					//move piece up board if they have temp
					GamePiece piece = tempUserPieces[i];
					if (piece.getCol()==col){
						piece.setY(height);
						piece.setBounds(piece.getXPos(), piece.getYPos(), 15, 28);
						boardPane.add(piece);
						columns[col-2].setTemp(Column.USER,height);
					}
				}
			}else{
				//place piece on board if they do not have temp
				tempUserPieces[numPieces]= new GamePiece(GamePiece.USER, col,height);
				GamePiece piece = tempUserPieces[numPieces];
				piece.setBounds(piece.getXPos(), piece.getYPos(), 15, 28);
				boardPane.add(piece, new Integer(7));
				numPieces++;
				columns[col-2].setTemp(Column.USER, height);
			}
			if (moveNum == 0){
				if (source == doubleMove){
					clearHighlight();
					move = col+","+col;
					connection.print(move);
					String response = connection.read();
					if(!response.equals("ack"))
						System.out.println("error sending move choice");
					dialogue.setText("Woah! A double!");
					buttonPanel.setVisible(true);
					moveNum=0;
					doubleMove = null;
				}else{
					move = col+",";
					moveNum++;
					clearHighlight();
					moveChoice = col;
					int [] available = availableColumns(d1,d2,d3,d4,col);
					for (int i =0;i < available.length; i++){
						if (available[i] != col)
							highlightPosition(available[i]);
					}
					if (numPieces ==3){
						//checks whether player can make another move if they have 3 pieces on board.
						int count = 0;
						for (int i =0;i< 3 ;i++){
							for (int j = 0; j<available.length; j++){
								GamePiece tempPiece = tempUserPieces[i];
								if ((tempPiece.getCol() == available[j])&&(available[j]!=col)){
									if (tempPiece.getHeight() != columns[col-2].getColHeight())
										count++;
								}
							}
						}
						if (count ==0){
							clearHighlight();
							contentPane.revalidate();
							move = getTurnString(d1,d2,d3,d4,col);
							connection.print(move);
							String response = connection.read();
							if(!response.equals("ack"))
								System.out.println("error sending move choice\n"+move);
							buttonPanel.setVisible(true);
							moveNum=0;
						}
					}else if (numPieces < 3){
						boolean nextMove = true;
						for (int i =0; i <available.length;i++){
							if (available[i] != col){
								int colNum = available[i];
								Column column = columns[colNum-2];
								if (((column.getConquered())||(column.getTemp(Column.USER)==column.getColHeight())))
									nextMove = false;
							}
						}
						if (!nextMove){
							clearHighlight();
							contentPane.revalidate();
							move = getTurnString(d1,d2,d3,d4,col);
							connection.print(move);
							String response = connection.read();
							if(!response.equals("ack"))
								System.out.println("error sending move choice");
							buttonPanel.setVisible(true);
							moveNum=0;
						}
					}
					
					//remove highlight from already chosen position.
					columns[col-2].getPosButton(height).removeActionListener(this);
					columns[col-2].getPosButton(height).setBackground(Color.white);
					columns[col-2].getPosButton(height).setRolloverEnabled(false);
					columns[col-2].removeMouseListener(this);
					
					contentPane.revalidate();
				}
			}else if (moveNum == 1){
				clearHighlight();
				contentPane.revalidate();
				move +=""+col;
				connection.print(move);
				String response = connection.read();
				if(!response.equals("ack"))
					System.out.println("error sending move choice");
				buttonPanel.setVisible(true);
				moveNum=0;
					
			}
			//clear choices
			//show button panel
			contentPane.revalidate();
			
		}else if(source == turnTimer){
			boolean opponentTurn = true;
			dialogue.setText("It is your opponent's turn!");
			
			//set piece panel to opponent color.
			GamePiecePanel.removeAll();
			for (int i =0; i <3; i++){
				GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
				GamePiecePanel.add(new GamePiece(GamePiece.OPPONENT));
			}
			GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
			
			String response = connection.read();
			if (response.equals("go")){
				//set opponents temp to final
				setPiecesToFinal(GamePiece.OPPONENT);
				updateFinalCol(GamePiece.OPPONENT);
				//clearTempPieces(GamePiece.OPPONENT);
				numOpPieces =0;
				dialogue.setText("It is your turn!");
				opponentTurn = false;
				buttonPanel.setVisible(true);
				numPieces =0;
				//set piece panel to your color.
				GamePiecePanel.removeAll();
				for (int i =0; i <3; i++){
					GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
					GamePiecePanel.add(new GamePiece(GamePiece.USER));
				}
				GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
			}else if ((response.equals("you lost")||response.equals("you won"))){
				dialogue.setText(response);
				opponentTurn = false;
				boolean won = response.equals("you won");
				EndPanel end = new EndPanel(won, connection);
				end.setBounds(50, 50, 500, 300);
				boardPane.add(end,new Integer(9));
			}else{
				Scanner rollScan = new Scanner(response).useDelimiter(",");
				d1 = rollScan.nextInt();
				d2 = rollScan.nextInt();
				d3 = rollScan.nextInt();
				d4 = rollScan.nextInt();
				dice1.setIcon(new ImageIcon(d1+".jpg"));
				dice2.setIcon(new ImageIcon(d2+".jpg"));
				dice3.setIcon(new ImageIcon(d3+".jpg"));
				dice4.setIcon(new ImageIcon(d4+".jpg"));
				boardPane.revalidate();
				contentPane.revalidate();
				response = connection.read();
				if (response.equals("go")){//in case opponent craps out
					//clearTempPieces(GamePiece.OPPONENT);
					numOpPieces = 0;
					dialogue.setText("It is your turn!");
					opponentTurn = false;
					buttonPanel.setVisible(true);
					numPieces =0;
					//set piece panel to your color.
					GamePiecePanel.removeAll();
					for (int i =0; i <3; i++){
						GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
						GamePiecePanel.add(new GamePiece(GamePiece.USER));
					}
					GamePiecePanel.add(Box.createRigidArea(new Dimension(10,50)));
				}else{
					rollScan = new Scanner(response).useDelimiter(",");
					int choice1 = rollScan.nextInt();
					int choice2 = rollScan.nextInt();
					int []choices = {choice1, choice2};
					for (int c: choices){
						if (columns[c-2].hasTemp(Column.OPPONENT)){
							for (int i =0; i<numOpPieces; i++){
								if (tempOpPieces[i].getCol() == c){
									int height = columns[c-2].getTemp(Column.OPPONENT);
									tempOpPieces[i].setY(height+1);
									columns[c-2].setTemp(Column.OPPONENT, height+1);
								}
							}
						}else if (numOpPieces <3){
							if (columns[c-2].hasFinal(Column.OPPONENT)){
								int height = columns[c-2].getFinal(Column.OPPONENT);
								tempOpPieces[numOpPieces++]= new GamePiece(GamePiece.OPPONENT,c,height+1);
								columns[c-2].setTemp(Column.OPPONENT, height+1);
							}else {
								tempOpPieces[numOpPieces++]= new GamePiece(GamePiece.OPPONENT,c,1);
								columns[c-2].setTemp(Column.OPPONENT, 1);
							}
						}
					}
					drawTempPieces(GamePiece.OPPONENT);
					//update RH Panel
				}
			}
			if (!opponentTurn)
				turnTimer.stop();
		}else if (source == fadeInTimer){
			if (alpha<255){
				backlight.setBackground(new Color(255,255,0,alpha));
				alpha+=15;
			}else if (alpha >= 255){
				fadeInTimer.stop();
			}
		}else if (source == fadeOutTimer){
			if (alpha >0){
				for (Component c : boardPane.getComponentsInLayer(2))
					c.setBackground(new Color(255,255,0,alpha));
				//backlight.setBackground(new Color(255,255,0,alpha));
				alpha -= 15;
			}else if (alpha <=0){
				//backlight.setBackground(new Color(255,255,0,alpha));
				for (Component c : boardPane.getComponentsInLayer(2)){
					c.setBackground(new Color(255,255,0,alpha));
				}
				fadeOutTimer.stop();
			}
		}
	}
	
	public void updateFinalCol(int PLAYER){
		if (PLAYER==1){
			for (GamePiece p : finalUserPieces){
				Column col = columns[p.getCol()-2];
				col.setFinal(Column.USER, col.getTemp(Column.USER));
				col.setTemp(Column.USER, 0);
				if (col.getFinal(Column.USER) == col.getColHeight()){
					col.setConquered(true);
					JLabel flag = new JLabel(new ImageIcon("userFlag.jpg"));
					flag.setBounds(col.getX()+10, col.getY()-25,30,30);
					boardPane.add(flag, new Integer(7));
				}
			}
		}else if (PLAYER == 2){
			for (GamePiece p : finalOpPieces){
				Column col = columns[p.getCol()-2];
				col.setFinal(Column.OPPONENT, col.getTemp(Column.OPPONENT));
				col.setTemp(Column.OPPONENT, 0);
				if (col.getFinal(Column.OPPONENT) == col.getColHeight()){
					col.setConquered(true);
					JLabel flag = new JLabel(new ImageIcon("opponentFlag.jpg"));
					flag.setBounds(col.getX()+10, col.getY()-25,30,30);
					boardPane.add(flag, new Integer(7));
				}
			}
		}
	}
	
	public void setPiecesToFinal(int PLAYER){
		if (PLAYER == 1){
			for (int i =0; i<numPieces;i++){
				tempUserPieces[i].setFinal(GamePiece.USER);
				boolean changed = false;
				for (GamePiece fin: finalUserPieces){
					if (fin.getCol() == tempUserPieces[i].getCol()){
						fin.setYPixels(tempUserPieces[i].getYPos());
						boardPane.remove(fin);
						fin.setBounds(fin.getXPos(), fin.getYPos(), 15, 28);
						fin.setFinal(GamePiece.USER);
						boardPane.add(fin,new Integer(8));
					 	changed = true;
					}
				}
				if (!changed)
					finalUserPieces.add(tempUserPieces[i]);
				boardPane.remove(tempUserPieces[i]);
				for (GamePiece fin: finalUserPieces){
					if (fin.getCol() == tempUserPieces[i].getCol()){
						changed = true;
					 	fin.setBounds(fin.getXPos(),fin.getYPos(), 15,28);
					 	boardPane.add(fin, new Integer(7));
					}
				}
			}
		}
		else if (PLAYER == 2){
			for (int i =0; i< numOpPieces;i++){
				tempOpPieces[i].setFinal(GamePiece.OPPONENT);
				boolean changed = false;
				for (GamePiece fin: finalOpPieces){
					if (fin.getCol() == tempOpPieces[i].getCol()){
						fin.setYPixels(tempOpPieces[i].getYPos());
						boardPane.remove(fin);
						fin.setBounds(fin.getXPos()+15, fin.getYPos(), 15, 28);
						boardPane.add(fin,new Integer(8));
					 	changed = true;
					}
				}
				if (!changed)
					finalOpPieces.add(tempOpPieces[i]);
				//boardPane.remove(tempOpPieces[i]);
				for (GamePiece fin: finalOpPieces){
					if (fin.getCol() == tempOpPieces[i].getCol()){
						changed = true;
					 	fin.setBounds(fin.getXPos()+15,fin.getYPos(), 15,28);
					 	boardPane.add(fin, new Integer(8));
					}
				}
			}
			boardPane.revalidate();
		}
	}
	
	public void clearTempPieces(int PLAYER){
		if (PLAYER == 1){
			for (int i=0; i < numPieces; i++){
				boardPane.remove(tempUserPieces[i]);
				columns[tempUserPieces[i].getCol()-2].setTemp(Column.USER, 0);
				tempUserPieces[i]=null;
				
			}
		}
		else if (PLAYER == 2){
			for (int i =0; i < numOpPieces; i++){
				GamePiece p = tempOpPieces[i];
				boardPane.remove(p);
				columns[tempOpPieces[i].getCol()-2].setTemp(Column.OPPONENT, 0);
				//tempOpPieces[i] = null;
			}
		}
	}
	
	public void drawTempPieces(int PLAYER){
		if (PLAYER == 1){
			for (int i=0; i < numPieces; i++){
				GamePiece p = tempUserPieces[i];
				p.setBounds(p.getXPos(), p.getYPos(), 15, 28);
				boardPane.add(p, new Integer(7));
			}
		}
		else if (PLAYER == 2){
			for (int i =0; i < numOpPieces; i++){
				GamePiece p = tempOpPieces[i];
				p.setBounds(p.getXPos()+15, p.getYPos(), 15, 28);
				boardPane.add(p, new Integer(7));
			}
		}
	}
	
	public void clearHighlight(){
		for (int i=2;i<=12;i++){
			Column col = columns[i-2];
			try {
				col.removeMouseListener(this);
			}
			catch(NullPointerException e){	
			}
			col.removeHighlight();
			PositionButton[] pos = col.getPositionArr();
			for (int j = 0; j<pos.length; j++){
				try{
					pos[j].removeActionListener(this);
				}
				catch (NullPointerException e){
					
				}
			}
			
		}
	}
	
	public void highlightPosition(int colNum){
		Column col = columns[colNum-2];
		int doublePos = doubleMove();
		if (!col.getConquered()){
			try{
				if (numPieces < 3){
					col.addMouseListener(this);
					if (col.hasTemp(Column.USER)){
						if ((doublePos == colNum)&&(moveNum == 0)){
							col.highLight(col.getTemp(Column.USER)+2);
							col.getPosButton(col.getTemp(Column.USER)+2).addActionListener(this);
							doubleMove = col.getPosButton(col.getTemp(Column.USER)+2);
						}else{
							col.highLight(col.getTemp(Column.USER)+1);
							col.getPosButton(col.getTemp(Column.USER)+1).addActionListener(this);
						}
					}else if (col.hasFinal(Column.USER)){
						if ((doublePos == colNum)&&(moveNum == 0)){
							col.highLight(col.getFinal(Column.USER)+2);
							col.getPosButton(col.getFinal(Column.USER)+2).addActionListener(this);
							doubleMove = col.getPosButton(col.getFinal(Column.USER)+2);
						}else{
							col.highLight(col.getFinal(Column.USER)+1);
							col.getPosButton(col.getFinal(Column.USER)+1).addActionListener(this);
						}
					}else
						if ((doublePos == colNum)&&(moveNum == 0)){
							col.highLight(2);
							col.getPosButton(2).addActionListener(this);
							doubleMove = col.getPosButton(2);
						}else{
							col.highLight(1);
							col.getPosButton(1).addActionListener(this);
						}
				}else if (col.hasTemp(Column.USER)){
					if ((doublePos == colNum)&&(moveNum == 0)){
						col.addMouseListener(this);
						col.highLight(col.getTemp(Column.USER)+2);
						col.getPosButton(col.getTemp(Column.USER)+2).addActionListener(this);
						doubleMove = col.getPosButton(col.getTemp(Column.USER)+2);
					}else{
						col.addMouseListener(this);
						col.highLight(col.getTemp(Column.USER)+1);
						col.getPosButton(col.getTemp(Column.USER)+1).addActionListener(this);
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException e){
				//do not highlight when piece at top
			}
		}
	}
	
	public boolean canMove(int[] available){
		if (numPieces < 3){
			for (int i =0; i <available.length; i++){
				if (!columns[available[i]-2].getConquered()){
					return true;
				}
			}
		}else if (numPieces == 3){
			for (int i =0; i <available.length; i++){
				if (columns[available[i]-2].hasTemp(Column.USER)){
					if(columns[available[i]-2].getTemp(Column.USER) != columns[available[i]-2].getColHeight())
						return true;
				}
			}
		}
		return false;
	}
	
	/* Will return the available columns with the specified dice roll.
	 * Will take into account the remaining moves for a if a column is selected
	 * @param d1 Value of first die 
	 * @param d2 Value of second die
	 * @param d3 Value of third die
	 * @param d4 Value of fourth die
	 * @param selected The selected column
	 * @return An array of integers representing the remaining moves if the column is selected*/
	public int[] availableColumns(int d1, int d2, int d3, int d4, int selected){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		if ((split1 == selected) || (halfsplit1 == selected)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}
	
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		if ((split2 == selected) || (halfsplit2 == selected)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		if ((split3 == selected) || (halfsplit3 == selected)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}
		
		int[] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++]=col;
		}
		return output;
	}
	
	public int[] availableColumns(int d1, int d2, int d3, int d4){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int [] split = {d1 + d4, d2 + d3, d1 + d3, d2 + d4, d1 + d2, d3 + d4};
		for (int i : split)
			colSet.add(i);
		int [] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++] = col;
		}
		return output;
	}
	
	public int[] availableColumns(int d1, int d2, int d3, int d4, int selected, int next){
		HashSet<Integer> colSet = new HashSet<Integer>();
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		if ((split1 == selected) && (halfsplit1 == next)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}else if ((split1 == next) && (halfsplit1 == selected)){
			colSet.add(split1);
			colSet.add(halfsplit1);
		}
	
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		if ((split2 == selected) && (halfsplit2 == next)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}else if ((split2 == next) && (halfsplit2 == selected)){
			colSet.add(split2);
			colSet.add(halfsplit2);
		}
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		if ((split3 == selected) && (halfsplit3 == next)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}else if ((split3 == next) && (halfsplit3 == selected)){
			colSet.add(split3);
			colSet.add(halfsplit3);
		}
		int[] output = new int[colSet.size()];
		int i =0;
		for (int col : colSet){
			output[i++]=col;
		}
		return output;
	}
	
	public String getTurnString(int d1, int d2, int d3, int d4, int firstChoice){
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		
		if (split1 == firstChoice){
			return split1+","+halfsplit1;
		}else if (halfsplit1 == firstChoice)
			return halfsplit1+","+split1;
		if (split2 == firstChoice){
			return split2+","+halfsplit2;
		}else if (halfsplit2 == firstChoice)
			return halfsplit2+","+split2;
		if (split3 == firstChoice){
			return split3+","+halfsplit3;
		}else if (halfsplit3 == firstChoice)
			return halfsplit3+","+split3;
		return "";
	}
	public int doubleMove(){
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		
		if (split1 == halfsplit1)
			return split1;
		else if (split2 == halfsplit2)
			return split2;
		else if (split3 == halfsplit3)
			return split3;
		else 
			return 0;
	}
	public void highlightDouble(int colNum){
		Column col = columns[colNum-2];
		if (!col.getConquered()){
			if (numPieces < 3){
				if (col.hasTemp(Column.USER)){
					col.highLight(col.getTemp(Column.USER)+2);
					col.getPosButton(col.getTemp(Column.USER)+2).addActionListener(this);
					doubleMove = col.getPosButton(col.getTemp(Column.USER)+2);
				}else if (col.hasFinal(Column.USER)){
					col.highLight(col.getFinal(Column.USER)+2);
					col.getPosButton(col.getFinal(Column.USER)+2).addActionListener(this);
					doubleMove = col.getPosButton(col.getFinal(Column.USER)+2);
				}else
					col.highLight(2);
					col.getPosButton(2).addActionListener(this);
					doubleMove = col.getPosButton(2);
			}else if (col.hasTemp(Column.USER)){
				col.addMouseListener(this);
				col.highLight(col.getTemp(Column.USER)+2);
				col.getPosButton(col.getTemp(Column.USER)+2).addActionListener(this);
				doubleMove = col.getPosButton(col.getTemp(Column.USER)+2);
			}
		}
	}
	
}
