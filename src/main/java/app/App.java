package app;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class App {

    private static void m1() {
        Class<?> clazz = Product.class;
        String className = clazz.getName();
        System.out.println("Classname is : " + className);
    }

    private static void m2(){
        Class<?> clazz = Product.class;
        Method[] methods = clazz.getDeclaredMethods();
        Arrays.stream(methods).forEach(m -> System.out.println("Method name: " + m.getName()));
    }

    //Acceder a un método en tiempo de ejecución
    private static void m3() throws Exception{
        Class<?> clazz = Product.class;
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("getName", int.class, String.class);
        Object result = method.invoke(instance, 10, "Hello");
        System.out.println("Result: " + result);
    }

    //Acceder y modificar a un método en tiempo de ejecución
    private static void m4() throws Exception{
        Class<?> clazz = Product.class;
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        Object actualValue = field.get(instance);
        System.out.println("Actual value: " + actualValue);
        field.set(instance, "mitocode");
        Object newValue = field.get(instance);
        System.out.println("New value: " + newValue);
    }

    private static void m5() throws Exception{
        String packageName = "com.mitocode.model";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace('.', '/');

        try(Stream<Path> paths = Files.walk(Paths.get(classLoader.getResource(packagePath).toURI()))){
            paths.filter(Files::isRegularFile)
                    .forEach(path -> {
                        //quitando el .class
                        String className = packageName + "." + path.getFileName().toString().replace(".class", "");

                        try{
                            Class<?> clazz = Class.forName(className);
                            System.out.println("Class: " + clazz.getName());
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                    });
        }
    }

    private static void m6(){
        Class<?> clazz = ProductoDAO.class;
        Class<?>[] interfaces = clazz.getInterfaces();
        Arrays.stream(interfaces).forEach(inter -> System.out.println("Interface: " + inter));
    }

    public static void main(String[] args) throws Exception {
        App.m6();
    }
}
