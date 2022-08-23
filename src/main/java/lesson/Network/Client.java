package lesson.Network;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (Socket socket = new Socket("localhost", 9000)) {
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

            new Thread(()-> {
                handleInput(socket);
                System.exit(0);
            }).start();

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader (System.in));

            for (;;) {
                String line  = consoleReader.readLine();
                writer.write(line);
                writer.write("\n");
                writer.flush();
            }
        }
    }
    private static void handleInput(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            for(;;) {
                String line = reader.readLine();
                if(line==null) break;
                System.out.println(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
