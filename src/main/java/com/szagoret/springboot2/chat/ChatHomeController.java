package com.szagoret.springboot2.chat;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class ChatHomeController {

    @GetMapping("/")
    public Mono<String> index(@AuthenticationPrincipal Authentication auth, Model model) {
        model.addAttribute("authentication", auth);
        return Mono.just("index");
    }
}
