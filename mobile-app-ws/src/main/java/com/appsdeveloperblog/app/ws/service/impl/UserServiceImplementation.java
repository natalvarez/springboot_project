package com.appsdeveloperblog.app.ws.service.impl;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{
    static final int ENCR_USER_LONG = 30;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record allready exists.");


        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String encriptedUserId = utils.generateUserId(ENCR_USER_LONG);
        userEntity.setUserId(encriptedUserId);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storesUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storesUserDetails, returnValue);

        return returnValue;
    }

    @Override //login
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);
        
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    } 
    
    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        
        BeanUtils.copyProperties(userEntity, returnValue);
        
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updateUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updateUserDetails, returnValue);
        
        return returnValue;
    }

    @Override
    public void deleteteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        
        if (userEntity == null) throw new UsernameNotFoundException(userId);
        
        userRepository.delete(userEntity);
    }

    
    
}
