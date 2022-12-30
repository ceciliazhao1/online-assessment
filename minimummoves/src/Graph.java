// Online Java Compiler
// Use this editor to write, compile and run your Java code
// online

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

// Java program to find the minimum numbers
// of moves needed to move from source to
// destination .

class Graph {
    private int V;
    private ArrayList<ArrayList<Integer> > adj;

    // Constructor
    Graph(int v)
    {
        V = v;
        adj = new ArrayList<>();
        for (int i = 0; i < v; i++)
            adj.add(new ArrayList<>());
    }

    // add edge to graph
    public void AddEdge(int s, int d)
    {
        adj.get(s).add(d);
        adj.get(d).add(s);
    }

    // Level BFS function to find minimum path
    // from source to sink
    public int BFS(int s, int d)
    {
        // Base case
        if (s == d)
            return 0;

        // make initial distance of all vertex -1
        // from source
        int[] level = new int[V];
        for (int i = 0; i < V; i++)
            level[i] = -1;

        // Create a queue for BFS
        Queue<Integer> queue = new ArrayDeque<>();

        // Mark the source node level[s] = '0'
        level[s] = 0;
        queue.add(s);

        while (queue.size() > 0) {
            // Dequeue a vertex from queue
            s = queue.remove();

            // Get all adjacent vertices of the
            // dequeued vertex s. If a adjacent has
            // not been visited ( level[i] < '0') ,
            // then update level[i] == parent_level[s] + 1
            // and enqueue it

            for (int i : adj.get(s)) {
                // Else, continue to do BFS
                if (level[i] < 0
                        || level[i] > level[s] + 1) {

                    level[i] = level[s]+1;
                    queue.add(i);
                }
            }
        }

        // return minimum moves from source to sink
        return level[d];
    }
}

// This code is contributed by Karandeep1234

