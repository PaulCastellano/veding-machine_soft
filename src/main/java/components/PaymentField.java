package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PaymentField extends JPanel {

    private static final String enteredText = "Money: $";
    private static final String buttonText = "Select";

    JLabel  enteredPayment = new JLabel();
    JTextField paymentSelected = new JTextField();
    JButton selectButton = new JButton();

    PaymentFieldInterface fieldInterface;

    float money = 0;

    public PaymentField(Rectangle rectangle) {
        super();
        this.setBounds(rectangle);
        this.setLayout(null);

        enteredPayment.setBounds(new Rectangle(0, 0, rectangle.width, 20));
        paymentSelected.setBounds(new Rectangle(0, 30, rectangle.width, rectangle.height - 100));
        selectButton.setBounds(new Rectangle(0, rectangle.height - 70, rectangle.width, 50));

        paymentSelected.setForeground(Color.GRAY);
        paymentSelected.setText("Money");

        enteredPayment.setText(enteredText + 0);

        selectButton.setText(buttonText);
        selectButton.addActionListener(e -> {
            if ((paymentSelected.getText().equals("Money") || paymentSelected.getText().isEmpty()) && money <= 0) {
                JOptionPane.showMessageDialog(new Frame(),
                        "Please enter money",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (fieldInterface != null)
                fieldInterface.onClick(paymentSelected.getText());
        });

        paymentSelected.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (paymentSelected.getText().equals("Money")) {
                    paymentSelected.setText("");
                    paymentSelected.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (paymentSelected.getText().isEmpty()) {
                    paymentSelected.setForeground(Color.GRAY);
                    paymentSelected.setText("Money");
                }
            }
        });

        add(enteredPayment);
        add(paymentSelected);
        add(selectButton);
    }

    public void setFieldInterface(PaymentFieldInterface fieldInterface) {
        this.fieldInterface = fieldInterface;
    }

    public void addPayment(float money) {
        this.money += money;
        this.enteredPayment.setText(enteredText + this.money);
    }

    public void removeMoney(float money) {
        this.money -= money;
        this.enteredPayment.setText(enteredText + this.money);
    }

    public float getMoney() {
        return this.money;
    }
}
