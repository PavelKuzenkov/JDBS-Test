package application.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import application.data.User;
import application.service.UserService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${filepath.users}")
    private String usersDir;

    @Override
    public void toJSON(User user) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.writeValue(createUserFile(), user);
            System.out.println("json created!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public User toJavaObject(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        User result = new User();
        try{
            result = mapper.readValue(new File(usersDir + fileName), User.class);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        for (String fileName : getAllFileNames()) {
            result.add(toJavaObject(fileName));
        }
        return result;
    }

    private List<String> getAllFileNames() {
        File dir = new File(usersDir);
        return new ArrayList<>(Arrays.asList(dir.list()));
    }

    private File createUserFile() {
        if (!ensureUsersDirIsExist()) {
            System.out.println("Users directory hasn't been created");
            return null;
        }
        return new File(usersDir, createUserFileName());
    }

    private boolean ensureUsersDirIsExist() {
        File file = new File(usersDir);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    private String createUserFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        try {
            return "User" + dateFormat.format(new Date()) + ".json";
        } catch (IllegalArgumentException e) {
            System.out.println("Can't format date");
        }
        return null;
    }
}
