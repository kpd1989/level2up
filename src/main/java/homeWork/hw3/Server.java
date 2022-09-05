package homeWork.hw3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server {
    private static List<String> historyMessages = Collections.synchronizedList(new ArrayList<>());
    private static List<BlockingQueue<String>> queuesBlockingMessages = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            for (;;) {
                Socket client = server.accept();

                printMessageStore(client);

                new Thread(() -> {
                    handleClientReader(client);
                }).start();

                new Thread(() -> {
                    handleClientWriter(client);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printMessageStore(Socket client){
        Writer writer = null;
        try {
            writer = new OutputStreamWriter((client.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> copyHistoryToSendClient;
        synchronized (historyMessages) {
            copyHistoryToSendClient = new ArrayList<>(historyMessages);
        }

        for (String lastText : copyHistoryToSendClient) {
            try {
                writer.write(lastText + "\n");
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void handleClientWriter(Socket client)  {
        BlockingQueue<String> messageToSend = new ArrayBlockingQueue<>(100);
        queuesBlockingMessages.add(messageToSend);

        try {
            while (true) {
                String message = messageToSend.take();

                Writer writer = new OutputStreamWriter(client.getOutputStream());
                writer.write(message + "\n");
                writer.flush();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            queuesBlockingMessages.remove(messageToSend);
        }
    }

    private static void handleClientReader(Socket client) {
        try {
            BufferedReader reader = new BufferedReader(
                    (new InputStreamReader(client.getInputStream())));

            while (true) {
                String message = reader.readLine();
                historyMessages.add(message);

                synchronized (queuesBlockingMessages) {
                    for (BlockingQueue<String> otherClients : queuesBlockingMessages) {
                        otherClients.add(message);
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