/*
* Name: Su Yong Chang
* Student ID #: 2016125016
*/

import java.lang.Math;
import java.util.Comparator;
/*
* Do NOT import additional packages/classes.
* If you (un)intentionally use packages/classes we did not provide,
* you will get 0.
*/

public class Tree<K> implements ITree<K> {
	int sz;
	TreeNode<K> root;
	Comparator<K> comp;

	public Tree(Comparator<K> comp) {
		/*
        * Constructor.
        *
        * Note that we will check the number of compare calls;
        * if the count is too low or too high (depending on cases),
        * you will fail the case.
        */
        sz = 0;
        root = null;
        this.comp = comp;
	}

	@Override
	public TreeNode<K> root() throws IllegalStateException {
		/*
        * Return the root node.
        * If there is no root, raise an IllegalStateException.
        */
        if(root==null)
        	throw new IllegalStateException();
        return root;
	}

	@Override
	public TreeNode<K> insert(K key) {
		/*
        * Insert the given key at the appropriate node
        * with correct position and return the node.
        * You need to handle the cases of 'overflow' and
        * perform a split operation.
        * Note that you do not need to consider the case of inserting
        * the key that is already in the tree. As stated in the
        * guideline, we will only insert the key that is not in the
        * tree.
        */
        if(isEmpty()){
        	root = new TreeNode<>();
        	root.insertKey(0, key);
        	sz++;
        	return root;
        }
        TreeNode<K> cur = root();
        while(true){
        	if(cur.numChildren()==0){
        		keyInsert(cur, key);
        		break;
        	}
        	for(int j=0;j<=cur.numKeys();j++){
        		if(j == cur.numKeys()){
        			cur = cur.getChild(j);
        			break;
        		}
        		if(comp.compare(key, cur.getKey(j))==-1){
        			cur = cur.getChild(j);
        			break;
        		}
        	}
        }
        sz++;
        //find node
        cur = root();
		while(true){
			for(int i=0;i<=cur.numKeys();i++){
				if(i == cur.numKeys()){
					cur = cur.getChild(i);
					break;
				}
				if(comp.compare(cur.getKey(i),key)==0){
					return cur;
				}
				else if(comp.compare(key,cur.getKey(i))==-1){
					cur = cur.getChild(i);
					break;
				}
			}
		}

	}

	public void keyInsert(TreeNode<K> node, K key){
		if(node.numKeys()==0){
			node.insertKey(0, key);
			return;
		}
		for(int i=0;i<=node.numKeys();i++){
			if(i==node.numKeys()){
				node.insertKey(i, key);
				break;
			}
			if(comp.compare(key, node.getKey(i))==-1){
				node.insertKey(i, key);
				break;
			}
		}
		if(node.numKeys()==4)
			split(node);
	}

	public void split(TreeNode<K> node){
		TreeNode<K> parentNode = node.getParent();
		if(parentNode == null){
			parentNode = new TreeNode<>();
			parentNode.insertChild(0, node);
			node.setParent(parentNode);
			root = parentNode;
		}
		K val = node.getKey(2);
		node.removeKey(2);
		
		for(int i=0;i<=parentNode.numKeys();i++){
			if(i==parentNode.numKeys()){
				parentNode.insertKey(i, val);
				break;
			}
			if(comp.compare(val, parentNode.getKey(i))==-1){
				parentNode.insertKey(i, val);
				break;
			}
		}

		TreeNode<K> newNode = new TreeNode<>();
		val = node.getKey(2);
		newNode.insertKey(0, val);
		node.removeKey(2);
		
		for(int i=0;i<parentNode.numChildren();i++){
			if(parentNode.getChild(i) == node){
				parentNode.insertChild(i+1, newNode);
				break;
			}
		}
		newNode.setParent(parentNode);

		if(node.numChildren()!=0){
			TreeNode<K> child1 = node.getChild(node.numChildren()-1);
			node.removeChild(node.numChildren()-1);
			TreeNode<K> child2 = node.getChild(node.numChildren()-1);
			node.removeChild(node.numChildren()-1);
			child1.setParent(newNode);
			child2.setParent(newNode);
			newNode.insertChild(0,child2);
			newNode.insertChild(1,child1);
		}

		if(parentNode.numKeys()==4){
			split(parentNode);
		}
	}

	@Override
	public void delete(K key) {
		/*
        * Find the node with a given key and delete the key.
        * You need to handle the cases of 'underflow' and
        * perform a fusion operation.
        * If there is no "key" in the tree, please ignore it.
        */
        if(size()==1){
        	root = null;
        	sz = 0;
        	return;
        }
        //find key
        TreeNode<K> target = findKey(key);
        if(target == null) return;

        //find successor
        TreeNode<K> succ = target;
        int idx;
        for(idx=0;idx<target.numKeys();idx++){
        	if(comp.compare(key, target.getKey(idx))==0){
        		break;
        	}
        }
       
         //if internal node
        if(target.numChildren()==0){
        	target.removeKey(idx);
        }else{
        	succ = succ.getChild(idx+1);
	        while(true){
	        	if(succ.numChildren()==0)
	        		break;
	        	succ = succ.getChild(0);
	        }

	        target.removeKey(idx);
	        target.insertKey(idx, succ.getKey(0));
	        succ.removeKey(0);
        }
        
        sz--;
        //if underflow
        while(succ.numKeys()==0){
        	if(succ.getParent().getChild(0)==succ){
    			if(succ.getParent().getChild(1).numKeys()==1){
    				fusion(succ);
    			}else{
    				transfer(succ);
    			}
    		}else{
    			int ind;
    			for(ind = 1;ind<succ.getParent().numChildren();ind++){
    				if(succ.getParent().getChild(ind)==succ)
    					break;
    			}
    			if(succ.getParent().getChild(ind-1).numKeys()==1){
    				fusion(succ);
    			}else{
    				transfer(succ);
    			}
    		}
    		if(succ.getParent()==null || succ.getParent()==root)
    			break;
        }
	}

	public void transfer(TreeNode<K> cur){
		TreeNode<K> parentNode = cur.getParent();
		TreeNode<K> sibling;
		if(cur.getParent().getChild(0)==cur){
			sibling = parentNode.getChild(1);
			if(sibling.numChildren()!=0){
				
				TreeNode<K> tmp = sibling.getChild(0);
				sibling.removeChild(0);
				tmp.setParent(cur);
				cur.insertChild(cur.numChildren(), tmp);
			}

			K val = parentNode.getKey(0);
			parentNode.removeKey(0);
			cur.insertKey(cur.numKeys(), val);

			val = sibling.getKey(0);
			sibling.removeKey(0);
			parentNode.insertKey(0, val);
		}else{
			int i;
			for(i=1;i<parentNode.numChildren();i++){
				if(parentNode.getChild(i)==cur)
					break;
			}
			sibling = parentNode.getChild(i-1);
			if(sibling.numChildren()!=0){
				TreeNode<K> tmp = sibling.getChild(sibling.numChildren()-1);
				sibling.removeChild(sibling.numChildren()-1);
				tmp.setParent(cur);
				cur.insertChild(0, tmp);
			}
			K val = parentNode.getKey(i-1);
			parentNode.removeKey(i-1);
			cur.insertKey(0, val);

			val = sibling.getKey(sibling.numKeys()-1);
			sibling.removeKey(sibling.numKeys()-1);
			parentNode.insertKey(i-1, val);
		}
	}

	public void fusion(TreeNode<K> cur){
		TreeNode<K> parentNode = cur.getParent();
		TreeNode<K> sibling;
		if(cur.getParent().getChild(0)==cur){
			sibling = parentNode.getChild(1);

			K val = parentNode.getKey(0);
			parentNode.removeKey(0);
			cur.insertKey(cur.numChildren(), val);

			val = sibling.getKey(0);
			sibling.removeKey(0);
			cur.insertKey(cur.numChildren(), val);

			if(sibling.numChildren()!=0){
				TreeNode tmp = sibling.getChild(0);
				sibling.removeChild(0);
				cur.insertChild(cur.numChildren(), tmp);
				tmp = sibling.getChild(0);
				sibling.removeChild(0);
				cur.insertChild(cur.numChildren(), tmp);
			}

			sibling.setParent(null);
			parentNode.removeChild(1);

			if(parentNode.numKeys()==0){
				parentNode.removeChild(0);
				cur.setParent(null);
				root = cur;
			}

		}else{
			int i;
			for(i=1;i<parentNode.numChildren();i++){
				if(parentNode.getChild(i)==cur)
					break;
			}
			sibling = parentNode.getChild(i-1);

			K val = parentNode.getKey(i-1);
			parentNode.removeKey(i-1);
			cur.insertKey(0, val);

			val = sibling.getKey(0);
			sibling.removeKey(0);
			cur.insertKey(0, val);

			if(sibling.numChildren()!=0){
				TreeNode tmp = sibling.getChild(sibling.numChildren()-1);
				sibling.removeChild(sibling.numChildren()-1);
				cur.insertChild(0, tmp);
				tmp = sibling.getChild(sibling.numChildren()-1);
				sibling.removeChild(sibling.numChildren()-1);
				cur.insertChild(0, tmp);
			}

			sibling.setParent(null);
			parentNode.removeChild(i-1);

			if(parentNode.numKeys()==0){
				parentNode.removeChild(i);
				cur.setParent(null);
				root = cur;
			}
		}
		if(cur.getParent()!=null && cur.getParent().numKeys()==0 && cur.getParent()!=root)
			fusion(cur.getParent());
	}

	public TreeNode<K> findKey(K key){
        TreeNode<K> cur = root;
        while(true){
        	for(int i=0;i<=cur.numKeys();i++){
        		if(i==cur.numKeys() && cur.numChildren()==0) return null;
        		if(cur.numChildren()!=0){
        			if(i==cur.numKeys() || comp.compare(key, cur.getKey(i))==-1){
        				cur = cur.getChild(i);
        				break;
        			}
        		}
        		if(comp.compare(key, cur.getKey(i))==0) return cur;
        	}
        }
	}

	@Override
	public boolean search(K key) {
		/*
		* Find a node with a given key and return true if you can find it.
		* Return false if you cannot.
		*/
		if(isEmpty())
			return false;
		TreeNode<K> cur = root();
		while(true){
			for(int i=0;i<=cur.numKeys();i++){
				if(i == cur.numKeys()){
					if(cur.numChildren()==0)
						return false;
					cur = cur.getChild(i);
					break;
				}
				if(comp.compare(cur.getKey(i),key)==0)
					return true;
				else if(comp.compare(key,cur.getKey(i))==-1){
					if(cur.numChildren()==0)
						return false;
					cur = cur.getChild(i);
					break;
				}
			}
		}
	}

	@Override
	public int size() {
		/*
		* Return the number of keys in the tree.
		*/
		return sz;
	}
	
	@Override
	public boolean isEmpty() {
		/*
		* Return whether the tree is empty or not.
		*/
		return size()==0;
	}

}