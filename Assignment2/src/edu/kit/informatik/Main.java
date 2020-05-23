package edu.kit.informatik;

/**
 * This class is here for maintaining in- and output of the matrix calculation programme.
 * @author Tarik Polat
 * @version 1.0.0
 */
public final class Main {

    /*
    Private because no objects of this class shall be created, this class is here only for maintaining
    in- and output of the matrix calculation programme.
     */
    private Main() { }

    /**
     * Main method for the matrix calculation programme.
     * @param args arguments of the programme
     */
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

    /*
    To get the matrix as a multidimensional integer array. The parameter is the argument from the args of main method
    containing the string representation of a matrix and must be semantically and grammatically correct.
     */
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
