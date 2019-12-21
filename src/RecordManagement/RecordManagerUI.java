
package RecordManagement;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RecordManagerUI extends JFrame {

	private JPanel contentPane;
	private JButton pdButton, rcButton, slButton, stButton, rtButton, csButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordManagerUI frame = new RecordManagerUI();
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
	public RecordManagerUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // x버튼을 누르면 해당 창만 종료
		setTitle("이력 관리");
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPDButton());
		contentPane.add(getRCButton());
		contentPane.add(getSLButton());
		contentPane.add(getSTButton());
		contentPane.add(getRTButton());
		contentPane.add(getCSButton());
		
		pdButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquiryProductRecordUI();
			}
		});
		rcButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquiryReceivingRecordUI();
			}
		});
		slButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquirySaleRecordUI();
			}
		});
		stButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquiryStockRecordUI();
			}
		});
		rtButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquiryReturnRecordUI();
			}
		});
		csButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayInquiryClosingRecordUI();
			}
		});
	}
	
	public void displayInquiryProductRecordUI() {
		//입고 등록 화면 호출
		InquiryProductRecordUI newScr=new InquiryProductRecordUI();
		newScr.setVisible(true);
	}
	public void displayInquiryReceivingRecordUI() {
		//입고 등록 화면 호출
		InquiryReceivingRecordUI newScr=new InquiryReceivingRecordUI();
		newScr.setVisible(true);
	}
	public void displayInquirySaleRecordUI() {
		//입고 등록 화면 호출
		InquirySaleRecordUI newScr=new InquirySaleRecordUI();
		newScr.setVisible(true);
	}
	public void displayInquiryStockRecordUI() {
		//입고 등록 화면 호출
		InquiryStockRecordUI newScr=new InquiryStockRecordUI();
		newScr.setVisible(true);
	}
	public void displayInquiryReturnRecordUI() {
		//입고 등록 화면 호출
		InquiryReturnRecordUI newScr=new InquiryReturnRecordUI();
		newScr.setVisible(true);
	}
	public void displayInquiryClosingRecordUI() {
		//입고 등록 화면 호출
		InquiryClosingRecordUI newScr=new InquiryClosingRecordUI();
		newScr.setVisible(true);
	}

	private JButton getPDButton() {
		if (pdButton == null) {
			pdButton = new JButton("상품 이력 조회");
			pdButton.setBounds(64, 51, 185, 67);
		}
		return pdButton;
	}
	private JButton getRCButton() {
		if (rcButton == null) {
			rcButton = new JButton("입고 이력 조회");
			rcButton.setBounds(329, 51, 185, 67);
		}
		return rcButton;
	}
	private JButton getSLButton() {
		if (slButton == null) {
			slButton = new JButton("판매 이력 조회");
			slButton.setBounds(64, 153, 185, 67);
		}
		return slButton;
	}
	private JButton getSTButton() {
		if (stButton == null) {
			stButton = new JButton("재고 이력 조회");
			stButton.setBounds(329, 153, 185, 67);
		}
		return stButton;
	}
	private JButton getRTButton() {
		if (rtButton == null) {
			rtButton = new JButton("반품 이력 조회");
			rtButton.setBounds(64, 255, 185, 67);
		}
		return rtButton;
	}
	private JButton getCSButton() {
		if (csButton == null) {
			csButton = new JButton("마감 이력 조회");
			csButton.setBounds(329, 255, 185, 67);
		}
		return csButton;
	}
}
