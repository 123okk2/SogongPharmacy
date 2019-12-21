package SaleManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CustomerManagement.CustomerManager;
import CustomerManagement.InquiryInterestCustomerUI;
import ProductManagement.Product;
import ProductManagement.ProductManager;
import RecordManagement.Record;
import RecordManagement.RecordManager;
import RecordManagement.SaleRecord;
import StockManagement.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SalesUI extends JFrame {

   private JPanel contentPane;
   private JTable table1;
   private DefaultTableModel model1;
   private JScrollPane scrollpane1;
   private JTable table2;
   private DefaultTableModel model2;
   private JScrollPane scrollpane2;
   private JTextField ProductNameText;
   private JTextField ProductQauntity;
   private JTextField BarcodeText;
   public static SalesUI salesui;
   private JLabel TotalPriceLabel;
   private Product productinfo;
   private int sum = 0;
   private Vector<Stock> salesstockinfo;
   private Vector<SalesProduct> salesproductinfo;
   private SalesProduct salesproduct;
   private int productid;
   private InquiryInterestCustomerUI customerui;
   private boolean flag1;
   private boolean flag2;
   
   /**
    * Launch the application.
    */
   public static void start() {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
            salesui = new SalesUI();
            salesui.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public SalesUI() {
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(100, 100, 789, 566);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JButton CashButton = new JButton("\uD604\uAE08\uACB0\uC81C");/*현금 결제 버튼*/
      CashButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.out.print(flag1);
            System.out.print(flag2);
            flag1 = customerui.getFlag();
            
            if(flag1 == true && flag2 == true) /*관심고객&위허약품 상품 판매x*/
               {
               JOptionPane.showMessageDialog(null, "관심고객에게 위험상품을 판매할수 없습니다." , "결제 실패", JOptionPane.INFORMATION_MESSAGE);
               return;
               }
            SaleManager manager = new SaleManager();
            RecordManager rm = new RecordManager();
            
            Date today1 = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            String to = transFormat.format(today1);
            
            try {
            int SaleID = (int)(Math.random()*900000)+100000;
            Sale sale = new Sale(SaleID,0,today1,0);
            manager.RegisterSale(sale);
            for(int i=0;i<salesproductinfo.size();i++) {
               System.out.println(salesproductinfo.get(i).getProductid());
            }
            for(int i=0;i<salesproductinfo.size();i++) {
               salesproductinfo.get(i).setSaleid(sale.getSaleid());
               
               manager.RegisterSalesProduct(salesproductinfo.get(i));
               String Productname = manager.inquiryProductname(salesproductinfo.get(i).getProductid()).getProductName();

                rm.registerRecord(RecordManager.SALE_RECORD, new SaleRecord(Record.REGISTER, Productname, salesproductinfo.get(i).getProductquantity()));
                new StockConditionManager().updateStockCondition(new ProductManager().getProductName(salesproductinfo.get(i).getProductid()), -1*salesproductinfo.get(i).getProductquantity());
            }
            for(int i=0;i<salesstockinfo.size();i++) {
                manager.UpdateStockQuantity(salesstockinfo.get(i).getBarcode(), salesproductinfo.get(i).getProductquantity());
            }
            }catch(Exception e2) {
            }
            SalesUI.salesui.setVisible(false);
            new SalesManagementUI().salesmanagementui.setVisible(true);
         }
      });
      CashButton.setBounds(50, 464, 97, 37);
      contentPane.add(CashButton);
   
      TotalPriceLabel = new JLabel("\uCD1D\uAE08\uC561 : 0\uC6D0");
      TotalPriceLabel.setBounds(50, 427, 211, 15);
      contentPane.add(TotalPriceLabel);
      
      JButton CardButton = new JButton("\uCE74\uB4DC\uACB0\uC81C");/*카드결제버튼*/
      CardButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            SaleManager manager = new SaleManager();
            RecordManager rm = new RecordManager();
            Date today1 = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            String to = transFormat.format(today1);
            int approvalnum = (int)(Math.random()*9000)+1000;
            int SaleID = (int)(Math.random()*900000)+100000;
            flag1 = customerui.getFlag();
            if(flag1 == true && flag2 == true)
               {
               JOptionPane.showMessageDialog(null, "위험고객에게 위험상품을 판매할수 없습니다." , "결제 실패", JOptionPane.INFORMATION_MESSAGE);
               return;
               }
            try {
               
            Sale sale = new Sale(SaleID,0,today1,approvalnum);
            manager.RegisterSale(sale);
            for(int i=0;i<salesstockinfo.size();i++) {
               SalesProduct salesproduct = new SalesProduct(salesstockinfo.get(i).getQuantity(),sale.getSaleid()
                     ,salesstockinfo.get(i).getProductID());
               manager.RegisterSalesProduct(salesproduct);
               manager.RegisterSalesProduct(salesproduct);
               String Productname = manager.inquiryProductname(salesproduct.getProductid()).getProductName();

   
                rm.registerRecord(RecordManager.SALE_RECORD, new SaleRecord(Record.REGISTER, Productname, salesproductinfo.get(i).getProductquantity()));
            }
            for(int i=0;i<salesstockinfo.size();i++) {
                manager.UpdateStockQuantity(salesstockinfo.get(i).getBarcode(), salesstockinfo.get(i).getQuantity());
             }
            }catch(Exception e2) {

            }
            JOptionPane.showMessageDialog(null, "승인번호:"+approvalnum , "결제 성공", JOptionPane.INFORMATION_MESSAGE);
            SalesUI.salesui.setVisible(false);
            new SalesManagementUI().salesmanagementui.setVisible(true);
            
         }
      });
      CardButton.setBounds(164, 464, 97, 37);
      contentPane.add(CardButton);
      
      scrollpane1=new JScrollPane();
      scrollpane1.setBounds(50, 200,566 , 200);
      contentPane.add(scrollpane1);
      
      String[] column1= {"","","",""};
      model1=new DefaultTableModel(column1,0);
      table1=new JTable(model1);
      scrollpane1.setViewportView(table1);
      
      model1.setColumnIdentifiers(new String[] {"바코드","상품명","상품개수","상품가격"});
      
      salesproductinfo = new Vector();
      salesstockinfo = new Vector();
      
      JButton AddProductButton = new JButton("\uBB3C\uD488 \uCD94\uAC00");/*상품추가 버튼*/
      AddProductButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
            SaleManager manager = new SaleManager();
            Product product;
         SalesProduct salesproduct = new SalesProduct();
         product = manager.inquiryProductid(ProductNameText.getText());
         
         Stock stock = manager.inquiryStockinfo(product.getProductID());
         salesstockinfo.add(stock);
         
         salesproduct.setProductid(product.getProductID());
         salesproduct.setProductquantity(Integer.parseInt(ProductQauntity.getText()));
         
         salesproductinfo.add(salesproduct);

         int total = Integer.parseInt(ProductQauntity.getText())*productinfo.getProductPrice();
            String[] data= {BarcodeText.getText(), productinfo.getProductName()
                  ,ProductQauntity.getText(),Integer.toString(total)};
            model1.addRow(data);
            
            sum = sum + total;
            TotalPriceLabel.setText("총금액 :"+sum+"원");
         

         salesui.getContentPane().add(scrollpane1);
            }catch(Exception e1) {
               e1.printStackTrace();
               JOptionPane.showMessageDialog(null, "상품선택을 먼저해주세요", "실패", JOptionPane.INFORMATION_MESSAGE);
            }
         }
      });
      AddProductButton.setBounds(558, 154, 116, 23);
      contentPane.add(AddProductButton);
      
      JLabel ProductNameLabel = new JLabel("\uC0C1\uD488\uBA85 :");
      ProductNameLabel.setBounds(23, 158, 57, 15);
      contentPane.add(ProductNameLabel);
      ProductNameText = new JTextField();
      ProductNameText.setBounds(78, 155, 97, 21);
      contentPane.add(ProductNameText);
      ProductNameText.setColumns(10);
      
      JLabel QuantityText = new JLabel("\uAC1C\uC218 :");
      QuantityText.setBounds(442, 158, 57, 15);
      contentPane.add(QuantityText);
      
      ProductQauntity = new JTextField();
      ProductQauntity.setBounds(480, 154, 77, 23);
      contentPane.add(ProductQauntity);
      ProductQauntity.setColumns(10);
      
      scrollpane2=new JScrollPane();
      scrollpane2.setBounds(50, 50,566 , 100);
      contentPane.add(scrollpane2);
      
      String[] column2= {"","","",""};
      model2=new DefaultTableModel(column2,0);
      table2=new JTable(model2);
      table2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
      table2.setAutoCreateRowSorter(true);
      table2.setCellSelectionEnabled(rootPaneCheckingEnabled);
      scrollpane2.setViewportView(table2);
      
      model2.setColumnIdentifiers(new String[] {"바코드","상품이름","상품개수","상품가격"});
      
      
      
      JButton ProductSearchButton = new JButton("\uC870\uD68C");
      ProductSearchButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         
         SaleManager manager = new SaleManager();
         
         try {
         productinfo = manager.inquiryProductid(ProductNameText.getText());
         productid = productinfo.getProductID();
         
         if(manager.isDngrProduct(productid))
         {
            JOptionPane.showMessageDialog(null, "위험약품입니다.", "위험", JOptionPane.INFORMATION_MESSAGE); 
            flag2 = true;
         }else {
         flag2 = false;
         }
         Stock stock = manager.inquiryStockinfo(productid);
         
            String[] data= {((Integer)stock.getBarcode()).toString(),
                  ProductNameText.getText(),((Integer)stock.getQuantity()).toString(),((Integer)productinfo.getProductPrice()).toString()};
            model2.addRow(data);
            
            
         salesui.getContentPane().add(scrollpane2);
         }catch(Exception e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null, "존재하지 않는 상품입니다.", "실패", JOptionPane.INFORMATION_MESSAGE); 
         }
         }
      });
      
      ProductSearchButton.setBounds(180, 154, 69, 23);/*조회버튼*/
      contentPane.add(ProductSearchButton);
      
      JLabel BarcodeLabel = new JLabel("\uBC14\uCF54\uB4DC :");
      BarcodeLabel.setBounds(297, 158, 57, 15);
      contentPane.add(BarcodeLabel);
      
      BarcodeText = new JTextField();
      BarcodeText.setBounds(350, 155, 91, 21);
      contentPane.add(BarcodeText);
      BarcodeText.setColumns(10);
      
      JButton BackButton = new JButton("\uB4A4\uB85C\uAC00\uAE30");
      BackButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            SalesUI.salesui.setVisible(false);
            new SalesManagementUI().salesmanagementui = new SalesManagementUI();
            new SalesManagementUI().salesmanagementui.main(null);
         }
      });
      BackButton.setBounds(664, 463, 97, 39);
      contentPane.add(BackButton);
      
      JButton InquiryCustomer = new JButton("\uAD00\uC2EC \uACE0\uAC1D \uD655\uC778");/*관심고객조회버튼*/
      InquiryCustomer.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {

            CustomerManager manager = new CustomerManager();
            customerui = new InquiryInterestCustomerUI(manager);   
         }
      });
      InquiryCustomer.setBounds(281, 464, 128, 37);
      contentPane.add(InquiryCustomer);
      
     

      

      
   }
}