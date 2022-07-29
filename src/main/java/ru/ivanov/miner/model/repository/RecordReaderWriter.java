package ru.ivanov.miner.model.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecordReaderWriter implements RecordReaderWriterInterface, ReadTableNotifier {
    private final String RECORDS_FILE_NAME = "records.min";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HighScoreTableUpdater highScoreTableUpdater;

    public void readRecordsTable() {
        logger.info("Start reading records table..");

        RecordsDTO recordsDTO = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(RECORDS_FILE_NAME))) {

            recordsDTO = (RecordsDTO) objectInputStream.readObject();
            logger.info("Got a records table!");

        } catch (IOException e) {
            logger.warn("Error reading records file! Probably it wasn`t created! Result would be null!" + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("File doesn`t contain a DTO class! Result will be empty!" + e.getMessage());
        }

        if (recordsDTO == null) {
            recordsDTO = new RecordsDTO(new ArrayList<>());
        }
        notifyListenerGotHighScores(recordsDTO.getRecordsList());
    }

    public void writeRecordsTable(List<Player> bestPlayers) {

        logger.info("Start writing new records table!");
        RecordsDTO recordsDTO = new RecordsDTO(bestPlayers);

        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(RECORDS_FILE_NAME))) {
            writer.writeObject(recordsDTO);
            logger.info("Writing successfull!");
        } catch (IOException e) {
            logger.warn("Error writing file! records would be empty!" + e.getMessage());
        }
    }

    @Override
    public void setRecordsTableListener(HighScoreTableUpdater highScoreTableUpdater) {
        this.highScoreTableUpdater = highScoreTableUpdater;
    }

    @Override
    public void notifyListenerGotHighScores(List<Player> bestPlayers) {
        if(highScoreTableUpdater != null){
            highScoreTableUpdater.gotHighScores(bestPlayers);
        }
    }
}
