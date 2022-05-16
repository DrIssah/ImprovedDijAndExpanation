
import java.awt.Color;

/*
 You can cut down on the search time further by exploiting the Euclidean
geometry of the problem, as described in Sedgewick 21.5. For general graphs,
Dijkstra's relaxes edge v-w by updating wt[w] to the sum of wt[v] plus the distance
from v to w. For maps, we instead update wt[w] to be the sum of wt[v] plus the
distance from v to w plus the Euclidean distance from w to d minus the Euclidean
distance from v to d. This is known as the A* algorithm. This heuristics affects
performance, but not correctness. (See Sedgewick 21.5 for a proof of correctness.)
 */
public class Dijkstra_improve_p1 {

    private static double INFINITY = Double.MAX_VALUE;
    private static double EPSILON = 0.000001;
    private EuclideanGraph G;
    private double[] dist;
    private int[] pred;

    public Dijkstra_improve_p1(EuclideanGraph G) {

        this.G = G;

    }

    // return shortest path distance from s to d
    public double distance(int s, int d) {

        dijkstra(s, d, 0);

        return dist[d];

    }

    // print shortest path from s to d  (interchange s and d to print in right order)
    public void showPath(int d, int s) {

        dijkstra(s, d, 0);

        if (pred[d] == -1) {

            System.out.println(d + " is unreachable from " + s);

            return;

        }

        for (int v = d; v != s; v = pred[v]) {
            System.out.print(v + "-");
        }

        System.out.println(s);

    }

    // plot shortest path from s to d
    public void drawPath(int s, int d) {

        dijkstra(s, d, 1);

        if (pred[d] == -1) {
            return;
        }

        Turtle.setColor(Color.red);

        for (int v = d; v != s; v = pred[v]) {
            G.point(v).drawTo(G.point(pred[v]));
        }

        Turtle.render();

    }

    // Dijkstra's algorithm to find shortest path from s to d
    public void dijkstra(int s, int d, int draw) {

        int V = G.V();

        // initialize
        dist = new double[V];

        pred = new int[V];

        for (int v = 0; v < V; v++) {
            dist[v] = INFINITY;
        }

        for (int v = 0; v < V; v++) {
            pred[v] = -1;
        }

        // priority queue
        IndexPQ pq = new IndexPQ(V);

        //changes made here too
        for (int v = 0; v < V; v++) {
            pq.insert(v, dist[v]);
        }
        // set distance of source
        dist[s] = 0.0;
        pred[s] = s;

        pq.change(s, dist[s]);

        // run Dijkstra's algorithm
        while (!pq.isEmpty()) {

            int v = pq.delMin();

            //// System.out.println("process " + v + " " + dist[v]);
            if (d == v) {
                break;

            }

            // v not reachable from s so stop
            if (pred[v] == -1) {
                break;
            }

            // scan through all nodes w adjacent to v
            IntIterator i = G.neighbors(v);

            while (i.hasNext()) {

                int w = i.next();
                if (draw == 1) {

                    Turtle.setColor(Color.yellow);

                    G.point(v).drawTo(G.point(w));

                    Turtle.render();

                    try {

                        for (long j = 0; j < 1000; j++){
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    } 

                }

                //improve goes here
                if (dist[v] - G.distance(v, d) + G.distance(v, w) + G.distance(w, d) < dist[w] - EPSILON) {

                    dist[w] = dist[v] - G.distance(v, d) + G.distance(v, w) + G.distance(w, d);

                    pq.change(w, dist[w]);

                    pred[w] = v;

                }

            }

        }

    }

}
