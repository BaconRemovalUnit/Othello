import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
*1/true is black, -1/false is white
*int[][] indicates x,y coordinates
*/


/*
 * TODO:
 * 1.isFull
 * 2.genChild
 * 3.
 */
public class Board extends JPanel{
	private final int WHITE = -1;//O white
	private final int BLACK = 1;//X black
	private final int BLANK = 0;
	private int currentPlayer = 1;//black first
	
	int[][] board = new int[8][8];
	int count;
	int whitescore = 2;
	int blackscore = 2;
	
	public Board(){
		LocationListener listen = new LocationListener();
		addMouseListener(listen);
		addMouseMotionListener(listen);
		
		for(int i = 0; i<board.length; i++){
			for(int j = 0; j<board[0].length;j++){
				board[i][j] = 0;
			}
		}
		board[3][3]=-1;
		board[4][4]=-1;
		board[3][4]=1;
		board[4][3]=1;
		count = 0;
	}
	
	public void place(int x, int y){
		if(!isFull())
		{
		if(canPlace(x,y,currentPlayer)){
		try{
			flip(x,y,currentPlayer);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		updateScore();
		currentPlayer = currentPlayer * -1;//flip side
		}
		else{
			System.err.println("NOT A VALID MOVE");
		}
		}
		else{//game finished
			if(blackscore==whitescore)
			JOptionPane.showMessageDialog(null, "OMG It's a Tie!", "Wow",JOptionPane.PLAIN_MESSAGE); 
			else{
			String winner = (blackscore>whitescore)?"BLACK":"WHITE";
			JOptionPane.showMessageDialog(null, winner+" won!", "Congratulations!",JOptionPane.PLAIN_MESSAGE); 
			}
		}
			
	}

	public boolean isFull() {
		for(int i=0; i <8 ; i++){
			for(int j=0; j <8 ; j++){
				if(board[i][j]==0)
					return false;
			}
		}
		return true;
	}

	public boolean canPlace(int x, int y, int currentPlayer) {
		try{
			if(board[x][y]!=0)
				return false;
			ArrayList<Point> list = genMovable(currentPlayer);
			if(list.size()==0)
				this.currentPlayer = this.currentPlayer*-1;
			Iterator<Point> iter = list.iterator();
			while(iter.hasNext()){
				Point current = iter.next();
				if(current.x==x&&current.y==y)
					return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//generate all the posible moves for one player
	public ArrayList<Point> genMovable(int color){
		ArrayList<Point> moves = new ArrayList<Point>();
			
			for(int j = 0; j<board.length; j++){
				for(int i = 0; i<board[0].length;i++){
					if(board[i][j]==color){
						//all eight directions
						if(j>0)
						if(board[i][j-1]+color==0){
							//search up
							int[] location = digDown(i,(j-1),8);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(j<7)
						if(board[i][j+1]+color==0){
							//search down
							int[] location = digDown(i,(j+1),2);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i>0)
						if(board[i-1][j]+color==0){
							//search left
							int[] location = digDown((i-1),j,4);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i<7)
						if(board[i+1][j]+color==0){
							//search right
							int[] location = digDown((i+1),j,6);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i>0&&j>0)
						if(board[i-1][j-1]+color==0){
							//search leftup
							int[] location = digDown(i-1,(j-1),7);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i<7&&j>0)
						if(board[i+1][j-1]+color==0){
							//search rightup
							int[] location = digDown((i+1),(j-1),9);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i>0&&j<7)
						if(board[i-1][j+1]+color==0){
							//search leftdown
							int[] location = digDown((i-1),(j+1),1);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
						if(i<7&&j<7)
						if(board[i+1][j+1]+color==0){
							//search rightdonw
							int[] location = digDown((i+1),(j+1),3);
							if(inGrid(location))
							moves.add(new Point(location[0],location[1]));}
					}
				}
		}
		System.out.println(moves.size());
		return moves;
	}
	
	private boolean inGrid(int[] arr) {		
		return arr[0]>-1&&arr[0]<8&&arr[1]>-1&&arr[1]<8;
	}

	/**
	 * Finding a valid place
	 * directions based on the num pad
	 * digDown starts with a node of one color, returns
	 * the location of a node of the different color
	 */
	private int[] digDown(int x, int y, int dir) {
		//getting current color
		int color = board[x][y];

		boolean tag = true;
		
		if(dir==8){//up
			while(y>0&&tag){
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		else if(dir==2){//down
			while(y<7&&tag){
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
			
		}
		else if(dir==6){//right
			while(x<7&&tag){
				x++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		else if(dir==4){//left
			while(x>0&&tag){
				x--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
			
		}
		else if(dir==9){//rightup
			while(x<7&&y>0&&tag){
				x++;
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		
		else if(dir==3){//rightdown
			while(x<7&&y<7&&tag){
				x++;
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}

		else if(dir==1){//leftdown
			while(x>0&&y<7&&tag){
				x--;
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}

		else if(dir==7){//leftup
			while(x>0&&y>0&&tag){
				x--;
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		
		
		return new int[]{9,9};
	}
	
	/*
	 * actually placing the piece
	 */
	private void flip(int x, int y, int color) {
		
		board[x][y]=color;
		
		//up
		boolean tag = false;
		int cy = y;
		int cx = x;
		
		while(cy>0){
			cy--;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color)
			{
				
				tag = true;
				break;
			}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cy++;
			if(board[cx][cy] == color)
				break;
			board[cx][cy] = color;
		}
		
		//down
		tag = false;
		cy = y;
		cx = x;
		
		while(cy<7){
			cy++;
			
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
			
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cy--;
			board[cx][cy] = color;
		}
		
		
		//left
		tag = false;
		cy = y;
		cx = x;
		while(cx>0){
			cx--;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx++;
			board[cx][cy] = color;
		}
		
		//right
		tag = false;
		cy = y;
		cx = x;
		while(cx<7){
			cx++;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx--;
			board[cx][cy] = color;
		}
		
		//leftup
		tag = false;
		cy = y;
		cx = x;
		while(cy>0&&cx>0){
			cx--;
			cy--;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx++;
			cy++;
			board[cx][cy] = color;
		}
		
		tag = false;
		cy = y;
		cx = x;
		while(cy>0&&cx<7){//rightup
			cx++;
			cy--;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx--;
			cy++;
			board[cx][cy] = color;
		}
		
		tag = false;
		cy = y;
		cx = x;
		while(cy<7&&cx>0){//leftdown
			cx--;
			cy++;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx++;
			cy--;
			board[cx][cy] = color;
		}
		
		tag = false;
		cy = y;
		cx = x;
		while(cy<7&&cx<7){//rightdown
			cx++;
			cy++;
			if(board[cx][cy]==0)
				break;
			else if(board[cx][cy]==color){
				tag = true;
				break;}
		}
		while((cx!=x||cy!=y)&&tag==true){//trace back and replace
			cx--;
			cy--;
			board[cx][cy] = color;
		}
	}

	//create a new instace of the board
	public Board copyBoard(Board a){
		Board b = new Board();
		for(int i = 0; i<a.board.length; i++){
			for(int j = 0; j<a.board[0].length;j++){
				b.board[i][j] = 0;
			}
		}
		return b;
	}
	
	public boolean boardFilled(){
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] ==0)
					return false;
			}
		}
		return true;
	}
	
	public void updateScore(){//calculate the total score of the board after each round
		count = 0;
		blackscore=0;
		whitescore=0;
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
			count += count + board[i][j];
			if(board[i][j]==BLACK)
				blackscore++;
			if(board[i][j]==WHITE)
				whitescore++;
			}
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(new Color(210,105,30));
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED,1,20));
		
		String player = (currentPlayer==1?"BLACK":"WHITE");
		g.drawString("Current Player: "+player, 150, 500);
		g.setFont(new Font(Font.MONOSPACED,1,15));
		g.drawString("Black Score",470,200);
		g.drawString(""+blackscore,470,250);
		g.drawString("White Score",470,300);
		g.drawString(""+whitescore,470,350);

		for(int i = 0;i<8;i++)
			for(int j = 0;j<8;j++)
			{
				if((i+j)%2==0){
					g.setColor(new Color(0,100,0));
					g.fillRect(i*50+50, j*50+50, 50, 50);
				}
				else{
					g.setColor(new Color(0,70,0));
					g.fillRect(i*50+50, j*50+50, 50, 50);
					}
			}
		
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] == BLACK){
					g.setColor(Color.BLACK);
					g.fillOval(i*50+50, j*50+50, 50, 50);
				}
				else if(board[i][j] == WHITE){
					g.setColor(Color.WHITE);
					g.fillOval(i*50+50, j*50+50, 50, 50);
				}
			}
		}
		
		ArrayList<Point> list = genMovable(currentPlayer);
		Iterator<Point> iter = list.iterator();
		while(iter.hasNext()){
			Point temp = iter.next();
			g.setColor(Color.GRAY);
			g.drawOval(temp.x*50+50, temp.y*50+50, 50, 50);
				
		}
	}

	public void print(){//can be extended to paint
		System.out.println("---------------------------------------------------------");
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] == BLACK)
					System.out.print("X\t");
				else if(board[i][j] == WHITE)
				System.out.print("O\t");
				else
				System.out.print("-\t");
			}
			System.out.println();
		}
	}
	
	private class LocationListener implements MouseListener,MouseMotionListener {

		public void mouseDragged(MouseEvent arg0) {}
		public void mouseMoved(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int a = (x-50)/50;
			int b = ((y-50)/50);
			if(a>-1&&a<8&&b>-1&&b<8)
			place(a,b);
			repaint();
			
		}

		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
}


