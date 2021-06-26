package components;

import utils.AdminButtonClicked;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JPanel {

    private static final String COMMENT = "ADMIN";
    private static final String ADD_ITEM = "Introduce new produce";
    private static final String ADD_FILE = "Introduce config file";

    private JLabel commentLabel = new JLabel();
    private JButton addItemButton = new JButton();
    private JButton addNewConfigFileButton = new JButton();

    private AdminUIInterface uiInterface;

    public AdminUI(Rectangle rectangle) {
        super();
        this.setBounds(rectangle);
        this.setLayout(null);

        commentLabel.setBounds((rectangle.width - 75) / 2, 0, rectangle.width - 75, 30);
        addItemButton.setBounds(0, 40, rectangle.width / 2 - 10, 50);
        addNewConfigFileButton.setBounds(rectangle.width / 2 + 10, 40, rectangle.width / 2 - 10, 50);

        addItemButton.addActionListener(e -> {
            if (uiInterface != null)
                uiInterface.onClick(AdminButtonClicked.ADD_ITEM);
        });

        addNewConfigFileButton.addActionListener(e -> {
            if (uiInterface != null)
                uiInterface.onClick(AdminButtonClicked.ADD_FILE);
        });

        commentLabel.setText(COMMENT);
        addItemButton.setText(ADD_ITEM);
        addNewConfigFileButton.setText(ADD_FILE);

        add(commentLabel);
        add(addItemButton);
        add(addNewConfigFileButton);
    }

    public void setInterface(AdminUIInterface uiInterface) {
        this.uiInterface = uiInterface;
    }
}
