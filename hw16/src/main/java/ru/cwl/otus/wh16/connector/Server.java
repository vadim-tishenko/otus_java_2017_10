package ru.cwl.otus.wh16.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tischenko on 05.03.2018 18:13.
 */
public class Server {

    private  ServerSocket serverSocket;

    Server(){
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket socket = serverSocket.accept();
        socket.getInputStream();
        socket.getOutputStream();
    }
}
