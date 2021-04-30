/*
* Name: Su Yong Chang
* Student ID#: 2016125016
*/

/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

import java.util.List;
import java.util.ArrayList;

public final class Tree<E> implements ITree<E> {

    int arity;
    int sz;
    TreeNode<E> root;


    public Tree(int arity) {
        /*
        * Input:
        *  + arity: max number of node's children. always positive.
        */
        this.arity = arity;
        sz = 0;
        root = new TreeNode<>(this.arity, null);
    }

    public int pow(int n, int ex){
        int tmp =1;
        for(int i=0;i<ex;i++)
            tmp*=n;
        return tmp;
    }

    @Override
    public TreeNode<E> root() throws IllegalStateException {
        /*
        * Return the root node.
        * If there is no root, raise an IllegalStateException.
        */
        if(isEmpty())
            throw new IllegalStateException();
        else
            return root;
    }

    @Override
    public int arity() {
        /*
        * Return the max number of children its node can have
        */
        return arity;
    }

    @Override
    public int size() {
        /*
        * Return the number of nodes in this tree.
        */
        return sz;
    }

    @Override
    public boolean isEmpty() {
        /*
        * Return true if this tree is empty.
        */
        return size()==0;
    }

    @Override
    public int height() throws IllegalStateException {
        /*
        * Return height of this tree.
        * If there are no nodes in this tree,
        * raise an IllegalStateException.
        */
        int h = 0;

        if(isEmpty())
            throw new IllegalStateException();
        else{
            Que<E> q = new Que<>();
            q.push(root);
            while(true){
                if(q.isEmpty())
                    break;
                int size = q.size();
                for(int j=0;j<size;j++){
                    TreeNode<E> tmp = q.pop();
                    for(int i=0;i<tmp.numChildren();i++){
                        q.push(tmp.getChild(i));
                    }
                }
                h++;
            }
        }
        return h-1;
    }

    @Override
    public TreeNode<E> add(E item) {
        /*
        * Insert the given node at the *end* of this tree and
        * return THE inserted NODE.
        * *End* means that the leftmost possible slot of
        * smallest depth of this tree.
        */

        if(isEmpty()){
            root.setValue(item);
            sz++;
            return root;
        }else{
            Que<E> q = new Que<>();
            q.push(root);
            while(true){
                TreeNode<E> tmp = q.pop();
                if(tmp.numChildren()<arity){
                    TreeNode<E> newNode = new TreeNode<>(arity, item);
                    tmp.insertChild(tmp.numChildren(), newNode);
                    sz++;
                    return newNode;
                }else{
                    for(int i=0;i<tmp.numChildren();i++){
                        q.push(tmp.getChild(i));
                    }
                }
            }
        }
    }

    @Override
    public void detach(TreeNode<E> node) throws IllegalArgumentException {
        /*
        * Detach the given node (and its descendants) from this tree.
        * if the node is not in this tree,
        * raise an IllegalArgumentException.
        */
        if(isEmpty())
            throw new IllegalArgumentException();
        else if(node == root){
            root = new TreeNode<>(this.arity, null);
            sz = 0;
        }
        else{
            int cnt = count(node);
            Que<E> q = new Que<>();
            q.push(root);
            while(true){
                if(q.isEmpty())
                    break;
                TreeNode<E> tmp = q.pop();
                for(int i=0;i<tmp.numChildren();i++){
                    if(tmp.getChild(i) == node){
                        tmp.removeChild(i);
                        sz-=cnt;
                        return;
                    }else{
                        q.push(tmp.getChild(i));
                    }
                }

            }
        }
    }

    public int count(TreeNode<E> node){
        int total=1;
        if(node.numChildren()!=0){
            Que<E> q = new Que<>();
            q.push(node);
            while(true){
                if(q.isEmpty())
                    break;
                TreeNode<E> tmp = q.pop();
                if(tmp.numChildren()!=0){
                    total+=tmp.numChildren();
                    for(int i=0;i<tmp.numChildren();i++){
                        q.push(tmp.getChild(i));
                    }
                }
            }
        }
        return total;
    }

    @Override
    public List<E> preOrder() {
        /*
        * Return the sequence of items visited by preorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */
        List<E> seq = new ArrayList<>();
        if(!isEmpty())
            po(seq, root);
        return seq;
    }
    public void po(List<E> seq, TreeNode<E> node){
        seq.add(node.getValue());
        for(int i=0;i<node.numChildren();i++)
            po(seq, node.getChild(i));
    }

    @Override
    public List<E> postOrder() {
        /*
        * Return the sequence of items visited by postorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */
        List<E> seq = new ArrayList<>();
        if(!isEmpty())
            pso(seq, root);
        return seq;
    }
    public void pso(List<E> seq, TreeNode<E> node){
        for(int i=0;i<node.numChildren();i++)
            pso(seq, node.getChild(i));
        seq.add(node.getValue());
    }

    @Override
    public List<E> depthOrder() {
        /*
        * Return the sequence of items visited by depthorder traversal.
        * If there are no nodes, return an empty list, NOT NULL.
        */
        List<E> seq = new ArrayList<>();
        if(!isEmpty())
            depthOrdRec(seq, root);
        return seq;
    }
    public void depthOrdRec(List<E> seq, TreeNode<E> node){
        Que<E> q = new Que<>();
        q.push(node);
        seq.add(node.getValue());
        while(true){
            if(q.isEmpty())
                break;
            TreeNode<E> tmp = q.pop();
            if(tmp.numChildren()!=0){
                for(int i=0;i<tmp.numChildren();i++){
                    q.push(tmp.getChild(i));
                    seq.add(tmp.getChild(i).getValue());
                }
            }
        }
    }
}

class Que<E>{
    public TNode top;
    public TNode tail;
    public int sz;

    public Que(){
        top = new TNode();
        tail = new TNode();
        sz=0;
    }

    public void push(TreeNode<E> node){
        TNode newNode = new TNode(node);
        if(isEmpty()){
            top.setNext(newNode);
            tail.setPrev(newNode);
            newNode.setPrev(top);
            newNode.setNext(tail);
        }else{
            tail.getPrev().setNext(newNode);
            newNode.setPrev(tail.getPrev());
            newNode.setNext(tail);
            tail.setPrev(newNode);
        }
        sz++;
    }
    public TreeNode<E> pop(){
        TreeNode<E> tmp = top.getNext().getValue();
        if(size()==1){
            top.setNext(null);
            tail.setPrev(null);
        }else{
            top.getNext().getNext().setPrev(top);
            top.setNext(top.getNext().getNext());
        }
        sz--;
        return tmp;
    }

    public int size() {
        return sz;
    }

    public boolean isEmpty() {
        return size()==0;
    }
}

class TNode {
    public TNode next;
    public TNode prev;

    public TreeNode value;

    public TNode() {
        next = null;
        prev = null;
        value = null;
    }

    public TNode(TreeNode node){
        next = null;
        prev = null;
        value = node;
    }

    TNode getNext() {
        return next;
    }

    TNode getPrev() {
        return prev;
    }

    void setNext(TNode next) {
        this.next = next;
    }

    void setPrev(TNode prev) {
        this.prev = prev;
    }

    TreeNode getValue() {
        return value;
    }

    void setValue(TreeNode value) {
        this.value = value;
    }


}
