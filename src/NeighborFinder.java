import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by lucasgagnon on 2/18/16.
 */
public class NeighborFinder implements Iterator<String> {

    private String word;
    private LinkedList<String> neighbors;
    private int len;
    private Iterator<String> itr;


    public NeighborFinder(String word, Set set) {
        this.word = word;
        this.len = word.length();
        this.neighbors = new LinkedList<String>();
        buildNeighbors(set);
        this.itr = neighbors.iterator();
    }

    private void buildNeighbors(Set wordSet) {
        if (word == null) {
            return;
        }
        for (int i = 0; i < len; i++) {
            char letter = word.charAt(i);
            if (i == 0) {
                String stub = word.substring(1, len);
                for (char c = 65; c < 91; c ++) {
                    if (c != letter && wordSet.contains(c + stub)){
                        neighbors.add(c + stub);
                    }
                }
            } else if (i == len - 1){
                String stub = word.substring(0, len-1);
                for (char c = 65; c < 91; c ++) {
                    if (c != letter && wordSet.contains(stub + c)){
                        neighbors.add(stub + c);
                    }
                }
            } else {
                String stub1 = word.substring(0, i);
                String stub2 = word.substring(i+1, len);
                for (char c = 65; c < 91; c ++) {
                    if (c != letter && wordSet.contains(stub1 + c + stub2)){
                        neighbors.add(stub1 + c + stub2);
                    }
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public String next() {
        return itr.next();
    }
}
