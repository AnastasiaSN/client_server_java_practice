package ua.edu.ukma.fido;

import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;

public class Main {

    public static void main(String[] args) {
        Message testMessage = new Message(3, 4, "test");
        Packet packet = new Packet((byte) 1, 2L, testMessage);
        System.out.println("Out packet: ");
        System.out.println(packet);
        byte[] encodedPacket = packet.toPacket();
        try {
            Packet decodedPacket = new Packet(encodedPacket);
            System.out.println("Int packet: ");
            System.out.println(decodedPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
