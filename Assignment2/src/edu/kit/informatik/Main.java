package edu.kit.informatik;

public class Main {

    public static void main(String[] args) {
        MathMatrix firstMatrix;
        MathMatrix secondMatrix = null;

        String operation = args[0];
        firstMatrix = new MathMatrix(getMatrix(args[1]));

        if (args.length > 2) {
            secondMatrix = new MathMatrix(getMatrix(args[2]));
        }

        switch (operation) {
            case "add":
                firstMatrix.add(secondMatrix);
                Terminal.printLine(firstMatrix.toString());
                break;
            case "multiply":
                firstMatrix.multiply(secondMatrix);
                Terminal.printLine(firstMatrix.toString());
                break;
            case "show":
                Terminal.printLine(firstMatrix.toString());
                break;
            default:
                break;
        }
    }

    private static int[][] getMatrix(String string) {
        String[] rows = string.split(";");
        int columnCount = rows[0].split(",").length;

        int[][] matrix = new int[rows.length][columnCount];

        for (int r = 0; r < rows.length; r++) {
            String[] columns = rows[r].split(",");

            for (int c = 0; c < columnCount; c++) {
                matrix[r][c] = Integer.parseInt(columns[c]);
            }
        }

        return matrix;
    }
}
