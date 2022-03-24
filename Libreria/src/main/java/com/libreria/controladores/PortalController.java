package com.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortalController {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

}
