/*
* Name: Su Yong Chang
* Student ID #: 2016125016
*/

/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Trie implements ITrie {

	Node<String> root;
	int sz;
	String str[];
	int index;

	public Trie() {
		/*
		* Constructor.
		*
		* Note that we will check the number of compare calls;
		* if the count is too low or too high (depending on cases),
		* you will fail the case.
		*/
		root = new Node<>();
	}

	@Override
	public void insert(String s) {
		int len = s.length();
		int idx = 0;
		Node<String> cur = root;

		while(idx<len){
			if(cur.numChildren()==0){
				Node<String> newNode = new Node<>();
				cur.insertKey(0, s.substring(idx,idx+1));
				idx++;
				cur.insertChild(0, newNode);
				newNode.setParent(cur);
				cur = cur.getChild(0);
				if(idx==len)
					newNode.setLastChar(true);
			}else{
				int nc = cur.numChildren();
				int i;
				for(i=0;i<nc;i++){
					if(s.substring(idx,idx+1).compareTo(cur.getKey(i))==0){
						idx++;
						cur = cur.getChild(i);
						break;
					}
				}
				if(i==nc){
					Node<String> newNode = new Node<>();
					newNode.setParent(cur);
					for(int j=0;j<=nc;j++){
						if(j==nc || (s.substring(idx,idx+1)).compareTo(cur.getKey(j))<0){
							cur.insertChild(j, newNode);
							cur.insertKey(j, s.substring(idx,idx+1));
							cur = cur.getChild(j);
							idx++;
							break;
						}
					}
					// if(idx==len)
					// 	newNode.setLastChar(true);
				}
			}
			if(idx==len)
				cur.setLastChar(true);
		}
		sz++;
	}

	@Override
	public boolean exists(String s) {
		int idx = 0;
		int len = s.length();
		Node<String> cur = root;
		while(idx<len){
			if(idx!=len && cur.numChildren()==0){
				return false;
			}else{
				int i;
				int nc = cur.numChildren();
				for(i=0;i<nc;i++){
					if(cur.getKey(i).compareTo(s.substring(idx,idx+1))==0){
						cur = cur.getChild(i);
						idx++;
						break;
					}
				}
				if(i==nc){
					return false;
				}
			}
			
		}
		return true;
	}

	@Override
	public String[] dictionary() {
		str = new String[sz];
		Node<String> cur = root;
		String tmp="";
		index = 0;
		preorder(cur, tmp);
		return str;
	}

	public void preorder(Node<String> cur, String tmp){
		if(cur.numChildren()!=0){
			for(int i=0;i<cur.numChildren();i++){
				if(i!=0){
					int leng = tmp.length();
					tmp = tmp.substring(0,leng-1);
				}
				tmp+=cur.getKey(i);
				if(cur.getChild(i).getLastChar()==true){
					str[index++] = tmp;
				}
				preorder(cur.getChild(i), tmp);
			}
		}

	}

	@Override
	public Node root() {
		return root;
	}
}