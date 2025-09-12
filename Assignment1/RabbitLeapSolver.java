import java.util.*;

public class RabbitLeapSolver {

    public static void main(String[] args) {
        String start = "_LLL_RRR_";
        String goal = "_RRR_LLL_";

        List<String> bfsResult = bfs(start, goal);
        if (bfsResult != null) {
            System.out.println("Bfs Solution :");
            for (String step : bfsResult) {
                System.out.println(step);
            }
        } else {
            System.out.println("No Bfs solution found.");
        }

        List<String> dfsPath = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        dfsPath.add(start);
        boolean dfsFound = dfs(start, goal, dfsPath, visited);

        if (dfsFound) {
            System.out.println("Dfs Solution (Any Valid Path):");
            for (String step : dfsPath) {
                System.out.println(step);
            }
        } else {
            System.out.println(" No dfs solution found.");
        }
    }
    public static List<String> bfs(String start, String goal) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        List<String> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.offer(initialPath);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String current = path.get(path.size() - 1);

            if (current.equals(goal)) {
                return path;
            }

            if (visited.contains(current)) continue;
            visited.add(current);

            for (String neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.offer(newPath);
                }
            }
        }
        return null;
    }

    public static boolean dfs(String current, String goal, List<String> path, Set<String> visited) {
        if (current.equals(goal)) return true;

        visited.add(current);

        for (String neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                path.add(neighbor);
                boolean found = dfs(neighbor, goal, path, visited);
                if (found) return true;
                path.remove(path.size() - 1); 
            }
        }

        return false;
    }

    public static List<String> getNeighbors(String state) {
        List<String> neighbors = new ArrayList<>();
        char[] arr = state.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'L') {
                if (i + 1 < arr.length && arr[i + 1] == '_') {
                    swapAndAdd(arr, i, i + 1, neighbors);
                }
                if (i + 2 < arr.length && (arr[i + 1] == 'L' || arr[i + 1] == 'R') && arr[i + 2] == '_') {
                    swapAndAdd(arr, i, i + 2, neighbors);
                }
            } else if (arr[i] == 'R') {
                if (i - 1 >= 0 && arr[i - 1] == '_') {
                    swapAndAdd(arr, i, i - 1, neighbors);
                }
                if (i - 2 >= 0 && (arr[i - 1] == 'L' || arr[i - 1] == 'R') && arr[i - 2] == '_') {
                    swapAndAdd(arr, i, i - 2, neighbors);
                }
            }
        }

        return neighbors;
    }

    public static void swapAndAdd(char[] arr, int i, int j, List<String> list) {
        char[] newArr = arr.clone();
        char temp = newArr[i];
        newArr[i] = newArr[j];
        newArr[j] = temp;
        list.add(new String(newArr));
    }
}
