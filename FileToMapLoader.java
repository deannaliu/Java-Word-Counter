import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Deanna Liu
 */
public class FileToMapLoader{
    public static final String FILEPATH = "C:\\Users\\cbex9\\Desktop\\Mei Qi Deanna Liu - Coding Portfolio\\Word Counter\\sample.csv";
    enum Order {
        NAME, PHONE, ORIGINAL
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILEPATH), StandardCharsets.UTF_8);
        for(int i = 0; i < lines.size(); i++)
            if(lines.get(i).equals(","))
                lines.remove(i);
        if(lines.size() <= 1)
            System.out.println(lines.get(0));
        else {
            Scanner myScanner = new Scanner(System.in);
            System.out.println("Name, Phone, or Original?");
            String input = myScanner.nextLine();
            List<String> names = new ArrayList<>();
            List<String> phones = new ArrayList<>();
            //System.out.println(lines.size());
            for (int i = 0; i < lines.size(); i++) {
                names.add(lines.get(i).split(",")[0]);
                phones.add(lines.get(i).split(",")[1]);
            }
            //System.out.println(names.toString());
            for (int i = 0; i < phones.size(); i++)
                phones.set(i, phones.get(i).replaceAll("\\D+", ""));
            //System.out.println(phones.toString());
            while (!(input.equalsIgnoreCase("name") || input.equalsIgnoreCase("phone") || input.equalsIgnoreCase("original"))) {
                System.out.println("Please input a valid response.");
                input = myScanner.nextLine();
            }
            if (input.equalsIgnoreCase("name"))
                displayOutput(lines, names, phones, Order.NAME);
            if (input.equalsIgnoreCase("phone"))
                displayOutput(lines, names, phones, Order.PHONE);
            if (input.equalsIgnoreCase("original"))
                displayOutput(lines, names, phones, Order.ORIGINAL);
        }
    }

    private static void displayOutput(List<String> input, List<String> names, List<String> phones, Order order){
        if(order.equals(Order.ORIGINAL)) {
            LinkedHashMap originalMap = new LinkedHashMap();
            for(int i = 0; i < input.size(); i++) {
                originalMap.put(i, input.get(i));
            }
            for(int i = 0; i < originalMap.size(); i++)
                System.out.println(originalMap.get(i));
        }
        else {
            if(order.equals(Order.PHONE)){
                LinkedHashMap<String, String> temp = new LinkedHashMap<>();
                for(int i = 0; i < input.size(); i++)
                    temp.put(names.get(i), phones.get(i));
                Comparator<String> comparator = new ValueComparator<String, String>(temp);
                TreeMap<String, String> phoneMap = new TreeMap<>(comparator);
                phoneMap.putAll(temp);
                TreeMap<String, String> holder = new TreeMap<>();
                for(int i = 0; i < input.size(); i++)
                    holder.put(names.get(i), input.get(i).split(",")[1]);
                for (String key : phoneMap.keySet()) {
                    System.out.println( key + " " + holder.get(key));
                }

                /*   for(int i = 0; i < input.size(); i++)
                    phoneMap.put(phones.get(i), input.get(i));
                Object[] sortedKeys = phoneMap.values().toArray();
                for(int i = 0; i < sortedKeys.length; i++)
                    System.out.println(sortedKeys[i]);
                */
            }
            else {
                if(order.equals(Order.NAME)) {
                    TreeMap<String, String> nameMap = new TreeMap<>();
                    for(int i = 0; i < input.size(); i++)
                        nameMap.put(names.get(i), input.get(i));
                    Object[] sortedKeys = nameMap.values().toArray();
                    for(int i = 0; i < sortedKeys.length; i++)
                        System.out.println(sortedKeys[i]);
                }
            }
        }
    }
}

class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {
    Map<K, V> map = new LinkedHashMap<>();

    public ValueComparator(Map map){
        this.map = map;
    }

    public int compare(K o1, K o2) {
        return map.get(o1).compareTo(map.get(o2));
    }
}

