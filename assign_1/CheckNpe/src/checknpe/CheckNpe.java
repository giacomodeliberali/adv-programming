package checknpe;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author giacomodeliberali
 */
public class CheckNpe {

    /**
     * The current checking class
     */
    private Class currentClass;

    /**
     * Crates a new NPE check for a class
     *
     * @param currentClass The class to check
     */
    public CheckNpe(Class currentClass) {
        this.currentClass = currentClass;
    }

    /**
     * Crates a new NPE check for a class
     *
     * @param className The class path to check
     */
    public CheckNpe(String className) {
        try {
            currentClass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Check every constructor and every method for NPE sensitivity
     *
     * @return The check result
     */
    public CheckNpeResult check() {
        if (currentClass == null) {
            return null;
        }

        ArrayList<Descriptor> constructorsDescriptors = new ArrayList<>();
        ArrayList<Descriptor> methodsDescriptors = new ArrayList<>();

        System.out.println("####### ----- " + currentClass.getName() + " ----- #######");
        System.out.println("Constructors:");

        ArrayList<Constructor> constructors = new ArrayList<>(Arrays.asList(currentClass.getDeclaredConstructors()));

        for (Constructor c : constructors) {

            // Information wrapper
            Descriptor descriptor = new Descriptor(c.getName());
            Object[] instanceArgs = this.getArgsDefaultValues(c.getAnnotatedParameterTypes(), descriptor);

            try {
                c.newInstance(instanceArgs);
            } catch (NullPointerException ex) {
                if (descriptor.hasReferenceTypes) {
                    descriptor.isNpeSensible = true;
                }
            } catch (InstantiationException ex) {
                descriptor.exception = "InstantiationException";
            } catch (IllegalAccessException ex) {
                descriptor.exception = "IllegalAccessException";
            } catch (IllegalArgumentException ex) {
                descriptor.exception = "IllegalArgumentException";
            } catch (InvocationTargetException ex) {
                if (ex.getTargetException() instanceof NullPointerException) {
                    if (descriptor.hasReferenceTypes) {
                        descriptor.isNpeSensible = true;
                    }
                    descriptor.exception = "NullPointerException";
                } else {
                    descriptor.exception = "InvocationTargetException";
                }
            }

            System.out.println(descriptor.toString());
            constructorsDescriptors.add(descriptor);
        }

        System.out.println("\nMethods:");

        ArrayList<Method> methods = new ArrayList<>(Arrays.asList(currentClass.getDeclaredMethods()));

        for (Method m : methods) {

            // Information wrapper
            Descriptor descriptor = new Descriptor(m.getName());
            Object[] instanceArgs = this.getArgsDefaultValues(m.getAnnotatedParameterTypes(), descriptor);

            try {
                m.invoke(currentClass.newInstance(), instanceArgs);
            } catch (NullPointerException ex) {
                if (descriptor.hasReferenceTypes) {
                    descriptor.isNpeSensible = true;
                }
            } catch (InstantiationException ex) {
                descriptor.exception = "InstantiationException";
            } catch (IllegalAccessException ex) {
                descriptor.exception = "IllegalAccessException";
            } catch (IllegalArgumentException ex) {
                descriptor.exception = "IllegalArgumentException";
            } catch (InvocationTargetException ex) {
                if (ex.getTargetException() instanceof NullPointerException) {
                    if (descriptor.hasReferenceTypes) {
                        descriptor.isNpeSensible = true;
                    }
                    descriptor.exception = "NullPointerException";
                } else {
                    descriptor.exception = "InvocationTargetException";
                }
            }

            System.out.println(descriptor.toString());
            methodsDescriptors.add(descriptor);

        }

        System.out.println("####### ------------------------------ #######\n");

        return new CheckNpeResult(constructorsDescriptors, methodsDescriptors);
    }

    /**
     * Return the array of default object values to pass to the method/constructor invocation
     * @param types The types of the parameters
     * @param descriptor The descriptor to update the hasReferenceTypes property
     * @return The array of default object values
     */
    private Object[] getArgsDefaultValues(AnnotatedType[] types, Descriptor descriptor) {
        ArrayList<AnnotatedType> params = new ArrayList<>(Arrays.asList(types));
        Object[] instanceArgs = new Object[params.size()];

        int currentIndex = 0;

        for (AnnotatedType p : params) {
            Type type = p.getType();
            if (type.equals(int.class)) {
                instanceArgs[currentIndex] = 0;
            } else if (type.equals(boolean.class)) {
                instanceArgs[currentIndex] = false;
            } else {
                descriptor.hasReferenceTypes = true;
                instanceArgs[currentIndex] = null;
            }
            descriptor.parameters.add(type.getTypeName());
            currentIndex++;
        }

        return instanceArgs;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (String className : args) {
            new CheckNpe(className).check();
        }

    }

}
