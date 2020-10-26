package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
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
    return fileMapper.getFile(fileId);
  }

  public int createFile(File file) {
    return fileMapper.insert(file);
  }

  public int deleteFile(Integer userId, Integer noteId) {
    return fileMapper.delete(userId, noteId);
  }

}
