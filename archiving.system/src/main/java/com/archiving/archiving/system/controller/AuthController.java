package com.archiving.archiving.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archiving.archiving.system.dto.UserRequest;
import com.archiving.archiving.system.model.User;
import com.archiving.archiving.system.payload.JwtResponse;
import com.archiving.archiving.system.repository.UserRepository;
import com.archiving.archiving.system.security.JwtTokenProvider;

@RestController
@RequestMapping("/auth")
public class AuthController {

private AuthenticationManager authenticationManager;
	
	private JwtTokenProvider jwtTokenProvider;
	
	private PasswordEncoder passwordEncoder;

	private UserRepository userRepository;
	
	
    public AuthController(AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }
    @CrossOrigin
    @PostMapping("/login")
	public JwtResponse login(@RequestBody UserRequest loginRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
		
		Authentication auth = authenticationManager.authenticate(authToken);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtTokenProvider.generateJwtToken(auth);
		User user = userRepository.findByUsername(loginRequest.getUsername());
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setMessage("Bearer "+ jwtToken);
		jwtResponse.setId(user.getId());
		
		return jwtResponse;
	}
    
    @PostMapping("/register")
	public ResponseEntity<JwtResponse> register(@RequestBody UserRequest registerRequest) {
    	JwtResponse jwtResponse = new JwtResponse();
		if(userRepository.findByUsername(registerRequest.getUsername()) != null) {
			jwtResponse.setMessage("username already exists");
			return new ResponseEntity<>(jwtResponse,HttpStatus.BAD_REQUEST);
		}	
		
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		userRepository.save(user);
		jwtResponse.setMessage("User succesfully registered.");
		jwtResponse.setId(user.getId());
		return new ResponseEntity<>(jwtResponse,HttpStatus.CREATED);
	}
    
	
	
	
	
	
	
	
}
