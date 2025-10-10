
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaExample {

    public static void main(String[] args) {
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 5 even? " + isEven.test(5));

        Runnable task = () -> System.out.println("Hello from lambda!");
        task.run();

        Consumer<String> printUpper = str -> System.out.println(str.toUpperCase());
        printUpper.accept("hello");

        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Random: " + randomSupplier.get());

        Function<Integer, String> intToString = num -> "Number: " + num;
        System.out.println(intToString.apply(10));

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        names.forEach(name -> System.out.println("Name: " + name));

        names.stream().filter(name -> name.length() > 3).forEach(printUpper);
    }
}

