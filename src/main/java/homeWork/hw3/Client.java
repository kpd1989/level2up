package homeWork.hw3;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Client {
    private static String name;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

       for (;;){
           System.out.println("Введите имя для отображения в чате:");
           String inputName = scanner.nextLine();
           if (!inputName.isEmpty()) {
               name=inputName;
               break;
           } else System.out.println("Вы ввели пустое поле!!!!! нельзя так");
       }

        try (Socket socket = new Socket("localhost", 9000)) {
            Writer writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader (System.in));
            System.out.println("Вы вошли в чат. Приятного общения!");

            new Thread(()-> {
                handleInput(socket);
                System.exit(0);
            }).start();

            for (;;) {
                String line  = consoleReader.readLine();
                String timeMessage = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                writer.write(timeMessage + " - " + name +": "+line);
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
