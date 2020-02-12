package project;

import project.antlr.ParseTreeGenerator;

import java.io.IOException;

/**
 * Execute this file with your filename as argument
 * your file should be located in the example_programs folder
 * if no argument is given "test.dy" is generated by default
 *
 * Executing this file will present you with a terminal loaded with the
 * generated haskell code, type "main" to execute your program.
 * The generated file can be found at project/sprockell/src and is called INSTRUCTIONS.hs
 */
public class Generate {

    private final static String PATH = "project/example_programs/";

    public static void main(String[] args) throws IOException {
        String fileName;
        if (args.length == 0) {
            fileName = PATH + "Test.dy";
        } else {
            fileName = PATH + args[0];
        }

        String s = "project/sprockell/src";

        ParseTreeGenerator gen = new ParseTreeGenerator(true);
        gen.start(fileName);

        Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd " + s + "&& ghci INSTRUCTIONS.hs\"");

        // this opens a terminal window
        // type "main" to run the program
    }

}