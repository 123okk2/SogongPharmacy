package SaleManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ClosingManagement.closingManager;
import RecordManagement.Record;
import RecordManagement.RecordManager;
import RecordManagement.SaleRecord;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;

public class SalesManagementUI extends JFrame {

   private JPanel contentPane;
   private DefaultTableModel model1;
   private JTable table1;
   private JScrollPane scrollpane1;
   private DefaultTableModel model2;
   private JTable table2;
   private JScrollPane scrollpane2;
   private JTextField SalesRecordIDText;
   public static SalesManagementUI salesmanagementui;

   

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               salesmanagementui = new SalesManagementUI();
               salesmanagementui.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public SalesManagementUI() {
      getContentPane().setLayout(null);

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(100, 100, 578, 383);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      

      JButton SearchButton = new JButton("\uC870\uD68C");/*�κ���ȸ ��ư*/
      SearchButton.setBounds(488, 35, 62, 30);
      SearchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           try {
           scrollpane1=new JScrollPane();
            scrollpane1.setBounds(12, 140, 520, 194);
             contentPane.add(scrollpane1);
            String[] column1= {"","",""};
           model1=new DefaultTableModel(column1,0);
           table1=new JTable(model1);
           table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
           table1.setAutoCreateRowSorter(true);
           table1.setCellSelectionEnabled(rootPaneCheckingEnabled);
           scrollpane1.setViewportView(table1);
           
           scrollpane1=new JScrollPane();
           table1=new JTable(model1);
           table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
           table1.setAutoCreateRowSorter(true);
           table1.setCellSelectionEnabled(rootPaneCheckingEnabled);
           scrollpane1.setViewportView(table1);
           
           model1.setColumnIdentifiers(new String[] {"�Ǹű��ID","�Ǹų�¥","���ι�ȣ"});
            SaleManager manager = new SaleManager();
          Sale sale = manager.inquirySale(Integer.parseInt(SalesRecordIDText.getText()));
          
          
          
          SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

       
          String[] data= {((Integer)sale.getSaleid()).toString(), transFormat.format(sale.getSalesdate())
                   ,((Integer)sale.getApprovalnum()).toString()};
          model1.addRow(data);
          table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
          salesmanagementui.getContentPane().add(scrollpane1);
         }catch(Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "������ �������� �ʽ��ϴ�." , "����", JOptionPane.INFORMATION_MESSAGE);
         }
            
         }
      });
      contentPane.add(SearchButton);
      
      JButton DeleteButton = new JButton("\uC0AD\uC81C");/*��ǰ ���� ��ư*/
      DeleteButton.setBounds(288, 75, 107, 55);
      DeleteButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            RecordManager rm = new RecordManager();
            SaleManager manager = new SaleManager();
            try {
               Vector <SalesProduct> info = manager.inquirySalesProduct(Integer.parseInt(SalesRecordIDText.getText()));
               for(int i=0;i<manager.inquirySalesProduct(Integer.parseInt(SalesRecordIDText.getText())).size();i++) {
                  manager.DeleteSalesProduct(Integer.parseInt(SalesRecordIDText.getText()));
                  
                  rm.registerRecord(RecordManager.SALE_RECORD, 
                        new SaleRecord(Record.DELETE, manager.inquiryProductname(info.get(i).getProductid()).getProductName(), info.get(i).getProductquantity()));
             }
            manager.DeleteSales(Integer.parseInt(SalesRecordIDText.getText()));
            }catch(Exception e3) {
               JOptionPane.showMessageDialog(null, "��ǰ����� �˻����ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
            }
         }
      });
      contentPane.add(DeleteButton);
      
      JButton PrintButton = new JButton("\uCD9C\uB825");/*������ ��� ��ư*/
      PrintButton.setBounds(425, 75, 107, 55);
      PrintButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
           SaleManager manager = new SaleManager();
           Sale sale = manager.inquirySale(Integer.parseInt(SalesRecordIDText.getText()));
           Vector<SalesProduct> salesproductinfo = manager.inquirySalesProduct(Integer.parseInt(SalesRecordIDText.getText()));
           SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
           
           String receiptinfo = "�Ǹ�id : " + ((Integer)sale.getSaleid()).toString()+","+
               "���ι�ȣ : " + ((Integer)sale.getApprovalnum()).toString() + ","+
               "�Ǹų�¥ : " + transFormat.format(sale.getSalesdate()) + ","+
               "�Ǹ� ���� : �ݿ��������б� �Ұ��౹" + ","+
               "�Ǹ��� : ������";
           
            FileWriter fw = new FileWriter("C:\\Users\\magic\\PrintRceipt.csv");/*������ ����ȭ�鿡 ����*/
            fw.write(receiptinfo);
            fw.append('\n');
            for(int i=0;i<salesproductinfo.size();i++) {
                 receiptinfo = "�Ǹ� ��ǰ : " + "," + manager.inquiryProductname(salesproductinfo.get(i).getProductid()).getProductName() +",";
                 fw.write(receiptinfo);

                 receiptinfo = "�Ǹ� ���� : " + "," + ((Integer)(((Integer)(manager.inquiryProductname(salesproductinfo.get(i).getProductid()).getProductPrice()))*((Integer)(salesproductinfo.get(i).getProductquantity())))).toString();
                 fw.write(receiptinfo);
                 fw.append('\n');
              }
            fw.close();
             JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ����ȭ�鿡 ����Ǿ����ϴ�.", "��� �Ϸ�", JOptionPane.INFORMATION_MESSAGE); 
         } catch (Exception e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "�Ǹų����� ���� �������ּ���.", "����", JOptionPane.INFORMATION_MESSAGE); 
            e1.printStackTrace();
         }
         }
      });
      contentPane.add(PrintButton);
      
      JButton SalesProductButton = new JButton("\uC0C1\uD488\uD310\uB9E4");/*��ǰ �Ǹ� ��ư*/
      SalesProductButton.setBounds(143, 75, 107, 55);
      SalesProductButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	if(!new closingManager().isClosed()) {
            SalesUI.salesui = new SalesUI();
            SalesUI.salesui.start();
            salesmanagementui.setVisible(false);
            /*JOptionPane.showMessageDialog(null, "��ǰ �Ǹ� �Ϸ�", "��ǰ �Ǹ�", JOptionPane.INFORMATION_MESSAGE); */
         }
            else
           	 JOptionPane.showMessageDialog(null, "�̹� �����Ǿ����ϴ�. �ǸŸ� �Ҽ� �����ϴ�.", "�Ǹ� ����", JOptionPane.ERROR_MESSAGE);
        	}
      });
      contentPane.add(SalesProductButton);
      
      JTextPane textPane = new JTextPane();
      textPane.setBounds(220, 0, 107, 30);
      textPane.setBackground(UIManager.getColor("Button.background"));
      textPane.setForeground(Color.BLACK);
      textPane.setFont(new Font("����", Font.PLAIN, 20));
      textPane.setText("\uD310\uB9E4\uAD00\uB9AC");
      contentPane.add(textPane);
      
      JLabel label = new JLabel("");
      label.setBounds(53, 148, 57, 15);
      contentPane.add(label);
     
      
      
      JButton AllSearchButton = new JButton("\uC804\uCCB4\uC870\uD68C");/*��ü ��ȸ ��ư*/
      AllSearchButton.setBounds(12, 75, 107, 55);
      AllSearchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
            scrollpane2=new JScrollPane();
            scrollpane2.setBounds(12, 140, 520, 194);
            contentPane.add(scrollpane2);
             String[] column2= {"","",""};
            model2=new DefaultTableModel(column2,0);
            table2=new JTable(model2);
            table2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            table2.setAutoCreateRowSorter(true);
            table2.setCellSelectionEnabled(rootPaneCheckingEnabled);
            scrollpane2.setViewportView(table2);
            scrollpane2=new JScrollPane();

            table2=new JTable(model2);
            table2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            table2.setAutoCreateRowSorter(true);
            table2.setCellSelectionEnabled(rootPaneCheckingEnabled);
            scrollpane2.setViewportView(table2);
          model2.setColumnIdentifiers(new String[] {"�Ǹű��ID","�Ǹų�¥","���ι�ȣ"});
          
            SaleManager manager = new SaleManager();
         Vector<Sale> saleinfo = manager.inquiryAllSale();

         SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

         for(int i=0;i<saleinfo.size();i++) {
            String[] data= {((Integer)saleinfo.get(i).getSaleid()).toString(), transFormat.format(saleinfo.get(i).getSalesdate())
                  ,((Integer)saleinfo.get(i).getApprovalnum()).toString()};
            model2.addRow(data);
            
         }
         table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         salesmanagementui.getContentPane().add(scrollpane2);
            }catch(Exception e1) {
               e1.printStackTrace();
            }
         }
      });
      contentPane.add(AllSearchButton);
      
      JLabel SalesRecordIDLabel = new JLabel("\uD310\uB9E4\uAE30\uB85DID");
      SalesRecordIDLabel.setBounds(12, 43, 81, 15);
      contentPane.add(SalesRecordIDLabel);
      
      SalesRecordIDText = new JTextField();
      SalesRecordIDText.setBounds(81, 32, 395, 38);
      contentPane.add(SalesRecordIDText);
      SalesRecordIDText.setColumns(10);
       
   
   }
}