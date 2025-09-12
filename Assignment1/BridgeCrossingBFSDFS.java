import java.util.*;
class State {
    List<String> left;
    List<String> right;
    boolean umbrellaLeft;
    int time;

    State(List<String> left, List<String> right, boolean umbrellaLeft, int time) {
        this.left = new ArrayList<>(left);
        this.right = new ArrayList<>(right);
        this.umbrellaLeft = umbrellaLeft;
        this.time = time;
    }
    public String hash() {
        List<String> l = new ArrayList<>(left);
        List<String> r = new ArrayList<>(right);
        Collections.sort(l);
        Collections.sort(r);
        return l.toString() + "|" + r.toString() + "|" + umbrellaLeft;
    }
}

public class BridgeCrossingBFSDFS {
    static Map<String, Integer> timeMap = new HashMap<>();
    static int MAX_TIME = 60;

    static {
        timeMap.put("Amogh", 5);
        timeMap.put("Ameya", 10);
        timeMap.put("Grandmother", 20);
        timeMap.put("Grandfather", 25);
    }

    static List<String> allPeople = Arrays.asList("Amogh", "Ameya", "Grandmother", "Grandfather");
    
public static void solveWithBFS() {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State initial = new State(allPeople, new ArrayList<>(), true, 0);
        queue.offer(initial);
        visited.add(initial.hash());

        while (!queue.isEmpty()) {
            State curr = queue.poll();
            if (curr.left.isEmpty() && curr.time <= MAX_TIME) {
                System.out.println("Bfs: Possible within " + curr.time + " minutes.");
                return;
            }

            if (curr.umbrellaLeft) {
                for (int i = 0; i < curr.left.size(); i++) {
                    for (int j = i + 1; j < curr.left.size(); j++) {
                        String p1 = curr.left.get(i);
                        String p2 = curr.left.get(j);
                        int t = Math.max(timeMap.get(p1), timeMap.get(p2));

                        List<String> newLeft = new ArrayList<>(curr.left);
                        newLeft.remove(p1);
                        newLeft.remove(p2);

                        List<String> newRight = new ArrayList<>(curr.right);
                        newRight.add(p1);
                        newRight.add(p2);

                        State next = new State(newLeft, newRight, false, curr.time + t);
                        if (!visited.contains(next.hash()) && next.time <= MAX_TIME) {
                            queue.offer(next);
                            visited.add(next.hash());
                        }
                    }
                }
            } else {
                for (int i = 0; i < curr.right.size(); i++) {
                    String p = curr.right.get(i);
                    int t = timeMap.get(p);

                    List<String> newLeft = new ArrayList<>(curr.left);
                    newLeft.add(p);

                    List<String> newRight = new ArrayList<>(curr.right);
                    newRight.remove(p);

                    State next = new State(newLeft, newRight, true, curr.time + t);
                    if (!visited.contains(next.hash()) && next.time <= MAX_TIME) {
                        queue.offer(next);
                        visited.add(next.hash());
                    }
                }
            }
        }

        System.out.println(" bfs : Not possible within 60 minutes.");
    }
    static int minTime = Integer.MAX_VALUE;

    public static void solveWithDFS() {
        Set<String> visited = new HashSet<>();
        dfs(new State(allPeople, new ArrayList<>(), true, 0), visited);

        if (minTime <= MAX_TIME) {
            System.out.println("dfs : Possible within " + minTime + " minutes.");
        } else {
            System.out.println(" dfs: Not possible within 60 minutes.");
        }
    }

    private static void dfs(State curr, Set<String> visited) {
        if (curr.time > MAX_TIME || visited.contains(curr.hash())) return;
        if (curr.left.isEmpty()) {
            minTime = Math.min(minTime, curr.time);
            return;
        }

        visited.add(curr.hash());

        if (curr.umbrellaLeft) {
            for (int i = 0; i < curr.left.size(); i++) {
                for (int j = i + 1; j < curr.left.size(); j++) {
                    String p1 = curr.left.get(i);
                    String p2 = curr.left.get(j);
                    int t = Math.max(timeMap.get(p1), timeMap.get(p2));

                    List<String> newLeft = new ArrayList<>(curr.left);
                    newLeft.remove(p1);
                    newLeft.remove(p2);

                    List<String> newRight = new ArrayList<>(curr.right);
                    newRight.add(p1);
                    newRight.add(p2);

                    dfs(new State(newLeft, newRight, false, curr.time + t), visited);
                }
            }
        } else {
            for (int i = 0; i < curr.right.size(); i++) {
                String p = curr.right.get(i);
                int t = timeMap.get(p);

                List<String> newLeft = new ArrayList<>(curr.left);
                newLeft.add(p);

                List<String> newRight = new ArrayList<>(curr.right);
                newRight.remove(p);

                dfs(new State(newLeft, newRight, true, curr.time + t), visited);
            }
        }

        visited.remove(curr.hash()); 
    }
}
