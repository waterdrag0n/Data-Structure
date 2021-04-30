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

public final class MST implements IMST {
    int[][] board;
    int n,m,sz;
    boolean[] visited;
    int[] path;

    public MST(String filename) {
        File file = new File(filename);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int spaceIdx = line.indexOf(" ");
            n = Integer.parseInt(line.substring(0,spaceIdx));
            m = Integer.parseInt(line.substring(spaceIdx+1));
            sz = n;
            board = new int[n][n];
            while ((line = br.readLine()) != null) {
                String[] a = line.split(" ");
                int from = Integer.parseInt(a[0]);
                int to = Integer.parseInt(a[1]);
                int weight = Integer.parseInt(a[2]);
                board[from][to] = weight;
                board[to][from] = weight;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] shortestPath(int u, int v) {
        visited = new boolean[sz];
        path = new int[sz];
        dijkstra(u, v);
        int tmp[] = new int[sz];
        int idx = 0;
        int cur = v;
        tmp[idx++] = v;
        while(cur!=u){
            tmp[idx++] = path[cur];
            cur = path[cur];
        }
        path = new int[idx];
        for(int i=0;i<idx;i++){
            path[i] = tmp[idx-i-1];
        }

        return path;
    }

    void dijkstra(int start, int target){
        int[] dist = new int[sz];
        for(int i=0;i<sz;i++)
            dist[i] = 200000000;
        dist[start] = 0;
        PriorityQueue pq = new PriorityQueue();
        pq.insert(new Edge(start, 0));
        while(!pq.isEmpty()){
            Edge tmp = pq.delete();
            int cur = tmp.end;
            int dis = tmp.weight;
            if(dist[cur]<dis) continue;
            for(int i=0; i<sz;i++){
                if(board[cur][i]==0) continue;
                int next= i;
                int nextDist = dis+board[cur][i];
                if(nextDist<dist[next]){
                    dist[next] = nextDist;
                    pq.insert(new Edge(next, nextDist));
                    path[next] = cur;
                }
            }
        }
    }

    @Override
    public int findMST() {
        PriorityQueue pq = new PriorityQueue();
        int result = 0;
        boolean[] visited = new boolean[sz];
        pq.insert(new Edge(0,0));
        while(!pq.isEmpty()){
            Edge e = pq.delete();
            if(visited[e.end]) continue;
            visited[e.end] = true;
            result+=e.weight;
            for(int i=0;i<sz;i++){
                if(!visited[i] && board[e.end][i]!=0){
                    pq.insert(new Edge(i, board[e.end][i]));
                }
            }
        }
        return result;
    }
}

class Edge{
    int end;
    int weight;
    Edge(int end, int weight){
        this.end = end;
        this.weight = weight;
    }
}

class PriorityQueue{
    Edge[] h;
    int size;
    int length;
    PriorityQueue(){
        length = 10;
        h = new Edge[length];
        size = 0;
    }

    int parent(int cur){
        return cur/2;
    }

    int leftChild(int cur){
        return cur*2;
    }

    int rightChild(int cur){
        return cur*2+1;
    }

    void resize(){
        length*=2;
        Edge[] newArr = new Edge[length];
        for(int i=0;i<size;i++){
            newArr[i] = h[i];
        }
        h = newArr;
    }

    void swap(int a, int b){
        Edge tmp = h[a];
        h[a] = h[b];
        h[b] = tmp;
    }

    void insert(Edge item){
        if(size==0){
            h[1] = item;
            size++;
            return;
        }
        if(size>=length-2) resize();
        h[++size] = item;

        int cur = size;
        while(cur!=1 && h[cur].weight<h[parent(cur)].weight){
            swap(cur, parent(cur));
        }
    }

    boolean isEmpty(){
        return size==0;
    }

    Edge delete(){
        if(size==1){
            size = 0;
            return h[1];
        }
        Edge min = h[1];
        h[1] = h[size--];
        int cur = 1;
        while(leftChild(cur)<size){
            Edge ch = h[leftChild(cur)];
            int chPos = leftChild(cur);

            if(rightChild(cur)<size && h[rightChild(cur)].weight<ch.weight){
                ch = h[rightChild(cur)];
                chPos = rightChild(cur);
            }
            if(h[cur].weight<ch.weight)
                break;
            swap(cur, ch.end);
            cur = ch.end;
        }
        return min;
    }
}