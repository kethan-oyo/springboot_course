package com.stacksimplify.restservices.Services;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public User createUser(User user){

        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);

        return user;
    }

    public User updateUserById(User user, Long id){
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        }
    }

    public User getUserByUername(String username){
        return userRepository.findByUsername(username);
    }
}
