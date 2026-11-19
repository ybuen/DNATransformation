import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AlmostDijkstraSearch newSearch = new AlmostDijkstraSearch("DNAsequences.txt");
        ArrayList<String> path = newSearch.findShortestPath("AAATT", "GAAAC");
        printPathResults(path);

    }

    private static void printPathResults(ArrayList<String> path) {
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