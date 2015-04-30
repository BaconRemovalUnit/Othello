import java.util.ArrayList;
import java.util.Iterator;


public class Tree {
	ArrayList<Tree> children = new ArrayList<Tree>();
	Tree Parent = null;
	Board b;
	int depth;
	int alpha;
	int beta;
	int value;
	int timeLimit;
	int depthLimit;
	int time;
	int player = 1;
	
	
	public Tree(){
		this.depth = 10;
		this.alpha = 0;
		this.beta = 0;
		this.value = 0;
	}
	
	public Tree(Board b,int depth){
		this.depth = depth - 1;//here we set the depth
		this.alpha = Integer.MIN_VALUE;
		this.beta = Integer.MAX_VALUE;
		this.value = 0;
		this.b = b;
		if(depth>0)
		genChild();
	}
	

	
	public void genChild(){
		ArrayList<Board> child = b.genChildren();
		for(int i = 0; i<child.size(); i++){
			children.add(new Tree(child.get(i),this.depth));
		}
	}

	public void printAll() {
		
		for(int i = 0; i< children.size(); i++){
			Tree temp = children.get(i);
			temp.b.print();
			temp.printAll();
		}
	}
}
