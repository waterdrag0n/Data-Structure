/*
* Name: Su Yong Chang
* Student ID#: 2016125016
*/

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class SCC implements ISCC {
    Graph G;
    int[][] reversed_board;
    boolean[] visited;
    Stack stack;
    List<List<Integer>> list;
    public SCC(String filename) {
        G = new Graph(filename);
        
    }

    @Override
    public boolean path(int u, int v) {
        visited = new boolean[G.sz];
        boolean isPath = pathDFS(u,v);
        return isPath;
    }

    boolean pathDFS(int start, int v){
        if(start==v)
            return true;
        visited[start] = true;

        for(int i=0;i<G.sz;i++){
            if(visited[i] || G.board[start][i]==0) continue;
            boolean ans = pathDFS(i, v);
            if(ans)
                return true;
        }
        return false;
    }

    void makeReversedMatrix(){
        for(int i=0;i<G.sz;i++){
            for(int j=0;j<G.sz;j++){
               if(G.board[i][j]==1){
                    reversed_board[j][i] = 1;
                }
            }
        }
    }

    @Override
    public boolean connectivity() {
        visited = new boolean[G.sz];
        conDFS(0);
        for(int i=0;i<G.sz;i++){
            if(!visited[i])
                return false;
        }
        visited = new boolean[G.sz];
        conDFSRev(0);
        for(int i=0;i<G.sz;i++){
            if(!visited[i])
                return false;
        }
        return true;
    }

    void conDFS(int start){
        visited[start] = true;
        for(int i=0;i<G.sz;i++){
            if(visited[i] || G.board[start][i]==0 || i==start) {
                continue;
            }
            conDFS(i);
        }
    }

    void conDFSRev(int start){
        visited[start] = true;
        for(int i=0;i<G.sz;i++){
            if(visited[i] || G.board[i][start]==0 || i==start) {
                continue;
            }
            conDFSRev(i);
        }
    }

    @Override
    public List<List<Integer>> findSCC() {
        list = new ArrayList<>();
        stack = new Stack();
        visited = new boolean[G.sz];
        for(int i=0;i<G.sz;i++){
            if(visited[i]) continue;
            SCCDFS(i);
        }

        visited = new boolean[G.sz];
        while(!stack.isEmpty()){
            int cur = stack.pop();
            if(visited[cur]) continue;
            List<Integer> tmpList = new ArrayList<>();
            dfsRev(cur, tmpList);
            tmpList.sort(null);
            list.add(tmpList);
        }
        list.sort((l1, l2) -> l1.get(0).compareTo(l2.get(0)));

        return list;
    }

    void dfsRev(int start, List<Integer> tmpList){
        visited[start] = true;
        tmpList.add(start);
        for(int i=1;i<G.sz;i++){
            if(visited[i] || G.board[i][start]==0 || i==start) {
                continue;
            }
            dfsRev(i, tmpList);
        }
    }

    void SCCDFS(int start){
        visited[start] = true;
        for(int i=1;i<G.sz;i++){
            if(visited[i] || G.board[start][i]==0 || i==start) {
                continue;
            }
            SCCDFS(i);
        }
        stack.push(start);
    }
}

class Stack{
    public Node top;
    public int sz;
    /*
    * Use some variables for your implementation.
    */

    public Stack() {
        /*
        * Constructor
        * This function is an initializer for this class.
        */
        top = null;
        sz = 0;
    }

    public void push(int item) {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Insert the item at the top of the stack.
        */
        if(isEmpty())
            top = new Node(item);
        else
        {
            Node newNode = new Node(item, top);
            top = newNode;
        }
        sz++;
    }

    public int pop() throws IllegalStateException {
        /*
        * Function input:
        *  item: an item to be inserted.
        *
        * Job:
        *  Remove an item from the top of the stack and return that item.
        */
        if(isEmpty())
            throw new IllegalStateException();
        else
        {
            int tmp = top.getValue();
            top = top.getNext();
            sz--;
            return tmp;
        }
    }

    public int size() {
        /*
        * Function input: Nothing
        *
        * Job:
        *  Return the number of items in the stack.
        */
        return sz;
    }

    public boolean isEmpty() {
        /* You must not edit this function. */
        return top == null;
    }
}

class Node {
    private Node next;
    private int value;

    Node() {
        next = null;
        value = 0;
    }

    Node(int item) {
        next = null;
        value = item;
    }

    Node(int item, Node next) {
        this.next = next;
        value = item;
    }

    Node getNext() {
        return next;
    }

    int getValue() {
        return value;
    }

    void setNext(Node next) {
        this.next = next;
    }

    void setValue(int value) {
        this.value = value;
    }
}
