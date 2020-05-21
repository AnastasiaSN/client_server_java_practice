package ua.edu.ukma.fido;

import com.google.common.primitives.UnsignedLong;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;
import ua.edu.ukma.fido.network.Network;
import ua.edu.ukma.fido.network.impl.TCPNetwork;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        Message testMessage = new Message(1, 1, "time");
        Packet packet = new Packet((byte) 1, UnsignedLong.ONE, testMessage);

        Message secondTestMessage = new Message(1, 1, "notTime");
        Packet secondPacket = new Packet((byte) 1, UnsignedLong.ONE, secondTestMessage);

        try {
            Network network = new TCPNetwork();
            network.connect();

            network.send(packet);
            network.receive();

            network.send(secondPacket);
            network.receive();

            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
