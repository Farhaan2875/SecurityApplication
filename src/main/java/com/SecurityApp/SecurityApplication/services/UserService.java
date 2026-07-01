package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.SecurityApp.SecurityApplication.dto.UserDTO;
import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("user with email " + username + " not found"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with id " + userId + " not found"));
    }


    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email " + signUpDTO.getEmail() + " already exists");
        }else{
            User tobeCreatedUser = modelMapper.map(signUpDTO,User.class);
            tobeCreatedUser.setPassword(passwordEncoder.encode(tobeCreatedUser.getPassword()));
            User saveduser = userRepository.save(tobeCreatedUser);
            return modelMapper.map(saveduser, UserDTO.class);
        }
    }




}
