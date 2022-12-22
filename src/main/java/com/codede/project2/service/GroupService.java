package com.codede.project2.service;

import com.codede.project2.DTO.GroupDTO;
import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.DTO.UserDTO;
import com.codede.project2.entity.Group;
import com.codede.project2.entity.Student;
import com.codede.project2.entity.User;
import com.codede.project2.repo.GroupRepo;
import com.codede.project2.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Transactional
    public PageDTO<GroupDTO> search(int id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Group> pageRS = null;

        pageRS = groupRepo.searchById(id, pageable);

        PageDTO<GroupDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<GroupDTO> groupDTOs = new ArrayList<>();
        for (Group group : pageRS.getContent()) {
            groupDTOs.add(new ModelMapper().map(group, GroupDTO.class));
        }

        pageDTO.setContents(groupDTOs); // set vao pagedto
        return pageDTO;
    }

    public void delete(int id) {

        groupRepo.deleteById(id);
    }
}
