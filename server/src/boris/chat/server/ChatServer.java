package boris.chat.server;

import boris.network.TCPConnection;
import boris.network.TCPConnectionListener;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener{

    public static void main(String[] args) {
        new ChatServer();
    }

    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    private ChatServer() {
        System.out.println("Server running on port 8189...");
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());

                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public synchronized void onConectionReady(TCPConnection tcpConnetction) {
        connections.add(tcpConnetction);
        sendToAllConnections("Client Connected: " + tcpConnetction);
    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sendToAllConnections("Client Disconnected: " + tcpConnection);
    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
    }

    private void sendToAllConnections(String value) {
        System.out.println(value);
        for (int i = 0; i < connections.size(); i++)
            connections.get(i).sendString(value);
    }
}
