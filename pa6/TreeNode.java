import java.util.List;
import java.util.ArrayList;

public final class TreeNode<K> {
	private TreeNode<K> parent;
	private List<TreeNode<K>> children;
	private List<K> keys;

	public TreeNode() {
		/*
		* Please be aware of the number of children and keys.
		*/
		parent = null;
		children = new ArrayList<>();
		keys = new ArrayList<>();
	}

	public TreeNode<K> getParent() {
		return parent;
	}

	public void setParent(TreeNode<K> parent) {
		this.parent = parent;
	}

	public TreeNode<K> getChild(int k)
		throws IndexOutOfBoundsException {
		/*
		* Return the node's k-th child.
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		return children.get(k);
	}

	public void insertChild(int k,TreeNode<K> child)
		throws IndexOutOfBoundsException {
		/*
		* Insert the node as its k-th child.
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		children.add(k,child);
    }

    public void removeChild(int k)
    	throws IndexOutOfBoundsException {
    	/*
		* Remove the k-th child node.
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		children.remove(k);
    }

	public K getKey(int k)
		throws IndexOutOfBoundsException {
		/*
		* Return the key with index k
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		return keys.get(k);
	}

	public void insertKey(int k,K key)
		throws IndexOutOfBoundsException {
		/*
		* Insert the key at the index of k.
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		keys.add(k,key);
	}

	public void removeKey(int k)
		throws IndexOutOfBoundsException {
		/*
		* Remove the key with index k.
		* If the index is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		keys.remove(k);	
	}

	public int numChildren() {
		/*
		* Return the number of children.
		*/
		return children.size();
	}

	public int numKeys() {
		/*
		* Return the number of keys.
		*/
		return keys.size();
	}
}