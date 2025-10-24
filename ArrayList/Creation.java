import java.util.ArrayList;

// ArrayList is a class
import java.util.*;

public class Creation{
    public static void main(String args[]){
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<Boolean> list3 = new ArrayList<>();

        //Operations
        // 1. Add Element -> O(1)

        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        list1.add(1, 9);    // O(n)

        System.out.println(list1);

        // 2. Get Element -> O(1)
        // searches the value at particular idx
        int element = list1.get(2);
        System.out.println (element);

        // 3. Remove Element -> O(n)
        list1.remove(2);
        System.out.println(list1);


        // 4. Set Element at Index -> O(n)
        // updates the element at the particulat idx
        list1.set(2, 10);
        System.out.println(list1);


        // 5. Contains Element -> O(n)
        // checks if the element exists or not -> returns true or false
        System.out.println(list1.contains(1));
        System.out.println(list1.contains(11));

    }
}

