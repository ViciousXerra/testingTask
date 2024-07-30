package org.viciousxerra;

import java.util.Stack;

import static java.lang.Math.round;

public class Diamond {

    private static final int SIZE_LIMIT = 1;
    private static final char WHITESPACE = ' ';
    private static final char OCTOTHORPE = '#';
    private final int height;
    private final int width;

    public Diamond(int height, int width) {
        validate(height, width);
        this.height = height;
        this.width = width;
    }

    public void print() {
        if (isBoundaryCase()) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.println(OCTOTHORPE);
                }
            }
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
                appendSideAngles(stringBuilder);
                renderRowAndPush(stringBuilder, bottomSide);
                break;
            }
            if (i == halfHeight && height % 2 != 0) {
                appendSideAngles(stringBuilder);
                System.out.println(stringBuilder);
                stringBuilder.setLength(0);
                break;
            }
            if (i == 0) {
                for (int j = 0; j < width; j++) {
                    stringBuilder.append(WHITESPACE);
                }
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

    private void appendSideAngles(StringBuilder stringBuilder) {
        stringBuilder.append(OCTOTHORPE);
        for (int j = 0; j < width - 2; j++) {
            stringBuilder.append(WHITESPACE);
        }
        stringBuilder.append(OCTOTHORPE);
    }

    private boolean isBoundaryCase() {
        return height == SIZE_LIMIT || width == SIZE_LIMIT;
    }

    private void renderRowAndPush(StringBuilder stringBuilder, Stack<String> bottomSide) {
        System.out.println(stringBuilder);
        bottomSide.push(stringBuilder.toString());
        stringBuilder.setLength(0);
    }

    private static void validate(int height, int width) {
        if (height < SIZE_LIMIT || width < SIZE_LIMIT) {
            throw new IllegalArgumentException("Height and width must be positive.");
        }
    }

}
