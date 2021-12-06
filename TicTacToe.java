import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.JPanel;

public class TicTacToe extends JPanel implements ActionListener{
	
	boolean running = true;
	static final int sWidth = 500; // Screen width
	static final int sHeight = 500; // Screen height
	static String name1, name2;
	static boolean end = false;
	static int[][] gameBoard = new int[3][3];
	static int turn = 0;
	int mX, mY; // Mouse click coordinates
	static boolean p1Win = false;
	static boolean p2Win = false;
	static boolean gameDraw = false;
	
	TicTacToe(){
		this.addMouseListener(new MyMouseAdapter());
		this.setPreferredSize(new Dimension(sWidth, sHeight));
		this.setBackground(Color.darkGray);
		this.setFocusable(true);
		setBoard();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw (Graphics g) {
		// Grid lines
		for (int i = 0; i < sWidth ; i += (int)(sWidth/3)) {
						
			// Color
			g.setColor(Color.gray);
					
			// Vertical grid lines
			g.drawLine(i, 0, i, sHeight);
			
			//Horizontal
			g.drawLine(0, i, sWidth, i);
		}
		g.drawString("x: " + mX + " y: " + mY, 20, 20);
		
		//Drawing the symbols on the board
		for (int x = 0 ; x < 3 ; x++) {
			
			for (int y = 0; y < 3 ; y++) {
				if (gameBoard[x][y] == 0) {
					g.setColor(Color.red);
					g.fillOval(x*sWidth/3, y*sHeight/3, sWidth/3,sHeight/3);
				}
				else if (gameBoard[x][y] == 1) {
					g.setColor(Color.blue);
					g.fillOval(x*sWidth/3, y*sHeight/3, sWidth/3,sHeight/3);
				}
			}
		}
		
		//Drawing end result
		if (end == true) {
			g.setColor(Color.darkGray);
			g.fillRect(0, 0, sWidth, sHeight);
			g.setFont(new Font("TimesRoman", Font.BOLD, 90));
			
			if (p1Win) {
				g.setColor(Color.red);
				g.drawString("RED", 100, sHeight/2);
				g.drawString("WINS!", 100, sHeight/2+90);
			}
			else if(p2Win) {
				g.setColor(Color.blue);
				g.drawString("BLUE", 10, sHeight/2);
				g.drawString("WINS!", 10, sHeight/2+90);
			}
			else {
				g.setColor(Color.lightGray);
				g.drawString("Draw :/", 10, sHeight/2);
			}
		}
	}
	
	// This method sets the board at the beginning
	public void setBoard()
	{
		for (int x = 0 ; x < 3 ; x++)
		{
			for (int y = 0 ; y < 3 ; y++)
			{
				gameBoard[x][y]=2;
			}
		}
	}
	
	// This method checks to see if someone has won after every turn
	public static void checkWin(int[][] board)
	{
		//Vertical check, checking to see if any columns are fully complete
		for (int x = 0 ; x < 3 ; x++)
		{
			// If the entire column has the same value
			if((board[x][0] == board[x][1]) && (board[x][1] == board[x][2]) && (board[x][0] != 2))
			{
				// Checks to see the value of the row, and assigns the win accordingly
				if (board[x][0] == 0)
				{
					p1Win = true;
				}
				else
				{
					p2Win = true;
				}
				end = true;
			}
		}
		
		//Horizontal check, checking to see if any rows are fully complete
		for (int x = 0 ; x < 3 ; x++)
		{
			// If the entire row has the same value
			if((board[0][x] == board[1][x]) && (board[1][x] == board[2][x]) && (board[0][x] != 2))
			{
				// Checks to see the value of the row, and assigns the win accordingly
				if (board[0][x] == 0) 
				{
					p1Win = true;
				}
				else
				{
					p2Win = true;
				}
				end = true;
			}
		}
		
		//Diagonal check, checking to see if the two diagonals are fully completed
		if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]) && (board[1][1] != 2))
		{
			if (board[0][0] == 0)
			{
				p1Win = true;
			}
			else
			{
				p2Win = true;
			}
			end = true;
		}
		else if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]) && (board[1][1] != 2))
		{
			if (board[0][2] == 0)
			{
				p1Win = true;
			}
			else
			{
				p2Win = true;
			}
			end = true;
		}
		if(end == false && turn >= 9)
		{
			gameDraw = true;
			end = true;
		}
	}
	
	// This method determines where and if to place a new value in the gameBoard array.
	// If it places a new value, it first determines who moved and places the corresponding value
	// in the correct spot in the array.
	public void move() {		
		// Game board tile placement algorithm
		// Iterates through the x coordinate values of the board
		for (int x = 0 ; x < 3 ; x++){
			
			// Iterates through the y coordinate values of the board
			for (int y = 0 ; y < 3 ; y++) {
				
				// Checking to see if the click was in a specific column, between one edge of the column the other
				if(mX < (x+1)*sWidth/3 && (mX > x*sWidth/3)) {
					
					// Checking to see if the click is in a specific row, between one edge of the row and the other
					// Both of these if statements combine to check if a click is within a certain box on the board
					if (mY < (y+1)*sHeight/3 && mY > y*sHeight/3) {
						
						// Assigns the value of the box accordingly, based on the turn at that point
						if(gameBoard[x][y] == 2) {
							if (turn % 2 == 0) {
								gameBoard[x][y] = 0;
							}
							else {
								gameBoard[x][y] = 1;
							}
							turn++; // Increment the turn number
						}
						//System.out.println(gameBoard[x][y]); // For debugging
					}
				}
			}
		}
	}
	
	public class MyMouseAdapter extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e) {
			mX = e.getX(); // get x coord
			mY = e.getY(); // get y coord
			if(running == true) { 
				move();
				checkWin(gameBoard);
				if (end == true) {
					running = false;
				}
			}
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
