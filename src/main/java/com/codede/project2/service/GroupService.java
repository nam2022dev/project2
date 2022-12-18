package com.codede.project2.service;

import com.codede.project2.DTO.GroupDTO;
import com.codede.project2.DTO.UserDTO;
import com.codede.project2.entity.Group;
import com.codede.project2.entity.User;
import com.codede.project2.repo.GroupRepo;
import com.codede.project2.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    GroupRepo groupRepo;

    @Transactional
    public void create(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());

        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : groupDTO.getUsers()) {
            User user = userRepo.findById(userDTO.getId()).orElseThrow(NoClassDefFoundError::new);

            users.add(user);
        }
        group.setUsers(users);
        groupRepo.save(group);
    }

    @Transactional
    public void update(Group group) {

    }
}
