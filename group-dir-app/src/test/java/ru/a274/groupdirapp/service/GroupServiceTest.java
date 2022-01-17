package ru.a274.groupdirapp.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import ru.a274.groupdirapp.model.Email;
import ru.a274.groupdirapp.model.Group;
import ru.a274.groupdirapp.model.Status;
import ru.a274.groupdirapp.repository.GroupRepo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @InjectMocks
    private GroupService groupService;

    @Captor
    private ArgumentCaptor<Group> captor;

    @Mock
    private GroupRepo groupRepo;
    private static Group group, group1;
    private static List<Email> emailList, emailList1;

    @BeforeAll
    static void create() {
        emailList = new ArrayList<>();
        emailList1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Email email = new Email();
            email.setId(i);
            email.setEmail("test" + i+ "@test.com");
            emailList.add(email);

            Email email1 = new Email();
            email1.setId(i);
            email1.setEmail("test" + (i+3) + "@test.com");
            emailList.add(email1);
        }

        group = new Group();
        group.setId("someId");
        group.setCreationDate(Date.from(Instant.now()));
        group.setModificationDate(Date.from(Instant.now()));
        group.setEmail(emailList);
        group.setStatus(Status.ACTIVE.name());

        group1 = new Group();
        group1.setId("someId1");
        group1.setCreationDate(Date.from(Instant.now()));
        group1.setModificationDate(Date.from(Instant.now()));
        group1.setEmail(emailList1);
        group1.setStatus(Status.ACTIVE.name());
    }

    @Test
    void getGroupById() {
        Mockito.when(groupRepo.findById(anyString())).thenReturn(java.util.Optional.ofNullable(group));
        assertEquals(group, groupRepo.findById("someId").get());
    }

    @Test
    void getAll() {
        Mockito.when(groupRepo.findAll()).thenReturn(List.of(group, group1));
        assertEquals(2, groupRepo.findAll().size());
    }

    @Test
    void saveGroup() {
        groupService.saveGroup(group);
        Mockito.verify(groupRepo).save(captor.capture());
        Group capturedUser = captor.getValue();
        assertEquals("someId", capturedUser.getId());
    }

}