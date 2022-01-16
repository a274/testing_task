package ru.a274.groupdirapp.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.a274.groupdirapp.model.User;
import ru.a274.groupdirapp.repository.UserRepo;

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

    public static String newLogin() {
        return RandomStringUtils.randomAlphanumeric(random.nextInt(16) + 5);
    }

    public static String newEmail() {
        String name = RandomStringUtils.randomAlphanumeric(random.nextInt(11) + 5);
        String domain = domains.get(random.nextInt(domains.size()));

        String domainExtension = domainExt.get(random.nextInt(domainExt.size()));
        return String.format("%s@%s.%s", name, domain, domainExtension);
    }

    private static String generateId() {
        return  RandomStringUtils.randomAlphanumeric(10).toLowerCase();
    }

    private static boolean isValid(String newId) {
        Optional<User> user = userRepo.findById(newId);
        return user.isPresent();
    }

    public static String newId() {
        String newId;
        do {
            newId = generateId();
        } while (isValid(newId));
        return newId;
    }
}
