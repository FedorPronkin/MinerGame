package ru.ivanov.miner.model.cellState;

import ru.ivanov.miner.model.CellType;

public class CellUnmarked implements State {

    private final CellType status = CellType.CLOSED;

    @Override
    public CellType getStatus() {
        return status;
    }

    @Override
    public void open(StateContext context) {
        context.setState(new CellOpened());
    }

    @Override
    public void touch(StateContext context) {
        context.setState(new CellMarked());
    }
}
