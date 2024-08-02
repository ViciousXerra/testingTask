package org.viciousxerra;

import java.util.Stack;

import static java.lang.Math.round;

public class Diamond {

    private static final int MIN_SIZE = 1;
    private static final int HEIGHT_WIDTH_BOUNDARY_CASE_SIZE = 2;
    private static final char WHITESPACE = ' ';
    private static final char OCTOTHORPE = '#';
    private final int height;
    private final int halfHeight;
    private final int width;
    private final int halfWidth;
    private final float ratio;

    public Diamond(int height, int width) {
        validate(height, width);
        this.height = height;
        this.halfHeight = height >> 1;
        this.width = width;
        this.halfWidth = width >> 1;
        ratio = (float) width / height;
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<String> stack = new Stack<>();
        if (isBoundaryCase()) {
            printBoundaryCase(stringBuilder, stack);
            return;
        }
        float currentLeftPos = halfWidth;
        float currentRightPos = halfWidth;
        for (int i = 0; i <= halfHeight; i++) {
            if (i == 0) {
                appendTopSideAngleRow(stringBuilder);
                stack.push(stringBuilder.toString());
                printRowAndFlushStringBuilder(stringBuilder);
                continue;
            }
            if (height % 2 != 0 && i == halfHeight) {
                appendSideAnglesRow(stringBuilder);
                printRowAndFlushStringBuilder(stringBuilder);
                continue;
            }
            if (height % 2 == 0 && i == halfHeight - 1) {
                appendSideAnglesRow(stringBuilder);
                stack.push(stringBuilder.toString());
                printRowAndFlushStringBuilder(stringBuilder);
                break;
            }
            currentLeftPos -= ratio;
            currentRightPos += ratio;
            for (int j = 0; j < width; j++) {
                if (j == round(currentLeftPos) || j == round(currentRightPos)) {
                    stringBuilder.append(OCTOTHORPE);
                } else {
                    stringBuilder.append(WHITESPACE);
                }
            }
            stack.push(stringBuilder.toString());
            printRowAndFlushStringBuilder(stringBuilder);
        }
        printBottomSide(stack);
    }

    private void appendSideAnglesRow(StringBuilder stringBuilder) {
        for (int j = 0; j < width; j++) {
            if (j == 0 || j == width - 1) {
                stringBuilder.append(OCTOTHORPE);
            } else {
                stringBuilder.append(WHITESPACE);
            }
        }
    }

    private void appendTopSideAngleRow(StringBuilder stringBuilder) {
        for (int j = 0; j < width; j++) {
            if (j == halfWidth) {
                stringBuilder.append(OCTOTHORPE);
            } else {
                stringBuilder.append(WHITESPACE);
            }
        }
    }

    private boolean isBoundaryCase() {
        return height == MIN_SIZE ||
               width == MIN_SIZE ||
               height == HEIGHT_WIDTH_BOUNDARY_CASE_SIZE ||
               width == HEIGHT_WIDTH_BOUNDARY_CASE_SIZE;
    }


    private void printBottomSide(Stack<String> rowStack) {
        while (!rowStack.isEmpty()) {
            System.out.println(rowStack.pop());
        }
    }

    private void printBoundaryCase(StringBuilder stringBuilder, Stack<String> rowStack) {
        if (height == MIN_SIZE) {
            for (int j = 0; j < width; j++) {
                System.out.print(OCTOTHORPE);
            }
            System.out.println();
            return;
        }
        if (height == HEIGHT_WIDTH_BOUNDARY_CASE_SIZE) {
            for (int i = 0; i < height; i++) {
                appendTopSideAngleRow(stringBuilder);
                printRowAndFlushStringBuilder(stringBuilder);
            }
            return;
        }
        if (width == MIN_SIZE) {
            for (int i = 0; i < height; i++) {
                System.out.println(OCTOTHORPE);
            }
            return;
        }
        if (width == HEIGHT_WIDTH_BOUNDARY_CASE_SIZE) {
            float minExtensionCoordinateDelta = (float) halfHeight / 2;
            for (int i = 0; i <= halfHeight; i++) {
                if (i == 0 || i < minExtensionCoordinateDelta) {
                    appendTopSideAngleRow(stringBuilder);
                    rowStack.push(stringBuilder.toString());
                    printRowAndFlushStringBuilder(stringBuilder);
                } else {
                    appendSideAnglesRow(stringBuilder);
                    if (height % 2 != 0 && i == halfHeight) {
                        printRowAndFlushStringBuilder(stringBuilder);
                        break;
                    } else {
                        rowStack.push(stringBuilder.toString());
                        printRowAndFlushStringBuilder(stringBuilder);
                        if (height % 2 == 0 && i == halfHeight - 1) {
                            break;
                        }
                    }
                }
            }
            printBottomSide(rowStack);
        }
    }

    private static void printRowAndFlushStringBuilder(StringBuilder stringBuilder) {
        System.out.println(stringBuilder);
        stringBuilder.setLength(0);
    }

    private static void validate(int height, int width) {
        if (height < MIN_SIZE || width < MIN_SIZE) {
            throw new IllegalArgumentException("Height and width must be positive.");
        }
    }

}
