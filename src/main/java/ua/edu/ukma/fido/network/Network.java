package ua.edu.ukma.fido.network;

import ua.edu.ukma.fido.entity.Packet;

import java.io.IOException;

public interface Network {
    void listen() throws IOException;
    Packet receive() throws IOException;

    void connect() throws IOException;
    void send(Packet packet) throws IOException;

    void close() throws IOException;
}
