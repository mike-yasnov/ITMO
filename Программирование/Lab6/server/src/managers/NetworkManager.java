package managers;

import commands.Container;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkManager {

    private static final Logger logger = Logger.getLogger(NetworkManager.class.getName());

    private DatagramChannel dc;
    private int port;
    private SocketAddress addr;

    public NetworkManager(int port, int timeout) {
        this.port = port;
    }

    public boolean init() {
        try {
            addr = new InetSocketAddress(port);
            dc = DatagramChannel.open();
            dc.bind(addr);
            dc.configureBlocking(false);
            return true;
        } catch (SocketException e) {
            logger.log(Level.SEVERE, "Socket exception during initialization", e);
            return false;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException during initialization", e);
            throw new RuntimeException(e);
        }
    }

    public boolean sendData(byte data[]) {
        try {
            if (data.length != 0) {
                ByteBuffer buf = ByteBuffer.wrap(data);
                dc.send(buf, addr);
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException while sending data", e);
            return false;
        }
    }

    public byte[] receiveData(int len) {
        try {
            ByteBuffer buf = ByteBuffer.allocate(len);
            addr = dc.receive(buf);
            if (addr != null) {
                logger.info("Received request from client!");
                return buf.array();
            }
            return null;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException while receiving data", e);
            return null;
        }
    }

    public static byte[] serializer(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.close();
            byte[] objBytes = bos.toByteArray();
            logger.info("Response successfully serialized!");
            return objBytes;

        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException while serializing response", e);
            return null;
        }
    }

    public static Container deserialize(byte[] bytes) {
        if (bytes == null) return null;
        InputStream is = new ByteArrayInputStream(bytes);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            logger.info("Command successfully deserialized!");
            return (Container) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error while deserializing object", e);
            return null;
        }
    }
}
