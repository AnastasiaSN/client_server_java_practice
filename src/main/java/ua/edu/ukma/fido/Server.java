package ua.edu.ukma.fido;

import ua.edu.ukma.fido.network.Network;
import ua.edu.ukma.fido.network.impl.TCPNetwork;
import ua.edu.ukma.fido.network.impl.UDPNetwork;
import ua.edu.ukma.fido.utils.NetworkProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Server {
    public static void main(String[] args) {
        try {
            String networkType = NetworkProperties.getProperty("type");

            Network network;
            if (networkType.toLowerCase().equals("tcp"))
                network = new TCPNetwork();
            else
                network = new UDPNetwork();

            System.out.println("Server running via " + network + " connection");

            network.listen();

            network.receive();

            network.receive();

            network.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
