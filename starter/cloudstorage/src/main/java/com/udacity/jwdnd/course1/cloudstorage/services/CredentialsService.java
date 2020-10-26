package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

  private CredentialMapper credentialMapper;
  private EncryptionService encryptionService;

  public CredentialsService(CredentialMapper credentialMapper,
      EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.encryptionService = encryptionService;
  }

  public List<Credential> getCredentials(int userId) {
    return credentialMapper.getUserCredentials(userId);
  }

  public Credential getCredential(Integer credentialId) {
    return credentialMapper.getCredential(credentialId);
  }

  public Credential getDecryptedCredential(Integer credentialId) {
    Credential credential = getCredential(credentialId);
    String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    credential.setPassword(decryptedPassword);
    return credential;
  }

  public int createCredential(Credential credential) {
    processSensitiveInformation(credential);
    return credentialMapper.insert(credential);
  }

  public int updateCredential(Credential credential) {
    processSensitiveInformation(credential);
    return credentialMapper.update(credential);
  }

  public int deleteCredential(Integer userId, Integer credentialId) {
    return credentialMapper.delete(userId, credentialId);
  }

  private void processSensitiveInformation(Credential credential) {
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String rawPassword = credential.getPassword();
    String encryptedPassword = encryptionService.encryptValue(rawPassword, encodedKey);
    credential.setPassword(encryptedPassword);
    credential.setKey(encodedKey);
  }

}
