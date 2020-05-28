package ua.edu.ukma.fido.classes;

import com.google.common.primitives.UnsignedLong;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;
import ua.edu.ukma.fido.network.Network;

import java.io.IOException;
import java.net.DatagramPacket;

public class Processor {
    public static void process(Network network, Packet packet) {
        String message = packet.getBMsq().getMessage();

        Message answerMessage;
        if (message.equals("time")) {
            answerMessage = new Message(1, 1, "now()");
        } else {
            answerMessage = new Message(1, 1, "other");
        }
        Packet answerPacket = new Packet((byte) 1, UnsignedLong.MAX_VALUE, answerMessage);

        answerPacket.setClientInetAddress(packet.getClientInetAddress());
        answerPacket.setClientPort(packet.getClientPort());

        try {
            network.send(answerPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
