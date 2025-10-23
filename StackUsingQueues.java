// Implementing a Stack Using Queues
// Create a stack using two queues.

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueues {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public StackUsingQueues() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int x) {
        queue2.offer(x);
        
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }

        Queue<Integer> tempQueue = queue1;
        queue1 = queue2;
        queue2 = tempQueue;
    }

    public int pop() {
      return queue1.poll();
   }

   public int top() {
      return queue1.peek();
   }

   public boolean empty() {
      return queue1.isEmpty();
   }

   public static void main(String[] args) {
       StackUsingQueues stack = new StackUsingQueues();
       stack.push(1);
       stack.push(2);
       System.out.println(stack.pop()); // Output: 2
   }
}
