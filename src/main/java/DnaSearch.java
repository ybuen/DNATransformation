import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;

public class DnaSearch {



    public static void findShortestPath(String start, String goal) {
        Search frontSearch = new Search(goal);
        Search backSearch = new Search(start);

        frontSearch.setFirst(start);
        backSearch.setFirst(goal);

        DnaState current = new DnaState("ABCD", null, 100 ); // PLACEHOLDER
        DnaState otherEnd;

        while (current.numDifferentChars > 0) {
            current = frontSearch.iterateSearch();
            if (backSearch.isVisited(current.dnaString)) {
                otherEnd = backSearch.visited.get(current.dnaString);
                break;
            }

            current = backSearch.iterateSearch();
            if (frontSearch.isVisited(current.dnaString)) {
                otherEnd = frontSearch.visited.get(current.dnaString);
                break;
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

        public void setFirst(String firstString) {
            DnaState newState = addNewState(firstString, null);
            visited.put(firstString, newState);
        }

        public DnaState iterateSearch() {
            DnaState current = queue.poll();
            visited.put(current.dnaString, current);
            addAdjacents(current);
            return current;
        }

        private void addAdjacents(DnaState state) {
            ArrayList<String> possibleStrings = state.getPossibleAdjacents();

            for (String possibleString:possibleStrings) {
                if (!isVisited(possibleString)) {
                    addNewState(possibleString, state);
                }
            }
        }

        public DnaState addNewState(String dnaString, DnaState previous) {
            int numDifferentChars = DnaState.getNumDifferentChars(goalString, dnaString);
            DnaState newState = new DnaState(dnaString, previous, numDifferentChars);
            queue.offer(newState);
            return newState;
        }

        public boolean isVisited(String dnaString) {
            return visited.containsKey(dnaString);
        }

    }



}
