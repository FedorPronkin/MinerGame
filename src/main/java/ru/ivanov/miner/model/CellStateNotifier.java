package ru.ivanov.miner.model;

public interface CellStateNotifier {
    void addCellListener(CellStateUpdater cellStateUpdater);

    void notifyViewOpenCell(int x, int y, Mines status);

    void notifyViewMarkCell(int x, int y, CellType status);
}
