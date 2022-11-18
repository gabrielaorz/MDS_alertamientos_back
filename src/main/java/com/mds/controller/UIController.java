package com.mds.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UIController {

	@GetMapping({"/index","/home","/"})
    public String index() {
        return "index";
    }
}
