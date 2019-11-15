package cn.moonlight035.chatclient.frame;

import cn.moonlight035.chatclient.chat.ChatClientStart;
import cn.moonlight035.chatclient.model.UserVO;
import cn.moonlight035.chatclient.utils.HttpRequestUtil;
import cn.moonlight035.chatclient.utils.HttpResponseUtil;
import cn.moonlight035.chatclient.utils.msg.Msg;
import cn.moonlight035.chatclient.utils.msg.MsgStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Client extends JFrame {

	private JPanel contentPane;
	private MessageButtonAction messageButtonAction;
	private JTextArea chatHistory;
	private JTextArea inputContent;
	private JTextArea account;
	private JComboBox friendList;
	private JTextArea note;

	private String userAccount;

	@Autowired
	private Login login;

	@Autowired
	private ChatClientStart chatClientStart;

	@Value("${baseUrl}")
	private String baseUrl;

	private  Map<String, List<String>> map = new ConcurrentHashMap<>();
	/**
	 * Create the frame.
	 */
	public Client() {
		setTitle("MoonChat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 391);
		contentPaneInit();
	}


	private void contentPaneInit(){
		messageButtonAction = new MessageButtonAction();

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 418, 216);
		contentPane.add(scrollPane);

		chatHistory = new JTextArea();
		scrollPane.setViewportView(chatHistory);

		JButton send = new JButton("\u53D1\u9001");
		send.setBounds(335, 319, 93, 23);
		send.addActionListener(messageButtonAction);
		contentPane.add(send);

		JButton clear = new JButton("\u6E05\u7A7A");
		clear.setBounds(335, 275, 93, 23);
		clear.addActionListener(messageButtonAction);
		contentPane.add(clear);

		JLabel label = new JLabel("\u76EE\u6807\u8D26\u53F7:");
		label.setBounds(438, 99, 54, 15);
		contentPane.add(label);

		account = new JTextArea();
		account.setBounds(438, 129, 184, 30);
		contentPane.add(account);

		JLabel label_1 = new JLabel("\u9009\u62E9\u597D\u53CB");
		label_1.setBounds(438, 27, 54, 15);
		contentPane.add(label_1);

		JButton targetConfirm = new JButton("\u786E\u8BA4\u9009\u62E9");
		targetConfirm.setBounds(438, 190, 93, 23);
		targetConfirm.addActionListener(messageButtonAction);
		contentPane.add(targetConfirm);

		note = new JTextArea();
		note.setBounds(438,220,200,40);
		contentPane.add(note);

		friendList = new JComboBox();
		friendList.setBounds(438, 52, 185, 21);
		contentPane.add(friendList);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 236, 315, 106);
		contentPane.add(scrollPane_1);

		inputContent = new JTextArea();
		scrollPane_1.setViewportView(inputContent);

		JButton button = new JButton("\u6DFB\u52A0\u597D\u53CB");
		button.setBounds(438, 275, 93, 23);
		contentPane.add(button);
	}

	public void getFriendList(){
		friendList.removeAllItems();
		friendList.addItem("blank");
		MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
		paramMap.add("account",userAccount);
		String url = baseUrl+"/user/getFriendInfo";
		Map<String, String> map = (Map<String, String>) HttpRequestUtil.sendPostRequest(paramMap, url);
		List<UserVO> userInfo = HttpResponseUtil.getUserInfo(map.get("data"));
		userInfo.forEach(u->{
			String s = u.getName()+"("+u.getAccount()+")";
			friendList.addItem(s);
		});
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
		setTitle("MoonChat:"+userAccount);
	}

	public String getUserAccount() {
		return userAccount;
	}

	public  Map<String, List<String>> getMap() {
		return map;
	}

	public void freshChat(String fromAccount){
		if(!account.getText().equals(fromAccount)) {
			note.setText(fromAccount+",来消息了");
			return ;
		}
		List<String> list = map.get(account.getText());
		if(list ==null){
			chatHistory.setText("");
		}
		else {
			StringBuilder temp = new StringBuilder();
			list.forEach(s->{
				temp.append(s+"\n");
			});
			chatHistory.setText(temp.toString());
		}
	}


		private class MessageButtonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if("发送".equals(e.getActionCommand())){
				if(StringUtils.isEmpty(account.getText())) return;
				if(StringUtils.isEmpty(inputContent.getText())) return;
				String target = account.getText();
				Msg msg = new Msg();
				msg.setFromAcount(userAccount);
				msg.setToAccount(account.getText());
				msg.setContent(inputContent.getText());
				msg.setStatus(MsgStatus.SEND_MSG.getStatus());
				chatClientStart.send(msg);
				inputContent.setText("");
			}

			if("清空".equals(e.getActionCommand())){
				inputContent.setText("");
			}

			if("确认选择".equals(e.getActionCommand())){
				String selectedItem = (String) friendList.getSelectedItem();
				if(selectedItem==null) return;
				if("blank".equals(selectedItem))
					account.setText("");
				else
					account.setText(selectedItem.split("\\(")[1].replace(")",""));
				if(account.getText().equals(note.getText().split(",")[0]))
					note.setText("");
				Client.this.freshChat(account.getText());
			}
		}
	}
}