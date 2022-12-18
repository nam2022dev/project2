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

import javax.persistence.NoResultException;
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
    public void update(GroupDTO groupDTO) {
        Group group = groupRepo.findById(groupDTO.getId()).orElseThrow(NoResultException::new);
        group.setName(group.getName());


        if (group.getUsers() != null) {
            group.getUsers().clear();

            for (UserDTO userDTO : groupDTO.getUsers()) {
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);

                group.getUsers().add(user);
            }
        } else {
            List<User> users = new ArrayList<>();

            for (UserDTO userDTO : groupDTO.getUsers()) {
                User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);

                users.add(user);
            }
            group.setUsers(users);
        }

        groupRepo.save(group);
    }
}
