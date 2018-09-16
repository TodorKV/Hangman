package com.idk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        ReadFile f = new ReadFile();
        f.OpenFile();
        ArrayList<String> lines;
        ArrayList<Integer> ctg = new ArrayList<>(); // the index of every line that starts with '_'
        int attempts = 10;
        int score = 0;
        char[] guess; // the array that is filled with '_' and ' '

        lines = f.GetArray();

        for (String line: lines) {
            if(line.startsWith("_")) {
                ctg.add(lines.indexOf(line));
            }
        }

        while(attempts != 0) {
            attempts = 10;
            ChooseCategory(ctg,lines);
            Scanner x = new Scanner(System.in);
            String choice = x.nextLine();
            int idx = GetRandomWordNumber(choice, ctg, lines);
            String original;

            if (idx > -1) {
                original = lines.get(idx).toLowerCase();
                int spaces = CountOfChar(original, ' ');
                int length = original.length();
                guess = FillGuessArray(length,original, spaces);

                System.out.println(original);
                attempts = LetterChoice(guess,attempts,original,x,length, score);
            }
        }
        System.out.println("You Lost");
        f.CloseFile();
    }
    private static int LetterChoice(char[] guess,int attempts,String original, Scanner x,int length,int score){
        while (attempts != 0) {
            PrintAttempts(attempts,guess);
            String tmp = x.nextLine();
            if(!tmp.isEmpty()) {
                char c = tmp.charAt(0);
                while (!Character.isLetter(c) && c != '\n') {
                    System.out.println("NOT A LETTER");
                    System.out.print('>');
                    c = x.next().charAt(0);
                }
                if (!CheckIfLetterIsIn(original, length, c, guess)) {
                    --attempts;
                }
                if (CountOfChar(String.valueOf(guess), '_') == 0) {
                    PrintOnWordCorrect(score, guess);
                    break;
                }
            }
        }
        return attempts;
    }

    private static char[] FillGuessArray(int length, String original, int spaces){
        char[] guess;
        if (spaces > 0) {
            guess = new char[(length - spaces) * 2 + (spaces - 1)];
        } else {
            guess = new char[length * 2 - 1];
        }
        Arrays.fill(guess, ' ');
        int parameter = 0;
        for (int i = 0; i < length; ++i) {
            char a = original.charAt(i);
            int newi = i * 2;
            if (Character.isLetter(a)) {
                if (i != length - 1) {
                    guess[newi - parameter] = '_';

                } else { // last letter
                    guess[newi - parameter] = '_';
                }
            } else {
                guess[newi] = ' ';
                ++parameter;
            }
        }
        return guess;
    }

    private static  void PrintOnWordCorrect(int score, char[] guess){
        ++score;
        System.out.println("Congratulations you have revealed the word/phrase:");
        System.out.println(guess);
        System.out.println("Current score: " + score);
        System.out.println();
    }

    private static void PrintAttempts(int attempts, char[] guess){
        System.out.println("Attempts left: " + attempts);
        System.out.print("Current word/phrase: ");
        System.out.println(guess);
        System.out.println("Please enter a letter.");
        System.out.print('>');
    }

    private static void ChooseCategory(ArrayList<Integer> ctg, ArrayList<String> lines){
        for (int value : ctg) {
            System.out.println(lines.get(value).replaceFirst("_", ""));
        }
        System.out.println("Choose category");
        System.out.print('>');
    }

    private static boolean CheckIfLetterIsIn(String org,int length,char c,char[] guess){
        int parameter = 0;
        boolean res = false;
        for(int i = 0;i < length; ++i){
            char a = org.charAt(i);
            if(a == ' '){
                ++parameter;
            }else {
                if (c == a) {
                    guess[i * 2 - parameter] = a;
                    res = true;
                }
            }
        }
        return res;
    }

    private static int CountOfChar(String line, char c){
        int cnt = 0;
        for(int i = 0;i < line.length(); ++i){
            if(line.charAt(i) == c) {
                ++cnt;
            }
        }
        return cnt;
    }

    private static int GetRandomWordNumber(String choice,ArrayList<Integer> ctg,ArrayList<String> lines){
        for (int value: ctg) {
            if(choice.toLowerCase().compareTo(lines.get(value).replaceFirst("_","").toLowerCase()) == 0){
                //System.out.println(value);
                int idx = ctg.indexOf(value);
                if(idx != (ctg.size() - 1)){
                    return  ThreadLocalRandom.current().nextInt(value +1, ctg.get(idx + 1));
                }else{
                    return ThreadLocalRandom.current().nextInt(value + 1,lines.size());
                }
            }
        }
        return -1;
    }
}