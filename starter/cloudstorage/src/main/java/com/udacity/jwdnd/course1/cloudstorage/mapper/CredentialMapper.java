package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CredentialMapper {

  @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
  Credential getCredential(int userId);

  @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
  List<Credential> getUserCredentials(int userId);

  @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "credentialId")
  int insert(Credential credential);

  @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, key = #{key} WHERE credentialId = #{credentialId}")
  int update(Credential credential);

  @Delete("DELETE FROM CREDENTIALS WHERE userId = #{userId} AND credentialId = #{credentialId}")
  int delete(Integer userId, Integer credentialId);

}
