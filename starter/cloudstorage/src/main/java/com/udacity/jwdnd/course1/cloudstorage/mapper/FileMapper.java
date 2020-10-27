package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

  @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileId = #{fileId}")
  File getFile(int userId, int fileId);

  @Select("SELECT * FROM FILES WHERE userId = #{userId}")
  List<File> getUserFiles(int userId);

  @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
  @Options(useGeneratedKeys = true, keyProperty = "fileId")
  int insert(File file);

  @Delete("DELETE FROM FILES WHERE userId = #{userId} AND fileId = #{fileId}")
  int delete(Integer userId, Integer fileId);
}
