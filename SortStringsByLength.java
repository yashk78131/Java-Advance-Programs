import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortStringsByLength {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("apple", "banana", "kiwi", "grape"));
        
        System.out.println("Original list: " + list);
        
        // Sort the list 
        list.sort((s1, s2) -> Integer.compare(s1.length(), s2.length()));
        
        System.out.println("Sorted by length (ascending): " + list);
    }
}
