package com.stacksimplify.restservices.Services;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public User createUser(User user) throws UserExistsException {
        User existinguser = userRepository.findByUsername(user.getUsername());
        if(existinguser != null){
            throw new UserExistsException("User already exists");
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFound {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()){
            throw new UserNotFound("User not found in user repository");
        }

        return user;
    }

    public User updateUserById(User user, Long id) throws UserNotFound{
        Optional<User> OptionalUser = userRepository.findById(id);

        if (!OptionalUser.isPresent()) {
            throw new UserNotFound("User not found in user repository Give valid id");
        }
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
        Optional<User> OptionalUser = userRepository.findById(id);

        if (!OptionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found in user repository Give valid id");
        }
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
