
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

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
public class Board {
	private final int WHITE = -1;//O white
	private final int BLACK = 1;//X black
	private final int BLANK = 0;
	private int currentPlayer = 1;//black first
	private boolean gg = false;
	
	int[][] board = new int[8][8];
	int score;
	int whitescore = 2;
	int blackscore = 2;
	int alpha = Integer.MIN_VALUE;
	int beta = Integer.MAX_VALUE;
	int depth = 8;//default depth
	ArrayList<Board> kids = new ArrayList<Board>();
	Board parent = null;
	
	public Board(){
		for(int i = 0; i<board.length; i++){
			for(int j = 0; j<board[0].length;j++){
				board[i][j] = 0;
			}
		}
		board[3][3]=-1;
		board[4][4]=-1;
		board[3][4]=1;
		board[4][3]=1;
		score = 0;
		genChildren();
	}
	
	public Board(int depth){
		this.depth = depth-1;
	}
	
	public boolean isOver(){
		return gg;
	}
	
	public void printTree() {
		for(int i = 0; i< kids.size(); i++){
			Board temp = kids.get(i);
			temp.print();
			temp.printTree();
		}
	}
	
	public void place(int x, int y){
		if(!gg)
		{
			if(!isFull()){
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
			else
				gg = true;
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
			if(list.size()==0){
				if(whitescore==0||blackscore==0)
					gg= true;
				this.currentPlayer = this.currentPlayer*-1;
			}
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
	
	public boolean isTerminal(){
		return  genMovable(currentPlayer).size()==0;
	}
	
	//generate all the posible moves for one player
	public ArrayList<Point> genMovable(int color){
		ArrayList<Point> moves = new ArrayList<Point>();
			boolean[][] loc = new boolean[8][8];
			for(int j = 0; j<board.length; j++){
				for(int i = 0; i<board[0].length;i++){
					if(board[i][j]==color){
						//all eight directions
						if(j>0)
						if(board[i][j-1]+color==0){
							//search up
							int[] location = digDown(i,(j-1),8);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(j<7)
						if(board[i][j+1]+color==0){
							//search down
							int[] location = digDown(i,(j+1),2);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i>0)
						if(board[i-1][j]+color==0){
							//search left
							int[] location = digDown((i-1),j,4);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i<7)
						if(board[i+1][j]+color==0){
							//search right
							int[] location = digDown((i+1),j,6);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i>0&&j>0)
						if(board[i-1][j-1]+color==0){
							//search leftup
							int[] location = digDown(i-1,(j-1),7);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i<7&&j>0)
						if(board[i+1][j-1]+color==0){
							//search rightup
							int[] location = digDown((i+1),(j-1),9);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i>0&&j<7)
						if(board[i-1][j+1]+color==0){
							//search leftdown
							int[] location = digDown((i-1),(j+1),1);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
						if(i<7&&j<7)
						if(board[i+1][j+1]+color==0){
							//search rightdonw
							int[] location = digDown((i+1),(j+1),3);
							if(inGrid(location))
								loc[location[0]][location[1]]=true;}
					}
				}
		}
			
		for(int i = 0; i< 8; i++){
			for(int j = 0; j<8; j++){
				if(loc[i][j])
					moves.add(new Point(i,j));
			}
		}
		return moves;
	}


	public void genChildren(){
		if(depth>0){
			int color = currentPlayer;
			ArrayList<Board> boards = new ArrayList<Board>();
			ArrayList<Point> points =  genMovable(color);
			for(int i = 0; i<points.size(); i++){
				Board temp = copyBoard();
				Point cord = points.get(i);
				temp.place(cord.x, cord.y);
				temp.genChildren();
				boards.add(temp);
			}
			kids = boards;
		}
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
	public Board copyBoard(){
		Board b = new Board(this.depth);
		b.currentPlayer = this.currentPlayer;
		b.blackscore = this.blackscore;
		b.whitescore = this.whitescore;
		b.score = this.score;
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8;j++){
				b.board[i][j] = this.board[i][j];
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
		score = 0;
		blackscore=0;
		whitescore=0;
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
			score += score + board[i][j];
			if(board[i][j]==BLACK)
				blackscore++;
			if(board[i][j]==WHITE)
				whitescore++;
			}
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

}