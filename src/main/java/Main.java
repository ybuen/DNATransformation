public class Main {
    public static void main(String[] args) throws Exception {
        DnaState.initializeStringTrie();
        RipoffDijkstraDnaSearch.findShortestPath("GTTATA", "CGATCA");
    }
}