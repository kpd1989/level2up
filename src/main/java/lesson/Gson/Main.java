package lesson.Gson;


import homeWork.hw1.User;

import static homeWork.hw1.WriteJson.gson;


public class Main {

    public static void main(String[] args) {
        User user = new User("user1", "Volodia", 15, 111);
        String json = userToGson(user);
        System.out.println(json);
        System.out.println(user);
    }

    public static String userToGson(User user){
        return gson.toJson(user);
    }

    public static User jsonToUser(String message){
        return gson.fromJson(message, User.class);
    }

}
