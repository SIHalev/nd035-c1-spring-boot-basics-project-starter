package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

  private final CredentialsService credentialsService;

  public CredentialsController(CredentialsService credentialsService) {
    this.credentialsService = credentialsService;
  }

  @PostMapping
  public String upsertCredential(Authentication authentication,
      @ModelAttribute Credential credential, Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());
    credential.setUserId(userId);

    if (!userId.equals(credential.getUserId())) {
      model.addAttribute("actionError", true);
      return "result";
    }

    int changes;
    if (StringUtils.isEmpty(credential.getCredentialId())) {
      changes = credentialsService.createCredential(credential);
    } else {
      changes = credentialsService.updateCredential(credential);
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
  @GetMapping("/delete/{credentialId}")
  public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId,
      Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());

    final int changes = credentialsService.deleteCredential(userId, credentialId);
    if (changes > 0) {
      model.addAttribute("actionSuccess", true);
    } else if (changes == 0) {
      model.addAttribute("actionPersistenceError", true);
    } else {
      model.addAttribute("actionError", true);
    }
    return "result";
  }

  @GetMapping("/get/{credentialId}")
  @ResponseBody
  public Credential getCredential(@PathVariable(name = "credentialId") String credentialId) {
    return credentialsService.getDecryptedCredential(Integer.parseInt(credentialId));
  }

}
