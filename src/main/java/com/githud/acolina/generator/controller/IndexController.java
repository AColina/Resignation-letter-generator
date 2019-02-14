package com.githud.acolina.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class IndexController {

    // Match everything without a suffix (so not a static resource)
    @GetMapping(value = "/")
    public RedirectView redirect() {
        // Forward to home page so that route is preserved.
        return new RedirectView("swagger-ui.html");
    }
}
