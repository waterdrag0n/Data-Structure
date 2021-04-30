import java.util.ArrayList;
import java.util.List;

public final class Node<K> {
	private Node<K> parent;
	private List<Node<K>> children;
	private List<K> keys;
	private boolean isLastChar;

	public Node() {
		/*
		* Please be aware of the number of children and keys.
		*/
		parent = null;
		children = new ArrayList<>();
		keys = new ArrayList<>();
	}

	public Node<K> getParent() {
		return parent;
	}

	public void setParent(Node<K> parent) {
		this.parent = parent;
	}

	public Node<K> getChild(int k)
		throws IndexOutOfBoundsException {
		/*
		* Return the node's k-th child.
		* If k is not in the valid range,
		* raise an IndexOutOfBoundsException.
		*/
		return children.get(k);
	}

	public void insertChild(int k,Node<K> child)
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
	
	boolean getLastChar() {
	return this.isLastChar;
	}
	
	void setLastChar(boolean isLastChar) {
	this.isLastChar = isLastChar;
	}

}
