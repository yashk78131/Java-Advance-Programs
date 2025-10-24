import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RunMe {
    String value() default "Executing annotated method...";
}

class Task {
    @RunMe("Running important task...")
    public void doTask() {
        System.out.println("Task completed!");
    }
}

public class AnnotationProcessorDemo {
    public static void main(String[] args) throws Exception {
        Task obj = new Task();
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RunMe.class)) {
                RunMe runMe = method.getAnnotation(RunMe.class);
                System.out.println(runMe.value());
                method.invoke(obj);
            }
        }
    }
}
