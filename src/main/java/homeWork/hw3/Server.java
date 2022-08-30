package homeWork.hw3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Server {
    private static List<String> listMessages = new ArrayList<>();
    private static List<Socket> listSocets = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket server = new ServerSocket(9000)) {
            for(;;) {
                Socket client = server.accept();

                printMessageStore(client);

                addClientToList(client);

                new Thread(() -> {
                    handleClient(client);
                    removeClientFromList(client);
                }).start();
            }
        }
    }

    private static void removeClientFromList(Socket client) {
        synchronized (listSocets) {
            listSocets.remove(client);
            System.out.println(listSocets);
        }
    }

    private static void addClientToList(Socket client) {
        synchronized (listSocets) {
            listSocets.add(client);
            System.out.println(listSocets);
        }
    }

    private static void printMessageStore(Socket client) throws IOException {
        Writer writer = new OutputStreamWriter((client.getOutputStream()));
        for(String lastText: listMessages){
            synchronized (lastText) {
                writer.write(lastText + "\n");
                writer.flush();
            }
        }
    }

    private static void handleClient(Socket client) {
        try {
            BufferedReader reader = new BufferedReader((new InputStreamReader(client.getInputStream())));

            for (;;) {
                String message = reader.readLine();
                synchronized (listMessages) {
                    listMessages.add(message);
                }

                for (Socket listSocet : listSocets) {
                    synchronized (listSocet) {
                        Writer writer = new OutputStreamWriter(listSocet.getOutputStream());
                        writer.write(message + "\n");
                        writer.flush();
                    }
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