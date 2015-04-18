
public class Board {
	int[][] board = new int[8][8];
	
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
		place(5,5,true);
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
	
	public boolean boardFilled(){
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] ==0)
					return false;
			}
		}
		return true;
	}

	public void print(){
		for(int j = 0; j<board.length; j++){
			for(int i = 0; i<board[0].length;i++){
				if(board[i][j] == 1)
					System.out.print("O\t");
				else if(board[i][j] == -1)
				System.out.print("X\t");
				else
				System.out.print("-\t");
			}
			System.out.println();
		}
	}
	
}
