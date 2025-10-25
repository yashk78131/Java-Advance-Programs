import java.util.*;
public class PairSum{
    public static void main(String args[]){

        ArrayList<Integer> list = new ArrayList<>();

        // adding elements in height ArrayList
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        // target element
        int target = 5;

        System.out.println("Pair sum1 problem using BruteForce Approach...");
        pairsBruteForce(list, target);
        System.out.println("Pair sum1 problem using 2Pointer Approach...");
        pairs2Pointer(list, target);
    }

    public static void pairsBruteForce(ArrayList<Integer> list, int target){

        for(int i = 0; i<list.size(); i++){
            for(int j = 1+i; j < list.size(); j++){
                int sum = list.get(i) + list.get(j);

                if(sum == target){
                    System.out.println(list.get(i) + " + " + list.get(j)  + " = " + target);
                }
            }
        }
    }

    public static void pairs2Pointer(ArrayList<Integer> list, int target){

        int i = 0;
        int j = list.size()-1;
        while(i < j){
            int sum = list.get(i) + list.get(j);
            if(sum == target){
                System.out.println(list.get(i) + " + " + list.get(j) + " = " + target);
            }

            if (sum == target) {
                
                i++;
                j--; // move both
            } else if (sum < target) {
                i++; // need bigger sum
            } else {
                j--; // need smaller sum
            }

           
        }
    }
}