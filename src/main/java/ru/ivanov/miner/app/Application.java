package ru.ivanov.miner.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ivanov.miner.common.GameType;
import ru.ivanov.miner.controller.Controller;
import ru.ivanov.miner.controller.ControllerInterface;
import ru.ivanov.miner.model.CellProcessor;
import ru.ivanov.miner.model.bestPlayers.BestPlayerCheck;
import ru.ivanov.miner.model.repository.RecordReaderWriter;
import ru.ivanov.miner.model.repository.RecordReaderWriterInterface;
import ru.ivanov.miner.timer.MinerTimer;
import ru.ivanov.miner.timer.MinerTimerInterface;
import ru.ivanov.miner.view.MainWindow;

import javax.swing.*;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        MinerTimerInterface timer = new MinerTimer();

        logger.info("Application started!");

        JFrame mainFrame = new JFrame("Miner");
        MainWindow mainWindow = new MainWindow(mainFrame);
        RecordReaderWriterInterface recordReaderWriter = new RecordReaderWriter();
        BestPlayerCheck bestPlayerCheck = new BestPlayerCheck(recordReaderWriter);
        CellProcessor cellProcessor = new CellProcessor();
        ControllerInterface controller = new Controller(cellProcessor, mainWindow, recordReaderWriter);

        setDependencies(mainWindow, cellProcessor, bestPlayerCheck, controller, timer);

        controller.setGameType(GameType.NOVICE);
        mainFrame.setVisible(true);
        controller.startGame();
    }

    private static void setDependencies(MainWindow mainWindow, CellProcessor cellProcessor,
                                        BestPlayerCheck bestPlayerCheck, ControllerInterface controller, MinerTimerInterface timer){
        logger.info("Creating starting parameters...");

        mainWindow.setNewGameMenuAction(e -> controller.startGame());
        mainWindow.setCellListener(controller::someMouseButtonClicked);
        mainWindow.setController(controller);
        mainWindow.setExitMenuAction(e -> {
            controller.stopTimer();
            logger.info("Application stopped!");
            mainWindow.getMainFrame().dispose();
        });
        mainWindow.setSettingsMenuAction(e -> mainWindow.showSettings());
        mainWindow.setHighScoresMenuAction(e -> mainWindow.showHighScores());

        cellProcessor.addCellListener(mainWindow);
        cellProcessor.addWinLoseListener(mainWindow);
        cellProcessor.addStartListener(mainWindow);
        cellProcessor.addTimerUpdateListener(mainWindow);

        controller.addBestPlayerCheckListener(bestPlayerCheck);
        controller.addTimerListener(timer);

        timer.addListener(cellProcessor);

        bestPlayerCheck.addWinnerNameListener(mainWindow);

        logger.info("Starting parameters created...");
    }
}
