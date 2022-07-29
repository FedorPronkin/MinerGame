package ru.ivanov.miner.model;

import ru.ivanov.miner.model.cellState.StateContext;

import java.util.ArrayList;

public class BackgroundField {
    private StateContext[][] cells;
    private ArrayList<Integer> minesCoordinates;
    private int filedHeight;
    private int fieldWidth;

    public StateContext[][] makeEmptyField(int height, int width) {
        this.filedHeight = height;
        this.fieldWidth = width;
        cells = new StateContext[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new StateContext(Mines.EMPTY);
            }
        }
        return cells;
    }

    public StateContext[][] putMines(int minesAmount, int excludeRow, int excludeColumn) {

        minesCoordinates = createMinesCoordinates(minesAmount, excludeRow, excludeColumn);

        for (int i = 0; i < filedHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (minesCoordinates.contains(i * fieldWidth + j)) {
                    cells[i][j].setMines(Mines.BOMB);
                } else {
                    cells[i][j].setMines(Mines.getValue(countMinesAround(i, j)));
                }
            }
        }
        return cells;
    }

    private ArrayList<Integer> createMinesCoordinates(int minesAmount, int excludedRow, int excludedColumn) {
        ArrayList<Integer> minesCoordinates = new ArrayList<>(minesAmount);

        while (minesCoordinates.size() < minesAmount) {
            int mine = (int) Math.round(Math.random() * filedHeight * fieldWidth);
            if (!minesCoordinates.contains(mine) && mine != (excludedRow * fieldWidth + excludedColumn)) {
                minesCoordinates.add(mine);
            }
        }
        return minesCoordinates;
    }

    private int countMinesAround(int row, int column) {
        int result = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i > filedHeight - 1) {
                continue;
            }
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j > fieldWidth - 1) {
                    continue;
                }
                if (i == row && j == column) {
                    continue;
                }
                if (minesCoordinates.contains(i * fieldWidth + j)) {
                    result++;
                }
            }
        }
        return result;
    }
}
