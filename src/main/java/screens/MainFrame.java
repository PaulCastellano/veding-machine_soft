package screens;

import javax.swing.*;
import java.awt.*;

public abstract class MainFrame extends JFrame {

    private static final Dimension windowDimension = new Dimension(1750, 500);

    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowDimension);
        this.setIconImage(new ImageIcon("src/main/resources/vending-machine.png").getImage());
        this.setResizable(false);
    }


}
