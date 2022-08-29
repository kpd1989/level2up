package homeWork.hw3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private static List<String> listMessages = Collections.synchronizedList(new ArrayList<>());
    private static List<Socket> listSocets = Collections.synchronizedList(new ArrayList<>());


    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            for(;;) {
                Socket client = server.accept();

                Writer writer = new OutputStreamWriter((client.getOutputStream()));
                for(String lastText: listMessages){
                    writer.write(lastText+"\n");
                    writer.flush();
                }
                listSocets.add(client);

                new Thread(() -> {
                    handleClient(client);
                }).start();
            }
        }
    }

    private static void handleClient(Socket client) {
        try {
            BufferedReader reader = new BufferedReader((new InputStreamReader(client.getInputStream())));

            for (;;) {
                String first = reader.readLine();
                listMessages.add(first);

                for (Socket listSocet : listSocets) {
                    /*if (listSocet==client){
                        continue;
                    }*/
                    Writer writer = new OutputStreamWriter(listSocet.getOutputStream());
                    writer.write(first + "\n");
                    writer.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
            }
        }
    }
}
