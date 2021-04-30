
/*
* Name: Su Yong Chang
* Student ID #: 2016125016
*/
import java.lang.Math;
/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Autocomplete implements IAutocomplete {
	Trie t;
	float sz;
	float total;
	public Autocomplete() {
		/*
		* Constructor.
		*
		* Note that we will check the number of compare calls;
		* if the count is too low or too high (depending on cases),
		* you will fail the case.
		*/
		t = new Trie();
		sz = 0;
		total = 0;
	}

	@Override
	public void construct(String[] s) {
		for(int i=0;i<s.length;i++){
			t.insert(s[i]);
			sz++;
		}
	}

	@Override
	public String autocompletedWord(String s) {
		Node<String> cur = t.root();
		String str = "";
		
		for(int j=0;j<s.length();j++){
			int nc = cur.numChildren();
			for(int i=0;i<nc;i++){
				if(cur.getKey(i).compareTo(s.substring(j, j+1))==0){
					str+=cur.getKey(i);
					cur = cur.getChild(i);
					break;
				}
			}
			while(cur.numChildren()==1 && cur.getLastChar()!=true){
				str+=cur.getKey(0);
				cur = cur.getChild(0);
			}
		}

		return str;
	}

	@Override
	public float average() {
		Node<String> cur = t.root();
		int cnt = 0;
		preorder(cur, cnt);
		return total/sz;
	}

	public void preorder(Node<String> cur, int cnt){
		if(cur.numChildren()!=0){
			for(int i=0;i<cur.numChildren();i++){
				if(i!=0 && cur!=t.root()){
					cnt--;
				}
				if(cur == t.root())
					cnt = 0;
				if(cur.getChild(i).getLastChar()==true || (cur.numChildren()!=1 && cur!=t.root())){
					cnt++;
				}
				if(cur.getChild(i).getLastChar()==true){
					total+=cnt;
					System.out.println(cnt);
				}
				preorder(cur.getChild(i), cnt);
			}
		}

	}
}