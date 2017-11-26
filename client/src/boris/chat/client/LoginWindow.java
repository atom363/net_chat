package boris.chat.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.sql.Types.NULL;

public class LoginWindow extends JFrame implements ActionListener {

    private static final int WIDTH = 180;
    private static final int HEIGHT = 160;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { new LoginWindow(); }
        });
    }

    private final JTextField fieldIP = new JTextField("Server IP address");
    private final JTextField fieldPort = new JTextField("Port number");
    private final JTextField fieldNickname = new JTextField("Your nickname");

    private LoginWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(fieldIP);
        add(fieldPort);
        add(fieldNickname);
        JButton buttonLogin = new JButton("Login");
        buttonLogin.addActionListener(this);
        add(buttonLogin);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int port = NULL;
        String ip = fieldIP.getText();
        if (fieldPort.getText().matches("[-+]?\\d+")) {
            port = Integer.parseInt(fieldPort.getText());
        }
        String nickname = fieldNickname.getText();
        if(ip.equals("") || port == NULL || nickname.equals("") ) {
            JOptionPane.showMessageDialog(null, "Incorrect inputs");
            return;
        }
        new ClientWindow(ip, port, nickname);
        setVisible(false);
    }
}
