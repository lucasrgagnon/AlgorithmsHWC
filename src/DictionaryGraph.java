import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by lucasgagnon on 2/18/16.
 */
public class DictionaryGraph {


    private Path filePath;
    private BufferedReader buffReader;
    private HashMap<String, List<String>> adjList;

    public DictionaryGraph(String path) throws IOException {
        setDict(path);
        buildGraph();
    }

    public void setDict(String path) throws IOException {
        this.filePath = Paths.get(path);
        this.buffReader = Files.newBufferedReader(filePath);
        this.adjList = new HashMap<String, List<String>>();
        while (buffReader.ready()) {
            String line = buffReader.readLine();
            if (line != null) {
                adjList.put(line.toUpperCase(), new ArrayList<String>());
            }
        }
    }

    private void buildGraph() {
        Iterator<String> itr = adjList.keySet().iterator();
        while (itr.hasNext()) {
            String word = itr.next();
            NeighborFinder nf = new NeighborFinder(word, adjList.keySet());
            while (nf.hasNext()) {
                adjList.get(word).add(nf.next());
            }
        }
    }

    public List<String> getNeighbors(String word) {
        if (adjList.containsKey(word)) {
            return adjList.get(word);
        } else {
            return new LinkedList<String>();
        }
    }

    public boolean contains(String word) {
        return adjList.containsKey(word);
    }

}
