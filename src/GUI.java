
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * GUI - Controls the graphical user interface
 *
 * @author Vance Field
 * @version 5-Jan-2017
 */
public class GUI extends JPanel {

    // instance variables //    
    Machine machine = new Machine();

    // gui size
    private final int FRAME_WIDTH = 300;
    private final int FRAME_HEIGHT = 400;

    // arrays
    private final JLabel[] jLabels;
    private final JButton[] jButtons;
    private final JTextPane[] pane;

    private JTextField jtf;
    private JTextArea console;
    private int y = 80;                 // y axis for purchase buttons

    public GUI() {
        jLabels = new JLabel[3];        // 3 labels
        jButtons = new JButton[9];      // 9 buttons
        pane = new JTextPane[2];        // 2 panes
        init();
    }

    private void init() {
        setLayout(null);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setBackground(Color.lightGray);

        // title label
        jLabels[0] = new JLabel("<HTML><U>Vending Machine</U></HTML>");
        jLabels[0].setLocation(100, 25);
        jLabels[0].setSize(100, 25);
        add(jLabels[0]);

        // column labels
        jLabels[1] = new JLabel("Product          Price              Quantity");
        jLabels[1].setLocation(20, 60);
        jLabels[1].setSize(300, 25);
        add(jLabels[1]);

        // console label
        jLabels[2] = new JLabel("Console");
        jLabels[2].setLocation(20, 240);
        jLabels[2].setSize(50, 25);
        add(jLabels[2]);

        // jTextPane for products, prices & remaining    
        pane[0] = new JTextPane();
        pane[0].setLocation(20, 80);
        pane[0].setSize(170, 155);
        pane[0].setEditable(false);
        updatePane();  // method sets the text for the jTextPane
        add(pane[0]);

        // jTextField for console
        console = new JTextArea(
                "Please enter the amount required for your desired product.");
        console.setLocation(20, 260);
        console.setSize(170, 50);
        console.setEditable(false);
        console.setLineWrap(true);
        console.setWrapStyleWord(true);
        add(console);

        // jTextField for inserting coins
        jtf = new JTextField("Insert coins here");
        jtf.setLocation(20, 325);
        jtf.setSize(100, 26);
        add(jtf);

        // purchase buttons
        for (int i = 0; i < 5; i++) {
            jButtons[i] = new JButton("PURCHASE");
            jButtons[i].setMargin(new Insets(0, 0, 0, 0));    // removes margins :)
            jButtons[i].setSize(90, 25);
            jButtons[i].setLocation(200, y);
            y += 33;              // increments the y axis of the buttons by 40
            jButtons[i].addActionListener(new ButtonClickHandler());
            add(jButtons[i]);
        }

        // insert button
        jButtons[5] = new JButton("INSERT");
        jButtons[5].setMargin(new Insets(0, 0, 0, 0));
        jButtons[5].setSize(65, 25);
        jButtons[5].setLocation(125, 325);
        jButtons[5].addActionListener(new ButtonClickHandler());
        add(jButtons[5]);

        // restock button
        jButtons[6] = new JButton("RESTOCK");
        jButtons[6].setMargin(new Insets(0, 0, 0, 0));
        jButtons[6].setSize(90, 25);
        jButtons[6].setLocation(200, 260);
        jButtons[6].addActionListener(new ButtonClickHandler());
        add(jButtons[6]);

        // withdraw button
        jButtons[7] = new JButton("WITHDRAW");
        jButtons[7].setMargin(new Insets(0, 0, 0, 0));
        jButtons[7].setSize(90, 25);
        jButtons[7].setLocation(200, 290);
        jButtons[7].addActionListener(new ButtonClickHandler());
        add(jButtons[7]);
    }

    /**
     * Creates and displays the JFrame
     */
    public void display() {
        JFrame frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setContentPane(this);        // this is JPanel
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.pack();                      // always pack the frame 
        frame.setLocationRelativeTo(null); // sets app to middle of the screen
        frame.setVisible(true);
    }

    /**
     * Sets the text for the JPane
     */
    private void updatePane() {
        // s is the String for the entire JPane
        // includes product, price, and remaining product
        String s = "Coke\t$1.50\t" + machine.remainingCoke() + "\n\n"
                + "Diet Coke\t$1.75\t" + machine.remainingDiet() + "\n\n"
                + "Sprite\t$1.50\t" + machine.remainingSprite() + "\n\n"
                + "Dr. Pepper\t$1.50\t" + machine.remainingDr() + "\n\n"
                + "Water\t$1.00\t" + machine.remainingWater();
        pane[0].setText(s);
    }

    /**
     * Contains all actions performed by buttons
     */
    private class ButtonClickHandler implements ActionListener {

        NumberFormat formatter = new DecimalFormat("#.00");
        double amountEntered = 0.0;
        double change = 0.0;

        /**
         * Handles the tasks of each button
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            try {
                // If coke is purchased
                if (e.getSource() == jButtons[0]) {
                    // if coke is in stock
                    if (machine.inStock(0)) {
                        // takes amount entered
                        amountEntered = Double.parseDouble(jtf.getText());
                        // machine calculates change
                        change = machine.getChange(0, amountEntered);

                        // fail-safe for checking for insufficient funds 
                        if (change == amountEntered) {
                            console.setText("Insufficient funds. Please enter a"
                                    + " correct amount.");
                        } else // resets console text
                        {
                            console.setText("Your product has been dispensed!"
                                    + " Your change is $" + formatter.format(change));
                        }

                    } else {
                        console.setText("Selected product is out of stock. "
                                + "Please restock.");
                    }
                    jtf.setText("");
                    updatePane();   // refresh the pane
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
            // If diet coke is purchased
            if (e.getSource() == jButtons[1]) {
                try {
                    // if diet coke is in stock
                    if (machine.inStock(1)) {
                        // takes amount entered
                        amountEntered = Double.parseDouble(jtf.getText());
                        // machine calculates change
                        change = machine.getChange(1, amountEntered);

                        if (change == amountEntered) {
                            console.setText("Insufficient funds. Please enter a"
                                    + " correct amount.");
                        } else // resets console text
                        {
                            console.setText("Your product has been dispensed!"
                                    + " Your change is $" + formatter.format(change));
                        }
                    } else {
                        console.setText("Selected product is out of stock. "
                                + "Please restock.");
                    }
                    jtf.setText("");
                    updatePane();   // refresh the pane
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            // If sprite is purchased
            if (e.getSource() == jButtons[2]) {
                try {
                    // if sprite is in stock
                    if (machine.inStock(2)) {
                        // takes amount entered
                        amountEntered = Double.parseDouble(jtf.getText());
                        // machine calculates change
                        change = machine.getChange(2, amountEntered);

                        if (change == amountEntered) {
                            console.setText("Insufficient funds. Please enter a"
                                    + " correct amount.");
                        } else // resets console text
                        {
                            console.setText("Your product has been dispensed!"
                                    + " Your change is $" + formatter.format(change));
                        }
                    } else {
                        console.setText("Selected product is out of stock. "
                                + "Please restock.");
                    }
                    jtf.setText("");
                    updatePane();   // refresh the pane
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            // If dr pepper is purchased
            if (e.getSource() == jButtons[3]) {
                try {
                    // if dr pepper is in stock
                    if (machine.inStock(3)) {
                        // takes amount entered
                        amountEntered = Double.parseDouble(jtf.getText());
                        // machine calculates change
                        change = machine.getChange(3, amountEntered);
                        if (change == amountEntered) {
                            console.setText("Insufficient funds. Please enter a"
                                    + " correct amount.");
                        } else // resets console text
                        {
                            console.setText("Your product has been dispensed!"
                                    + " Your change is $" + formatter.format(change));
                        }
                    } else {
                        console.setText("Selected product is out of stock. "
                                + "Please restock.");
                    }
                    jtf.setText("");
                    updatePane();   // refresh the pane
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            // If water is purchased
            if (e.getSource() == jButtons[4]) {
                try {
                    // if water is in stock
                    if (machine.inStock(4)) {
                        // takes amount entered
                        amountEntered = Double.parseDouble(jtf.getText());
                        // machine calculates change
                        change = machine.getChange(4, amountEntered);
                        if (change == amountEntered) {
                            console.setText("Insuffecient funds. Please enter a"
                                    + " correct amount.");
                        } else // resets console text
                        {
                            console.setText("Your product has been dispensed!"
                                    + " Your change is $" + formatter.format(change));
                        }
                    } else {
                        console.setText("Selected product is out of stock. "
                                + "Please restock.");
                    }
                    jtf.setText("");
                    updatePane();   // refresh the pane
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            // If insert is clicked
            if (e.getSource() == jButtons[5]) {
                try {
                    console.setText("Click purchase for your product!");
                } catch (Exception er) {
                    er.printStackTrace();
                }
            }

            // If restock is clicked
            if (e.getSource() == jButtons[6]) {
                String pw = JOptionPane.showInputDialog(null, "Enter machine password.");
                if(pw.equals("admin")){
                    try {
                        String productNum = JOptionPane.showInputDialog(null,
                                "Enter: 0 for Coke | 1 for Diet Coke | 2 for Sprite"
                                + " | 3 for Dr. Pepper | 4 for Water");
                        int prodNum = parseInt(productNum);

                        String restock = JOptionPane.showInputDialog(null,
                                "Enter amount you wish to restock. "
                                        + "(10 max per product)");
                        int amnt = parseInt(restock);

                        // sends your choices to restock method
                        machine.restock(prodNum, amnt);
                        updatePane();
                    } catch (HeadlessException | NumberFormatException er) {
                        er.printStackTrace();
                    } 
                }
                else
                    JOptionPane.showMessageDialog(null, "Invalid password");
            }

            // If withdraw is clicked
            if (e.getSource() == jButtons[7]) {
                String pw = JOptionPane.showInputDialog(null, "Enter machine password.");
                if(pw.equals("admin")){
                    try {
                        String s = JOptionPane.showInputDialog(null,
                                "Enter amount you wish to withdraw. $"
                                + formatter.format(machine.getAmountInMachine())
                                + " in the machine.");
                        double choice3 = parseDouble(s);
                        machine.withdraw(choice3);
                        console.setText(
                                "Withdraw successful. There is $"
                                + formatter.format(machine.getAmountInMachine())
                                + " left in the machine.");
                    } catch (HeadlessException | NumberFormatException er) {
                        er.printStackTrace();
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Invalid password");
            }

        } // actionPerformed

    } // buttonClickHandler 

} // GUI class
