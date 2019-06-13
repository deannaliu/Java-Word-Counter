import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Deanna Liu
 */
public class WordCounter{
    public static final String FILEPATH = "C:\\Users\\cbex9\\Desktop\\Mei Qi Deanna Liu - Coding Portfolio\\Word Counter\\sentences.txt";
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(new FileReader(FILEPATH));
        Map<String, Integer> wordCounts = new TreeMap<>();
        while (input.hasNext()) {
            String next = input.next().toLowerCase().replaceAll("[^a-zA-z0-9'\\-]", "");
            //System.out.print(next);
            if (!wordCounts.containsKey(next)) {
                wordCounts.put(next, 1);
            } else {
                wordCounts.put(next, wordCounts.get(next) + 1);
            }
        }
      //  System.out.println(wordCounts);
        List<Integer> count = new ArrayList<>();
        Set<String> words = wordCounts.keySet();
        List<String> wordList = new ArrayList<>();
        for(String key : words){
            count.add(wordCounts.get(key));
            wordList.add(key);
        }
       // System.out.println(wordList);

        List list = new ArrayList(wordCounts.entrySet());
        Collections.sort(list, new FreqComparator<String, Integer>(wordCounts));
        Map<String, Integer> result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put((String)entry.getKey(), (Integer)entry.getValue());
        }
      //  System.out.println(result);
        for (String key : result.keySet()) {
            if(!key.isEmpty())
                System.out.println(key + " " + result.get(key));
        }
    }
}

class FreqComparator<K, V extends Comparable<V>> implements Comparator<K> {
    Map<K, V> map = new LinkedHashMap<>();

    public FreqComparator(Map map){
        this.map = map;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
    }

}




