package screens;

import components.PaymentField;
import components.PaymentFieldInterface;
import components.TableViewProduct;
import components.TextTableRenderer;
import data.Repository;
import model.Data;
import model.Item;
import utils.WindowPosition;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainScreen extends MainFrame implements PaymentFieldInterface {

    private static final Rectangle leftPanelRect = new Rectangle(0, 0, 1250, 500);
    private static final Rectangle rightPanelRect = new Rectangle(1250, 0, 500, 500);

    private PaymentField field = new PaymentField(new Rectangle(100, 20, 300, 120));

    private TableViewProduct viewProduct = new TableViewProduct();
    private JTable table;

    private JPanel leftPanel = new JPanel();
    private JPanel rightPanel = new JPanel();

    private Repository repository;

    public MainScreen(String title, Repository repository) throws HeadlessException {
        super(title);
        this.repository = repository;

        setLayout(null);

        leftPanel.setLayout(new FlowLayout());
        rightPanel.setLayout(null);

        leftPanel.setBounds(leftPanelRect);
        rightPanel.setBounds(rightPanelRect);

        createTableView();
        addToPanel(WindowPosition.RIGHT, field);
        field.setFieldInterface(this);

        addPanels();

        setVisible(true);

        addData(repository.getData());
    }

    public void addToPanel(WindowPosition position, Component component) {
        switch (position) {
            case LEFT:
                leftPanel.add(component);
                break;
            case RIGHT:
                rightPanel.add(component);
                break;
        }
    }

    public void addPanels() {
        add(leftPanel);
        add(rightPanel);
    }

    private void createTableView() {
        table = new JTable(viewProduct);
        table.setPreferredScrollableViewportSize(new Dimension(leftPanelRect.width, leftPanelRect.height));
        table.setFillsViewportHeight(true);
        table.setRowHeight(50);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        addToPanel(WindowPosition.LEFT, scrollPane);
    }

    private void addData(Data data) {
        viewProduct.replaceData(data);
        for (int i = 0; i <= Integer.parseInt(data.getConfig().getColumns()); i++) {
            if (i == 0) {
                table.getColumn("Row/Column").setCellRenderer(new TextTableRenderer());
            } else {
                table.getColumn(String.valueOf(i)).setCellRenderer(new TextTableRenderer());
            }
        }
    }

    @Override
    public void onClick(String s) {
        Pattern pattern1 = Pattern.compile("[0-9]*[.,][0-9]*");
        Matcher matcher1 = pattern1.matcher(s);
        boolean b = matcher1.matches();
        if (!b && field.getMoney() <= 0) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Enter money normal  format.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!s.equals("Money")) {
            field.addPayment(Float.parseFloat(s));
        }
        String index = JOptionPane.showInputDialog("Select the product from table", "A-1");
        if (index == null || index.isEmpty()) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Invalid line/column format.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Pattern pattern = Pattern.compile("[A-Z]-[0-9]");
        Matcher matcher = pattern.matcher(index);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Invalid line/column format.",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int line = index.substring(0, 1).charAt(0) - 'A';
        int col = Integer.parseInt(index.substring(2, 3)) - 1;
        Item item = repository.getItemByIndex(line * Integer.parseInt(repository.getData().getConfig().getColumns()) + col);
        if (item == null || item.getAmount() <= 0) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Is empty",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Float money = field.getMoney();
        if (money < Float.parseFloat(item.getPrice().replace("$", ""))) {
            JOptionPane.showMessageDialog(new Frame(),
                    "There is not enough money",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        field.removeMoney(Float.parseFloat(item.getPrice().replace("$", "")));
        item.setAmount(item.getAmount() - 1);
        viewProduct.replaceData(repository.getData());
    }
}
