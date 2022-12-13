package com.codede.project2.service;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.entity.User;
import com.codede.project2.DTO.UserDTO;
import com.codede.project2.entity.UserRole;
import com.codede.project2.repo.UserRepo;
import com.codede.project2.repo.UserRoleRepo;
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
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserRoleRepo userRoleRepo;

    @Transactional
    public void create(UserDTO userDTO) {
        User user = new ModelMapper().map(userDTO, User.class);

        //convert dto => entity
//        user.setName(userDTO.getName());
//        user.setUsername(userDTO.getUsername());
//        user.setBirthdate(userDTO.getBirthdate());
//        user.setPassword(userDTO.getPassword());
//        user.setAvatar(userDTO.getAvatar());
//        user.setCreateAt(userDTO.getCreateAt());

        //rollback
        userRepo.save(user);

        List<UserRoleDTO> userRoleDTOs = userDTO.getUserRoles();
        for (UserRoleDTO userRoleDTO : userRoleDTOs) {
            if (userRoleDTO.getRole() != null) {
                //save to db
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(userRoleDTO.getRole());

                userRoleRepo.save(userRole);
            }
        }
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId())
                .orElseThrow(NoResultException::new);

        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());


        userRepo.save(user);
    }


    public UserDTO getById(int id) {
        User user = userRepo.findById(id).orElseThrow(NoResultException::new); //java8 lambda
//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setBirthdate(user.getBirthdate());
////        userDTO.setPassword(user.getPassword());
//        userDTO.setCreateAt(user.getCreateAt());
//        userDTO.setAvatar(user.getAvatar());

        // co the dung nhu nay
        UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);

        return userDTO;
    }

    public PageDTO<UserDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageRS = userRepo.searchByName("%" + name +"%", pageable);

        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : pageRS.getContent()) {
            userDTOs.add(new ModelMapper().map(user, UserDTO.class));
        }

        pageDTO.setContents(userDTOs); // set vao pagedto
        return pageDTO;
    }

    @Transactional
    public void delete(int id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Integer> ids) {
        userRepo.deleteAllById(ids);
    }

}


