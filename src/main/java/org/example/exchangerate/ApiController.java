package org.example.exchangerate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
@Controller
public class ApiController {

    RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://open.er-api.com/v6/latest/";
    // Обращаемся за курсом и временем для header
    String UsdReq = restTemplate.getForObject(url+"USD", String.class);
    String EuroReq = restTemplate.getForObject(url+"EUR", String.class);
    String frankReq = restTemplate.getForObject(url+"CHF", String.class);
    String funtReq = restTemplate.getForObject(url+"GBP", String.class);
    // Форматируем полученные переменные
    String usd = UsdReq.split("RUB" + "\":")[1].split(",")[0];
    String euro = EuroReq.split("RUB" + "\":")[1].split(",")[0];
    String frank = frankReq.split("RUB" + "\":")[1].split(",")[0];
    String funt = funtReq.split("RUB" + "\":")[1].split(",")[0];
    // Получаем актуальное время курса
    String time = UsdReq.split("time_last_update_utc\":\"")[1].split(",")[1].split("\\+")[0];

    @GetMapping
    public String index(Model model){
        model.addAttribute("time", time);
        model.addAttribute("usd", usd);
        model.addAttribute("euro",euro);
        model.addAttribute("frank",frank);
        model.addAttribute("funt",funt);
        return "index";
    }

    @GetMapping("/exchange")
    public String exchange(@RequestParam String firstCurrency, @RequestParam String secondCurrency, @RequestParam String currCount, Model model){
        model.addAttribute("time", time);
        model.addAttribute("usd", usd);
        model.addAttribute("euro",euro);
        model.addAttribute("frank",frank);
        model.addAttribute("funt",funt);
        // Получаем api запрос
        String req = restTemplate.getForObject(url+firstCurrency, String.class);
        // Обрабатываем полученный ответ и умножаем на необходимое количество
        String answer = req.split(secondCurrency + "\":")[1].split(",")[0];
        Double formatted = (Double.parseDouble(answer) * Integer.parseInt(currCount));
        answer = String.format("%.2f", formatted);

        model.addAttribute("answer", answer);
        return "index";
    }
}
