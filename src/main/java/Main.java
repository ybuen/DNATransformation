import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        DnaState.initializeStringTrie();
        AlmostDijkstraSearch newSearch = new AlmostDijkstraSearch("GGTGCCG");
        ArrayList<String> path = newSearch.findShortestPath("CATAGTA");

        if (path == null) {
            System.out.println("Impossible to reach goal strand.");
        } else {

            int pathSize = path.size();
            System.out.println("Possible in " + (pathSize - 1) + " steps:");

            for (String strand:path) {
                System.out.println(strand);
            }
        }

    }
}