import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

public class DnaSearch {

    public static void findShortestPath(String start, String goal) {
        Search frontSearch = new Search(goal);
        frontSearch.addNewState(start, null);

        DnaState current = null; // PLACEHOLDER

        while (!frontSearch.queue.isEmpty()) {
            current = frontSearch.iterateSearch();
            if (current.dnaString.equals(goal)) {
                break;
            }
        }

        if (current == null || !current.dnaString.equals(goal)) {
            System.out.println("Impossible to reach new strand");
        } else {
            while (current != null) {
                System.out.print(current.dnaString + " ");
                current = current.previous;
                System.out.println();
            }
        }

    }

    private static class Search {
        public final PriorityQueue<DnaState> queue;
        public final HashMap<String, DnaState> visited;
        public final String goalString;

        public Search(String goalString) {
            this.queue = new PriorityQueue<>();
            this.visited = new HashMap<>();
            this.goalString = goalString;
        }

        public DnaState iterateSearch() {
            DnaState current = queue.poll();
            if (visited.get(current.dnaString) == current) {
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

        public void addNewState(String dnaString, DnaState previous) {
            int numDifferentChars = DnaState.getNumDifferentChars(goalString, dnaString);
            DnaState newState = new DnaState(dnaString, previous, numDifferentChars);

            visited.put(dnaString, newState);

            queue.offer(newState);
        }

    }



}
