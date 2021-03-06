import utils.Utils;
import data.Repository;
import screens.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class Application {

    Repository repository = new Repository();

    public Application() {
        verifyExistingConfigFile();
        addNewData();

        if (repository.getData() != null) {
            new MainScreen(Utils.TITLE, repository);
            repository.saveData();
        }
    }

    private void verifyExistingConfigFile() {
        try {
            repository.getSavedData();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void addNewData() {
        while (repository.getData() == null) {
            String s = JOptionPane.showInputDialog("Enter the file path with data");
            if (s.toLowerCase().equals("close"))
                break;
            try {
                repository.setNewDataFromFile(s);
                repository.saveData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new Frame(),
                        e.getMessage(),
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                System.err.println(e.getMessage());
            }
        }
    }
}
