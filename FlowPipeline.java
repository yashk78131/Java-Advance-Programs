// File: FlowPipeline.java
import java.util.concurrent.*;
import java.util.concurrent.Flow.*;

public class FlowPipeline {
    static class IntPublisher implements Publisher<Integer> {
        private final int max;
        private final ExecutorService exec = Executors.newSingleThreadExecutor();
        public IntPublisher(int max) { this.max = max; }
        public void subscribe(Subscriber<? super Integer> s) {
            s.onSubscribe(new Subscription() {
                volatile boolean cancelled = false;
                int current = 1;
                public void request(long n) {
                    exec.submit(() -> {
                        long sent = 0;
                        try {
                            while (!cancelled && sent < n && current <= max) {
                                s.onNext(current++);
                                sent++;
                            }
                            if (current > max && !cancelled) { s.onComplete(); exec.shutdown(); }
                        } catch (Throwable t) { s.onError(t); }
                    });
                }
                public void cancel() { cancelled = true; exec.shutdown(); }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntPublisher pub = new IntPublisher(10);
        pub.subscribe(new Subscriber<>() {
            private Subscription sub;
            public void onSubscribe(Subscription s) { sub = s; s.request(5); }
            public void onNext(Integer item) {
                System.out.println("Got: " + item);
                if (item % 2 == 0) sub.request(1); // dynamic request
            }
            public void onError(Throwable t) { t.printStackTrace(); }
            public void onComplete() { System.out.println("Done"); }
        });
        Thread.sleep(2000);
    }
}
