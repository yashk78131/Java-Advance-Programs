import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionExample {

    public static void main(String[] args) {
        try {
            Class<?> clazz = String.class;
            Method[] methods = clazz.getMethods();
            System.out.println("Methods in String class:");
            for (Method method : methods) {
                System.out.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName());
            }

            String str = "hello";
            Method toUpper = clazz.getMethod("toUpperCase");
            String result = (String) toUpper.invoke(str);
            System.out.println("Uppercase: " + result);

            Field[] fields = clazz.getDeclaredFields();
            System.out.println("Fields in String class:");
            for (Field field : fields) {
                System.out.println(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
            }

            Constructor<?>[] constructors = clazz.getConstructors();
            System.out.println("Constructors in String class:");
            for (Constructor<?> constructor : constructors) {
                System.out.println(constructor.toString());
            }

            Class<?> arrayClass = int[].class;
            System.out.println("Is array: " + arrayClass.isArray());
            System.out.println("Component type: " + arrayClass.getComponentType());

            Class<?> superClass = clazz.getSuperclass();
            System.out.println("Superclass: " + superClass.getSimpleName());

            Class<?>[] interfaces = clazz.getInterfaces();
            System.out.println("Interfaces:");
            for (Class<?> iface : interfaces) {
                System.out.println(iface.getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
