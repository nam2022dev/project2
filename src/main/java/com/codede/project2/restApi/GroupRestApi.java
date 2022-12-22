package com.codede.project2.restApi;

import com.codede.project2.DTO.GroupDTO;
import com.codede.project2.DTO.ResponseDTO;
import com.codede.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
public class GroupRestApi {

    @Autowired
    GroupService groupService;

    @PostMapping("/")
    public ResponseDTO<GroupDTO> add(@RequestBody @Validated GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Void> delete(@PathVariable int id) {
        groupService.delete(id);
        return ResponseDTO.<Void>builder().status(200).build();
    }


}
