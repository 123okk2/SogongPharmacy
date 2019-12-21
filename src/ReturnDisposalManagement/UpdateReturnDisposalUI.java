package ReturnDisposalManagement;

import StockManagement.*;
import SupplierManagement.*;
import RecordManagement.*;
import ProductManagement.*;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;

public class UpdateReturnDisposalUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel labelTitle;
	private JLabel labelBarcode;
	private JLabel labelSupplier;
	private JLabel labelCount;
	private JLabel labelState;
	private JLabel labelReason;
	private JTextField barcode;
	private JTextField supplier;
	private JSpinner count;
	private JComboBox status;
	private JButton confirm;
	private JButton cancel;
	private JTextField reason;

	private ReturnDisposalManager rdm;
	private StockManager sm;
	private SupplierManager spm;
	private RecordManager rcm;
	private ProductManager pm;

	private ReturnDisposal rd;
	private Stock stock;

	public UpdateReturnDisposalUI(ReturnDisposalUI parent, ReturnDisposalManager rdm, ReturnDisposal rd) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.rdm = rdm;
		this.sm = new StockManager();
		this.spm = new SupplierManager();
		this.pm = new ProductManager();
		this.rcm = new RecordManager();

		this.rd = rd;
		this.stock = sm.getStock(rd.getBarcode());

		contentPane.add(getLabelTitle());
		contentPane.add(getLabelBarcode());
		contentPane.add(getLabelSupplier());
		contentPane.add(getLabelCount());
		contentPane.add(getLabelState());
		contentPane.add(getLabelReason());
		contentPane.add(getBarcode());
		contentPane.add(getSupplier());
		contentPane.add(getCount());
		contentPane.add(getStatus());
		contentPane.add(getReason());
		contentPane.add(getConfirm());
		contentPane.add(getCancel());

		confirm.addActionListener(this);
		cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 수정 버튼 입력 시
		if ((JButton) e.getSource() == confirm) {
			// 정보 입력
			int barcode = rd.getBarcode();
			int supplierID = rd.getSupplierID();
			int quantity = (int) this.count.getValue();
			String state = (String) status.getSelectedItem();
			String reason = this.reason.getText(); 

			// 재고의 수량과 비교 및 1이상 인지 확인
			if (quantity > this.stock.getQuantity()+rd.getQuantity() || quantity < 1) {
				JOptionPane.showMessageDialog(null, "올바른 수량을 입력해주세요!");
			}
			// 등록한 수량만큼 재고량 조절 후 반품 및 폐기 목록에 등록
			else {
				stock.setQuantity(stock.getQuantity() + (rd.getQuantity() - quantity));
				sm.updateStock(barcode, stock.getReceivingDate(), stock);
				rdm.updateReturnDisposal(rd.getReturnDisposalID(), barcode, supplierID, quantity, state, reason);
				ReturnRecord rr = new ReturnRecord(Record.UPDATE, pm.getProductName(stock.getProductID()), supplier.getText());
				rcm.registerRecord(RecordManager.RETURN_RECORD, rr);
				this.setVisible(false);
			}
		}
		// 취소 버튼 입력 시
		else if ((JButton) e.getSource() == cancel) {
			this.setVisible(false);
		}
	}

	private JLabel getLabelTitle() {
		if (labelTitle == null) {
			labelTitle = new JLabel("\uBC18\uD488 \uBC0F \uD3D0\uAE30 \uC218\uC815");
			labelTitle.setFont(new Font("굴림", Font.BOLD, 20));
			labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
			labelTitle.setBounds(12, 10, 410, 30);
		}
		return labelTitle;
	}

	private JLabel getLabelBarcode() {
		if (labelBarcode == null) {
			labelBarcode = new JLabel("\uBC14\uCF54\uB4DC");
			labelBarcode.setLabelFor(getBarcode());
			labelBarcode.setBounds(22, 54, 57, 15);
		}
		return labelBarcode;
	}

	private JLabel getLabelSupplier() {
		if (labelSupplier == null) {
			labelSupplier = new JLabel("\uAC70\uB798\uCC98");
			labelSupplier.setBounds(22, 84, 57, 15);
		}
		return labelSupplier;
	}

	private JLabel getLabelCount() {
		if (labelCount == null) {
			labelCount = new JLabel("\uC218\uB7C9");
			labelCount.setBounds(22, 114, 57, 15);
		}
		return labelCount;
	}

	private JLabel getLabelState() {
		if (labelState == null) {
			labelState = new JLabel("\uC0C1\uD0DC");
			labelState.setBounds(22, 144, 57, 15);
		}
		return labelState;
	}

	private JLabel getLabelReason() {
		if (labelReason == null) {
			labelReason = new JLabel("\uC0AC\uC720");
			labelReason.setBounds(22, 174, 57, 15);
		}
		return labelReason;
	}

	private JTextField getBarcode() {
		if (barcode == null) {
			barcode = new JTextField();
			barcode.setText(String.valueOf(rd.getBarcode()));
			barcode.setEditable(false);
			barcode.setBounds(91, 50, 139, 23);
			barcode.setColumns(10);
		}
		return barcode;
	}

	private JTextField getSupplier() {
		if (supplier == null) {
			supplier = new JTextField();
			supplier.setText(spm.getSupplierName(stock.getSupplierID()));
			supplier.setEditable(false);
			supplier.setBounds(91, 80, 139, 23);
			supplier.setColumns(10);
		}
		return supplier;
	}

	private JSpinner getCount() {
		if (count == null) {
			count = new JSpinner();
			count.setValue(rd.getQuantity());
			count.setBounds(91, 110, 139, 23);
		}
		return count;
	}

	private JComboBox getStatus() {
		if (status == null) {
			status = new JComboBox();
			if(rd.getState().equals("반품대기")) {
				status.setModel(new DefaultComboBoxModel(new String[] { "반품대기", "폐기대기" }));				
			}
			else if(rd.getState().equals("폐기대기")) {
				status.setModel(new DefaultComboBoxModel(new String[] { "폐기대기", "반품대기" }));
			}
			status.setBounds(91, 140, 139, 23);
		}
		return status;
	}

	private JTextField getReason() {
		if (reason == null) {
			reason = new JTextField();
			reason.setColumns(10);
			reason.setBounds(91, 171, 139, 23);
		}
		return reason;
	}

	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uC218\uC815");
			confirm.setBounds(252, 206, 79, 45);
		}
		return confirm;
	}

	private JButton getCancel() {
		if (cancel == null) {
			cancel = new JButton("\uCDE8\uC18C");
			cancel.setBounds(343, 206, 79, 45);
		}
		return cancel;
	}
}