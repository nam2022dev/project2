package com.codede.project2.restApi;

import com.codede.project2.DTO.PageDTO;
import com.codede.project2.DTO.ResponseDTO;
import com.codede.project2.DTO.UserDTO;
import com.codede.project2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserRestAPI {
    @Autowired
    UserService userService;

    @PostMapping("/new")
    public ResponseDTO<Void> add(@ModelAttribute UserDTO u) throws IllegalAccessError, IOException {
        if (u.getFile() != null && u.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "E:/WorkSpace/file/";

            String filename = u.getFile().getOriginalFilename();
            File file = new File(UPLOAD_FOLDER + filename);
            u.getFile().transferTo(file);

            u.setAvatar(filename); //save to db
        }
        //goi qua service
        userService.create(u);

//        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
//        responseDTO.setStatus(200);
//        return responseDTO;
        return ResponseDTO.<Void>builder().status(200).build();
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename,
                         HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "E:/WorkSpace/file/";

        File file = new File(UPLOAD_FOLDER + filename);

        Files.copy(file.toPath(), response.getOutputStream());
    }

    @PostMapping("/search")//?name
    public ResponseDTO<PageDTO<UserDTO>> search(@RequestParam(name = "id", required = false) Integer id,
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

        return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).data(pageRS).build();
    }

    @GetMapping("/get/{id}") // get/10
    public ResponseDTO<UserDTO> get(@PathVariable("id") int id) {
        UserDTO userDTO = userService.getById(id);

        return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
//        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<UserDTO>();
//        responseDTO.setStatus(200);
//        responseDTO.setData(userDTO);
    }

    @GetMapping("/edit")
    public UserDTO edit(@RequestParam("id") int id) {
        return userService.getById(id);
    }

    @PostMapping("/edit")
    public ResponseDTO<UserDTO> edit(@ModelAttribute UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseDTO.<UserDTO>builder().status(200).data(userDTO).build();
    }

    @DeleteMapping("delete")
    public ResponseDTO<Void> delete(@RequestParam("id") int id) {
        userService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }

}
