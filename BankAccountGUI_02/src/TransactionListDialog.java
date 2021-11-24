import java.awt.BorderLayout;
import java.time.LocalDateTime;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionListDialog extends JDialog {
        private final JPanel contentPanel = new JPanel();
        private BankAccount selectedAccount = new BankAccount("selectedAccount");
        private JCheckBox chckbxStartDate;
        private JCheckBox chckbxEndDate;
        private JComboBox<String> cmbxStartMonth;
        private JComboBox<String> cmbxEndMonth;
        private JComboBox<String> cmbxEndDay;
        private JComboBox<String> cmbxStartDay;
        private JComboBox<String> cmbxStartYear;
        private JComboBox<String> cmbxEndYear;
        private JTextArea txtrTransactions;
        private JScrollPane scrlTransactions;
        private String month[] = {"Month","01","02","03","04","05","06","07","08","09","10","11","12"};
        private String day[] = {"Day","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        private String year[] = {"Year","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021"};
        private JLabel lblStartDateWarning;
        private JLabel lblEndDateWarning;
        /**
         * Create the dialog.
         */
        public TransactionListDialog(BankAccount selectedAccount) {
                this.selectedAccount = selectedAccount;
                
                setBounds(100, 100, 423, 348);
                getContentPane().setLayout(new BorderLayout());
                contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
                getContentPane().add(contentPanel, BorderLayout.CENTER);
                contentPanel.setLayout(null);
                
                chckbxStartDate = new JCheckBox("Start date:");
                chckbxStartDate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                chckbxStartDateMouseClicked(e);
                        }
                });
                chckbxStartDate.setBounds(6, 15, 97, 23);
                contentPanel.add(chckbxStartDate);
                
                chckbxEndDate = new JCheckBox("End date:");
                chckbxEndDate.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                chckbxEndDateMouseClicked(e);
                        }
                });
                chckbxEndDate.setBounds(6, 60, 97, 23);
                contentPanel.add(chckbxEndDate);
                
                cmbxStartMonth = new JComboBox(month);
                cmbxStartMonth.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxStartMonth.setEnabled(false);
                cmbxStartMonth.setBounds(114, 15, 71, 22);
                contentPanel.add(cmbxStartMonth);
                
                cmbxEndMonth = new JComboBox(month);
                cmbxEndMonth.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxEndMonth.setEnabled(false);
                cmbxEndMonth.setBounds(114, 60, 71, 22);
                contentPanel.add(cmbxEndMonth);
                
                cmbxEndDay = new JComboBox(day);
                cmbxEndDay.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxEndDay.setEnabled(false);
                cmbxEndDay.setBounds(195, 60, 59, 22);
                contentPanel.add(cmbxEndDay);
                
                cmbxStartDay = new JComboBox(day);
                cmbxStartDay.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxStartDay.setEnabled(false);
                cmbxStartDay.setBounds(195, 15, 59, 22);
                contentPanel.add(cmbxStartDay);
                
                cmbxStartYear = new JComboBox(year);
                cmbxStartYear.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxStartYear.setEnabled(false);
                cmbxStartYear.setBounds(264, 15, 80, 22);
                contentPanel.add(cmbxStartYear);
                
                cmbxEndYear = new JComboBox(year);
                cmbxEndYear.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		setControls();
                	}
                });
                cmbxEndYear.setEnabled(false);
                cmbxEndYear.setBounds(264, 60, 80, 22);
                contentPanel.add(cmbxEndYear);
                
                txtrTransactions = new JTextArea();
                txtrTransactions.setEditable(false);
                txtrTransactions.setFont(new Font("Monospaced", Font.PLAIN, 10));
                txtrTransactions.setBounds(6, 75, 363, 175);
                contentPanel.add(txtrTransactions);
                
                scrlTransactions = new JScrollPane (txtrTransactions);
                scrlTransactions.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrlTransactions.setBounds(6, 123, 363, 175);
                contentPanel.add(scrlTransactions);
                
                lblStartDateWarning = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
                lblStartDateWarning.setToolTipText("Enter a valid date.");
                lblStartDateWarning.setBounds(354, 6, 46, 38);
                lblStartDateWarning.setVisible(false);
                contentPanel.add(lblStartDateWarning);
                
                lblEndDateWarning = new JLabel(UIManager.getIcon("OptionPane.warningIcon"));
                lblEndDateWarning.setToolTipText("Enter a valid date.");
                lblEndDateWarning.setBounds(354, 52, 46, 37);
                lblEndDateWarning.setVisible(false);
                contentPanel.add(lblEndDateWarning);
                
                setControls();
        }
        
        public void setControls() {
                LocalDateTime startDateTime = null;
                LocalDateTime endDateTime = null;
                
                if (chckbxStartDate.isSelected()) {
                        cmbxStartMonth.setEnabled(true);
                        cmbxStartDay.setEnabled(true);
                        cmbxStartYear.setEnabled(true);
                }
                else {
                        cmbxStartMonth.setEnabled(false);
                        cmbxStartDay.setEnabled(false);
                        cmbxStartYear.setEnabled(false);
                }
                if (chckbxEndDate.isSelected()) {
                        cmbxEndMonth.setEnabled(true);
                        cmbxEndDay.setEnabled(true);
                        cmbxEndYear.setEnabled(true);
                }
                else {
                        cmbxEndMonth.setEnabled(false);
                        cmbxEndDay.setEnabled(false);
                        cmbxEndYear.setEnabled(false);
                }
                boolean startDateSelected = false;
                boolean endDateSelected = false;
                
                if (cmbxStartMonth.getSelectedIndex() != 0 && cmbxStartDay.getSelectedIndex() != 0 && cmbxStartYear.getSelectedIndex() != 0) { 
                        startDateSelected = true;
                }
                if (cmbxEndMonth.getSelectedIndex() != 0 && cmbxEndDay.getSelectedIndex() != 0 && cmbxEndYear.getSelectedIndex() != 0) {
                        endDateSelected = true;
                }
                
                final String HEADER = "     Date     Time       Amount            Description\n---------------------------------------------------------";
                String transactions = "";
                lblStartDateWarning.setVisible(false);
                lblEndDateWarning.setVisible(false);
                
                if (startDateSelected && chckbxStartDate.isSelected()) {
                        try {
                        startDateTime = LocalDateTime.parse(String.format("%s-%s-%sT00:00:00",cmbxStartYear.getSelectedItem(),cmbxStartMonth.getSelectedItem(),cmbxStartDay.getSelectedItem()));
                        } catch (Exception e) {
                                lblStartDateWarning.setVisible(true);
                        }
                }
                else {
                        startDateTime = null; 
                }
                if (endDateSelected && chckbxEndDate.isSelected()) {
                        try {
                                endDateTime = LocalDateTime.parse(String.format("%s-%s-%sT23:59:59",cmbxEndYear.getSelectedItem(),cmbxEndMonth.getSelectedItem(),cmbxEndDay.getSelectedItem()));
                        } catch (Exception e) {
                                lblEndDateWarning.setVisible(true);
                        }
                }
                else {
                        endDateTime = null;
                }
                
                for (int i = 0; i < selectedAccount.getTransactions(startDateTime, endDateTime).size(); i++) {
                        Transaction transaction = selectedAccount.getTransactions(startDateTime, endDateTime).get(i); 
                        transactions += String.format("\n %-20.19s %9.2f %25s" ,transaction.getTransactionTime().toString().replace("T", " "),transaction.getAmount(),transaction.getDescription());
                }
                if (startDateTime != null && endDateTime != null && endDateTime.isBefore(startDateTime)) {
                txtrTransactions.setForeground(Color.red);
                txtrTransactions.setText("Invalid date range.");
                }
                else {
                        txtrTransactions.setForeground(Color.black);
                        txtrTransactions.setText(HEADER + transactions);
                //scrlTransactions.getVerticalScrollBar().setValue(0); BAR IS JUMPTING TO BOTTOM EVERYTIME TEXT IS UPDATED
                }
        }
        //Events
        protected void chckbxStartDateMouseClicked(MouseEvent e) {
                setControls();
        }
        protected void chckbxEndDateMouseClicked(MouseEvent e) {
                setControls();
        }
}