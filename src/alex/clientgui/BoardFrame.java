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
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	private ClientConnection connection;
	private JPanel buttonPanel;
	private JButton roll;
	private JButton stop;
	private JLabel dice1, dice2, dice3, dice4;
	//game logic
	private int numPieces;
	private String move;
	private int moveNum;
	private int d1,d2,d3,d4;
	private int moveChoice;
	private Timer turnTimer;
	private GamePiece [] tempUserPieces= new GamePiece[3];
	private ArrayList<GamePiece> finalUserPieces = new ArrayList<GamePiece>();
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
		JPanel GamePiecePanel = new JPanel();
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
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(101+i*(47), 430-(i*50), 35, 120+(i*50));
			boardPane.add(temp, new Integer(3));
			
		}
		for (int i=6;i<11;i++){
			Column temp = columns[i];
			temp.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			temp.setBounds(101+i*(47), 180+((i-5)*50), 35, 370-((i-5)*50));
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
		
		//set up content pane
		contentPane.add(leftPanel, BorderLayout.WEST);
		contentPane.add(centrePanel,BorderLayout.CENTER);
		contentPane.add(rightPanel,BorderLayout.EAST);
		contentPane.revalidate();
		
		//some game logic initialization
		numPieces = 0;
		turnTimer = new Timer(5,this);
		if (playerNum == 2){
			buttonPanel.setVisible(false);
			turnTimer.start();
		}
		
		
		
	}
	
	 public void mouseEntered(MouseEvent e) {
		 Object source = e.getSource();
	     if ((source.getClass()== Column.class) && (moveNum == 0)){
	    	 Column col = (Column)source;
	    	 backLight(col.getX(),col.getY(),col.getWidth(),col.getHeight());
	       	 int colNum = col.getColNum();
	       	 clearHighlight();
	       	 int[] available = availableColumns(d1,d2,d3,d4,colNum);
	       	 for (int a : available){
	       		 highlightPosition(a);
	       	 }
	       	 contentPane.revalidate();
	     }else if ((source.getClass()== Column.class) && (moveNum == 1)){
	    	 Column col = (Column)source;
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
		Object source = e.getSource();
	     if ((source.getClass() == Column.class) && (moveNum == 0)) {
			clearHighlight();
			int[] available = availableColumns(d1, d2, d3, d4);
			for (int a : available) {
				highlightPosition(a);
			}
			contentPane.revalidate();
		}else if ((source.getClass()== Column.class) && (moveNum == 1)){
			clearHighlight();
			int colNum = ((Column)source).getColNum();
	    	highlightPosition(colNum);
			/*int[] available = availableColumns(d1, d2, d3, d4,moveChoice);
			for (int a : available) {
				highlightPosition(a);
			}*/
			contentPane.revalidate();
	    }
	}
	 
	public void backLight(int xPos, int yPos, int xSize, int ySize){
		JPanel light = new JPanel();
		light.setBounds(xPos -10, yPos - 10, xSize + 20, ySize + 20);
		light.setBackground(Color.yellow);
		boardPane.add(light, new Integer(2));
		
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
			
			/*int doublePos = doubleMove();
			if (doublePos > 0)
				highlightDouble(doublePos);
				//create local doublebutton var.*/
			
			boolean canMove = canMove(availableCol);
			if (!canMove){
				connection.print("crap");
				//show crap out screen
				String response = connection.read();
				while (!response.equals("ack")){
					System.out.println("There was an error crapping out.");
					response = connection.read();
				}
				turnTimer.start();
			}
				
			//create list of all possible
			//if above list.length > the list length ignoring doubles
				//find duplicate columns
				//highlight doubles if exist.
		}
		else if (source == stop){
			buttonPanel.setVisible(false);
			connection.print("stop");
			//copy temppieces into final collection
			//empty temp piece arr.
			String response = connection.read();
			if (response.equals("ack"))
				turnTimer.start();
		}
		else if (source.getClass() == PositionButton.class){
			PositionButton button = (PositionButton)source;
			int height = button.getColHeight();
			int col = button.getColumn();
			if (columns[col-2].hasTemp(Column.USER)){
				for (int i=0; i< numPieces; i++){
					GamePiece piece = tempUserPieces[i];
					if (piece.getCol()==col){
						piece.setY(height);
						piece.setBounds(piece.getXPos(), piece.getYPos(), 25, 25);
						boardPane.add(piece);
						columns[col-2].setTemp(Column.USER,height);
					}
				}
			}else{
				GamePiece piece = new GamePiece(GamePiece.USER, col,height);
				piece.setBounds(piece.getXPos(), piece.getYPos(), 25, 25);
				boardPane.add(piece, new Integer(7));
				tempUserPieces[numPieces++] = piece;
				columns[col-2].setTemp(Column.USER, height);
			}
			Point pt = button.getLocation();
			
			int x = pt.x;
			int y = pt.y;
			
			//JLabel piece = new JLabel("1");
			//piece.setBackground(new Color(0,0,0,0));
			//piece.setBounds(y,boardPane.getHeight()- x, 50,50);
			//boardPane.add(piece, new Integer(5));
			if (moveNum == 0){
				if (source == doubleMove){
					clearHighlight();
					move = col+","+col;
					connection.print(move);
					String response = connection.read();
					if(!response.equals("ack"))
						System.out.println("error sending move choice");
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
						int count = 0;
						for (int i =0;i<tempUserPieces.length ;i++){
							for (int j = 0; j<available.length; j++){
								if ((tempUserPieces[i].getCol() == available[j])&&(available[j]!=col))
									count++;
							}
						}
						if (count ==0){
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
			//set piece panel to opponent color.
			String response = connection.read();
			if (response.equals("go")){
				opponentTurn = false;
				buttonPanel.setVisible(true);
				//numPieces =0;
				//set piece panel to your color.
			}else if (response.equals("you lost")){
				opponentTurn = false;
				//show lost screen
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
				contentPane.revalidate();
				response = connection.read();
				if (response.equals("go")){
					opponentTurn = false;
					buttonPanel.setVisible(true);
					//numPieces =0;
					//set piece panel to your color.
				}else{
					rollScan = new Scanner(response).useDelimiter(",");
					int choice1 = rollScan.nextInt();
					int choice2 = rollScan.nextInt();
				}
				//show move on board
				//if (columns[choice1-2].hasTemp(Column.OPPONENT))
					//advance piece
				//else if (columns[choice1-2].hasFinal(Column.OPPONENT))
					//place piece
				//else if (opponentpieces < 3)
					//place piece at bottom
			}
			if (!opponentTurn)
				turnTimer.stop();
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
			if (numPieces < 3){
				col.addMouseListener(this);
				if (col.hasTemp(Column.USER)){
					col.highLight(col.getTemp(Column.USER)+1);
					col.getPosButton(col.getTemp(Column.USER)+1).addActionListener(this);
					if ((doublePos == colNum)&&(moveNum == 0)){
						col.highLight(col.getTemp(Column.USER)+2);
						col.getPosButton(col.getTemp(Column.USER)+2).addActionListener(this);
						doubleMove = col.getPosButton(col.getTemp(Column.USER)+2);
					}
				}else if (col.hasFinal(Column.USER)){
					col.highLight(col.getFinal(Column.USER)+1);
					col.getPosButton(col.getFinal(Column.USER)+1).addActionListener(this);
					if ((doublePos == colNum)&&(moveNum == 0)){
						col.highLight(col.getFinal(Column.USER)+2);
						col.getPosButton(col.getFinal(Column.USER)+2).addActionListener(this);
						doubleMove = col.getPosButton(col.getFinal(Column.USER)+2);
					}
				}else
					col.highLight(1);
					col.getPosButton(1).addActionListener(this);
					if ((doublePos == colNum)&&(moveNum == 0)){
						col.highLight(2);
						col.getPosButton(2).addActionListener(this);
						doubleMove = col.getPosButton(2);
					}
			}else if (col.hasTemp(Column.USER)){
				col.addMouseListener(this);
				col.highLight(col.getTemp(Column.USER)+1);
				col.getPosButton(col.getTemp(Column.USER)+1).addActionListener(this);
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
