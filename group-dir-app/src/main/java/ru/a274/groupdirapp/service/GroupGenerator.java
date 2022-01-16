package ru.a274.groupdirapp.service;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.a274.groupdirapp.model.Group;
import ru.a274.groupdirapp.repository.GroupRepo;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Component
public class GroupGenerator {
    private static GroupRepo groupRepo;

    @Autowired
    public GroupGenerator(GroupRepo groupRepo) {
        GroupGenerator.groupRepo = groupRepo;
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


    private static String generateId() {
        return RandomStringUtils.randomAlphanumeric(10).toLowerCase();
    }

    private static boolean isValid(String newId) {
        Optional<Group> user = groupRepo.findById(newId);
        return user.isPresent();
    }

    public static String newId() {
        String newId;
        do {
            newId = generateId();
        } while (isValid(newId));
        return newId;
    }

    public static String generateEmail() {
        String name = RandomStringUtils.randomAlphanumeric(random.nextInt(11) + 5);
        String domain = domains.get(random.nextInt(domains.size()));

        String domainExtension = domainExt.get(random.nextInt(domainExt.size()));
        return String.format("%s@%s.%s", name, domain, domainExtension);
    }
}
