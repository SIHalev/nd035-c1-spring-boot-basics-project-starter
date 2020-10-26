package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

  public static final String RELATIVE_URL = "/home";

  @FindBy(id = "logoutButton")
  private WebElement logoutButton;

  @FindBy(id = "nav-files-tab")
  private WebElement filesTab;

  @FindBy(id = "nav-notes-tab")
  private WebElement notesTab;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credentialsTab;

  // Add note
  @FindBy(id = "addNoteButton")
  private WebElement addNoteButton;

  @FindBy(id = "note-title")
  private WebElement noteTitleInput;

  @FindBy(id = "note-description")
  private WebElement noteDescriptionInput;

  // @FindBy(id = "noteSubmit")
  @FindBy(id = "submitNoteModal")
  private WebElement noteSubmitButton;

  // Add credential
  @FindBy(id = "addCredentialButton")
  private WebElement addCredentialButton;

  @FindBy(id = "credential-url")
  private WebElement credentialUrlInput;

  @FindBy(id = "credential-username")
  private WebElement credentialUsernameInput;

  @FindBy(id = "credential-password")
  private WebElement credentialPasswordInput;

  //  @FindBy(id = "credentialSubmit")
  @FindBy(id = "submitCredentialModal")
  private WebElement credentialSubmitButton;

  @FindBy(className = "noteTitle")
  private WebElement noteTitle;

  @FindBy(className = "noteDescription")
  private WebElement noteDescription;

  @FindBy(css = ".editNoteButton:last-of-type")
  private WebElement editNoteButton;

  @FindBy(css = ".deleteNoteLink:last-of-type")
  private WebElement deleteNoteLink;

  @FindBy(className = "credentialUrl")
  private WebElement credentialUrl;

  @FindBy(className = "credentialUsername")
  private WebElement credentialUsername;

  @FindBy(className = "credentialPassword")
  private WebElement credentialPassword;

  @FindBy(css = ".editCredentialButton:last-of-type")
  private WebElement editCredentialButton;

  @FindBy(css = ".deleteCredentialLink:last-of-type")
  private WebElement deleteCredentialLink;

  public HomePage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void logout() {
    logoutButton.click();
  }

  public void createNote(String title, String description, WebDriverWait wait) {
    moveToTab(notesTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
    addNoteButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton));
    noteTitleInput.sendKeys(title);
    noteDescriptionInput.sendKeys(description);
    noteSubmitButton.click();
  }

  public void editNote(String title, String description, WebDriverWait wait) {
    moveToTab(notesTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(editNoteButton));
    editNoteButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton));
    noteTitleInput.clear();
    noteTitleInput.sendKeys(title);
    noteDescriptionInput.clear();
    noteDescriptionInput.sendKeys(description);
    noteSubmitButton.click();
  }

  public void deleteNote(WebDriverWait wait) {
    moveToTab(notesTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(deleteNoteLink));
    deleteNoteLink.click();
  }

  public void createCredential(String url, String username, String password, WebDriverWait wait) {
    moveToTab(credentialsTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(credentialsTab));
    credentialsTab.click();
    wait.until(ExpectedConditions.elementToBeClickable(addCredentialButton));
    addCredentialButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitButton));
    credentialUrlInput.sendKeys(url);
    credentialUsernameInput.sendKeys(username);
    credentialPasswordInput.sendKeys(password);
    credentialSubmitButton.click();
  }

  public void editCredential(String url, String username, String password, WebDriverWait wait) {
    moveToTab(credentialsTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton));
    editCredentialButton.click();
    wait.until(ExpectedConditions.elementToBeClickable(credentialSubmitButton));
    credentialUrlInput.clear();
    credentialUrlInput.sendKeys(url);
    credentialUsernameInput.clear();
    credentialUsernameInput.sendKeys(username);
    credentialPasswordInput.clear();
    credentialPasswordInput.sendKeys(password);
    credentialSubmitButton.click();
  }

  public String getUnencryptedCredential(WebDriverWait wait) {
    moveToTab(credentialsTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(editCredentialButton));
    editCredentialButton.click();
    wait.until(ExpectedConditions.visibilityOf(credentialPasswordInput));
    return credentialPasswordInput.getAttribute("value");
  }

  public void deleteCredential(WebDriverWait wait) {
    moveToTab(credentialsTab, wait);
    wait.until(ExpectedConditions.elementToBeClickable(deleteCredentialLink));
    deleteCredentialLink.click();
  }

  public Credential getRecentCredential(WebDriverWait wait) {
    moveToTab(credentialsTab, wait);

    wait.until(ExpectedConditions
        .visibilityOfAllElements(credentialUrl, credentialUsername, credentialPassword));
    return new Credential(null, credentialUrl.getAttribute("innerHTML"),
        credentialUsername.getAttribute("innerHTML"), null,
        credentialPassword.getAttribute("innerHTML"), null);
  }

  public Note getRecentNote(WebDriverWait wait) {
    moveToTab(notesTab, wait);
    wait.until(ExpectedConditions.visibilityOfAllElements(notesTab, noteDescription));
    return new Note(null, noteTitle.getAttribute("innerHTML"),
        noteDescription.getAttribute("innerHTML"), null);
  }

  public boolean isNoteVisible(WebDriverWait wait) {
    try {
      return noteTitle.isDisplayed() || noteDescription.isDisplayed();
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public boolean isCredentialVisible(WebDriverWait wait) {
    try {
      return credentialUrl.isDisplayed() || credentialUsername.isDisplayed() || credentialPassword.isDisplayed();
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private void moveToTab(WebElement tab, WebDriverWait wait) {
    try {
      // No clue why FE doesn't load this item even with wait condition
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }

    wait.until(ExpectedConditions.elementToBeClickable(tab));
    tab.click();
  }

}
