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

public final class Graph implements IGraph {
    int n, m, sz;
    int board[][];
    public Graph(String filename) {
        /*
         * Constructor
         * This function is an initializer for this class.
         */
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
    }

    @Override
    public void insertVertex() {
        int newBoard[][] = new int[sz+1][sz+1];
        for(int i=0;i<sz;i++){
            for(int j=0;j<sz;j++){
                newBoard[i][j] = board[i][j];
            }
        }
        board = newBoard;
        sz++;
    }

    @Override
    public void deleteVertex(int n) {
        n--;
        if(sz-1<=n) return;
        int newBoard[][] = new int[sz-1][sz-1];
        for(int i=0;i<sz;i++){
            for(int j=0;j<sz;j++){
                if(i>n && j>n){
                    newBoard[i-1][j-1] = board[i][j];
                }else if(i>n){
                    newBoard[i-1][j] = board[i][j];
                }else if(j>n){
                    newBoard[i][j-1] = board[i][j];
                }else{
                    newBoard[i][j] = board[i][j];
                }
            }
        }
        board = newBoard;
        sz--;
    }

    @Override
    public void insertEdge(int u, int v) {
        board[u][v] = 1;
    }

    @Override
    public void deleteEdge(int u, int v) {
        board[u][v] = 0;
    }

    @Override
    public int[][] matrix() {
        return board;
    }

    @Override
    public int size() {
        return sz;
    }
}