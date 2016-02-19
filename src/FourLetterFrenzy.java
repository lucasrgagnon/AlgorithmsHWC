import java.io.IOException;
import java.util.*;

/**
 * Created by lucasgagnon on 2/18/16.
 */
public class FourLetterFrenzy {

    private static DictionaryGraph dictGraph;
    private static final String PATH = "/Users/lucasgagnon/Documents/intellij/AlgorithmsHWC/dictionaries/fourletterwords.txt";

    public static void main(String[] args) {
        try {
            dictGraph = new DictionaryGraph(PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String source = readSource();
        String target = readTarget(source);
        try {
            shortestPath(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner keyboard = new Scanner(System.in);
        System.out.println("q to quit, anything else to try again:");
        while (!(keyboard.nextLine().equals("q"))) {
            source = readSource();
            target = readTarget(source);
            // check if source and target are valid
            try {
                shortestPath(source, target);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("q to quit, anything else to try again:");
        }
    }

    private static void shortestPath(String source, String target) throws IOException {
        if (source.length() != 4 && target.length() != 4) {
            throw new IOException();
        }

        Queue<String> bfsQueue = new LinkedList<String>();
        HashMap<String, String> searchTree = new HashMap<String, String>();

        bfsQueue.offer(source);
        searchLoop: while (!(bfsQueue.isEmpty())) {
            String node = bfsQueue.poll();
            for (String neighbor : dictGraph.getNeighbors(node)) {
                if (neighbor.equals(target)) {
                    searchTree.put(neighbor, node);
                    break searchLoop;
                } else if (!(searchTree.containsKey(neighbor))) {
                    searchTree.put(neighbor, node);
                    bfsQueue.offer(neighbor);
                }
            }
        }
        presentSolution(source, target, searchTree);
    }

    private static void presentSolution(String source, String target, HashMap<String, String> searchTree) {
        if (!(searchTree.containsKey(target))) {
            System.out.println("No such path exists.");
            return;
        }
        String child = target;
        String solution = target;
        while (!(child.equals(source))) {
            solution = searchTree.get(child) + " -> " + solution;
            child = searchTree.get(child);
        }
        System.out.println(solution);
    }

    private static String readSource() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter source word: ");
        String source = keyboard.nextLine().toUpperCase();
        while (!(dictGraph.contains(source))) {
            System.out.println("No such entry in Dictionary");
            System.out.println("Enter source word: ");
            source = keyboard.nextLine().toUpperCase();
        }
        return source;
    }

    private static String readTarget(String source) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter target word: ");
        String target = keyboard.nextLine().toUpperCase();
        while (!(dictGraph.contains(source)) || source.equals(target)) {
            System.out.println("This target isn't allowed.");
            System.out.println("Enter target word: ");
            target = keyboard.nextLine().toUpperCase();
        }
        return target;
    }

}
