package com.codede.project2.coltroller.restApi;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.UserDTO;
import com.codede.project2.repo.UserRepo;
import com.codede.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@Controller
@RequestMapping("/api/user")
public class UserRestAPI {
    @Autowired
    UserService userService;

    @PostMapping("/new")
    @ResponseBody
    public void add(@RequestBody UserDTO u) throws IllegalAccessError, IOException {
        if (u.getFile() != null && u.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "E:/WorkSpace/file";

            String filename = u.getFile().getOriginalFilename();
            File file = new File(UPLOAD_FOLDER + filename);
            u.getFile().transferTo(file);

            u.setAvatar(filename); //save to db
        }
        //goi qua service
        userService.create(u);

    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename,
                         HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "E:/WorkSpace/file/";

        File file = new File(UPLOAD_FOLDER + filename);

        Files.copy(file.toPath(), response.getOutputStream());
    }

    @PostMapping("/search")//?name
    public PageDTO<UserDTO> search(@RequestParam(name = "id", required = false) Integer id,
                                            @RequestParam(name = "name", required = false) String name,

                                            @RequestParam(name = "start", required = false)
                         @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                                            @RequestParam(name = "end", required = false)
                         @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

                                            @RequestParam(name = "size", required = false) Integer size,
                                            @RequestParam(name = "page", required = false) Integer page) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        PageDTO<UserDTO> pageRS = userService.searchByName(name, page, size);

        return pageRS;
    }

    @GetMapping("/get/{id}") // get/10
    public UserDTO get(@PathVariable("id") int id) {
        return userService.getById(id);
    }

    @GetMapping("/edit")
    public UserDTO edit(@RequestParam("id") int id) {
        return userService.getById(id);
    }
    @PostMapping("/edit")
    public UserDTO edit(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return userDTO;
    }

}
