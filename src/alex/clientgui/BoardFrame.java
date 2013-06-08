package alex.clientgui;

import java.awt.event.*;
import java.awt.*;
//import java.awt.Image;
import javax.swing.*;
import java.util.HashSet;
import java.util.Scanner;

public class BoardFrame extends JFrame implements ActionListener, MouseMotionListener {
	private JLayeredPane boardPane;
	private Column[] columns;
	private ImageIcon backgroundIcon = new ImageIcon("BoardBackground.png");
	private Container contentPane;
	private ClientConnection connection;
	private JPanel buttonPanel;
	private JButton roll;
	private JButton stop;
	
	public BoardFrame(Container contentPaneIn, ClientConnection connectionIn, int playerNum){
		connection = connectionIn;
		
		contentPaneIn.removeAll();
		contentPane = contentPaneIn;
		contentPane.setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel();
		
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new GridLayout(4,4));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
		roll = new JButton("Roll");
		stop = new JButton("Stop");
		roll.addActionListener(this);
		stop.addActionListener(this);
		//stop.disable();
		buttonPanel.add(Box.createRigidArea(new Dimension(15,7)));
		buttonPanel.add(roll);
		buttonPanel.add(Box.createRigidArea(new Dimension(5,7)));
		buttonPanel.add(stop);
		
		leftPanel.add(buttonPanel);
		
		if (playerNum == 2){
			buttonPanel.setVisible(false);//turn off buttons when it's opponent's turn.
		}
		
		
		columns = new Column[12];
		for (int i=2;i<=12;i++){
			columns[i-2] = new Column(i);
		}
		
		Image scaled = backgroundIcon.getImage().getScaledInstance(691, 600,Image.SCALE_DEFAULT);
		backgroundIcon = new ImageIcon(scaled);
		JLabel background = new JLabel(backgroundIcon);
		
		background.setBounds(10, 10, 700, 600);
		boardPane = new JLayeredPane();
		boardPane.setPreferredSize(new Dimension(800, 600));
		
		boardPane.add(background,new Integer(1));
		
		/*/Example column
		col = new JPanel();
		col.setBounds(150, 430, 45, 125);
		col.setBackground(Color.white);
		col.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		col.addMouseMotionListener(this);*/
		
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
		
		contentPane.add(buttonPanel, BorderLayout.WEST);
		contentPane.add(boardPane,BorderLayout.CENTER);
		contentPane.revalidate();
		
	}
	
	 public void mouseMoved(MouseEvent e) {
		 Object source = e.getSource();
	     if (source.getClass()== Column.class){
	    	 //get column selected
	    	 //remove highlight from columns that do not contain the selected column
	    	 Column col = (Column)source;
	       	 int ySize = 120+(col.getColNum()-2)*50;
	       	 int xSize = 35;
	       	 backLight(col.getX(),col.getY(),xSize,ySize);
	     }
	 }
	
	public void backLight(int xPos, int yPos, int xSize, int ySize){
		JPanel light = new JPanel();
		light.setBounds(xPos -10, yPos - 10, xSize + 20, ySize + 20);
		light.setBackground(Color.yellow);
		boardPane.add(light, new Integer(2));
		
	}
	 
	public void mouseDragged(MouseEvent e) {}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == roll){
			connection.print("roll");
			buttonPanel.setVisible(false);
			String roll = connection.read();
			Scanner rollScan = new Scanner(roll).useDelimiter(",");
			boolean i = rollScan.hasNextInt();
			int d1 = rollScan.nextInt();//throwing input mismatch?
			int d2 = rollScan.nextInt();
			int d3 = rollScan.nextInt();
			int d4 = rollScan.nextInt();
			//set roll values to 4 ints
			//create list of integers
			int[] availableCol = availableColumns(d1,d2,d3,d4);//add combinations of roll values into list (ignore duplicates) (maybe using sets) 1,2,3,4
			for (int colNum : availableCol)
				highlightPosition(colNum);
			//highlight doubles if exist.
		}
		else if (source == stop){
			
		}
		else if ((source.getClass() == JButton.class) && ((source != stop)||(source != roll))){
			JButton but = (JButton)source;
			System.out.println("button sent event.");
		}
	}
	
	public void highlightPosition(int colNum){
		Column col = columns[colNum-2];
		if (!col.getConquered()){
			col.addMouseMotionListener(this);
			if (col.hasTemp(1/*USER*/)){
				//highlight position above temp
				col.highLight(col.getTemp(col.USER));
				col.getPosButton(col.getTemp(col.USER)-1).addActionListener(this);
			}else if (col.hasFinal(1/*USER*/)){
				col.highLight(col.getFinal(col.USER));
				col.getPosButton(col.getFinal(col.USER)-1).addActionListener(this);
			}else
				col.highLight(1);
				col.getPosButton(1).addActionListener(this);
		}
	}
	
	/* Will return the available columns with the specified dice roll.
	 * Will take into account the remaining moves for a if a column is selected
	 * @param d1 Value of first die 
	 * @param d2 Value of second die
	 * @param d3 Value of third die
	 * @param d4 Value of fourth die
	 * @param selected The selected column
	 * @return An array of integers representing the remaining moves if the column is selected
	 */
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
	
}
