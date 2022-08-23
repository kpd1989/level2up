package lesson.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SummingServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            for(;;) {
                Socket client = server.accept();
                new Thread(() -> {
                    handleClient(client);
                }).start();
            }
        }
    }

    private static void handleClient(Socket client) {
        try {
            BufferedReader reader = new BufferedReader((new InputStreamReader(client.getInputStream())));
            Writer writer = new OutputStreamWriter((client.getOutputStream()));

            for (; ; ) {
                int first = Integer.parseInt(reader.readLine());
                int second = Integer.parseInt(reader.readLine());
                int sum = first + second;

                writer.write("SUM: " + first + " + " + second + " = " + sum + "\n");
                writer.flush();
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
