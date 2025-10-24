import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

public class DynamicPluginLoader {
    public static void main(String[] args) {
        try {
            File pluginDir = new File("plugins/");
            for (File file : pluginDir.listFiles((d, name) -> name.endsWith(".class"))) {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{pluginDir.toURI().toURL()});
                String className = file.getName().replace(".class", "");
                Class<?> pluginClass = classLoader.loadClass(className);
                Object pluginInstance = pluginClass.getDeclaredConstructor().newInstance();
                Method executeMethod = pluginClass.getMethod("execute");
                executeMethod.invoke(pluginInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
