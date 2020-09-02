package com.javarush.task.task36.task3606;

import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {

        File[] files = new File(packageName).listFiles();
        for (File file: files
             ) {

            Class clazz = new ClassLoader() {
                @Override
                protected Class<?> findClass(String className) throws ClassNotFoundException {

                   try {
                       byte b[] = Files.readAllBytes(Paths.get(packageName + File.separator + className + ".class"));
                       return defineClass(null, b, 0 , b.length);
                   } catch (Exception e) {
                       return super.findClass(className);
                   }

                }
            }.loadClass(file.getName().substring(0, file.getName().length() - 6));
            hiddenClasses.add(clazz);

        }

    }

    public HiddenClass getHiddenClassObjectByKey(String key) {

        try {
            for (Class clazz : hiddenClasses) {
                if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                    Constructor[] constructors = clazz.getDeclaredConstructors();
                    for (Constructor constructor:constructors
                         ) {
                        if (constructor.getParameters().length == 0) {
                            constructor.setAccessible(true);
                            return (HiddenClass) constructor.newInstance(null);
                        }
                    }

                }
            }
        } catch (Exception e) {
        }
        return null;

    }
}

