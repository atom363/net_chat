package boris.chat.client;

import boris.network.TCPConnection;
import boris.network.TCPConnectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ActionListener, TCPConnectionListener {

    private String IP_ADDR;
    private int PORT;
    private String nickname;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private final JTextArea log = new JTextArea();
    private final JTextField fieldInput = new JTextField();

    private TCPConnection connection;

    ClientWindow(String ip, int portnum, String nickname) {
        this.IP_ADDR = ip;
        this.PORT = portnum;
        this.nickname = nickname;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        log.setEditable(false);
        log.setLineWrap(true);
        add(log, BorderLayout.CENTER);

        fieldInput.addActionListener(this);
        add(fieldInput, BorderLayout.SOUTH);

        setVisible(true);
        try {
            connection = new TCPConnection(this, IP_ADDR, PORT);
            connection.sendString(nickname + " is online!");
        } catch (IOException e) {
            printMsg("Connection exception: " + e);
        }
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                return;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                connection.sendString(nickname + " is offline!");
            }

            @Override
            public void windowClosed(WindowEvent e) {
                return;
            }

            @Override
            public void windowIconified(WindowEvent e) {
                return;
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                return;
            }

            @Override
            public void windowActivated(WindowEvent e) {
                return;
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                return;
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = fieldInput.getText();
        if(msg.equals("")) return;
        fieldInput.setText(null);
        connection.sendString(nickname + ": " + msg);
    }

    @Override
    public void onConectionReady(TCPConnection tcpConnetction) {
        printMsg("Connection established...");
    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {
        printMsg(value);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {
        printMsg("Connection closed.");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        printMsg("Connection exception: " + e);
    }

    private synchronized void printMsg(String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }
}
