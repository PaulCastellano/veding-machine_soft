package screens;

import components.*;
import data.Repository;
import model.Data;
import model.Item;
import utils.AdminButtonClicked;
import utils.Utils;
import utils.WindowPosition;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainScreen extends MainFrame implements PaymentFieldInterface, AdminUIInterface {

    private static final Rectangle leftPanelRect = new Rectangle(0, 0, 1250, 500);
    private static final Rectangle rightPanelRect = new Rectangle(1250, 0, 500, 500);

    private PaymentField field = new PaymentField(new Rectangle(100, 20, 300, 120));

    private AdminUI adminUI = new AdminUI(new Rectangle(50, 170, 400, 200));

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

        addToPanel(WindowPosition.RIGHT, adminUI);
        adminUI.setInterface(this);

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
        if (data != null) {
            viewProduct.replaceData(data);
            for (int i = 0; i <= Integer.parseInt(data.getConfig().getColumns()); i++) {
                if (i == 0) {
                    table.getColumn("Row/Column").setCellRenderer(new TextTableRenderer());
                } else {
                    table.getColumn(String.valueOf(i)).setCellRenderer(new TextTableRenderer());
                }
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
        repository.saveData();
    }

    @Override
    public void onClick(AdminButtonClicked clicked) {
        String s = JOptionPane.showInputDialog("Introduce password");
        if (s == null || s.isEmpty() || !s.equals(Utils.ADMIN_PASS)) {
            JOptionPane.showMessageDialog(new Frame(),
                    "Incorrect password",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        switch (clicked) {
            case ADD_FILE:
                String file = JOptionPane.showInputDialog("Enter the file path with data");
                try {
                    repository.setNewDataFromFile(file);
                    repository.saveData();
                    viewProduct.replaceData(repository.getData());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(new Frame(),
                            e.getMessage(),
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    System.err.println(e.getMessage());
                }
                break;
            case ADD_ITEM:
                String productName = JOptionPane.showInputDialog("Enter product name");
                if (productName == null || productName.isEmpty()) {
                    JOptionPane.showMessageDialog(new Frame(),
                            "Field is empty",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int index = repository.getData().getItems().indexOf(new Item(productName, 0, ""));
                if (index >= 0 && index < repository.getData().getItems().size()) {
                    Item item = repository.getData().getItems().get(index);
                    int option = JOptionPane.showConfirmDialog(new Frame(),
                            "Did you like to change that?",
                            "Exist the same product",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        String amount = JOptionPane.showInputDialog("Introduce amount of product", item.getAmount());
                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher matcher = pattern.matcher(amount);
                        if (amount == null || amount.isEmpty() || !matcher.matches()) {
                            JOptionPane.showMessageDialog(new Frame(),
                                    "Field is  or si not a number",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        } else
                            item.setAmount(Integer.parseInt(amount));
                        String price = JOptionPane.showInputDialog("Introduce price of product", item.getPrice());
                        Pattern pattern1 = Pattern.compile("[$][0-9]*[.,][0-9]*");
                        Matcher matcher1 = pattern1.matcher(amount);
                        if (price == null || price.isEmpty() || !matcher1.matches()) {
                            JOptionPane.showMessageDialog(new Frame(),
                                    "Field is empty or is not money format",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        } else
                            item.setPrice(price);
                        repository.saveData();
                        viewProduct.replaceData(repository.getData());
                    }
                } else {
                    boolean finish = false;
                    Item item = new Item(productName, 0, "");
                    while (!finish) {
                        String amount = JOptionPane.showInputDialog("Introduce amount of product", 1);
                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher matcher = pattern.matcher(amount);
                        if (amount == null || amount.isEmpty() || !matcher.matches() || Integer.parseInt(amount) == 0) {
                            JOptionPane.showMessageDialog(new Frame(),
                                    "Field is empty or si not a number",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            finish = true;
                            item.setAmount(Integer.parseInt(amount));
                        }
                    }
                    finish = false;
                    while (!finish) {
                        String price = JOptionPane.showInputDialog("Introduce price of product", item.getPrice());
                        Pattern pattern1 = Pattern.compile("[$][0-9]*[.,][0-9]*");
                        Matcher matcher1 = pattern1.matcher(price);
                        if (price == null || price.isEmpty() || !matcher1.matches()) {
                            JOptionPane.showMessageDialog(new Frame(),
                                    "Field is empty or is not money format",
                                    "ERROR",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            finish = true;
                            item.setPrice(price);
                        }
                    }
                    repository.addItem(item);
                    repository.saveData();
                    viewProduct.replaceData(repository.getData());
                }
                break;
        }
    }
}
