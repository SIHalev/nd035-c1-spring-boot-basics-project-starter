package com.udacity.jwdnd.course1.cloudstorage.controller;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping("/upload")
  public String uploadFile(Authentication authentication,
      @RequestParam("fileUpload") MultipartFile multipartFile, Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());

    File file = new File();
    file.setFileName(multipartFile.getOriginalFilename());
    file.setContentType(multipartFile.getContentType());
    file.setFileSize(String.valueOf(multipartFile.getSize()));
    file.setUserId(userId);
    try {
      file.setFileData(multipartFile.getBytes());
      fileService.createFile(file);
      model.addAttribute("actionSuccess", true);
    } catch (IOException e) {
      model.addAttribute("actionError", true);
    }

    return "result";
  }

  @GetMapping("/download/{fileId}")
  public ResponseEntity downloadFile(Authentication authentication, @PathVariable Integer fileId,
      Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());
    final File file = fileService.getFile(userId, fileId);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(file.getContentType()))
        .header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
        .body(file.getFileData());
  }

  @GetMapping("/delete/{fileId}")
  public String deleteFile(Authentication authentication, @PathVariable Integer fileId,
      Model model) {
    final Integer userId = Integer.parseInt(authentication.getName());
    final int changes = fileService.deleteFile(userId, fileId);

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
