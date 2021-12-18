package com.controller;

import com.jwt.jwtModel.JwtRequest;
import com.jwt.jwtModel.JwtRespone;
import com.jwt.jwtUtil.JwtTokenUtil;
import com.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class JwtAuthenticateController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());

        final UserDetails userDetail = jwtUserDetailService.loadUserByUsername(jwtRequest.getUsername());

        final  String jwtToken = jwtTokenUtil.generateToken(userDetail);
        return ResponseEntity.ok(new JwtRespone(jwtToken));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
