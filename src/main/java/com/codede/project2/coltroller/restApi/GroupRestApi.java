package com.codede.project2.coltroller.restApi;

import com.codede.project2.DTO.GroupDTO;
import com.codede.project2.DTO.ResponseDTO;
import com.codede.project2.DTO.StudentDTO;
import com.codede.project2.entity.Group;
import com.codede.project2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
public class GroupRestApi {

    @Autowired
    GroupService groupService;

    @PostMapping("/new")
    public ResponseDTO<GroupDTO> add(@RequestBody GroupDTO groupDTO) {
        groupService.create(groupDTO);
        return ResponseDTO.<GroupDTO>builder().status(200).data(groupDTO).build();
    }
}
