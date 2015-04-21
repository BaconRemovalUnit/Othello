
public class Main {
	public static void main(String[] args){
		Board a = new Board();
		a.print();
		a.place(4, 5);
		a.print();
		a.genMovable(-1);
		a.place(5,4);
		a.print();
	}
}
