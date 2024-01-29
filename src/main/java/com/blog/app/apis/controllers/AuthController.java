package com.blog.app.apis.controllers;

import com.blog.app.apis.entities.User;
import com.blog.app.apis.exceptions.ApiException;
import com.blog.app.apis.payloads.JwtAuthRequest;
import com.blog.app.apis.payloads.JwtAuthResponse;
import com.blog.app.apis.payloads.UserDto;

import com.blog.app.apis.repository.UserRepo;
import com.blog.app.apis.security.CustomUserDetailService;
import com.blog.app.apis.security.JwtTokenHelper;
import com.blog.app.apis.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private ModelMapper mapper;


    @Autowired
    private UserDetailsService userDetailsService;



    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;

//    @Autowired
    UserRepo userRepo;




    @PostMapping( "/login")
    public ResponseEntity<JwtAuthResponse>createToken(
            @RequestBody JwtAuthRequest request
            ) throws Exception {

        authenticate(request.getUsername(),request.getPassword());
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());

         UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
            String genrateToken=  this.jwtTokenHelper.generateToken(userDetails);

            JwtAuthResponse respose=new JwtAuthResponse();
            respose.setToken(genrateToken);
            return  new ResponseEntity<JwtAuthResponse>(respose, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        System.out.println(username);
        System.out.println(password);
        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
           // e.printStackTrace();
           throw new ApiException("Invalid username or password !!");

        }

    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser( @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }



//    @GetMapping("/current-user/")
//    public ResponseEntity<UserDto> getUser(Principal principal) {
//        User user = this.userRepo.findByEmail(principal.getName()).get();
//        return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
//    }
}
