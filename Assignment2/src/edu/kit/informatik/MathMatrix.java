package edu.kit.informatik;

/**
 * This class represents a matrix and contains operations about matrices.
 * @author Tarik Polat
 * @version 1.0.0
 */
public class MathMatrix {

    private int[][] matrix;

    /**
     * Constructs a matrix object with the given matrix.
     * @param matrix the matrix to be assigned to this object
     */
    public MathMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * To get the matrix as a multidimensional array.
     * @return the matrix as a multidimensional array
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Adds the given matrix to this matrix.
     * @param secondMatrixObj the second matrix
     */
    public void add(MathMatrix secondMatrixObj) {
        int[][] secondMatrix = secondMatrixObj.getMatrix();

        for (int r = 0; r < this.matrix.length; r++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                matrix[r][c] += secondMatrix[r][c];
            }
        }
    }

    //Utility method for multiply(). Used for the sum inside the nested for loop to increase readability.
    private int sumUp(int row, int col, int[][] secondMatrix) {
        int sum = 0;

        for (int i = 0; i < secondMatrix.length; i++) {
            sum += this.matrix[row][i] * secondMatrix[i][col];
        }
        return sum;
    }

    /**
     * Multiplies this matrix (left-hand matrix) with the given matrix (right-hand matrix) and assigns the result to
     * this matrix.
     * @param secondMatrixObj the right-hand matrix
     */
    public void multiply(MathMatrix secondMatrixObj) {
        int[][] secondMatrix = secondMatrixObj.getMatrix();
        int[][] result = new int[this.matrix.length][secondMatrix[0].length];

        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                result[r][c] = sumUp(r, c, secondMatrix);
            }
        }

        this.matrix = result;
    }

    /**
     * To get a string representation of this matrix. Columns are separated by a whitespace and rows are
     * on different lines.
     * @return the string representation of this matrix
     */
    public String toString() {
        StringBuilder representation = new StringBuilder();

        for (int[] row : this.matrix) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                representation.append(row[c]).append(" ");
            }

            representation.deleteCharAt(representation.length() - 1);
            representation.append(System.getProperty("line.separator"));
        }

        representation.deleteCharAt(representation.length() - 1);
        return representation.toString();
    }
}
