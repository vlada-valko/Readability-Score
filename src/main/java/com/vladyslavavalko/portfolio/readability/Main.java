package com.vladyslavavalko.portfolio.readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static String text;

    public static void main(String[] args) throws IOException {

        String filename = args[0];
        text = Files.readString(Path.of(filename));
        Methods.outputForMain();
    }

}