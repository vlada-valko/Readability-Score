package com.portfolio.readability;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Methods {
    static Scanner SCANNER = new Scanner(System.in);
    static String text = Main.text;
    static int countSentences;
    static int countWords;
    static int countCharacters;
    static double countARI;
    static double countFK;
    static double countCL;
    static double countSMOG;
    static int countSyllables;
    static int Polysyllables;
    static List<String> splitedTextToWords = new ArrayList<>();

    public static void outputForMain() {
        textFromDoc();
        countWords();
        countSentences();
        countCharacters();
        countSyllables();
        promptWhichCountUse();
    }

    private static void promptWhichCountUse() {
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all)");
        String x = SCANNER.nextLine();
        System.out.println("");
        switch (x) {
            case "ARI":
                ARI();
                break;
            case "FK":
                FK();
                break;
            case "SMOG":
                SMOG();
                break;
            case "CL":
                CL();
                break;
            case "all":
                ARI();
                FK();
                SMOG();
                CL();
                avAge();
                break;
        }
    }

    public static void textFromDoc() {
        System.out.println("The text is:");
        System.out.println(text);
        System.out.println("");
    }

    public static void countSentences() {
        String[] s = text.split("[!.?]");
        countSentences = s.length;
        System.out.println("Sentences: " + countSentences);
    }

    public static int countWords() {
        String wordRegex = "\\b(?:\\d{1,3},\\d{3}|\\w+)\\b";
        Pattern wordPattern = Pattern.compile(wordRegex);
        Matcher wordMatcher = wordPattern.matcher(text);
        while (wordMatcher.find()) {
            splitedTextToWords.add(wordMatcher.group());
        }
        countWords = splitedTextToWords.size();
        System.out.println("Words: " + countWords);
        return countWords;
    }

    public static int countCharacters() {
        char[] count = text.toCharArray();
        int countWithoutSpaces = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] != ' ' && count[i] != '\n' && count[i] != '\t') {
                countWithoutSpaces++;
            }
        }
        countCharacters = countWithoutSpaces;
        System.out.println("Characters: " + countCharacters);
        return countCharacters;
    }

    public static int countSyllables() {
        for (int i = 0; i < splitedTextToWords.size(); i++) {
            String st = splitedTextToWords.get(i).toLowerCase();
            if (st.endsWith("e")) {
                st = st.substring(0, st.length() - 1);
            }
            String nn = st.replaceAll("(?i)[aeiouy]{2}", "o");
            String regex = "[aeiouy]+";
            Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(nn);
            int count = 0;
            while (matcher.find()) {
                count++;
                countSyllables++;
            }
            if (count > 2) {
                Polysyllables++;
            }
            if (count == 0) {
                count++;
                countSyllables++;
            }
        }
        System.out.println("Syllables: " + countSyllables);
        System.out.println("Polysyllables:  " + Polysyllables);
        return countSyllables;
    }

    public static void ARI() {
        countARI = 4.71 * ((double) countCharacters / countWords)
                + 0.5 * ((double) countWords / countSentences) - 21.43;
        System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).%n", countARI, decodinres().get((int) (Math.ceil(countARI))));
    }

    public static void FK() {
        countFK = 0.39 * ((double) countWords / countSentences) + 11.8 *
                ((double) countSyllables / countWords) - 15.59;
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).%n", countFK, decodinres().get((int) (Math.ceil(countFK))));
    }

    public static void SMOG() {
        double t1 = 30.0 / countSentences;
        double t2 = Polysyllables * t1;
        double t3 = Math.sqrt(t2);
        countSMOG = 1.043 * t3 + 3.1291;
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).%n", countSMOG, decodinres().get((int) (Math.ceil(countSMOG))));
    }

    public static void CL() {
        countCL = 0.0588 * (countCharacters / (double) countWords * 100) - 0.296 * (countSentences / (double) countWords * 100) - 15.8;
        System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).%n", countCL, decodinres().get((int) (Math.ceil(countCL))));
    }


    public static Map<Integer, Integer> decodinres() {
        Map<Integer, Integer> answer = new HashMap<>();
        for (int i = 0; i < 13; i++) {
            answer.put(i + 1, i + 6);
        }
        for (int i = 14; i < 25; i++) {
            answer.put(i, 22);
        }
        return answer;
    }

    public static void avAge() {
        double a1 = ((double) decodinres().get((int) (Math.ceil(countARI)))
                + decodinres().get((int) (Math.ceil(countFK)))
                + decodinres().get((int) (Math.ceil(countSMOG)))
                + decodinres().get((int) (Math.ceil(countCL)))) / 4;
        System.out.println("");
        System.out.printf("This text should be understood in average by %.2f-year-olds.", a1);
    }
}