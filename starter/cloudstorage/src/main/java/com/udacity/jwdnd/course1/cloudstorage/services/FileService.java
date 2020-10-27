package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  private final FileMapper fileMapper;

  public FileService(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  public List<File> getFiles(Integer userId) {
    return fileMapper.getUserFiles(userId);
  }

  public File getFile(Integer userId, Integer fileId) {
    return fileMapper.getFile(userId, fileId);
  }

  public int createFile(File file) {
    final Optional<File> fileWithSameName = fileMapper.getUserFiles(file.getUserId()).stream()
        .filter(userFile -> file.getFileName().equals(userFile.getFileName()))
        .findAny();

    if (fileWithSameName.isEmpty()) {
      return fileMapper.insert(file);
    }

    return 0;
  }

  public int deleteFile(Integer userId, Integer noteId) {
    return fileMapper.delete(userId, noteId);
  }

}
