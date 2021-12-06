// Ethan Bitnun
// TicTacToe main class

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) throws Exception 
	{
		JFrame frame = new JFrame();
		TicTacToe ttt = new TicTacToe();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(ttt);
		frame.setTitle("TicTacToe");
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}