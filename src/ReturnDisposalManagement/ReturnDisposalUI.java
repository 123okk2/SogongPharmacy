package ReturnDisposalManagement;

import StockManagement.*;
import SupplierManagement.*;
import ProductManagement.*;
import RecordManagement.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReturnDisposalUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JButton inquiry;
	private JButton register;
	private JButton update;
	private JButton delete;
	private JButton complete;
	private JScrollPane scrollPane;
	public String[] col;
	public String[][] rows;
	public DefaultTableModel model;
	public JTable table;

	private ReturnDisposalManager rdm;
	private StockManager sm;
	private SupplierManager spm;
	private ProductManager pm;
	private RecordManager rcm;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReturnDisposalUI frame = new ReturnDisposalUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ReturnDisposalUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		col = new String[] { "ID", "��ǰ��", "����", "�ŷ�ó", "�������", "����", "����" };
		model = new DefaultTableModel(rows, col);

		rdm = new ReturnDisposalManager();
		sm = new StockManager();
		spm = new SupplierManager();
		pm = new ProductManager();
		rcm = new RecordManager();

		contentPane.add(getTextField());
		contentPane.add(getInquiry());
		contentPane.add(getRegister());
		contentPane.add(getUpdate());
		contentPane.add(getDelete());
		contentPane.add(getComplete());
		contentPane.add(getScrollPane());

		inquiry.addActionListener(this);
		register.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		complete.addActionListener(this);
	}

	// �� ��ư �� ACTION ����
	@Override
	public void actionPerformed(ActionEvent e) {
		// ��ȸ ACTION
		if ((JButton) e.getSource() == inquiry) {
			rows = rdm.inquiryReturnDisposal(textField.getText());
			model.setDataVector(rows, col);
			table.setModel(model);
		}
		// ��� ACTION
		else if ((JButton) e.getSource() == register) {
			// ��� ȭ�� ȣ��
			RegisterReturnDisposalUI rrd = new RegisterReturnDisposalUI(this, rdm);
			rrd.setVisible(true);
		}
		// ���� ACTION
		else if ((JButton) e.getSource() == update) {
			// ������ �׸��� ���� ��
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "������ �׸��� �������ּ���!");
			}
			// ������ ���� �Ѱ��ְ� ���� ȭ�� ȣ��
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				// �Ϸ� ó���� �׸� ���� �õ� ��
				if (rd.getState().equals("��ǰ�Ϸ�") || rd.getState().equals("���Ϸ�")) {
					JOptionPane.showMessageDialog(null, "�Ϸ�ó���� �׸��� ������ �� �����ϴ�!");
				}
				// ���� UI ȣ��
				else {
					UpdateReturnDisposalUI urd = new UpdateReturnDisposalUI(this, rdm, rd);
					urd.setVisible(true);
				}
			}
		}
		// ���� ACTION
		else if ((JButton) e.getSource() == delete) {
			// ������ �׸��� ���� ��
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "������ �׸��� �������ּ���!");
			}
			// ������ ���� �����ϰ� ��� ���� ������Ʈ
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				Stock stock = sm.getStock(rd.getBarcode());

				// �Ϸ� ó���� �׸� ���� �õ� ��
				if (rd.getState().equals("��ǰ�Ϸ�") || rd.getState().equals("���Ϸ�")) {
					JOptionPane.showMessageDialog(null, "�Ϸ� ó���� �׸��� ������ �� �����ϴ�!");
				}
				// ���� ���� ���� Ȯ��
				else {
					int confirm = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "Ȯ��", JOptionPane.OK_OPTION);
					// ���� ����
					if (confirm == 0) {
						String message = rdm.deleteReturnDisposal(ReturnDisposalId);
						stock.setQuantity(stock.getQuantity() + rd.getQuantity());
						sm.updateStock(rd.getBarcode(), stock.getReceivingDate(), stock);
						ReturnRecord rr = new ReturnRecord(Record.DELETE, pm.getProductName(stock.getProductID()),
								spm.getSupplierName(stock.getSupplierID()));
						rcm.registerRecord(RecordManager.RETURN_RECORD, rr);
						JOptionPane.showMessageDialog(null, message);
					}
				}
			}
		}
		// �Ϸ� ó�� ACTION
		else if ((JButton) e.getSource() == complete) {
			// ������ �׸��� ���� ��
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "�Ϸ� ó���� �׸��� �������ּ���!");
			}
			// ������ �׸��� �Ϸ� ó��
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				Stock stock = sm.getStock(rd.getBarcode());
				// �Ϸ� ó�� ���� ���� Ȯ��
				int confirm = JOptionPane.showConfirmDialog(null, "���� �Ϸ� ó���Ͻðڽ��ϱ�? �Ϸ� ó�� �Ŀ��� ���� �� ������ �Ұ����մϴ�.", "Ȯ��",
						JOptionPane.OK_OPTION);
				if (confirm == 0) {
					String message = rdm.completeReturnDisposal(rd);
					// �̹� �Ϸ� ó���� �׸��� ��
					if (message.equals("�̹� �Ϸ�ó���� �����Դϴ�.")) {
					}
					// �Ϸ� ó�� ����
					else {
						ReturnRecord rr = new ReturnRecord(Record.UPDATE, pm.getProductName(stock.getProductID()),
								spm.getSupplierName(stock.getSupplierID()));
						rcm.registerRecord(RecordManager.RETURN_RECORD, rr);
					}
					JOptionPane.showMessageDialog(null, message);
				}
			}
		}
	}

	// GUI ����
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(12, 10, 575, 45);
			textField.setColumns(10);
		}
		return textField;
	}

	private JButton getInquiry() {
		if (inquiry == null) {
			inquiry = new JButton("\uC870\uD68C");
			inquiry.setBounds(12, 65, 105, 57);
		}
		return inquiry;
	}

	private JButton getRegister() {
		if (register == null) {
			register = new JButton("\uB4F1\uB85D");
			register.setBounds(129, 65, 105, 57);
		}
		return register;
	}

	private JButton getUpdate() {
		if (update == null) {
			update = new JButton("\uC218\uC815");
			update.setBounds(246, 65, 105, 57);
		}
		return update;
	}

	private JButton getDelete() {
		if (delete == null) {
			delete = new JButton("\uC0AD\uC81C");
			delete.setBounds(363, 65, 105, 57);
		}
		return delete;
	}

	private JButton getComplete() {
		if (complete == null) {
			complete = new JButton("\uC644\uB8CC \uCC98\uB9AC");
			complete.setBounds(480, 65, 107, 56);
		}
		return complete;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 132, 575, 297);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(new Object[][] {}, col) {
				Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class,
						Object.class, String.class, String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(5).setResizable(false);
			table.getColumnModel().getColumn(6).setResizable(false);
		}
		return table;
	}
}
