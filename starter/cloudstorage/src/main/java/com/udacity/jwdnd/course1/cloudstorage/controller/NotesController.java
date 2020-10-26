package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {

  private final NoteService noteService;

  public NotesController(NoteService noteService) {
    this.noteService = noteService;
  }

  @PostMapping
  public String upsertNote(Authentication authentication, @ModelAttribute Note note, Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());
    note.setUserId(userId);

    if (!userId.equals(note.getUserId())) {
      model.addAttribute("actionError", true);
      return "result";
    }

    int changes;
    if (StringUtils.isEmpty(note.getNoteId())) {
      changes = noteService.createNote(note);
    } else {
      changes = noteService.updateNote(note);
    }

    if (changes > 0) {
      model.addAttribute("actionSuccess", true);
    } else if (changes == 0) {
      model.addAttribute("actionPersistenceError", true);
    } else {
      model.addAttribute("actionError", true);
    }

    return "result";
  }

  // I have no clue why deletion of notes is done via <a> in the FE presets
  @GetMapping("/delete/{nodeId}")
  public String deleteNote(Authentication authentication, @PathVariable Integer nodeId, Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());

    final int changes = noteService.deleteNote(userId, nodeId);
    if (changes > 0) {
      model.addAttribute("actionSuccess", true);
    } else if (changes == 0) {
      model.addAttribute("actionPersistenceError", true);
    } else {
      model.addAttribute("actionError", true);
    }

    return "result";
  }

}

