import java.util.*;
public class MultiDimensionalArrayList{
    public static void main(String args[]){

        // creating a mainList (ArrayList) that contains multiple ArrayLists
        ArrayList<ArrayList<Integer>> mainList = new ArrayList<>();

        // creating 1st subArraylist 
        ArrayList<Integer> list1 = new ArrayList<>();

        // adding elements in 1st subArrayList
        list1.add(1);
        list1.add(2);

        // adding the subArrayLists in the mainArrayList
        mainList.add(list1);

        // creating 2nd subArrayList
        ArrayList<Integer> list2 = new ArrayList<>();

        // adding elements in 2nd SubArrayList
        list2.add(3);
        list2.add(4);

        // adding the subArrayLists in the mainArrayList
        mainList.add(list2);

        for(int i=0; i<mainList.size(); i++){
            ArrayList<Integer> currList = mainList.get(i);

            for(int j=0; j<currList.size(); j++){
                System.out.print(currList.get(j) + " ");
            }
            System.out.println();
        }

        System.out.println(mainList);
    }
}