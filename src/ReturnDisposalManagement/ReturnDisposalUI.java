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
		col = new String[] { "ID", "상품명", "수량", "거래처", "유통기한", "상태", "사유" };
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

	// 각 버튼 별 ACTION 설정
	@Override
	public void actionPerformed(ActionEvent e) {
		// 조회 ACTION
		if ((JButton) e.getSource() == inquiry) {
			rows = rdm.inquiryReturnDisposal(textField.getText());
			model.setDataVector(rows, col);
			table.setModel(model);
		}
		// 등록 ACTION
		else if ((JButton) e.getSource() == register) {
			// 등록 화면 호출
			RegisterReturnDisposalUI rrd = new RegisterReturnDisposalUI(this, rdm);
			rrd.setVisible(true);
		}
		// 수정 ACTION
		else if ((JButton) e.getSource() == update) {
			// 선택한 항목이 없을 시
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요!");
			}
			// 선택한 값을 넘겨주고 수정 화면 호출
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				// 완료 처리된 항목 수정 시도 시
				if (rd.getState().equals("반품완료") || rd.getState().equals("폐기완료")) {
					JOptionPane.showMessageDialog(null, "완료처리된 항목은 수정할 수 없습니다!");
				}
				// 수정 UI 호출
				else {
					UpdateReturnDisposalUI urd = new UpdateReturnDisposalUI(this, rdm, rd);
					urd.setVisible(true);
				}
			}
		}
		// 삭제 ACTION
		else if ((JButton) e.getSource() == delete) {
			// 선택한 항목이 없을 시
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제할 항목을 선택해주세요!");
			}
			// 선택한 값을 삭제하고 재고 수량 업데이트
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				Stock stock = sm.getStock(rd.getBarcode());

				// 완료 처리된 항목 삭제 시도 시
				if (rd.getState().equals("반품완료") || rd.getState().equals("폐기완료")) {
					JOptionPane.showMessageDialog(null, "완료 처리된 항목은 삭제할 수 없습니다!");
				}
				// 삭제 여부 최종 확인
				else {
					int confirm = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "확인", JOptionPane.OK_OPTION);
					// 삭제 진행
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
		// 완료 처리 ACTION
		else if ((JButton) e.getSource() == complete) {
			// 선택한 항목이 없을 시
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "완료 처리할 항목을 선택해주세요!");
			}
			// 선택한 항목을 완료 처리
			else {
				String id = table.getValueAt(table.getSelectedRow(), 0).toString();
				int ReturnDisposalId = Integer.parseInt(id);
				ReturnDisposal rd = rdm.searchReturnDisposal(ReturnDisposalId);
				Stock stock = sm.getStock(rd.getBarcode());
				// 완료 처리 여부 최종 확인
				int confirm = JOptionPane.showConfirmDialog(null, "정말 완료 처리하시겠습니까? 완료 처리 후에는 수정 및 삭제가 불가능합니다.", "확인",
						JOptionPane.OK_OPTION);
				if (confirm == 0) {
					String message = rdm.completeReturnDisposal(rd);
					// 이미 완료 처리된 항목일 시
					if (message.equals("이미 완료처리된 상태입니다.")) {
					}
					// 완료 처리 진행
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

	// GUI 생성
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
