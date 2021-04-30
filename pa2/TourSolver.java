/*
 * Name: Su Yong Chang
 * Student ID #: 2016125016
 */

/*
 * Do NOT import any additional packages/classes.
 * If you (un)intentionally use some additional packages/classes we did not
 * provide, you may receive a 0 for the homework.
 */

public final class TourSolver implements ITourSolver {
    /*
     * Add some variables you will use.
     */
    int dx[] = { 1, 2,2,1,-1,-2,-2,-1};
    int dy[] = {-2,-1,1,2, 2, 1,-1,-2};
    boolean flag[];
    int height;
    int width;
    int target;
    int moved;
    int ans[];

    @Override
    public int[] solve(Board board) {
        /*
        * Function input:
        *  + board: A board with some missing squares.
        *
        * Job:
        *  Return a seqence of knight's tour solution on the given board.
        *  If there is no solution, return an empty sequence.
        */

        height = board.getHeight();
        width = board.getWidth();
        target = 0;
        flag = new boolean[height*width];
        moved = 0;


        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
               if(board.isMissing(i,j)){
                    flag[i*height+j] = true;
               }else{
                    flag[i*height+j] = false;
                    target++;
               }
            }
        }

        ans = new int[target];

        for(int j=0;j<height;j++){
            for(int i=0;i<width;i++){
                moved=0;
                dfs(board, i, j);
                if(target != moved){
                    flag[i*height+j] = false;
                    moved--;
                }
                if(target == moved){
                    return ans;
                }
            }
        }
        return new int[] { 0 };
    }

    public void dfs(Board board, int x, int y){
            flag[x*height+y] = true;
            ans[moved] = x*height+y;
            moved++;

            for(int dir=0;dir<8;dir++){
                int nextX = x+dx[dir];
                int nextY = y+dy[dir];
                if(nextX >= 0 && nextY >= 0 && nextX < width && nextY < height){
                    if(!flag[nextX*height+nextY]){
                        dfs(board, nextX, nextY);
                        if(target != moved){
                            flag[nextX*height+nextY] = false;
                            moved--;
                        }else
                            return;
                      }
                }
            }
    }
}
