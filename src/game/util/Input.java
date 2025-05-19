package util;

import java.io.IOException;
import java.util.Scanner;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);
    
    private static final int ENTER = 13;

    public static void cnt() {
        System.out.println();
        scanner.nextLine();
    }

    public static String space() {
        try {
            new ProcessBuilder("sh", "-c", "stty raw -echo").inheritIO().start().waitFor();

            if (System.in.available() > 0) return scanner.nextLine();

            new ProcessBuilder("sh", "-c", "stty sane -echo").inheritIO().start().waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static int num(int min, int max, boolean enter) {
        System.out.println();
        
        int num = -1;
        try {
            new ProcessBuilder("sh", "-c", "stty raw -echo").inheritIO().start().waitFor();

            while (num < min || num > max) {
                int ascii = System.in.read();
                char ch = (char)ascii;
                
                num = Character.getNumericValue(ch);
                
                if (enter && ascii == ENTER) return -1;
            }

            new ProcessBuilder("sh", "-c", "stty sane -echo").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;
    }

    public static int num(int max) {
        return num(1, max, false);
    }
}
