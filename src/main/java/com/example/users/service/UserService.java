package com.example.users.service;

import com.example.users.controller.CreateUserDto;
import com.example.users.controller.UpdateUserDto;
import com.example.users.entity.User;
import com.example.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserDto dto){
        var user = new User(null, dto.username(), dto.email(), dto.password(), Instant.now(), null);

        return userRepository.save(user);

    }

    public Optional<User> getUserById(int userId){
        return userRepository.findById(userId);
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void deleteById(int userId){
        var userExist = userRepository.existsById(userId);

        if (userExist){
            userRepository.deleteById(userId);
        }
    }

    public void updateUserById(int userId, UpdateUserDto dto){
         var userExist = userRepository.findById(userId);

         if (userExist.isPresent()){
             var user = userExist.get();

             if (dto.username() != null){
                 user.setUsername(dto.username());
             }
             if (dto.password() != null){
                 user.setPassword(dto.password());
             }
             user.setUpdateDate(Instant.now());

             userRepository.save(user);
         }
    }
}
