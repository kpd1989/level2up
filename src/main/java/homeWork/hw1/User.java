package homeWork.hw1;

import java.util.Objects;

public class User {
    private String login;
    private String name;
    private int age;
    private int bonuses;

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bonuses=" + bonuses +
                '}';
    }

    public User(String login, String name, int age, int bonuses) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.bonuses = bonuses;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login.isEmpty()) {
            throw new IllegalArgumentException("Вы вввели пустой логин");
        }
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Вы вввели пустое имя");
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age<=13) {
            throw new IllegalArgumentException("Допускаются лица старше 13 лет");
        }
        this.age = age;
    }

    public int getBonuses() {
        return bonuses;
    }

    public void setBonuses(int bonuses) {
        if (bonuses<=0) {
            throw new IllegalArgumentException("Бонусы не могут иметь отрицательное значение");
        }
        this.bonuses = bonuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && bonuses == user.bonuses && login.equals(user.login) && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, age, bonuses);
    }
}
