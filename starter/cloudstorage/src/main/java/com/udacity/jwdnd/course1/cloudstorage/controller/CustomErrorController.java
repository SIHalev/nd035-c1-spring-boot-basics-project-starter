package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

  @GetMapping("/error")
  public String handleError(Model model) {
    model.addAttribute("actionError", true);
    model.addAttribute("errorMessage",
        "The page you are trying to visit is no longer present or visible.");
    return "result";
  }

  @Override
  public String getErrorPath() {
    return null;
  }
}
