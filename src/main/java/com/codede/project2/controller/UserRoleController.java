package com.codede.project2.controller;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.UserRoleDTO;
import com.codede.project2.repo.UserRoleRepo;
import com.codede.project2.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user-role")
public class UserRoleController {

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRoleRepo userRoleRepo;

    @GetMapping("/new")
    public String create() {
        return "/userrole/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute UserRoleDTO userRoleDTO) {
        userRoleService.create(userRoleDTO);
        return "redirect:/user-role/search";
    }

    @GetMapping("/search")//?name
    public String search(
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "userID", required = false) Integer userId,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "page", required = false) Integer page, Model model) {


        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        role = role == null ? "%%" : role;

        PageDTO<UserRoleDTO> pageRS = null;

        if ((userId != null)) {
            pageRS = userRoleService.searchByUserId(userId, page, size);
        } else {
            pageRS = userRoleService.searchByRole(role, page, size);
        }

        model.addAttribute("totalPage", pageRS.getTotalPage());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userRoleList", pageRS.getContents());

        model.addAttribute("role", role);
        model.addAttribute("userId", userId);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "userrole/search.html";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("userRole", userRoleService.getById(id));
        return "userrole/detail.html";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        userRoleService.delete(id);
        return "redirect:/user-role/search";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("userRole", userRoleService.getById(id));
        return "userrole/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserRoleDTO userRoleDTO) {
        userRoleService.update(userRoleDTO);
        return "redirect:/user-role/search";
    }

}
