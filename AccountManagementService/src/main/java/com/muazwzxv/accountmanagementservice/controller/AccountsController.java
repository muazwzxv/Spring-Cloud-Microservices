package com.muazwzxv.accountmanagementservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountsController {

    @GetMapping("/status/check")
    public String status() {
        return "accounts management is working ....";
    }
}
