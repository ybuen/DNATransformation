import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;

public class AlmostDijkstraSearch {
    final static private char[] POSSIBLE_CHARS = {'A', 'C', 'T', 'G'};

    final private StringTrie TRIE = new StringTrie();
    public final PriorityQueue<DnaState> queue;
    public final HashMap<String, DnaState> visited;
    private String goalString;
    private int numExplored;

    public AlmostDijkstraSearch() {
        this.queue = new PriorityQueue<>();
        this.visited = new HashMap<>();
        TRIE.addStringsFromFile("DNAsequences.txt");
    }

    public ArrayList<String> findShortestPath(String start, String goal) { // iterate until reaches goal or queue is empty
        resetSearchParameters(goal);

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
            else if (isGoalString(current.dnaString)) {
                break;
            }
        }
        if (current == null || !current.dnaString.equals(goalString)) {
            return null;
        }

        System.out.println("Explored " + numExplored + " possible strands");
        return getPath(current);
    }

    public DnaState iterateSearch() { // Single iteration of the algorithm
        DnaState current = queue.poll();
        if (current == null) {
            return null;
        }
        if (visited.get(current.dnaString) == current && !isGoalString(current.dnaString)) {
            // Check first if current state isn't outdated or if it isn't already the goal string
            numExplored ++;
            addAdjacents(current);
        }
        return current;
    }

    // ADDING ADJACENTS METHOD & HELPERS  ---------------------------------------------------------------
    private void addAdjacents(DnaState state) {
        String dnaString = state.dnaString;

        for (int i = 0; i < state.length; i++) {
            for (int ci = 0; ci < 4; ci ++) {
                if (dnaString.charAt(i) == POSSIBLE_CHARS[ci]) {
                    continue;
                }
                tryNewDnaString(state, i, POSSIBLE_CHARS[ci]);
            }
        }
    }

    private void tryNewDnaString(DnaState state, int i, char c) { // Checks and tries to add new dna string
        String dnaString = state.dnaString;
        String modified = dnaString.substring(0, i) + c + dnaString.substring(i + 1, state.length);

        if (!TRIE.search(modified) && !isGoalString(modified)) {
            return;
        }
        DnaState existing = visited.get(modified);

        if (existing == null || existing.steps > state.steps + 1) {
            addNewState(modified, state);
        }
    }

    // OTHER HELPERS ---------------------------------------------------------------
    private void addNewState(String dnaString, DnaState previous) {
        int numDifferentChars = DnaState.getNumDifferentChars(goalString, dnaString);
        DnaState newState = new DnaState(dnaString, previous, numDifferentChars);

        visited.put(dnaString, newState);
        queue.offer(newState);
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

    // Getters and Setters -----------------------------------------------------------
    private boolean isGoalString(String currentString) {
        return currentString.equals(goalString);
    }

    private void resetSearchParameters(String goalString) {
        this.goalString = goalString;
        numExplored = 0;
        queue.clear();
        visited.clear();
    }
}

