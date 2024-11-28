package org.example.exchangerate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
@Controller
public class ApiController {
    String currencyOne;
    String currencyTwo;

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://open.er-api.com/v6/latest/";


    @GetMapping
    public String index(){
        return "index";
    }
    @GetMapping("/exchange")
    public String exchange(@RequestParam String firstCurrency, @RequestParam String secondCurrency, Model model){
        // Получаем api запрос
        String req = restTemplate.getForObject(url+firstCurrency, String.class);
        // Обрабатываем полученный ответ и достаем из него необходимый курс
        String answer = req.split(secondCurrency + "\":")[1].split(",")[0];
        model.addAttribute("answer", answer);
        return "answer";
    }
}
