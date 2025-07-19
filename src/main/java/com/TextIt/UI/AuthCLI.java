package com.TextIt.UI;

import com.TextIt.database.DataBase;
import com.TextIt.model.utils.CommonMethods;
import com.TextIt.service.pages.LoginAuth;
import com.TextIt.service.pages.SignUpAuth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static com.TextIt.model.utils.CommonMethods.*;

public class AuthCLI {

    //Objects Of Different Classes
    private final Scanner scanner = new Scanner(System.in);


    public static void openInNewCMD(String className) {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + "\\bin\\java";
            String workingDir = System.getProperty("user.dir");
            
            // Build classpath with all required components
            StringBuilder classpath = new StringBuilder();
            
            // 1. Add target/classes
            classpath.append("\"").append(workingDir).append("\\target\\classes\"");
            
            // 2. Add all JARs from target/dependency
            java.nio.file.Path dependencyDir = java.nio.file.Paths.get(workingDir, "target", "dependency");
            if (java.nio.file.Files.exists(dependencyDir)) {
                try (java.util.stream.Stream<java.nio.file.Path> walk = java.nio.file.Files.walk(dependencyDir)) {
                    String deps = walk.filter(path -> path.toString().endsWith(".jar"))
                            .map(p -> "\"" + p.toString() + "\"")
                            .collect(java.util.stream.Collectors.joining(";"));
                    if (!deps.isEmpty()) {
                        classpath.append(";").append(deps);
                    }
                }
            }
            
            // 3. Add the current classpath
            String currentClasspath = System.getProperty("java.class.path");
            if (currentClasspath != null && !currentClasspath.isEmpty()) {
                classpath.append(";").append(currentClasspath);
            }
            
            // Build the full command
            String javaCommand = String.format("\"%s\" -cp %s %s", 
                javaBin, classpath.toString(), className);
                
            // For debugging
            System.out.println("Launching: " + className);
            System.out.println("Working directory: " + workingDir);
            System.out.println("Classpath: " + classpath);
            
            // Create and start the process
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "cmd", "/k", 
                "title " + className + " && " + javaCommand);
                
            pb.directory(new java.io.File(workingDir));
            pb.inheritIO(); // This will show the output in the new window
            
            Process process = pb.start();
            Thread.sleep(1000); // Give it a moment to start
            
            // Check if process is still alive after a short delay
            if (!process.isAlive() && process.exitValue() != 0) {
                System.err.println("Process exited with code: " + process.exitValue());
            }
            
        } catch (Exception e) {
            System.err.println("Failed to launch new CMD for class: " + className);
            e.printStackTrace();
        }
    }

    public void showWelcomeScreen() throws SQLException {

        while (true) {
            System.out.println(CommonMethods.CYAN + CommonMethods.BOLD + """
                    ╔════════════════════════════════════════╗
                    ║           Welcome to TextIt            ║
                    ╚════════════════════════════════════════╝
                    """ + RESET);
            System.out.println(CommonMethods.YELLOW + "1. " + GREEN + "Sign Up");
            System.out.println(CommonMethods.YELLOW + "2. " + CommonMethods.BLUE + "LoginAuth");
            System.out.println(CommonMethods.YELLOW + "3. " + RED + "Exit");
            System.out.print("\n" + CommonMethods.PURPLE + "Enter your choice: " + RESET);


            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException _) {
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    openInNewCMD("com.TextIt.UI.SignupPage");
                    break;
                case 2:
                    openInNewCMD("com.TextIt.UI.LoginPage");
                    break;
                case 3: {
                    System.out.println(RED + "\nThank you for using TextIt. Goodbye!" + RESET);
                    System.exit(0);
                }
                default: {
                    System.out.println(RED + "\nInvalid choice. Please try again." + RESET);
                    pressEnterToContinue();
                    showWelcomeScreen();
                }
            }
        }
    }
}