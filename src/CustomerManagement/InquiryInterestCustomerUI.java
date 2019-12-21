package CustomerManagement;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;

public class InquiryInterestCustomerUI extends JFrame {
   private boolean flag;
   private CustomerManager cm;
   
   private JPanel contentPane;
   private JLabel lblNewLabel;
   private JLabel label;
   private JLabel label_1;
   private JLabel lblNewLabel_1;
   private JTextField nameField;
   private JSpinner birthdateField;
   private JButton confirm;
   private JButton cancles;
   private JTextField phoneNumField;

   private boolean isStringInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isProperPhoneNum(String s) {
		String regexp = "^01(?:0|1|[6-9])(\\d{7}|\\d{8})$";
       
       if(s.matches(regexp)){
          return true;
       }
       
       return false;
	}
	
	private boolean isProperDate(Date birthdate) {
		Date now = new Date();
		
		int flag = birthdate.compareTo(now);
		
		// birthdate < now
		if(flag == -1) return true;
		
		return false;
	}
   
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
   public InquiryInterestCustomerUI(CustomerManager cm) {
      this.cm = cm;
      flag = false;
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
      contentPane.add(getNameField());
      contentPane.add(getBirthdateField());
      contentPane.add(getConfirm());
      contentPane.add(getCancles());
      contentPane.add(getPhoneNumField());
      setVisible(true);

      cancles.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            setVisible(false);
         }
      });
      
      confirm.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         
            String name = nameField.getText();
            String phoneNum = phoneNumField.getText();
            Date birthdate = (Date)birthdateField.getValue();
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            
            ArrayList<Customer> cList;
            if(name.equals("") || phoneNum.equals("")) {
               JOptionPane.showMessageDialog(null, "��ĭ�� ä�� �ּ���", "���ɰ� Ȯ�� ����", JOptionPane.WARNING_MESSAGE);
               return;
            }
            
            if(!(isStringInteger(phoneNum))) {
				JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���", "���ڰ� �ƴ� ����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!(isProperPhoneNum(phoneNum))) {
				JOptionPane.showMessageDialog(null, "��ȭü�谡 ���� �ʽ��ϴ�", "�߸��� ��ȭ��ȣ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!(isProperDate(birthdate))) {
				JOptionPane.showMessageDialog(null, "��������� �������� �ʽ��ϴ�.", "�߸��� �������", JOptionPane.ERROR_MESSAGE);
				return;
			}
            
            
            
           
           //Customer c = new Customer(name, phoneNum, java.sql.Date.valueOf(sdf.format(birthdate)))
           cList = cm.inquiryCustomer(name, phoneNum, java.sql.Date.valueOf(sdf.format(birthdate)));
            
               
            if(cList.isEmpty()) {
               JOptionPane.showMessageDialog(null, "�߸��� �� �����Դϴ�", "�߸��� �� ����", JOptionPane.WARNING_MESSAGE);
            }
            
            else if(cList.get(0).getInterest() == true){
            	flag = true;
            	JOptionPane.showMessageDialog(null, "���� �� �Դϴ�", "�Ǹ� �Ұ�", JOptionPane.WARNING_MESSAGE);   
            }else {
            	flag = false;
            	JOptionPane.showMessageDialog(null, "���� �� �Դϴ�", "�Ǹ� ����", JOptionPane.WARNING_MESSAGE);
            }
            setVisible(false);
         }    
      });
      
      
   }
   public boolean getFlag() {
	   return flag;
   }
   private JLabel getLblNewLabel() {
      if (lblNewLabel == null) {
         lblNewLabel = new JLabel("\uAD00\uC2EC \uACE0\uAC1D \uD655\uC778");
         lblNewLabel.setFont(new Font("����", Font.BOLD, 20));
         lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
         lblNewLabel.setBounds(12, 10, 410, 30);
      }
      return lblNewLabel;
   }
   private JLabel getLabel() {
      if (label == null) {
         label = new JLabel("\uC774\uB984");
         label.setLabelFor(getNameField());
         label.setBounds(12, 50, 57, 15);
      }
      return label;
   }
   private JLabel getLabel_1() {
      if (label_1 == null) {
         label_1 = new JLabel("\uC804\uD654\uBC88\uD638");
         label_1.setBounds(12, 114, 57, 15);
      }
      return label_1;
   }
   private JLabel getLblNewLabel_1() {
      if (lblNewLabel_1 == null) {
         lblNewLabel_1 = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
         lblNewLabel_1.setBounds(12, 176, 57, 15);
      }
      return lblNewLabel_1;
   }
   private JTextField getNameField() {
      if (nameField == null) {
         nameField = new JTextField();
         nameField.setBounds(81, 47, 139, 21);
         nameField.setColumns(10);
      }
      return nameField;
   }
   private JSpinner getBirthdateField() {
      if (birthdateField == null) {
         
          Calendar calendar = Calendar.getInstance();//��¥
          Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
          Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
          Date end = calendar.getTime();
          SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
         birthdateField = new JSpinner(dateModel);
          birthdateField.setEditor(new JSpinner.DateEditor(birthdateField, "yyyy ��MM�� dd��"));
         birthdateField.setBounds(81, 172, 139, 22);
      }
      return birthdateField;
   }
   private JButton getConfirm() {
      if (confirm == null) {
         confirm = new JButton("\uD655\uC778");
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
   private JTextField getPhoneNumField() {
      if (phoneNumField == null) {
         phoneNumField = new JTextField();
         phoneNumField.setColumns(10);
         phoneNumField.setBounds(83, 110, 139, 21);
      }
      return phoneNumField;
   }
}