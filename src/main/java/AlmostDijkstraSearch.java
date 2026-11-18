import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.HashMap;


public class AlmostDijkstraSearch {
    public final PriorityQueue<DnaState> queue;
    public final HashMap<String, DnaState> visited;
    private String goalString;
    private int numExplored;

    public AlmostDijkstraSearch(String goalString) {
        this.queue = new PriorityQueue<>();
        this.visited = new HashMap<>();
        this.goalString = goalString;
    }

    public ArrayList<String> findShortestPath(String start) {
        numExplored = 0;
        queue.clear();
        visited.clear();

        if (start.length() != goalString.length()) {
            return null;
        }

        DnaState current = null;
        addNewState(start, null);

        while (!queue.isEmpty()) {
            current = iterateSearch();

            if (current == null) {
                return null;
            }
            else if (isGoalReached(current)) {
                break;
            }
        }

        if (current == null || !current.dnaString.equals(goalString)) {
            return null;
        }

        System.out.println("Explored " + numExplored + " possible strands");

        return getPath(current);
    }

    public DnaState iterateSearch() {
        DnaState current = queue.poll();

        if (current == null) {
            return null;
        }

        if (visited.get(current.dnaString) == current && !isGoalReached(current)) {
            // Check if current state isnt a previous unoptimal one
            numExplored ++;
            addAdjacents(current);
        }

        return current;
    }

    private void addAdjacents(DnaState state) {
        ArrayList<String> possibleStrings = state.getPossibleAdjacents();

        for (String possibleString:possibleStrings) {
            DnaState existing = visited.get(possibleString);

            if (existing == null || state.steps + 1 < existing.steps) {
                addNewState(possibleString, state);
            }
        }
    }

    private void addNewState(String dnaString, DnaState previous) {
        int numDifferentChars = DnaState.getNumDifferentChars(goalString, dnaString);
        DnaState newState = new DnaState(dnaString, previous, numDifferentChars);

        visited.put(dnaString, newState);

        queue.offer(newState);
    }

    public void changeGoal(String goalString) {
        this.goalString = goalString;
    }


    private ArrayList<String> getPath(DnaState state) {
        ArrayList<String> path = new ArrayList<>();

        while (state != null) {
            path.add(state.dnaString);
            state = state.previous;
        }
        Collections.reverse(path);

        return path;
    }

    private boolean isGoalReached(DnaState current) {
        return current.dnaString.equals(goalString);
    }

}

