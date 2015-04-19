import java.util.ArrayList;

/*
*1/true is black, -1/false is white
*int[][] indicates x,y coordinates
*/

public class Board {
	private final int WHITE = -1;
	private final int BLACK = 1;
	private final int BLANK = 0;
	int[][] board = new int[8][8];
	int count;
	
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
		count = 0;
	}
	
	public void place(int x, int y, boolean side){
		if(canPlace(x,y))
		try{
			if(side)
			board[x][y] = 1;
			else
			board[x][y] = -1;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean canPlace(int x, int y) {
		try{
			return (board[x][y]==0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//generate all the posible moves for one player
	public ArrayList<Board> genChild(int color){
		ArrayList<Board> moves = new ArrayList<Board>();
		if(color == WHITE){
			for(int j = 0; j<board.length; j++){
				for(int i = 0; i<board[0].length;i++){
					if(board[i][j]==BLANK){
						//all eight directions
						try{
						if(board[i][j-1]==BLACK){
							//search up
							if(digDown(i,(j-1),8));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i][j+1]==BLACK){
							//search down
							if(digDown(i,(j+1),2));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i-1][j]==BLACK){
							//search left
							if(digDown((i-1),j,4));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i+1][j]==BLACK){
							//search right
							if(digDown((i+1),j,6));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i-1][j-1]==BLACK){
							//search leftup
							if(digDown((i-1),(j-1),7));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i+1][j-1]==BLACK){
							//search rightup
							if(digDown((i+1),(j-1),2));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i-1][j+1]==BLACK){
							//search leftdown
							if(digDown((i-1),(j+1),2));
						}}
						catch(NullPointerException e){};
						
						try{
						if(board[i+1][j+1]==BLACK){
							//search rightdonw
							if(digDown((i+1),(j+1),2));
						}}
						catch(NullPointerException e){};	
						
					}
				}
			}
		}
		//for black
		else{
			
		}

		return moves;
	}
	
	/**
	 * directions based on the num pad
	 */
	private boolean digDown(int i, int j, int k) {
		// TODO Auto-generated method stub
		return false;
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
	
	public void updateScore(){
		count = 0;
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
			count += count + board[i][j];
			}
		}
		this.count  = count;
	}
	
	

	public void print(){
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] == BLACK)
					System.out.print("O\t");
				else if(board[i][j] == WHITE)
				System.out.print("X\t");
				else
				System.out.print("-\t");
			}
			System.out.println();
		}
	}
}
