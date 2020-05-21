package ua.edu.ukma.fido.network.impl;

import ua.edu.ukma.fido.classes.Processor;
import ua.edu.ukma.fido.entity.Message;
import ua.edu.ukma.fido.entity.Packet;
import ua.edu.ukma.fido.network.Network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TCPNetwork implements Network {
    Socket socket;

    ServerSocket serverSocket;

    OutputStream socketOutputStream;
    InputStream serverInputStream;

    @Override
    public void listen() throws IOException {
        serverSocket = new ServerSocket(2305);

        socket = serverSocket.accept();

        socketOutputStream = socket.getOutputStream();
        serverInputStream = socket.getInputStream();;
    }

    @Override
    public Packet receive() {
        try {
            byte firstBuffer[] = new byte[Packet.packetPartFirstLengthWithCRC16];
            while ((serverInputStream.read(firstBuffer)) == -1) System.out.println("waiting");

            ByteBuffer byteBuffer = ByteBuffer.wrap(firstBuffer);
            System.out.println(Arrays.toString(firstBuffer));

            Integer wLen = byteBuffer.getInt(Packet.packetPartFirstLengthWithoutwLen);
            Short wCrc16_1_Packet = byteBuffer.getShort(Packet.packetPartFirstLength);

            byte[] packetFirstPart = new byte[Packet.packetPartFirstLength];
            byteBuffer.get(packetFirstPart, 0, Packet.packetPartFirstLength);
            Short wCrc16_1_Calculated = Packet.calculateCRC16(packetFirstPart);

            System.out.println("wCrc16_1_Packet: " + wCrc16_1_Packet);
            System.out.println("wCrc16_1_Calculated: " + wCrc16_1_Calculated + "\n");

            Integer secondPartLength = Message.BYTES_WITHOUT_MESSAGE + wLen + Short.BYTES;

            byte secondBuffer[] = new byte[secondPartLength];
            while ((serverInputStream.read(secondBuffer)) == -1) System.out.println("waiting");

            byte[] packetBytes = ByteBuffer.allocate(Packet.packetPartFirstLengthWithCRC16 + secondPartLength)
                    .put(firstBuffer)
                    .put(secondBuffer)
                    .array();

            System.out.println(Arrays.toString(packetBytes));
            System.out.println("Received\n");

            Packet packet = new Packet(packetBytes);
            System.err.println(packet.getBMsq().getMessage());


            Processor.process(this, packet);
        } catch (Exception e) {
            System.err.println("Error:" + socket);
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    @Override
    public void connect() throws IOException {
        socket = new Socket("localhost",2305);
        socketOutputStream = socket.getOutputStream();
        serverInputStream = socket.getInputStream();
    }

    @Override
    public void send(Packet packet) throws IOException {
        byte[] packetBytes = packet.toPacket();

        socketOutputStream.write(packetBytes);
        socketOutputStream.flush();

        System.out.println(Arrays.toString(packetBytes));
        System.out.println("Send\n");
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
