public class Main {
    public static void main(String[] args) throws Exception {
        DnaState.initializeStringTrie();
        RipOffDijkstraDnaSearch.findShortestPath("GTTATA", "CGATCA");
    }
}