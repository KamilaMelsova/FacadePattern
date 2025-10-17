package ECommerceSystem.demo;
import ECommerceSystem.builder.*;
import ECommerceSystem.factory.*;
import ECommerceSystem.facade.*;
import ECommerceSystem.decorators.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main{public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShow);}
    private static void createAndShow(){
        JFrame frame = new JFrame("E-Commerce Checkout System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(400, 0));
        JTextField itemField = createField("Laptop");
        JTextField priceField = createField("250000");
        JTextField quantityField = createField("1");
        JComboBox<String> paymentCombo = new JComboBox<>(new String[]{"Credit", "PayPal"});
        JCheckBox discountBox = new JCheckBox("Apply 10% discount");
        JCheckBox cashbackBox = new JCheckBox("Apply 5% cashback");
        JCheckBox fraudBox = new JCheckBox("Enable fraud detection", true);
        JButton checkoutBtn = new JButton("Process Checkout");
        JButton clearBtn = new JButton("Clear Log");
        form.add(labeled("Item:", itemField));
        form.add(labeled("Price:", priceField));
        form.add(labeled("Quantity:", quantityField));
        form.add(labeled("Payment Type:", paymentCombo));
        form.add(discountBox);
        form.add(cashbackBox);
        form.add(fraudBox);
        form.add(Box.createVerticalStrut(10));
        form.add(checkoutBtn);
        form.add(clearBtn);
        JTextArea logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(logArea);
        root.add(form, BorderLayout.WEST);
        root.add(scroll, BorderLayout.CENTER);
        frame.setContentPane(root);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        PrintStream guiStream = new PrintStream(new TextAreaOutputStream(logArea), true, StandardCharsets.UTF_8);
        PrintStream originalOut = System.out;
        System.setOut(guiStream);
        CheckoutFacade facade = new CheckoutFacade();
        checkoutBtn.addActionListener(e -> {
            try {
                String item = itemField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int qty = Integer.parseInt(quantityField.getText().trim());
                Order order = new OrderBuilder()
                        .setItem(item)
                        .setPrice(price)
                        .setQuantity(qty)
                        .build();
                String type = ((String) paymentCombo.getSelectedItem()).toLowerCase();
                Payment payment = PaymentFactory.createPayment(type);
                if (discountBox.isSelected()) payment = new DiscountDecorator(payment, 10);
                if (cashbackBox.isSelected()) payment = new CashbackDecorator(payment, 5);
                if (fraudBox.isSelected()) payment = new FraudDetectionDecorator(payment);
                facade.processOrder(order, payment, false, false, false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clearBtn.addActionListener(e -> logArea.setText(""));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.setOut(originalOut);
            }
        });}
    private static JTextField createField(String text) {
        JTextField f = new JTextField(text);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return f;}
    private static JPanel labeled(String text, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(100, 25));
        p.add(label, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return p;}
        private static class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;
        private final StringBuilder buffer = new StringBuilder();
        TextAreaOutputStream(JTextArea area) { this.textArea = area; }
        @Override
        public void write(int b) {write(new byte[]{(byte) b}, 0, 1);}
        @Override
        public void write(byte[] b, int off, int len) {
            String s = new String(b, off, len, StandardCharsets.UTF_8);
            buffer.append(s);
            if (s.contains("\n")) flush();}
        @Override
        public void flush() {
            final String text = buffer.toString();
            buffer.setLength(0);
            SwingUtilities.invokeLater(() -> {
                textArea.append(text);
                textArea.setCaretPosition(textArea.getDocument().getLength());
            });}}}

