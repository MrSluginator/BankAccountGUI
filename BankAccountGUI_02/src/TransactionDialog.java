import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransactionDialog extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private JTextField txtAmount;
        private JTextField txtDescription;
        private JLabel lblAmount;
        private JLabel lblWarning;
        private BankAccount selectedAccount = new BankAccount("Selected Account");
        private String transactionType;
        /**
         * Create the dialog.
         */
        public TransactionDialog(BankAccount selectedAccount) {
                this.selectedAccount = selectedAccount;
                
                setBounds(100, 100, 300, 207);
                getContentPane().setLayout(new BorderLayout());
                contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                getContentPane().add(contentPanel, BorderLayout.CENTER);
                contentPanel.setLayout(null);
                
                lblAmount = new JLabel("Please provide the amount to be _______:");
                lblAmount.setBounds(10, 11, 264, 20);
                contentPanel.add(lblAmount);
                
                txtAmount = new JTextField();
                txtAmount.setBounds(10, 42, 212, 20);
                contentPanel.add(txtAmount);
                txtAmount.setColumns(10);
                
                JLabel lblDescription = new JLabel("(Optional) provide a description:");
                lblDescription.setBounds(10, 73, 224, 14);
                contentPanel.add(lblDescription);
                txtDescription = new JTextField();
                txtDescription.setBounds(10, 98, 212, 20);
                contentPanel.add(txtDescription);
                txtDescription.setColumns(10);
                
                lblWarning = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
                lblWarning.setToolTipText("Enter a numerical amount.");
                lblWarning.setBounds(228, 31, 46, 42);
                lblWarning.setVisible(false);
                contentPanel.add(lblWarning);
                
                        JPanel buttonPane = new JPanel();
                        getContentPane().add(buttonPane, BorderLayout.SOUTH);
                        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                        {
                                JButton okButton = new JButton("OK");
                                okButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                                okButtonMouseClicked(e);
                                        }
                                });
                                okButton.setActionCommand("OK");
                                buttonPane.add(okButton);
                                getRootPane().setDefaultButton(okButton);
                        }
                        {
                                JButton cancelButton = new JButton("Cancel");
                                cancelButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                                cancelButtonMouseClicked(e);
                                        }
                                });
                                cancelButton.setActionCommand("Cancel");
                                buttonPane.add(cancelButton);
                        }
        }
        public static boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }
            try {
                double d = Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }
        
        public void setControls() {
                if (transactionType == "Withdraw") {
                        lblAmount.setText("Please provide the amount to be withdrawn:");
                }
                else {
                        lblAmount.setText("Please provide the amount to be deposited:");
                }
                
                boolean amountNotGiven = this.txtAmount.getText().equals("");
                lblWarning.setVisible(amountNotGiven == true && isNumeric(txtAmount.getText()));
        }
        
        public String getTransactionType() {
                return transactionType;
        }
        
        public void setTransactionType(String transactionType) {
                this.transactionType = transactionType;
        }
        
        protected void okButtonMouseClicked(MouseEvent e) {
                if (isNumeric(txtAmount.getText())) {
                        if (transactionType == "Withdraw") {
                                selectedAccount.withdraw(Double.parseDouble(txtAmount.getText()), txtDescription.getText());
                        }
                        else {
                                selectedAccount.deposit(Double.parseDouble(txtAmount.getText()), txtDescription.getText());
                        }
                        this.setVisible(false);
                }
                else {
                        JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "INVALID", JOptionPane.WARNING_MESSAGE);
                        txtAmount.setText("");
                }
        }
        
        protected void cancelButtonMouseClicked(MouseEvent e) {
                this.setVisible(false);
        }
}

