
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
	}
	
	public void print(){
		for(int i = 0; i<board.length; i++){
			for(int j = 0; j<board[0].length;j++){
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
