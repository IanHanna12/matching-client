package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Service
public class userService {

    boolean isExecuted;
    private final List<User> userList;

    public userService(List<User> userList) {
        this.userList = userList;
    }

    int userIdCounter = 0;

    public User generateRandomUser() {
        java.time.LocalTime startingTime = java.time.LocalTime.of(11, 30);
        int randomMinutes = ThreadLocalRandom.current().nextInt(0, 150);
        java.time.LocalTime randomTime = startingTime.plusMinutes(randomMinutes);
        String time = randomTime.toString();
        String uniqueID = String.valueOf(userIdCounter++);
        //return new User("User" + ThreadLocalRandom.current().nextInt(100), ""  + " " + ThreadLocalRandom.current().nextInt(100)
        return new User("User" + ThreadLocalRandom.current().nextInt(100), uniqueID, time);
    }

    @PostConstruct
    public void writeUserstoJSON() {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        ObjectMapper mapper = new ObjectMapper();
        if (file.exists() && file.length() == 0) {
            try {
                List<User> Users = new ArrayList<>();
                for (int i = 0; i <= 100; i++) {
                    User user = generateRandomUser();
                    Users.add(user);
                }
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, Users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PostConstruct
    public List<User> readUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/users.JSON");
        if (file.exists() && file.length() != 0) {
            try {
                List<User> Users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
                userList.addAll(Users);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userList;
    }


    public void WritematchedUserstoJSON(List<Match> matchedusers) {
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, matchedusers);
            isExecuted = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> ReadmatchedUsersfromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("Matching-Client-main (1)/Matching-Client-main/demo/src/main/java/com/example/demo/json/matchedusers.JSON");
        List<User> matchedusers = new ArrayList<>();
        if (file.exists() && file.length() != 0) {
            try {
                matchedusers = mapper.readValue(file, new TypeReference<List<User>>() {
                });
                userList.addAll(matchedusers);
                isExecuted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return matchedusers;
    }

    public List<User> getandmatchusersRandomly(List<User> matcheduserList) {
        List<User> Users = new ArrayList<>(userList);
        if (Users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!Users.isEmpty()) {
            int randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
            matcheduserList.add(Users.remove(randomIndex));
            if (!Users.isEmpty()) {
                randomIndex = ThreadLocalRandom.current().nextInt(Users.size());
                matcheduserList.add(Users.remove(randomIndex));
            }
        }
        return matcheduserList;
    }


    public List<User> getandmatchUsers(List<User> matcheduserList) {
        List<User> Users = new ArrayList<>(userList);
        if (Users.size() < 2) {
            throw new IllegalStateException("Not enough users to Match");
        }
        while (!Users.isEmpty()) {
            User user1 = Users.removeFirst();
            User user2 = Users.removeFirst();
            validateUser(user1);
            validateUser(user2);
            matcheduserList.add(user1);
            matcheduserList.add(user2);
        }
        return matcheduserList;
    }

    private void validateUser(User user) {
        String Inputtime = user.getUsertime();
        if (Inputtime == null) {
            throw new IllegalStateException("User has no time");
        }
        if (Inputtime.equals(user.getUsertime())) {
            return;
        }
        throw new IllegalStateException("User not added");
    }
}



