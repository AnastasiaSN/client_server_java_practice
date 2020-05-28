package ua.edu.ukma.fido.network.impl;

import ua.edu.ukma.fido.classes.Processor;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;
import ua.edu.ukma.fido.network.Network;
import ua.edu.ukma.fido.utils.NetworkProperties;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class UDPNetwork implements Network {
    private DatagramSocket socket;

    @Override
    public void listen() throws IOException {
        String portProperty = NetworkProperties.getProperty("port");
        if (portProperty == null)
            portProperty = "2305";

        socket = new DatagramSocket(Integer.parseInt(portProperty));
    }

    @Override
    public Packet receive() {
        Integer wLen = 0;
        Boolean packetReceived = true;

        byte emptyBuffer[] = new byte[Packet.packetMaxSize];
        try {
            while (packetReceived) {
                byte maxPacketBuffer[] = emptyBuffer.clone();

                DatagramPacket datagramPacket = new DatagramPacket(maxPacketBuffer, maxPacketBuffer.length);
                socket.receive(datagramPacket);

                if (maxPacketBuffer != emptyBuffer) {
                    packetReceived = false;

                    ByteBuffer byteBuffer = ByteBuffer.wrap(maxPacketBuffer);
                    wLen = byteBuffer.getInt(Packet.packetPartFirstLengthWithoutwLen);
                    byte fullPacket[] = byteBuffer.slice(0, Packet.packetPartFirstLength + Message.BYTES_WITHOUT_MESSAGE + wLen).array();

                    System.out.println("Received");
                    System.out.println(Arrays.toString(fullPacket) + "\n");

                    Packet packet = new Packet(fullPacket);
                    System.err.println(packet.getBMsq().getMessage());

                    packet.setClientInetAddress(datagramPacket.getAddress());
                    packet.setClientPort(datagramPacket.getPort());

                    Processor.process(this, packet);
                }
            }
        } catch (Exception e) {
            System.err.println("Error:" + socket);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void connect() throws IOException {
        socket = new DatagramSocket();
    }

    @Override
    public void send(Packet packet) throws IOException {
        String hostProperty = NetworkProperties.getProperty("host");
        if (hostProperty == null)
            hostProperty = "localhost";

        String portProperty = NetworkProperties.getProperty("port");
        if (portProperty == null)
            portProperty = "2305";

        InetAddress inetAddress = packet.getClientInetAddress() != null ? packet.getClientInetAddress() : InetAddress.getByName(hostProperty);
        Integer port = packet.getClientPort() != null ? packet.getClientPort() : Integer.parseInt(portProperty);

        byte[] packetBytes = packet.toPacket();

        DatagramPacket datagramPacket = new DatagramPacket(packetBytes, packetBytes.length, inetAddress, port);
        socket.send(datagramPacket);

        System.out.println("Send");
        System.out.println(Arrays.toString(packetBytes) + "\n");
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
