package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  private final NoteMapper noteMapper;

  public NoteService(NoteMapper noteMapper) {
    this.noteMapper = noteMapper;
  }

  public List<Note> getNotes(int userId) {
    return noteMapper.getUserNotes(userId);
  }

  public int createNote(Note note) {
    return noteMapper.insert(note);
  }

  public int updateNote(Note note) {
    return noteMapper.update(note);
  }

  public int deleteNote(Integer userId, Integer noteId) {
    return noteMapper.delete(userId, noteId);
  }

}
