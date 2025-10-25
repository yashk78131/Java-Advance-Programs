import java.util.*;
public class ContainerWithMostWater2Pointer{
    public static void main(String args[]){

        ArrayList<Integer> height = new ArrayList<>();

        // adding heights in height ArrayList
        height.add(1);
        height.add(8);
        height.add(6);
        height.add(2);
        height.add(5);
        height.add(4);
        height.add(8);
        height.add(3);
        height.add(7);

        int maxArea = Integer.MIN_VALUE;
        System.out.println(mostWater(height, maxArea));
    }

    public static int mostWater(ArrayList<Integer> height, int maxArea){
        int i = 0;
        int j = height.size()-1;

        while(i < j){
            int h = Math.min(height.get(i), height.get(j));
            int w = j-i;
            int area = h*w;
            maxArea = Math.max(area, maxArea); 

            if(height.get(i) < height.get(j)){
                i++;
            }else{
                j--;
            }
        }

        return maxArea;
    }
}