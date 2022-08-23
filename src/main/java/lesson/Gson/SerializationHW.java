package lesson.Gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class SerializationHW {

    public static void main(String[] args) throws IOException {
        ArrayList<Account> accounts = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv", StandardCharsets.UTF_8))) {
            reader.readLine();

            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.isBlank()) continue;

                String[] cells = line.split(",");
                Account account = new Account(cells[0], cells[1], parseInt(cells[2]), parseInt(cells[3]));
                accounts.add(account);
            }
        }

           try (FileWriter writer = new FileWriter("results.json")){
              writer.write(gson.toJson(accounts));
           }
    }
}

    class Account {
        private String login;
        private String name;
        private int age;
        private int bonuses;

        public Account(String login, String name, int age, int bonuses) {
            this.login = login;
            this.name = name;
            this.age = age;
            this.bonuses = bonuses;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getBonuses() {
            return bonuses;
        }

        public void setBonuses(int bonuses) {
            this.bonuses = bonuses;
        }
    }

