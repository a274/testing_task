package ru.a274.userdirapp.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.a274.userdirapp.model.User;
import ru.a274.userdirapp.repository.UserRepo;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Component
public class UserGenerator {
    private static UserRepo userRepo;

    @Autowired
    public UserGenerator(UserRepo userRepo) {
        UserGenerator.userRepo = userRepo;
    }

    private static final Random random = new Random();
    private static final ArrayList<String> domains = new ArrayList<>();
    private static final ArrayList<String> domainExt = new ArrayList<>();

    static {
        domains.add("gmail");
        domains.add("mail");
        domains.add("yahoo");
        domains.add("e-mail");
        domains.add("freemail");
        domains.add("hotbox");

        domainExt.add("com");
        domainExt.add("ru");
        domainExt.add("org");
        domainExt.add("uk");
        domainExt.add("co");
        domainExt.add("net");
        domainExt.add("edu");
        domainExt.add("info");
        domainExt.add("gov");
    }

    private static String generateLogin() {
        return RandomStringUtils.randomAlphanumeric(random.nextInt(16) + 5);
    }

    private static String generateEmail() {
        String name = RandomStringUtils.randomAlphanumeric(random.nextInt(11) + 5);
        String domain = domains.get(random.nextInt(domains.size()));

        String domainExtension = domainExt.get(random.nextInt(domainExt.size()));
        return String.format("%s@%s.%s", name, domain, domainExtension);
    }

    private static boolean isInvalidEmail(String email) {
        Optional<User> user = userRepo.findById(email);
        return user.isPresent();
    }

    public static String newEmail() {
        String newEmail;
        do {
            newEmail = generateEmail();
        } while (isInvalidEmail(newEmail));
        return newEmail;
    }

    private static boolean isInvalidLogin(String login) {
        User user = userRepo.findByLogin(login);
        return user != null;
    }

    public static String newLogin() {
        String newLogin;
        do {
            newLogin = generateLogin();
        } while (isInvalidLogin(newLogin));
        return newLogin;
    }

}
