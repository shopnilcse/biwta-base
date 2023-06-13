package com.synesisit.biwta.base.controller;


import com.synesisit.biwta.base.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {



    @GetMapping("/apicheck")
    public String getTest(){
        return "base Service is running!";
    }

    @GetMapping("/testUsers")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity(new ApiResponse(true, "Got Request"),
                HttpStatus.OK);
    }
}
