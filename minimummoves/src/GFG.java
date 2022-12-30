
import java.util.ArrayDeque;
class GFG {


    static boolean IsSafe(int i, int j, int[][] M, int width, int length) {
        if ((i < 0 || i >= width) || (j < 0 || j >= length)
                || M[i][j] == 0)
            return false;
        return true;
    }
    static boolean IsZero(int i, int j, int[][] M, int soursei, int soursej) {
        if (i!=soursei){
            for(int k=Math.min(i,soursei)+1;k<=Math.max(i,soursei);k++){
                if(M[k][j]==0)
                    return false;
            }
        }else {
            for(int k=Math.min(j,soursej)+1;k<=Math.max(j,soursej);k++){
                if(M[i][k]==0)
                    return false;
            }
        }
        return true;
    }

    // Returns minimum numbers of moves from a source (a
    // cell with value 1) to a destination (a cell with
    // value 2)
    static int MinimumPath(int[][] M, int width, int length, int ways) {
        int V = width * length + 2;
        Graph g = new Graph(V);

        // create graph with n*n node
        // each cell consider as node
        int k = 1; // Number of current vertex

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length ; j++) {
                if (M[i][j] != 0) {
                    // connect all 4 adjacent cell to
                    // current cell
                    for(int gg=1;gg<=ways;gg++) {

                        if (IsSafe(i, j + gg, M, width, length)) {
                            if(IsZero(i,j+gg, M, i, j))
                                g.AddEdge(k, k + gg);
                            //g.AddEdge(k, k + 1);
                        }

                        if (IsSafe(i, j - gg, M, width, length)) {
                            if(IsZero(i,j-gg, M, i, j))
                                g.AddEdge(k, k - gg);
                        }
                        //g.AddEdge(k, k - 1);
                        if (j < length - 1 && IsSafe(i + gg, j, M, width, length)) {
                            if(IsZero(i+gg,j, M, i, j))
                                g.AddEdge(k, k + gg * length);
                        }
                        //g.AddEdge(k, k + length);
                        if (i > 0 && IsSafe(i - gg, j, M, width, length)) {
                            if(IsZero(i-gg,j, M, i, j))
                                g.AddEdge(k, k - gg * length);
                        }
                        //g.AddEdge(k, k - length);
                    }
                }
                k++;
            }
        }

        // find minimum moves

        return g.BFS(1,width*length);
    }

    // driver program to check above function
    public static void main(String[] args) {

        int[][] M2 = {{1, 3, 3},
                      {3, 0, 3},
                      {3, 0, 3},
                      {3, 3, 3},
                      {0, 0, 3},
                      {0, 0, 2}};

        int ans2 = MinimumPath(M2,6,3,5);
        System.out.println(ans2);
    }
}
