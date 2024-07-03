import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputOutput {
    public static void readWriteInputOutput(String[] args, String part) {
        for (String arg : args) {
            System.out.println("Processing file: " + arg);
            String num = arg.substring(arg.indexOf("input") + "input".length());
            File outputFile = new File(part + "/outputs/output" + num);
            try {
                System.out.println("Reading input from: " + part + "/inputs/" + arg);
                String input = readInput(part + "/inputs/" + arg);
                System.out.println("Input read: " + input);
                String output = "";
                if (part.equals("part2")) {
                    output = Part2Main.solve(input);
                } else if (part.equals("part3")) {
                    output = Part3Main.solve(input);
                } else if (part.equals("part4")) {
                    output = Part4Main.solve(input);
                }
                // Add other parts here as needed

                System.out.println("Writing output to: " + outputFile.getPath());
                System.out.println("Output content: " + output);
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                writer.write(output);
                writer.close();
                System.out.println("Output written to: " + outputFile.getPath());
            } catch (Exception e) {
                System.out.println("An error occurred while creating the file: " + outputFile.getName());
                e.printStackTrace();
            }
        }
    }

    public static String readInput(String path) {
        StringBuilder content = new StringBuilder();
        System.out.println("Reading file from: " + path);
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("File content: " + content.toString());
        return content.toString();
    }
}


//import java.io.File;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class InputOutput {
//    public static void readWriteInputOutput(String[] args, String part) {
//        for (String arg : args) {
//            System.out.println("Processing file: " + arg);
//            String num = arg.substring(arg.indexOf("input") + "input".length());
//            File outputFile = new File(part + "/outputs/output" + num);
//            try {
//                System.out.println("Reading input from: " + part + "/inputs/" + arg);
//                String input = readInput(part + "/inputs/" + arg);
//                System.out.println("Input read: " + input);
//                String output = "";
//                if (part.equals("part2")) {
//                    output = Part2Main.solve(input);
//                } else if (part.equals("part3")) {
//                    output = Part3Main.solve(input);
//                } else if (part.equals("part4")) {
//                    output = Part4Main.solve(input);
//                }
//
//                System.out.println("Writing output to: " + outputFile.getPath());
//                System.out.println("Output content: " + output);
//                if (!outputFile.exists()) {
//                    outputFile.createNewFile();
//                }
//                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
//                    writer.write(output);
//                }
//                System.out.println("Output written to: " + outputFile.getPath());
//            } catch (Exception e) {
//                System.out.println("An error occurred while creating the file: " + outputFile.getName());
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static String readInput(String path) {
//        StringBuilder content = new StringBuilder();
//        System.out.println("Reading file from: " + path);
//        try (Scanner scanner = new Scanner(new File(path))) {
//            while (scanner.hasNextLine()) {
//                content.append(scanner.nextLine()).append("\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("File content: " + content.toString());
//        return content.toString();
//    }
//}


