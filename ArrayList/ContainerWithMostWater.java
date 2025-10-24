import java.util.*;

public class ContainerWithMostWater{
    public static void main(String args[]){

        ArrayList<Integer> height = new ArrayList<>();

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
        for(int i=0; i<height.size(); i++){
            for(int j=i+1; j<height.size(); j++){
                int h = Math.min(height.get(i), height.get(j));
                int w = j-i;
                int area = h*w;
                maxArea = Math.max(area, maxArea);
            }
        }
        return maxArea;
    }
}