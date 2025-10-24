import java.util.*;
public class MultiDimensionalArr2{
    public static void main(String args[]){

        // creating a mainArrayList
        ArrayList<ArrayList<Integer>> mainArrayList = new ArrayList<>();
        
        // creating 3 subArrayLists
        ArrayList<Integer> subArrayList1 = new ArrayList<>();
        ArrayList<Integer> subArrayList2 = new ArrayList<>();
        ArrayList<Integer> subArrayList3 = new ArrayList<>();

        for(int i=1; i<=5; i++){
            subArrayList1.add(1*i);
            subArrayList2.add(2*i);
            subArrayList3.add(3*i);
        }

        // adding the subArrayList in the mainList
        mainArrayList.add(subArrayList1);
        mainArrayList.add(subArrayList2);
        mainArrayList.add(subArrayList3);

        // deleting last 2 elements from subArrayList2
        subArrayList2.remove(2);
        subArrayList2.remove(3);

        System.out.println(mainArrayList);

        for(int i=0; i<mainArrayList.size(); i++){
            ArrayList<Integer> currList = mainArrayList.get(i);

            for(int j=0; j<currList.size(); j++){
                System.out.print(currList.get(j) + " ");
            }
            System.out.println();
        }

    }
}