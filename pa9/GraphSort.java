/*
* Name: Su Yong Chang
* Student ID#: 2016125016
*/

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
* Do NOT use any external packages/classes.
* If you (un)intentionally use them we did not provide,
* you will get 0.
* Also do NOT use auto-import function on IDEs.
* If the import statements change, you will also get 0.
*/

public final class GraphSort implements IGraphSort {
    int[][] board;
    int n,m,sz;
    int[] link;
    boolean visited[];
    boolean isC=false;
    public GraphSort(String filename) {
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int spaceIdx = line.indexOf(" ");
            n = Integer.parseInt(line.substring(0,spaceIdx));
            m = Integer.parseInt(line.substring(spaceIdx+1));
            sz = n;
            board = new int[n][n];
            while ((line = br.readLine()) != null) {
                spaceIdx = line.indexOf(" ");
                int from = Integer.parseInt(line.substring(0,spaceIdx));
                int to = Integer.parseInt(line.substring(spaceIdx+1));
                board[from][to] = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeLink();
    }

    @Override
    public boolean cycle() {
        for(int i=0;i<sz;i++){
            if(isC){
                return true;
            }
            visited = new boolean[sz];
            findCycle(i);
        }
        return isC;
    }

    void findCycle(int start){
        if(isC) return;
        visited[start] = true;
        for(int i=0;i<sz;i++){
            if(isC) return;
            if(visited[i]){
                isC = true;
                return;
            }
            if(!visited[i])
                findCycle(i);
        }
        visited[start] = false;
    }

    @Override
    public int[] topologicalOrder() {
        Que q = new Que();
        int result[] = new int[sz];
        int idx=0;
        for(int i=0;i<sz;i++){
            if(link[i]==0)
                q.push(i);
        }
        for(int k=0;k<sz;k++){
            int cur = q.pop();
            result[idx++] = cur;
            for(int i=0;i<sz;i++){
                if(board[cur][i]!=0){
                    link[i]--;
                    if(link[i]==0)
                        q.push(i);
                }
            }
        }
        return result;
    }

    void makeLink(){
        link = new int[sz];
        for(int i=0;i<sz;i++){
            for(int j=0;j<sz;j++){
                link[i]+=board[j][i];
            }
        }
    }
}

class Que{
    public Node top;
    public Node tail;
    public int sz;

    public Que(){
        top = new Node();
        tail = new Node();
        sz=0;
    }

    public void push(int item){
        Node newNode = new Node(item);
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
    public int pop(){
        int tmp = top.getNext().getValue();
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

class Node {
    private Node next;
    private Node prev;
    private int value;

    Node() {
        next = null;
        prev = null;
        value = 0;
    }

    Node(int item) {
        next = null;
        prev = null;
        value = item;
    }

    Node(int item, Node next, Node prev) {
        this.next = next;
        this.prev = prev;
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

    Node getPrev() {
        return next;
    }

    void setPrev(Node prev) {
        this.prev = prev;
    }

}
