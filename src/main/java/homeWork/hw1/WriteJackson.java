package homeWork.hw1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class WriteJackson {

    public static void main(String[] args) throws IOException {
        List<User> dataBase = new ArrayList<>();

        BufferedWriter writer = new BufferedWriter(new FileWriter("baseUser1.csv"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader readerFromFile = new BufferedReader(new FileReader("baseUser1.csv"));

        writeUsers(dataBase, writer, reader);

       //readUsers(readerFromFile);
    }

    /*private static void readUsers(BufferedReader readerFromFile) throws IOException {
        String jsonString = readerFromFile.readLine();
        StringReader reader1 = new StringReader(jsonString);

        ObjectMapper mapper1 = new ObjectMapper();

        User user1 = mapper1.readValue(reader1, User.class);
        System.out.println(user1);
    }*/

    private static void writeUsers(List<User> dataBase, BufferedWriter writer, BufferedReader reader) throws IOException {
        User user;
        while (true) {
            user = new User("login", "name", 0, 0);

            System.out.println("Добавляем пользователя в систему? (yes/no) \n " +
                    "для выхода нажмите <Enter>");
            String answer = reader.readLine();

            if (!answer.equals("")) {
                System.out.println("Введите Логин:");
                user.setLogin(reader.readLine());

                System.out.println("Введите Имя:");
                user.setName(reader.readLine());

                System.out.println("Введите возраст \n допускаются лица от 14 лет и старше:");
                user.setAge(Integer.parseInt(reader.readLine()));

                user.setBonuses(10); // Презент за регистрацию в размере 10 бонусов

                dataBase.add(user);
                // System.out.println(user);

                continue;
            }
            break;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, dataBase);

        /*если вместо коллекции dataBase поставить user и переместить в цикл (чтобы обойтись без коллекции)
        * при сериализации в файл второго пользователя возникает ошибка.
        * Exception in thread "main" java.io.IOException: Stream closed
	at java.base/java.io.BufferedWriter.ensureOpen(BufferedWriter.java:107)
	at java.base/java.io.BufferedWriter.write(BufferedWriter.java:171)
	at com.fasterxml.jackson.core.json.WriterBasedJsonGenerator._flushBuffer(WriterBasedJsonGenerator.java:2034)
	at com.fasterxml.jackson.core.json.WriterBasedJsonGenerator.close(WriterBasedJsonGenerator.java:1003)
	at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4492)
	at com.fasterxml.jackson.databind.ObjectMapper.writeValue(ObjectMapper.java:3725)
	at homeWork.WriteUser.main(WriteUser.java:47)

* как её обойти пока не придумал
*/
    }
}


