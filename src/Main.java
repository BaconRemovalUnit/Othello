import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;


public class Main {
	public static void main(String[] args){
		Board a = new Board();
		
		

	        JFrame frame = new JFrame("Othello");
	        frame.setSize(600, 600);
	        frame.add(a);
	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
	

		
	}

