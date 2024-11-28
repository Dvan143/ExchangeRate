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
    // Обращаемся за курсом и временем для header
    String UsdReq = restTemplate.getForObject(url+"USD", String.class);
    String EuroReq = restTemplate.getForObject(url+"EUR", String.class);
    // Получаем актуальное время курса
    String time = UsdReq.split("time_last_update_utc\":\"")[1].split(",")[1].split("\\+")[0];

    String usd = UsdReq.split("RUB" + "\":")[1].split(",")[0];
    String euro = EuroReq.split("RUB" + "\":")[1].split(",")[0];

    @GetMapping
    public String index(Model model){
        model.addAttribute("time", time);
        model.addAttribute("usd", usd);
        model.addAttribute("euro",euro);
        return "index";
    }

    @GetMapping("/exchange")
    public String exchange(@RequestParam String firstCurrency, @RequestParam String secondCurrency, @RequestParam String currCount, Model model){
        model.addAttribute("time", time);
        model.addAttribute("usd", usd);
        model.addAttribute("euro",euro);
        // Получаем api запрос
        String req = restTemplate.getForObject(url+firstCurrency, String.class);
        // Обрабатываем полученный ответ и умножаем на необходимое количество, далее округляем до 2 цифр после точки
        String answer = req.split(secondCurrency + "\":")[1].split(",")[0];
        answer = String.valueOf((Double.valueOf(answer) * Integer.parseInt(currCount)));
        model.addAttribute("answer", answer);
        return "index";
    }
}
