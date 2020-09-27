package sudoku.persistence;

import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {

    private static File GAME_DATA;

    private static File GetLocalStorageFileInstance() {
        if (GAME_DATA != null) {
            return GAME_DATA;

        } else {
            GAME_DATA = new File(System.getProperty("user.home"), "gamedata.txt");
            return GAME_DATA;
        }

    }

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(GetLocalStorageFileInstance());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(GetLocalStorageFileInstance());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("File Not Found.");
        }
    }
}
