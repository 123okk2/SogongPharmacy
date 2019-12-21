package BaselineDataManagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;

public class SellerManagerUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SellerManagerUI frame = new SellerManagerUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public SellerManagerUI() {
		SellerManager sm = new SellerManager();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615,478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());
		
		
		JPanel panel = new JPanel();
		panel.setBounds(67, 10, 522, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\uD310\uB9E4\uC790 \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("����", Font.BOLD, 25));
		label.setBounds(82, 10, 294, 37);
		panel.add(label);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(67, 91, 522, 127);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_1 = new JLabel("\uD310\uB9E4\uC790 \uC774\uB984 : ");
		label_1.setFont(new Font("����", Font.PLAIN, 15));
		label_1.setBounds(35, 13, 109, 35);
		panel_1.add(label_1);
		
		JTextField textField = new JTextField();
		textField.setBounds(138, 10, 185, 41);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JButton registerButton = new JButton("���");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayRegisterSellerUI(sm);
				
			}
		});
		registerButton.setFont(new Font("����", Font.PLAIN, 15));
		registerButton.setBounds(138, 74, 109, 41);
		panel_1.add(registerButton);
		
		JButton updateButton = new JButton("����");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int checkcnt = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					if ((boolean) table.getValueAt(i, 0) == true) {
						checkcnt++;
					}
				}
				if (checkcnt == 0) {
					JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				} else if (checkcnt == 1) {
					try {
						displayUpdateSellerUI(sm);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "1���� ������ �ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		updateButton.setFont(new Font("����", Font.PLAIN, 15));
		updateButton.setBounds(270, 74, 109, 41);
		panel_1.add(updateButton);
		
		JButton inquiryButton = new JButton("��ȸ");
		inquiryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=textField.getText();
				ArrayList<Seller> sl=new ArrayList<>();
				sl=sm.inquirySeller(str);
				
				//table�� ���� �߰� �ϴ� �κ�
				DefaultTableModel model = (DefaultTableModel) table.getModel(); // DefaultTableModelŬ������ ���̺��� ���� get�ϰ� 
				model.setNumRows(0); //���̺� �ʱ�ȭ
				for(int i=0;i<sl.size();i++) {
					//�� �߰�
					model.addRow(new Object[]{false, sl.get(i).getName() , sl.get(i).getPhoneNum()});
					}
			}
		});
		inquiryButton.setFont(new Font("����", Font.PLAIN, 15));
		inquiryButton.setBounds(12, 74, 109, 41);
		panel_1.add(inquiryButton);
		
		
		JButton deleteButton = new JButton("����");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int checkcnt = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					if ((boolean) table.getValueAt(i, 0) == true) {
						checkcnt++;
					}
				}
				if (checkcnt == 0) {
					JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (checkcnt == 1) {
					boolean chk = true;
					chk = sm.deleteSeller((String)table.getValueAt(table.getSelectedColumn(),1));
					if (chk == true)
						JOptionPane.showMessageDialog(null, "���õ� �׸��� �����Ǿ����ϴ�.", "���� ����",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "���õ� �׸��� ���������߽��ϴ�", "���� ����",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "1���� ������ �ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		deleteButton.setFont(new Font("����", Font.PLAIN, 15));
		deleteButton.setBounds(411, 74, 109, 41);
		panel_1.add(deleteButton);
	
		
	
		
		
		
		
	}
	
	protected String String(Object valueAt) {
		// TODO Auto-generated method stub
		return null;
	}

	public void displayRegisterSellerUI(SellerManager sm) {
		//�԰� ��� ȭ�� ȣ��
		RegisterSellerUI newScr=new RegisterSellerUI(sm);
		newScr.setVisible(true);
	}
	
	public void displayUpdateSellerUI(SellerManager sm) throws ParseException {
		//�԰� ���� ȭ�� ȣ��
		Seller s = new Seller();
		
		for(int i=0;i<table.getRowCount();i++) {
			if(table.getValueAt(i,0).equals(true)) {
				String name =  (String) table.getValueAt(i, 1);
				String phoneNum = (String) table.getValueAt(i, 2);
				
				s = new Seller(name,phoneNum);
				break;
			}
		}
		UpdateSellerUI newScr=new UpdateSellerUI(s,sm);
		newScr.setVisible(true);
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(67, 228, 522, 203);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"",  "�Ǹ��� �̸�", "��ȭ ��ȣ"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			
		}
		return table;
	}
}
