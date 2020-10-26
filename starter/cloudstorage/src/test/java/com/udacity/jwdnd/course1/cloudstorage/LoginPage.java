package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  public final static String RELATIVE_URL = "/login";

  @FindBy(id="inputUsername")
  private WebElement usernameField;

  @FindBy(id="inputPassword")
  private WebElement passwordField;

  @FindBy(className="btn-primary")
  private WebElement submitButton;

  @FindBy(id="signup-link")
  private WebElement signupLink;

  public LoginPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void login(String username, String password) {
    this.usernameField.sendKeys(username);
    this.passwordField.sendKeys(password);
    this.submitButton.click();
  }
}
