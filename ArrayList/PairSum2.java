import java.util.*;
public class PairSum2{
    public static void main(String args[]){

        ArrayList<Integer> list = new ArrayList<>();

        list.add(11);
        list.add(15);
        list.add(6);
        list.add(8);
        list.add(9);
        list.add(10);

        int target = 16;

        pairs(list, target);
    }

    public static void pairs(ArrayList<Integer> list, int target){

        int n = list.size();

        // initializing bp for storing pivot point
        int bp = -1;

        // finding left and right pointers

        for(int i=0; i<list.size(); i++){
            if(list.get(i) > list.get(i+1)){

                // pivot found
                bp = i;
                break;
            }
        }

        int left = bp+1;
        int right = bp;
        while(left != right){

            // case 1
            if(list.get(left) + list.get(right) == target){
                System.out.println(list.get(left) + " + " + list.get(right) + " = " + target);
                return;
            }

            // case2
            if(list.get(left) + list.get(right) < target){
                left = (left+1) % n;
            }else{
                // case3
                right = (n+right-1)%n;
            }


        }
        System.out.println("No");
    }
}