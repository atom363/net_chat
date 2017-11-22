package boris.network;

public interface TCPConnectionListener {

    void onConectionReady(TCPConnection tcpConnetction);
    void onReceiveString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);
}
