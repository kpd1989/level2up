package homeWork.hw1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WriteJson {
    public static Gson gson = new GsonBuilder().create();

    public static void main(String[] args) throws IOException {

        BufferedWriter writerToFile = new BufferedWriter(new FileWriter("baseUser.csv"));
        BufferedReader readerKeyboard = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader readerFromFile = new BufferedReader(new FileReader("baseUser.csv", StandardCharsets.UTF_8));

        writeUsersWhithKeyboard(writerToFile, readerKeyboard);

        writeUsersWhithFile(readerFromFile);
    }

    private static void writeUsersWhithFile(BufferedReader readerFromFile) throws IOException {

       List<String> listString = new ArrayList<>();

       while (true) {
            String line = readerFromFile.readLine();
            if (line == null) {
                break;
            }
            String[] strings = line.trim().split("%");
            listString.addAll(List.of(strings));
        }
        for (String s : listString) {
            User user1 = gson.fromJson(s, User.class);
            System.out.println(user1);

        }
    }

    private static void writeUsersWhithKeyboard(BufferedWriter writerToFile, BufferedReader readerKeyboard) throws IOException {
        User user;
        while (true) {
            user = new User("login", "name", 0, 0);

            System.out.println("Добавляем пользователя в систему? (yes/no) \n " +
                    "для выхода нажмите <Enter>");
            String answer = readerKeyboard.readLine();

            if (!answer.equals("")) {
                System.out.println("Введите Логин:");
                user.setLogin(readerKeyboard.readLine());

                System.out.println("Введите Имя:");
                user.setName(readerKeyboard.readLine());

                System.out.println("Введите возраст \n допускаются лица от 14 лет и старше:");
                user.setAge(Integer.parseInt(readerKeyboard.readLine()));

                user.setBonuses(10); // Презент за регистрацию в размере 10 бонусов

                writerToFile.write(gson.toJson(user)+"%"+"\n");
                writerToFile.flush();

                continue;
            }
            break;
        }
    }
}


