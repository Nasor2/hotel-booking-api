package com.nasor.bookingapi.service;

import com.nasor.bookingapi.dto.user.UserDto;
import com.nasor.bookingapi.dto.user.UserRequestRegistration;
import com.nasor.bookingapi.exception.ResourceNotFound;
import com.nasor.bookingapi.mapper.UserDtoMapper;
import com.nasor.bookingapi.model.User;
import com.nasor.bookingapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;


    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userDtoMapper)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFound("User with id " + id + " not found");
        }
        return userDtoMapper.apply(user.get());
    }

    public UserDto create(UserRequestRegistration  userRequest) {

        if (userRepository.findByEmail(userRequest.email()).isPresent()) {
            throw new ResourceNotFound("User with email " + userRequest.email() + " Already exists");
        }

        User newUser = new User();
        newUser.setEmail(userRequest.email());
        newUser.setFirstName(userRequest.firstName());
        newUser.setLastName(userRequest.lastName());
        newUser.setPhoneNumber(userRequest.phoneNumber());
        newUser.setAddress(userRequest.address());

        return userDtoMapper.apply(userRepository.save(newUser));
    }

    public UserDto update(Long id, UserRequestRegistration  userRequest) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResourceNotFound("User with id " + id + " not found");
        }
        User user = existingUser.get();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setAddress(userRequest.address());
        user.setEmail(userRequest.email());

        return userDtoMapper.apply(userRepository.save(user));
    }

    public void delete(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResourceNotFound("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
