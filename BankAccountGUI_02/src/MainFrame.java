import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

public class MainFrame extends JFrame implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7717735913687946436L;

	private final String SETTINGS_FILE = "settings.txt";
	
	private JPanel contentPane;
	private JTextField txtBalance;
	private JButton btnWithdraw;
	private JButton btnDeposit;
	private JButton btnTransactions;
	private JComboBox<String> cmbxAccounts;
	private BankAccount chequing = BankAccountTestData.getChequingBankAccount();
	private BankAccount savings = BankAccountTestData.getSavingsBankAccount();
	private String[] accountNames = {chequing.getAccountName(), savings.getAccountName()};
	private BankAccount selectedAccount;
	private Settings settings = new Settings();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					Serializer s = new Serializer();
					settings.setAccount((String) cmbxAccounts.getSelectedItem());
					s.serialize(settings, SETTINGS_FILE);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
        	}
        });
		
		Serializer s = new Serializer();
		try {
			settings = (Settings)s.deserialize(SETTINGS_FILE);
			if (settings == null) {
				throw new Exception();
			}
		} catch (Exception e1) {
			settings = new Settings();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 262, 255);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setBounds(10, 11, 75, 14);
		contentPane.add(lblAccount);
		
		cmbxAccounts = new JComboBox(accountNames);
		try {
			if (settings.getAccount().equals("chequing")) {
				cmbxAccounts.setSelectedIndex(0);
			}
			else if (settings.getAccount().equals("savings")) {
				cmbxAccounts.setSelectedIndex(1);
			}
			else {
				cmbxAccounts.setSelectedIndex(-1);
			}
		} catch (Exception e) {
			cmbxAccounts.setSelectedIndex(-1);
		}
		cmbxAccounts.setBounds(95, 7, 119, 22);
		contentPane.add(cmbxAccounts);
		cmbxAccounts.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				setControls();
			}
		});
                
		JLabel lblBalance = new JLabel("Balance");
		lblBalance.setBounds(10, 66, 75, 14);
		contentPane.add(lblBalance);
		
		txtBalance = new JTextField();
		txtBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBalance.setEditable(false);
		txtBalance.setBounds(95, 63, 119, 20);
		contentPane.add(txtBalance);
		txtBalance.setColumns(10);
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnWithdrawMouseClicked(e);
			}
		});
		btnWithdraw.setEnabled(false);
		btnWithdraw.setBounds(10, 117, 89, 23);
		contentPane.add(btnWithdraw);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnDepositMouseClicked(e);
			}
		});
		btnDeposit.setEnabled(false);
		btnDeposit.setBounds(124, 117, 89, 23);
		contentPane.add(btnDeposit);
		
		btnTransactions = new JButton("Transactions");
		btnTransactions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnTransactionsMouseClicked(e);
			}
		});
		btnTransactions.setEnabled(false);
		btnTransactions.setBounds(10, 175, 203, 23);
		contentPane.add(btnTransactions);
		
		setControls();
    }
        	
	public void setControls() {
		
		if (cmbxAccounts.getSelectedIndex() != -1) { //checking to see if an account is selected (default is non)
			if (cmbxAccounts.getSelectedItem() == chequing.getAccountName()) {
				selectedAccount = chequing;
			}
			else if (cmbxAccounts.getSelectedItem() == savings.getAccountName()) {
				selectedAccount = savings;
			}
			btnTransactions.setEnabled(true);
			btnDeposit.setEnabled(true);
			if (selectedAccount.isOverDrawn()) {
				txtBalance.setForeground(Color.RED);
				btnWithdraw.setEnabled(false);
			}
			else {
				txtBalance.setForeground(Color.BLACK);
				btnWithdraw.setEnabled(true);
			}
			txtBalance.setText(String.format("%.2f",selectedAccount.getBalance()));
		}
		else {
			btnDeposit.setEnabled(false);
			btnWithdraw.setEnabled(false);
			btnTransactions.setEnabled(false);
		}
	}
        
	//Events
	protected void btnWithdrawMouseClicked(MouseEvent e) {
		TransactionDialog transactionDialog = new TransactionDialog(selectedAccount);
		transactionDialog.setTransactionType("Withdraw");
		transactionDialog.setTitle(transactionDialog.getTransactionType());
		transactionDialog.setLocationRelativeTo(this);
		transactionDialog.setModal(true);
		transactionDialog.setControls();
		transactionDialog.setVisible(true);
		setControls();
	}
        
	protected void btnDepositMouseClicked(MouseEvent e) {
		TransactionDialog transactionDialog = new TransactionDialog(selectedAccount);
		transactionDialog.setTransactionType("Deposit");
		transactionDialog.setTitle(transactionDialog.getTransactionType());
		transactionDialog.setLocationRelativeTo(this);
		transactionDialog.setModal(true);
		transactionDialog.setControls();
		transactionDialog.setVisible(true);
		setControls();
	}
        
	protected void btnTransactionsMouseClicked(MouseEvent e) {
		TransactionListDialog transactionListDialog = new TransactionListDialog(selectedAccount);
		transactionListDialog.setLocationRelativeTo(this);
		transactionListDialog.setModal(true);
		transactionListDialog.setControls();
		transactionListDialog.setVisible(true);
	}
}
