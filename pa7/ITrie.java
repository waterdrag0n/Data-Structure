public interface ITrie {
	void insert(String s);

	boolean exists(String s);

	String[] dictionary();

	Node root();
}
