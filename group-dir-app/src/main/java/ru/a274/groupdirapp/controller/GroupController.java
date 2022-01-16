package ru.a274.groupdirapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.a274.groupdirapp.model.Group;
import ru.a274.groupdirapp.service.GroupService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<Object> getGroupsByIds(@RequestParam("id") List<String> ids) {
        List<ResponseGroup> responseGroups = new ArrayList<>();
        for (String groupId : ids) {
            Group group = groupService.getGroupById(groupId);
            if (group == null)
                return ResponseEntity.badRequest().body(new ResponseMessage("Group not found for id: " + groupId));
            responseGroups.add(new ResponseGroup(group));
        }
        return ResponseEntity.ok().body(responseGroups);
    }

    /*
    @PutMapping("/disable")
    public ResponseEntity<Object> disableGroup(@RequestParam("id") String groupId) {
        Group group = groupService.getGroupById(groupId);
        if (group == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Group not found for id: " + groupId));
        }
        groupService.disableGroup(groupId);
        return ResponseEntity.ok().body(new ResponseMessage("Group disabled for id: " + groupId));
    }

    @PutMapping("/enable")
    public ResponseEntity<Object> enableGroup(@RequestParam("id") String groupId) {
        Group group = groupService.getGroupById(groupId);
        if (group == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Group not found for id: " + groupId));
        }
        groupService.enableGroup(groupId);
        return ResponseEntity.ok().body(new ResponseMessage("Group enabled for id: " + groupId));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroup(@RequestParam("id") String groupId) {
        Group group = groupService.getGroupById(groupId);
        if (group == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Group not found for id: " + groupId));
        }
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().body(new ResponseMessage("Group deleted for id: " + groupId));
    }



    @PostMapping("/create")
    public ResponseEntity<Object> postController(
            @RequestBody User user) {
        log.info("user is being created " + user.getLogin());
        userService.create(user.getLogin(), user.getEmail(), user.getStatus());
        return ResponseEntity.ok().body(user);
    }

     */

}
