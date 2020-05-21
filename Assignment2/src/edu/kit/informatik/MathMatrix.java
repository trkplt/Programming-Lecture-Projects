package edu.kit.informatik;

public class MathMatrix {

    private int[][] matrix;

    public MathMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void add(MathMatrix secondMatrixObj) {
        int[][] secondMatrix = secondMatrixObj.getMatrix();

        for (int r = 0; r < this.matrix.length; r++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                matrix[r][c] += secondMatrix[r][c];
            }
        }
    }

    public void multiply(MathMatrix secondMatrixObj) {
        int[][] secondMatrix = secondMatrixObj.getMatrix();
        int[][] result = new int[this.matrix.length][secondMatrix[0].length];

        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                int sum = 0;

                for (int i = 0; i < secondMatrix.length; i++) {
                    sum += this.matrix[r][i] * secondMatrix[i][c];
                }

                result[r][c] = sum;
            }
        }

        this.matrix = result;
    }

    public String toString() {
        StringBuilder representation = new StringBuilder();

        for (int r = 0; r < this.matrix.length; r++) {
            for (int c = 0; c < this.matrix[0].length; c++) {
                representation.append(this.matrix[r][c]).append(" ");
            }

            representation.deleteCharAt(representation.length() - 1);
            representation.append(System.getProperty("line.separator"));
        }

        representation.deleteCharAt(representation.length() - 1);
        return representation.toString();
    }
}
