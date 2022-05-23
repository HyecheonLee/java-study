package com.hyecheon.annotation_init;

import com.hyecheon.annotation_init.annotations.InitializerClass;
import com.hyecheon.annotation_init.annotations.InitializerMethod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/24
 */
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        initialize("com.hyecheon.annotation_init.app",
                "com.hyecheon.annotation_init.app.config",
                "com.hyecheon.annotation_init.app.databases", "com.hyecheon.annotation_init.app.http");
    }

    public static void initialize(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final var classes = getAllClasses(packageNames);

        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(InitializerClass.class)) {
                continue;
            }
            final var methods = getAllInitializingMethods(clazz);
            final Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Method method : methods) {
                method.invoke(instance);
            }
        }
    }

    public static List<Method> getAllInitializingMethods(Class<?> clazz) {
        final var initializingMethods = new ArrayList<Method>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(InitializerMethod.class)) {
                initializingMethods.add(method);
            }
        }
        return initializingMethods;
    }

    public static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
        final var allClasses = new ArrayList<Class<?>>();
        for (String packageName : packageNames) {
            final var packageRelativePath = packageName.replace(".", "/");

            final var packageUri = Main.class.getClassLoader().getResource(packageRelativePath).toURI();


            if (packageUri.getScheme().equals("file")) {
                final var packageFullPath = Paths.get(packageUri);

                allClasses.addAll(getAllPackageClasses(packageFullPath, packageName));
            } else if (packageUri.getScheme().equals("jar")) {
                final var fileSystem = FileSystems.newFileSystem(packageUri, Collections.emptyMap());

                final var packageFullPathInJar = fileSystem.getPath(packageRelativePath);
                allClasses.addAll(getAllPackageClasses(packageFullPathInJar, packageName));

                fileSystem.close();
            }
        }
        return allClasses;
    }

    private static List<Class<?>> getAllPackageClasses(Path packagePath, String packageName) throws IOException, ClassNotFoundException {
        if (!Files.exists(packagePath)) {
            return Collections.emptyList();
        }

        final var files = Files.list(packagePath)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        final var classes = new ArrayList<Class<?>>();

        for (Path filePath : files) {

            final var fileName = filePath.getFileName().toString();

            if (fileName.endsWith(".class")) {


                final String classFullName;
                if (packageName.isBlank()) {
                    classFullName = fileName.replaceFirst("\\.class$", "");
                } else {
                    classFullName = packageName + "." + fileName.replaceFirst("\\.class$", "");
                }

                final Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
