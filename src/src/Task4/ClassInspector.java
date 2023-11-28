package Task4;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassInspector {

    public static void main(String[] args) {
        String className = "Task4.ExampleClass";

        try {
            Class<?> clazz = Class.forName(className);

            printClassDetails(clazz);
        } catch (ClassNotFoundException e) {
            System.err.println("Клас не знайдено: " + className);
        }
    }

    private static void printClassDetails(Class<?> clazz) {
        System.out.println("Клас: " + clazz.getSimpleName());
        System.out.println("Модифікатор досутупу до класу: " + Modifier.toString(clazz.getModifiers()));

        System.out.println("\nПоля:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName());
        }

        // В умові непотрібно, але нехай буде, інакше у чому сенс?)
        System.out.println("\nКонструктори:");
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.print(Modifier.toString(constructor.getModifiers()) + " " + clazz.getSimpleName() + "(");
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    System.out.print(", ");
                }
                System.out.print(parameterTypes[i].getSimpleName() + " param" + (i + 1));
            }
            System.out.println(")");
        }

        System.out.println("\nМетоди:");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + getParameters(method));
        }
    }

    private static String getParameters(Method method) {
        StringBuilder parameters = new StringBuilder("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                parameters.append(", ");
            }
            parameters.append(parameterTypes[i].getSimpleName()).append(" param").append(i + 1);
        }
        parameters.append(")");
        return parameters.toString();
    }
}

