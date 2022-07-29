package ru.ivanov.miner.model.cellState;

import ru.ivanov.miner.model.CellType;

public class CellOpened implements State {
    private final CellType status = CellType.OPENED;

    @Override
    public CellType getStatus() {
        return status;
    }

    @Override
    public void touch(StateContext context) {
        /*Do nothing*/
    }

    @Override
    public void open(StateContext stateContext) {
        /*Do nothing*/
    }
}
