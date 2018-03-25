package ru.cwl.otus.hw16.msghub;

import connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tischenko on 16.03.2018 17:06.
 */
public class MsgHub {
    final static Logger log = LoggerFactory.getLogger(MsgHub.class);


    public static void main(String[] args) {
        MsgRouter router = new MsgRouter();

        int portNumber = 8000;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            log.info("msg hub started on port: {}", portNumber);
            while (true) {
                Socket socket = serverSocket.accept();
                log.info("accept connection: {}", socket);
                Connector connector = new Connector(socket);
                router.attach(connector);
            }
        } catch (IOException e) {
            log.error("", e);
        }

    }


    // get address
    // configure out queue
    // узнать адрес клиента
    // добавить в "адресную книгу"
             /*   BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
                addrMap.put("addr", queue);*/
    // запустить обработчик
    //exec.execute(() -> runIncomingHandler(clientSocket));






}
