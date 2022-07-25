package com.appsdeveloperblog.app.ws.ui.controller;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequest;
import com.appsdeveloperblog.app.ws.ui.model.response.OpertationStatusModel;
import com.appsdeveloperblog.app.ws.ui.model.response.RequesOperationName;
import com.appsdeveloperblog.app.ws.ui.model.response.RequesOperationStatus;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users") // http://localhost:8080/users (url para todo lo que tenga que ver con users)
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequest userDetails) {
        
        UserRest responseValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto); // converts the userDetails into userDto

        UserDto createdUSer = this.userService.createUser(userDto);
        BeanUtils.copyProperties(createdUSer, responseValue); // converts the createdUSer into responseValue
       
        return responseValue;
    }

    @PutMapping(path = "/{id}")
    public UserRest updataeUser(@PathVariable String id, @RequestBody UserDetailsRequest userDetails) {
        UserRest responseValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto); 

        UserDto updateUSer = this.userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updateUSer, responseValue);
       
        return responseValue;
    }

    @DeleteMapping(path = "/{id}")
    public OpertationStatusModel deleteUser(@PathVariable String id) {
        
        OpertationStatusModel returnValue = new OpertationStatusModel();
        returnValue.setOperationName(RequesOperationName.DELETE.name());

        this.userService.deleteteUser(id);

        returnValue.setOperationResult(RequesOperationStatus.SUCCESS.name());
        
        return returnValue;
    }


}
