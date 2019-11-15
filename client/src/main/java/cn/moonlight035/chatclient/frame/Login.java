package cn.moonlight035.chatclient.frame;


import cn.moonlight035.chatclient.chat.ChatClientStart;
import cn.moonlight035.chatclient.utils.HttpRequestUtil;
import cn.moonlight035.chatclient.utils.ResultStatus;
import cn.moonlight035.chatclient.utils.msg.Msg;
import cn.moonlight035.chatclient.utils.msg.MsgStatus;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/11/11
 * @description:
 */
@Component
public class Login extends JFrame {
    private JPanel contentPane;
    private JTextField account;
    private JTextField password;
    private LoginButtonAction loginButtonAction;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ChatClientStart chatClientStart;

    @Autowired
    private Client client;

    @Value("${baseUrl}")
    private String baseUrl;

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("MoonLogin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        init();
        setVisible(true);
    }

    private void init(){
        loginButtonAction = new LoginButtonAction();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel accountLable = new JLabel("account:");
        accountLable.setBounds(82, 82, 75, 21);
        contentPane.add(accountLable);

        account = new JTextField();
        account.setBounds(139, 82, 153, 21);
        contentPane.add(account);
        account.setColumns(10);

        JLabel lblPassword = new JLabel("password:");
        lblPassword.setBounds(82, 140, 54, 15);
        contentPane.add(lblPassword);

        password = new JTextField();
        password.setBounds(139, 137, 153, 21);
        contentPane.add(password);
        password.setColumns(10);

        JButton loginButton = new JButton("\u767B\u5F55");
        loginButton.setBounds(82, 199, 69, 23);
        loginButton.addActionListener(loginButtonAction);
        contentPane.add(loginButton);

        JButton registerButton = new JButton("\u6CE8\u518C");
        registerButton.setBounds(230, 199, 62, 23);
        registerButton.addActionListener(loginButtonAction);
        contentPane.add(registerButton);
    }

    private class LoginButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if("登录".equals(e.getActionCommand())){
                String url = baseUrl+"/user/login";
                MultiValueMap<String, String> map = new LinkedMultiValueMap();
                map.add("account",account.getText());
                map.add("password", password.getText());
                Map<String, String> result = (Map<String, String>) HttpRequestUtil.sendPostRequest(map,url);
                if(ResultStatus.OK.toString().equals(result.get("resultStatus"))){
                    client.setUserAccount(account.getText());
                    client.getFriendList();
                    setVisible(false);
                    client.setVisible(true);
                    chatClientStart.syncStart();
                }
            }

            if("注册".equals(e.getActionCommand())){

            }
        }
    }
}
