package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /*
    맨 처음에 http://localhost:8080/ 으로 들어오면
    @GetMapping("/")가 있는 이곳으로 가장 먼저 오게 된다.
     */
    @GetMapping("/")
    public String home(){
        // home.html 호출
        return "home";
    }

}
