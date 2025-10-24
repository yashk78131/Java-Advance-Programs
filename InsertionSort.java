import java.util.Scanner;

public class InsertionSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Enter the elements for array(unsorted elements) : ");
        int n = sc.nextInt();

        int array[] = new int[n];
        System.out.println(" Enter the "+ n +" elements : ");
        for(int i = 0; i < n; i++){
            array[i] = sc.nextInt();
        }

        array = insertionSortingAlgorithm(array, n);
        display(array);
    }

    private static int[] insertionSortingAlgorithm(int[] array, int n) {
        int x , j;

        for(int i = 1; i < n; i++){
            j = i-1;
            x = array[i];  //the element to be placed.

            //For each element ( x ), it compares with elements in the sorted left part and shifts them right if they are greater than x.

            while(j >= 0 && array[j] > x){
                array[j+1]= array[j];  //// shifting element right if it is greater than x, (ie swapping ) 
                j--; //decrementing the j

            }
            array[j+1] = x;
        }
        return array;
    }

    private static void display(int[] array) {
        System.out.println(" ");
        for(int i : array){
            System.out.print(""+i+"\n");
        }
        
    }
    
}
/*
 * 
    TC: 
    Worst-case O(n2)^2, when the array is reverse sorted causing maximum shifts.
    Best-case O(n)^2 occurs if the array is already sorted (only one comparison each).
    Average-case is also O(n2)^2, making it inefficient for large datasets.

 * 
 */