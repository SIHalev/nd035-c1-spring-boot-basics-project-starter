package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

  public final static String RELATIVE_URL = "/signup";

  @FindBy(id = "inputFirstName")
  private WebElement firstNameField;

  @FindBy(id = "inputLastName")
  private WebElement lastNameField;

  @FindBy(id = "inputUsername")
  private WebElement usernameField;

  @FindBy(id = "inputPassword")
  private WebElement passwordField;

  @FindBy(className = "btn-primary")
  private WebElement submitButton;

  public SignupPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void signup(String firstName, String lastName, String username, String password) {
    this.firstNameField.sendKeys(firstName);
    this.lastNameField.sendKeys(lastName);
    this.usernameField.sendKeys(username);
    this.passwordField.sendKeys(password);
    this.submitButton.click();
  }
}
