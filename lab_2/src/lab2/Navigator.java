/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2;

import java.awt.List;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author giacomodeliberali
 */
public class Navigator {

    private String testField;
    private String testField1;

    public String getTestField() {
        return testField;
    }

    public void setTestField(String testField) {
        this.testField = testField;
    }

    public String getTestField1() {
        return testField1;
    }

    public void setTestField1(String testField1) {
        this.testField1 = testField1;
    }

    private void testMethod() {
    }

    public static void main(String args[]) {

//String className = "javax.swing.JButton";
        Class classObj = JComponent.class;

        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(classObj.getDeclaredFields()));
        ArrayList<Method> methods = new ArrayList<>(Arrays.asList(classObj.getDeclaredMethods()));

        System.out.println("R/W properties:");
        AtomicInteger count = new AtomicInteger(0);
        // Must search for get<MethodName> and set<MethodName>
        Stream<Method> currentMethods = methods.stream().filter(m -> m.getName().startsWith("get"));
        currentMethods.forEach(method -> {
            String methodName = method.getName().substring(3);
            String setMethodName = "set" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
            Method setMethod = methods.stream().filter(m -> m.getName().equals(setMethodName)).findAny().orElse(null);
            if (setMethod != null) {
                count.incrementAndGet();
                System.out.println("- " + methodName);
            }
        });
        System.out.println("| total (" + count.get() + ")");

        System.out.println("\nR properties:");
        count.set(0);
        currentMethods = methods.stream().filter(m -> m.getName().startsWith("get"));
        currentMethods.forEach(method -> {
            String methodName = method.getName().substring(3);
            String setMethodName = "set" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
            Method setMethod = methods.stream().filter(m -> m.getName().equals(setMethodName)).findAny().orElse(null);
            if (setMethod == null) {
                count.incrementAndGet();
                System.out.println("- " + methodName);
            }
        });
        System.out.println("| total (" + count.get() + ")");

        System.out.println("\nW properties:");
        count.set(0);
        currentMethods = methods.stream().filter(m -> m.getName().startsWith("set"));
        currentMethods.forEach(method -> {
            String methodName = method.getName().substring(3);
            String setMethodName = "get" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
            Method setMethod = methods.stream().filter(m -> m.getName().equals(setMethodName)).findAny().orElse(null);
            if (setMethod == null) {
                count.incrementAndGet();
                System.out.println("- " + methodName);
            }
        });
        System.out.println("| total (" + count.get() + ")");

        System.out.println("\n\nEvents:");
        count.set(0);
        for (Method v : methods) {
            if (v.getName().endsWith("Listener") && v.getName().startsWith("add")) {
                String eventName = v.getName().substring(3, v.getName().indexOf("Listener"));
                count.incrementAndGet();
                System.out.println("- " + eventName);
            }
        }
        System.out.println("| total (" + count.get() + ")");

    }

}
