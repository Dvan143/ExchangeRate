package org.example.exchangerate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
@Controller
public class ApiController {
    RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://open.er-api.com/v6/latest/";

    @GetMapping
    public String index(){
        return "index";
    }
}
