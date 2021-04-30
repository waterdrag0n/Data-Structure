public interface ITree<K> {
	TreeNode<K> root()
		throws IllegalStateException;
	TreeNode<K> insert(K key);
	void delete(K key);
	boolean search(K key);
	int size();
	boolean isEmpty();
}