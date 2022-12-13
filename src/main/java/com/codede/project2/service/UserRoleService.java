package com.codede.project2.service;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.entity.User;
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
public class UserRoleService {

    @Autowired
    UserRoleRepo userRoleRepo;

    @Autowired
    UserRepo userRepo;

    @Transactional
    public void create(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void update(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepo.findById(userRoleDTO.getId())
                .<NoResultException>orElseThrow(NoResultException::new);

        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getId()).orElseThrow(NoResultException::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void delete(int id) {
        userRoleRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Integer> ids) {
        userRoleRepo.deleteAllById(ids);
    }

    @Transactional
    public void deleteByUserId(int userId) {
        userRoleRepo.deleteByUserId(userId);
    }

    public UserRoleDTO getById(int id) {
        UserRole userRole = userRoleRepo.findById(id).orElseThrow(NoResultException::new);

        return new ModelMapper().map(userRole, UserRoleDTO.class);
    }

    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();

        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOS.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOS); // set vao pagedto
        return pageDTO;

    }

    public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> pageRS = userRoleRepo.searchByRole(role, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPage(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOS = new ArrayList<>();

        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOS.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOS); // set vao pagedto
        return pageDTO;

    }


}
