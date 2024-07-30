package org.viciousxerra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Height: ");
            int height = Integer.parseInt(reader.readLine());
            System.out.print("Width: ");
            int width = Integer.parseInt(reader.readLine());
            System.out.println("------------");
            Diamond diamond = new Diamond(height, width);
            diamond.print();
        } catch (IOException e) {
            System.out.println("I/O issues.");
        } catch (NumberFormatException e) {
            System.out.println("Unable to cast input string to integer.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
