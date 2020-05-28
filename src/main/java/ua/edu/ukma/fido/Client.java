package ua.edu.ukma.fido;

import com.google.common.primitives.UnsignedLong;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;
import ua.edu.ukma.fido.network.Network;
import ua.edu.ukma.fido.network.impl.TCPNetwork;
import ua.edu.ukma.fido.network.impl.UDPNetwork;
import ua.edu.ukma.fido.utils.NetworkProperties;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        Message testMessage = new Message(1, 1, "time");
        Packet packet = new Packet((byte) 1, UnsignedLong.ONE, testMessage);

        Message secondTestMessage = new Message(1, 1, "notTime");
        Packet secondPacket = new Packet((byte) 1, UnsignedLong.ONE, secondTestMessage);

        try {
            String networkType = NetworkProperties.getProperty("type");

            Network network;
            if (networkType.toLowerCase().equals("tcp"))
                network = new TCPNetwork();
            else
                network = new UDPNetwork();

            System.out.println("Client running via " + network + " connection");

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
