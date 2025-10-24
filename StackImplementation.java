import java.util.Scanner;

class Stack {
    private int[] arr;
    private int top;
    private int capacity;

    public Stack(int size) {
        arr = new int[size];
        capacity = size;
        top = -1;
    }

    public void push(int x) {
        if (top == capacity - 1) {
            System.out.println("Stack Overflow");
            return;
        }
        arr[++top] = x;
    }

    public int pop() {
        if (top == -1) {
            System.out.println("Stack Underflow");
            return -1;
        }
        return arr[top--];
    }

    public int peek() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return -1;
        }
        return arr[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}

public class StackDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter stack size: ");
        int size = sc.nextInt();
        Stack stack = new Stack(size);

        System.out.println("Push elements (-1 to stop):");
        while (true) {
            int val = sc.nextInt();
            if (val == -1) break;
            stack.push(val);
        }

        System.out.println("Stack elements popped:");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }

        sc.close();
    }
}
