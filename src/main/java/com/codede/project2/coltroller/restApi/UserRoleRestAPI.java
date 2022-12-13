package com.codede.project2.coltroller.restApi;

import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-role")
public class UserRoleRestAPI {

    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/new")
//    @ResponseStatus(HttpStatus.CREATED) // muon tra ve trang thai khac thi viet cai nay
    public void add(@ModelAttribute UserRoleDTO userRoleDTO) {
        userRoleService.create(userRoleDTO);
    } // thanh cong tra ve 200

    @PostMapping("/new-json")
    public void create(@RequestBody UserRoleDTO userRoleDTO) {
        userRoleService.create(userRoleDTO);
    }

    @DeleteMapping("/delete") //?id=1 REST API
    public void delete(@RequestParam("id") int id) {
        userRoleService.delete(id);
    }

    @GetMapping("/get/{id}")
    public UserRoleDTO get(@PathVariable("id") int id) {
        return userRoleService.getById(id);
        //jackson
    }

}
