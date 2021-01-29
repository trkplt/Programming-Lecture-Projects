package edu.kit.informatik;

public class TorusGame extends StandardGame{

    private int newLoc(int loc) {
        if(loc < 0) {
            return ((loc % 6) + 6) % 6;
        }
        return loc % 6;
    }

    @Override
    protected boolean checkColor(StandardBoard board, Stone stone, int checkRow, int checkCol) {
        return super.checkColor(board, stone, newLoc(checkRow), newLoc(checkCol));
    }

    @Override
    protected boolean checkEdge(StandardBoard board, Stone stone, int checkRow, int checkCol) {
        return super.checkEdge(board, stone, newLoc(checkRow), newLoc(checkCol));
    }

    @Override
    protected boolean checkSize(StandardBoard board, Stone stone, int checkRow, int checkCol) {
        return super.checkSize(board, stone, newLoc(checkRow), newLoc(checkCol));
    }

    @Override
    protected boolean checkShape(StandardBoard board, Stone stone, int checkRow, int checkCol) {
        return super.checkShape(board, stone, newLoc(checkRow), newLoc(checkCol));
    }

    @Override
    protected void setValuesFast(int row, int col, int addRow, int addCol) {

        checkRow = newLoc(row + addRow) ;
        onePlusCheckRow = newLoc(checkRow + addRow);
        twoPlusCheckRow = newLoc(onePlusCheckRow + addRow);
        checkCol = newLoc(col + addCol);
        onePlusCheckCol = newLoc(checkCol + addCol);
        twoPlusCheckCol = newLoc(onePlusCheckCol + addCol);

    }

    @Override
    protected boolean checkFeatureOneDir(StandardBoard board, Stone stone, int row, int col, String direction, String feature) {
        return super.checkFeatureOneDir(board, stone, row, col, direction, feature);
    }

    @Override
    protected boolean checkFeaturesTwoOppDir(StandardBoard board, Stone stone, int row, int col, String direction) {
        return super.checkFeaturesTwoOppDir(board, stone, row, col, direction);
    }

    @Override
    public void checkWin(StandardBoard board, Stone stone, int row, int col) {
        super.checkWin(board, stone, row, col);
    }
}
