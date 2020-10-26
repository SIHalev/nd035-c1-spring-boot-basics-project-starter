package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

  private NoteService noteService;
  private CredentialsService credentialsService;
  private FileService fileService;

  public HomeController(NoteService noteService,
      CredentialsService credentialsService,
      FileService fileService) {
    this.noteService = noteService;
    this.credentialsService = credentialsService;
    this.fileService = fileService;
  }

  @GetMapping
  public String homeView(Authentication authentication, Model model) {
    final int userId = Integer.parseInt(authentication.getName());
    model.addAttribute("userNotes", noteService.getNotes(userId));
    model.addAttribute("userCredentials", credentialsService.getCredentials(userId));
    model.addAttribute("userFiles", fileService.getFiles(userId));
    return "home";
  }
}
