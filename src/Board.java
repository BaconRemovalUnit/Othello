import java.util.ArrayList;

/*
*1/true is black, -1/false is white
*int[][] indicates x,y coordinates
*/

public class Board {
	private final int WHITE = -1;//X
	private final int BLACK = 1;//O
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
		try{//needs to be modified
			return (board[x][y]==0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//generate all the posible moves for one player
	public ArrayList<Board> genMovable(int color){
		ArrayList<Board> moves = new ArrayList<Board>();
			
			for(int j = 0; j<board.length; j++){
				for(int i = 0; i<board[0].length;i++){
					if(board[i][j]==color){
						//all eight directions
						if(j>0){
						if(board[i][j-1]+color==0){
							//search up
							int[] location = digDown(i,(j-1),8);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}}
						
						if(j<7){
						if(board[i][j+1]+color==0){
							//search down
							int[] location = digDown(i,(j+1),2);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}}
						
						if(i>0)
						if(board[i-1][j]+color==0){
							//search left
							int[] location = digDown(i-1,j,4);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
						
						if(i<7)
						if(board[i+1][j]+color==0){
							//search right
							int[] location = digDown(i+1,j,6);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
						
						if(i>0&&j>0)
						if(board[i-1][j-1]+color==0){
							//search leftup
							int[] location = digDown(i-1,(j-1),7);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
						
						if(i<7&&j>0)
						if(board[i+1][j-1]+color==0){
							//search rightup
							int[] location = digDown((i+1),(j-1),8);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
						
						if(i>0&&j<7)
						if(board[i-1][j+1]+color==0){
							//search leftdown
							int[] location = digDown(i,(j-1),1);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
						
						if(i<7&&j<7)
						if(board[i+1][j+1]+color==0){
							//search rightdonw
							int[] location = digDown(i,(j-1),3);
							System.out.println("x:"+location[0]+"y:"+location[1]);
						}
					}
				}
		}
		return moves;
	}
	
	/**
	 * directions based on the num pad
	 * digDown starts with a node of one color, returns
	 * the location of a node of the different color
	 */
	private int[] digDown(int x, int y, int dir) {
		//getting current color
		int color = board[x][y];
		
		boolean tag = true;
		if(dir==8){//up
			while(y!=0||tag){
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		else if(dir==2){//down
			while(y!=7||tag){
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
			
		}
		else if(dir==6){//right
			while(x!=7||tag){
				x++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		else if(dir==4){//left
			while(x!=0||tag){
				x--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
			
		}
		else if(dir==9){//rightup
			while(x!=7||y!=0||tag){
				x++;
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		
		else if(dir==3){//rightdown
			while(x!=7||y!=7||tag){
				x++;
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}

		else if(dir==1){//leftdown
			while(x!=0||y!=7||tag){
				x--;
				y++;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}

		else if(dir==7){//leftup
			while(x!=0||y!=0||tag){
				x--;
				y--;
				if(board[x][y]==0)
					return new int[]{x,y};
				else if(board[x][y]!=color)
					tag = false;
			}
		}
		
		return new int[]{-1,-1};
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
