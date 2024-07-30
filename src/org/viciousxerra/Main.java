package org.viciousxerra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import static java.lang.Math.round;

public class Main {

    private static final int SIZE_LIMIT = 1;
    private static final char WHITESPACE = ' ';
    private static final char OCTOTHORPE = '#';

    private Main() {
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Height: ");
            int height = Integer.parseInt(reader.readLine());
            System.out.print("Width: ");
            int width = Integer.parseInt(reader.readLine());
            System.out.println("------------");
            Main.printDiamond(height, width);
        } catch (IOException e) {
            System.out.println("I/O issues.");
        } catch (NumberFormatException e) {
            System.out.println("Unable to cast input string to integer.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printDiamond(int height, int width) {
        if (height < SIZE_LIMIT || width < SIZE_LIMIT) {
            throw new IllegalArgumentException("Height and width must be positive.");
        }
        if (isBoundaryCase(height, width)) {
            return;
        }
        int halfHeight = height >> 1;
        int halfWidth = width >> 1;
        float ratio = (float) width / height;
        float leftPos = halfWidth;
        float rightPos = halfWidth;
        StringBuilder stringBuilder = new StringBuilder();
        Stack<String> bottomSide = new Stack<>();
        for (int i = 0; i <= halfHeight; i++) {
            if (i == halfHeight - 1 && height % 2 == 0) {
                appendSideAngles(width, stringBuilder);
                renderRowAndPush(stringBuilder, bottomSide);
                break;
            }
            if (i == halfHeight && height % 2 != 0) {
                appendSideAngles(width, stringBuilder);
                System.out.println(stringBuilder);
                stringBuilder.setLength(0);
                break;
            }
            if (i == 0) {
                stringBuilder.repeat(WHITESPACE, width);
                stringBuilder.setCharAt(halfWidth, OCTOTHORPE);
                if (width % 2 == 0) {
                    stringBuilder.setCharAt(halfWidth - 1, OCTOTHORPE);
                }
            } else {
                leftPos -= ratio;
                rightPos += ratio;
                for (int j = 0; j < width; j++) {
                    if (j == round(leftPos) || j == round(rightPos)) {
                        stringBuilder.append(OCTOTHORPE);
                    } else {
                        stringBuilder.append(WHITESPACE);
                    }
                }
            }
            renderRowAndPush(stringBuilder, bottomSide);
        }
        while (!bottomSide.isEmpty()) {
            System.out.println(bottomSide.pop());
        }
    }

    private static void appendSideAngles(int width, StringBuilder stringBuilder) {
        stringBuilder.append(OCTOTHORPE);
        stringBuilder.repeat(WHITESPACE, width - 2);
        stringBuilder.append(OCTOTHORPE);
    }

    private static boolean isBoundaryCase(int height, int width) {
        if (height == SIZE_LIMIT) {
            System.out.println(OCTOTHORPE);
            return true;
        } else if (width == SIZE_LIMIT) {
            for (int i = 0; i < height; i++) {
                System.out.println(OCTOTHORPE);
            }
            return true;
        } else {
            return false;
        }
    }

    private static void renderRowAndPush(StringBuilder stringBuilder, Stack<String> bottomSide) {
        System.out.println(stringBuilder);
        bottomSide.push(stringBuilder.toString());
        stringBuilder.setLength(0);
    }

}
