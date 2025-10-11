import java.util.ArrayList;
import java.util.List;

class GenericBox<T> {
    private T value;

    public GenericBox(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

class BoundedBox<T extends Number> {
    private T value;

    public BoundedBox(T value) {
        this.value = value;
    }

    public double doubleValue() {
        return value.doubleValue();
    }
}

public class GenericsExample {

    public static <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        intList.add(1);
        intList.add(2);
        printList(intList);

        List<String> strList = new ArrayList<>();
        strList.add("Hello");
        strList.add("World");
        printList(strList);

        GenericBox<String> stringBox = new GenericBox<>("Hello Generics");
        System.out.println("Box value: " + stringBox.getValue());

        BoundedBox<Integer> intBounded = new BoundedBox<>(10);
        System.out.println("Double value: " + intBounded.doubleValue());

        List<? extends Number> numList = new ArrayList<Integer>();
        List<? super Integer> intSuperList = new ArrayList<Number>();

        System.out.println("Max of 3 and 5: " + max(3, 5));
        System.out.println("Max of 'a' and 'b': " + max("a", "b"));
    }
}
