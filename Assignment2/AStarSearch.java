import java.util.*;

class Node {
    int row, col, g;
    Node parent;

    public Node(int row, int col, int g, Node parent) {
        this.row = row;
        this.col = col;
        this.g = g;
        this.parent = parent;
    }
}

public class AStarSearch {
    int[][] dirs = {
        {-1,-1}, {-1,0}, {-1,1},
        {0,-1},          {0,1},
        {1,-1}, {1,0},   {1,1}
    };

    private int heuristic(int x, int y, int n) {
        return Math.abs(x - (n-1)) + Math.abs(y - (n-1));
    }

    private List<int[]> reconstructPath(Node node) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(new int[]{node.row, node.col});
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    public void aStarSearch(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] != 0 || grid[n-1][n-1] != 0) {
            System.out.println("Path: []");
            System.out.println("Length: -1");
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) ->
            Integer.compare(a.g + heuristic(a.row,a.col,n), b.g + heuristic(b.row,b.col,n))
        );
        boolean[][] visited = new boolean[n][n];
        pq.add(new Node(0,0,1,null));
        visited[0][0] = true;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (cur.row == n-1 && cur.col == n-1) {
                List<int[]> path = reconstructPath(cur);
                System.out.println("A* Search Path:");
                for (int[] p : path) System.out.print(Arrays.toString(p) + " ");
                System.out.println("\nLength: " + cur.g);
                return;
            }

            for (int[] d : dirs) {
                int nr = cur.row + d[0];
                int nc = cur.col + d[1];
                if (0 <= nr && nr < n && 0 <= nc && nc < n && grid[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    pq.add(new Node(nr,nc,cur.g+1,cur));
                }
            }
        }
        System.out.println("Path: []");
        System.out.println("Length: -1");
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0,1,0},
            {0,0,0},
            {1,0,0}
        };
        AStarSearch astar = new AStarSearch();
        astar.aStarSearch(grid);
    }
}
