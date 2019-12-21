package ReceivingManagement;
import StockManagement.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import StockManagement.StockConditionManager;
import StockManagement.StockManager;
import SupplierManagement.SupplierManager;
import ProductManagement.ProductManager;

public class RegisterReceivingUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField pro_name;
	private JSpinner enr_date;
	private JSpinner cnt_pro;
	private JSpinner exp_date;
	private JTextField bar_cod;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField from_wh;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enr_pro frame = new enr_pro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public RegisterReceivingUI(ReceivingManager rm, StockManager sm, StockConditionManager scm) {
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getLabel());
		contentPane.add(getLabel_1());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getLblNewLabel_2());
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getPro_name());
		contentPane.add(getEnr_date());
		contentPane.add(getCnt_pro());
		contentPane.add(getExp_date());
		contentPane.add(getBar_cod());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getLabel_2());
		contentPane.add(getFrom_wh());
		
		enr_date.setValue(new Date());
		exp_date.setValue(new Date());

		
		cancles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		confirm.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					if(new ProductManager().getProductID(pro_name.getText())==0) {
						JOptionPane.showMessageDialog(null, "�������� �ʴ� ��ǰ�Դϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						return;
					}
					if(new SupplierManager().getSupplierID(from_wh.getText())==0) {
						JOptionPane.showMessageDialog(null, "�������� �ʴ� �ŷ�ó�Դϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						return;
					}
					
					
					String enrDate=enr_date.getValue().toString();
					String expDate=exp_date.getValue().toString();
					
					int yy=Integer.parseInt(enrDate.substring(24,28));
					int yy2=Integer.parseInt(expDate.substring(24,28));
					String mmm=enrDate.substring(4,7);
					String mmm2=expDate.substring(4,7);
					int mm=0, mm2=0;
					int dd=Integer.parseInt(enrDate.substring(8, 10));
					int dd2=Integer.parseInt(expDate.substring(8,10));
					
					String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
					
					for(int i=0;i<months.length;i++) {
						if(mmm.equals(months[i])) mm=i;
						if(mmm2.equals(months[i])) mm2=i;
						if(mm!=0 && mm2!=0) break;
					}
					
					
					Boolean chk=rm.RegisterReceiving(new Receiving(1, Integer.parseInt(bar_cod.getText()), Integer.parseInt(cnt_pro.getValue().toString()),
							new Date(yy-1900,mm,dd), new Date(yy2-1900,mm2,dd2),
							new SupplierManager().getSupplierID(from_wh.getText()), new ProductManager().getProductID(pro_name.getText())));
					
					if(chk.equals(false)) {
						JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ��Ͽ� �����߽��ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
						dispose();
						return;
					}
					
					chk=sm.registerStock(new Stock(Integer.parseInt(bar_cod.getText()), Integer.parseInt(cnt_pro.getValue().toString()),
							new Date(yy-1900,mm,dd), new Date(yy2-1900,mm2,dd2),
							new SupplierManager().getSupplierID(from_wh.getText()), new ProductManager().getProductID(pro_name.getText())));
					
					if(chk.equals(false)) {
						JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ��Ͽ� �����߽��ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
						rm.deleteReceiving(pro_name.getText(), Integer.parseInt(cnt_pro.getValue().toString()), new Date(yy-1900,mm,dd));
						dispose();
						return;
					}
					
					chk=scm.updateStockCondition(pro_name.getText(), Integer.parseInt(cnt_pro.getValue().toString()));
					
					if(chk.equals(true)) JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ���������� ��ϵǾ����ϴ�.", "��� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
					else {
						JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ��Ͽ� �����߽��ϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
						rm.deleteReceiving(pro_name.getText(), Integer.parseInt(cnt_pro.getValue().toString()), new Date(yy-1900,mm,dd));
						sm.deleteStock(new ProductManager().getProductID(pro_name.getText()), Integer.parseInt(bar_cod.getText()), new Date(yy-1900,mm,dd), new Date(yy2-1900,mm2,dd2));
					}
				}
				catch(NumberFormatException er) {
					JOptionPane.showMessageDialog(null, "�Է°��� ������ �ùٸ��� ���մϴ�..", "��� ����", JOptionPane.INFORMATION_MESSAGE);
					er.printStackTrace();
				}
				dispose();
			}
		});
		
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("\uC785\uACE0 \uB4F1\uB85D");
			lblNewLabel.setFont(new Font("����", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(12, 10, 410, 30);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("\uC0C1\uD488\uBA85");
			label.setLabelFor(getPro_name());
			label.setBounds(12, 50, 57, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("\uC0C1\uD488\uC218\uB7C9");
			label_1.setBounds(12, 75, 57, 15);
		}
		return label_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("\uC785\uACE0\uC77C");
			lblNewLabel_1.setBounds(12, 100, 57, 15);
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("\uC720\uD1B5\uAE30\uD55C");
			lblNewLabel_2.setBounds(12, 125, 57, 15);
		}
		return lblNewLabel_2;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("\uBC14\uCF54\uB4DC");
			lblNewLabel_3.setBounds(12, 150, 57, 15);
		}
		return lblNewLabel_3;
	}
	private JTextField getPro_name() {
		if (pro_name == null) {
			pro_name = new JTextField();
			pro_name.setBounds(81, 47, 139, 21);
			pro_name.setColumns(10);
		}
		return pro_name;
	}
	private JSpinner getEnr_date() {
		if (enr_date == null) {
		    Calendar calendar = Calendar.getInstance();//��¥
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			enr_date = new JSpinner(new SpinnerDateModel(value, start, end, Calendar.YEAR));
		    enr_date.setEditor(new JSpinner.DateEditor(enr_date, "yyyy-MM-dd"));
			enr_date.setBounds(81, 97, 139, 22);
		}
		return enr_date;
	}
	private JSpinner getCnt_pro() {
		if (cnt_pro == null) {
			cnt_pro = new JSpinner();
			cnt_pro.setBounds(81, 72, 139, 22);
		}
		return cnt_pro;
	}
	private JSpinner getExp_date() {
		if (exp_date == null) {
		    Calendar calendar = Calendar.getInstance();//��¥
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			exp_date = new JSpinner();
			exp_date.setModel(new SpinnerDateModel(value, start, end, Calendar.YEAR));
		    exp_date.setEditor(new JSpinner.DateEditor(exp_date, "yyyy-MM-dd"));
			exp_date.setBounds(81, 122, 139, 22);
		}
		return exp_date;
	}
	private JTextField getBar_cod() {
		if (bar_cod == null) {
			bar_cod = new JTextField();
			bar_cod.setBounds(81, 147, 139, 21);
			bar_cod.setColumns(10);
		}
		return bar_cod;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uB4F1\uB85D");
			confirm.setBounds(252, 206, 79, 45);
		}
		return confirm;
	}
	private JButton getCancles() {
		if (cancles == null) {
			cancles = new JButton("\uCDE8\uC18C");
			cancles.setBounds(343, 206, 79, 45);
		}
		return cancles;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("\uAC70\uB798\uCC98");
			label_2.setBounds(12, 175, 57, 15);
		}
		return label_2;
	}
	private JTextField getFrom_wh() {
		if (from_wh == null) {
			from_wh = new JTextField();
			from_wh.setBounds(81, 172, 139, 21);
			from_wh.setColumns(10);
		}
		return from_wh;
	}
}