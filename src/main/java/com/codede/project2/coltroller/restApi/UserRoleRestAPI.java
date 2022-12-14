package com.codede.project2.coltroller.restApi;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/search")//?name
    public PageDTO<UserRoleDTO> search(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "userID", required = false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page) {


        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ? "%%" : role;

        PageDTO<UserRoleDTO> pageRS = null;

        if ((userId != null)) {
            pageRS = userRoleService.searchByUserId(userId, page, size);
        } else {
            pageRS = userRoleService.searchByRole(role, page, size);
        }


        return pageRS;
    }

}
