import java.util.ArrayList;


public class MinmaxTree {
	ArrayList<MinmaxTree> child = new ArrayList<MinmaxTree>();
	MinmaxTree Parent = null;
	Board b;
	int depth;
	int alpha;
	int beta;
	int value;
	
	
	public MinmaxTree(){
		this.depth = 0;
		this.alpha = 0;
		this.beta = 0;
		this.value = 0;
	}
	
	public MinmaxTree(Board b){
		this.depth = 0;
		this.alpha = 0;
		this.beta = 0;
		this.value = 0;
		this.b = b;
	}
	
	public void genNodes(){
		
		
		
		
		
	}
}
