package com.hyecheon.repeatable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2022/05/28
 */
@Annotations.ScanPackages({"com.hyecheon.repeatable.cache"})
public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        schedule();
    }

    private static void schedule() throws URISyntaxException, IOException, ClassNotFoundException {
        final var scanPackages = Main.class.getAnnotation(Annotations.ScanPackages.class);
        if (scanPackages == null || scanPackages.value().length == 0) {
            return;
        }
        final var allClasses = getAllClasses(scanPackages.value());
        final var scheduledExecutorMethods = getScheduledExecutorMethods(allClasses);

        for (Method method : scheduledExecutorMethods) {
            scheduleMethodExecution(method);
        }

    }

    private static void scheduleMethodExecution(Method method) {
        final var schedules = method.getAnnotationsByType(Annotations.ExecuteOnSchedule.class);
        final var scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        for (Annotations.ExecuteOnSchedule schedule : schedules) {
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                runWhenScheduled(method);
            }, schedule.delaySeconds(), schedule.periodSeconds(), TimeUnit.SECONDS);
        }
    }

    private static void runWhenScheduled(Method method) {
        final var currentDate = new Date();
        final var dateFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println("Executing at %s".formatted(dateFormat.format(currentDate)));

        try {
            method.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static List<Method> getScheduledExecutorMethods(List<Class<?>> allClasses) {
        final var scheduledMethods = new ArrayList<Method>();
        for (Class<?> clazz : allClasses) {
            if (!clazz.isAnnotationPresent(Annotations.ScheduledExecutorClass.class)) {
                continue;
            }
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getAnnotationsByType(Annotations.ExecuteOnSchedule.class).length != 0) {
                    scheduledMethods.add(method);
                }
            }
        }
        return scheduledMethods;
    }

    private static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, IOException, ClassNotFoundException {
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

    private static Collection<Class<?>> getAllPackageClasses(Path packagePath, String packageName) throws IOException, ClassNotFoundException {
        if (!Files.exists(packagePath)) return Collections.emptyList();

        final var files = Files.list(packagePath).filter(Files::isRegularFile).collect(Collectors.toList());

        final var classes = new ArrayList<Class<?>>();

        for (Path filePath : files) {
            final var fileName = filePath.getFileName().toString();

            if (fileName.endsWith(".class")) {
                final var replace = fileName.replace(".class", "");
                System.out.println(replace);
                final var classFullName = packageName.isBlank() ? fileName.replaceFirst("\\.class$", "") :
                        packageName + "." + fileName.replaceFirst("\\.class$", "");
                final Class<?> clazz = Class.forName(classFullName);
                classes.add(clazz);
            }
        }
        return classes;
    }
}
