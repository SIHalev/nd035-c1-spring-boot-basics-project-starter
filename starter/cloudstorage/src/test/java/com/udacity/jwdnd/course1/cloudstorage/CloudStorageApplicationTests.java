package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

  private static WebDriver driver;
  private static WebDriverWait wait;

  @LocalServerPort
  private int port;
  public String baseURL;

  @BeforeAll
  public static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    baseURL = "http://localhost:" + port;
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, 15);
  }

  @AfterEach
  public void afterEach() {
    driver.quit();
  }

  @Test
  public void testIfUnauthorizedUserCanAccessLoginPage() {
    driver.get(baseURL + LoginPage.RELATIVE_URL);
    assertEquals("login", driver.getTitle().toLowerCase());
  }

  @Test
  public void testIfUnauthorizedUserCanAccessSignupPage() {
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    assertEquals("sign up", driver.getTitle().toLowerCase());
  }

  @Test
  public void testIfUnauthorizedUserCanNotAccessHomePage() {
    driver.get(baseURL + HomePage.RELATIVE_URL);
    assertNotEquals("home", driver.getTitle().toLowerCase());
  }

  @Test
  public void getLoginPage() {
    driver.get("http://localhost:" + this.port + "/login");
    assertEquals("Login", driver.getTitle());
  }

  @Test
  public void testIfUserCanSignupAndLogin() {
    String username = "test_username";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    String expectedUrl = baseURL + HomePage.RELATIVE_URL;
    driver.get(expectedUrl);
    assertEquals(expectedUrl, driver.getCurrentUrl());
  }

  @Test
  public void testIfUserCanSignupAndLoginAndLogout() {
    String username = "test_username";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    String expectedUrl = baseURL + HomePage.RELATIVE_URL;
    driver.get(expectedUrl);
    assertEquals(expectedUrl, driver.getCurrentUrl());
  }

  @Test
  public void testIfUserCanNotSeeHomePageAfterLogout() {
    String username = "test_username";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    String expectedUrl = baseURL + HomePage.RELATIVE_URL;
    driver.get(expectedUrl);
    assertEquals(expectedUrl, driver.getCurrentUrl());

    driver.get(baseURL + HomePage.RELATIVE_URL);
    assertEquals("home", driver.getTitle().toLowerCase());

    HomePage homePage = new HomePage(driver);
    homePage.logout();
    driver.get(baseURL + HomePage.RELATIVE_URL);
    assertNotEquals("home", driver.getTitle().toLowerCase());
  }

  @Test
  public void testIfUserCanCreateNote() {
    String username = "createNote";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String noteTitle = "noteTitle";
    String noteDescription = "noteDescription";

    homePage.createNote(noteTitle, noteDescription, wait);

    goToHome();

    final Note visibleNote = homePage.getRecentNote(wait);
    assertEquals(noteTitle, visibleNote.getNoteTitle());
    assertEquals(noteDescription, visibleNote.getNoteDescription());
  }

  @Test
  public void testIfUserCanEditNote() {
    String username = "editNote";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String noteTitle = "noteTitle";
    String noteDescription = "noteDescription";

    homePage.createNote(noteTitle, noteDescription, wait);

    goToHome();

    String noteEditedTitle = "noteEditedTitle";
    String noteEditedDescription = "noteEditedDescription";
    homePage.editNote(noteEditedTitle, noteEditedDescription, wait);

    goToHome();

    final Note visibleNote = homePage.getRecentNote(wait);
    assertEquals(noteEditedTitle, visibleNote.getNoteTitle());
    assertEquals(noteEditedDescription, visibleNote.getNoteDescription());
    assertNotEquals(noteTitle, visibleNote.getNoteTitle());
    assertNotEquals(noteDescription, visibleNote.getNoteDescription());
  }

  @Test
  public void testIfUserCanDeleteNote() {
    String username = "deleteNote";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String noteTitle = "noteTitle";
    String noteDescription = "noteDescription";
    homePage.createNote(noteTitle, noteDescription, wait);
    goToHome();
    homePage.deleteNote(wait);
    goToHome();
    assertFalse(homePage.isNoteVisible(wait));
  }

  private void goToHome() {
//    ResultPage resultPage = new ResultPage(driver);
//    assertTrue(resultPage.isSuccess(wait));
//    resultPage.goToHome(wait);
    driver.get(baseURL + HomePage.RELATIVE_URL);
  }

  @Test
  public void testIfUserCanCreateEncryptedCredential() {
    String username = "encryptCredentials";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String credentialUrl = "credentialUrl";
    String credentialUsername = "credentialUsername";
    String credentialPassword = "credentialPassword";

    homePage.createCredential(credentialUrl, credentialUsername, credentialPassword, wait);
    goToHome();
    final Credential credential = homePage.getRecentCredential(wait);
    assertEquals(credentialUrl, credential.getUrl());
    assertEquals(credentialUsername, credential.getUsername());
    assertNotEquals(credentialPassword, credential.getPassword());
  }

  @Test
  public void testIfUserCanEditCredential() {
    String username = "editCredentials";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String credentialUrl = "credentialUrl";
    String credentialUsername = "credentialUsername";
    String credentialPassword = "credentialPassword";

    homePage.createCredential(credentialUrl, credentialUsername, credentialPassword, wait);

    goToHome();

    String credentialEditedUrl = "credentialEditedUrl";
    String credentialEditedUsername = "credentialEditedUsername";
    String credentialEditedPassword = "credentialEditedPassword";
    homePage.editCredential(credentialEditedUrl, credentialEditedUsername, credentialEditedPassword,
        wait);

    goToHome();

    final Credential credential = homePage.getRecentCredential(wait);
    assertNotEquals(credentialUrl, credential.getUrl());
    assertNotEquals(credentialUsername, credential.getUsername());
    assertNotEquals(credentialPassword, credential.getPassword());
    assertEquals(credentialEditedUrl, credential.getUrl());
    assertEquals(credentialEditedUsername, credential.getUsername());
    assertNotEquals(credentialEditedPassword, credential.getPassword());
  }

  @Test
  public void testIfUserCanViewNotEncryptedCredential() {
    String username = "decryptCredentials";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String credentialUrl = "credentialUrl";
    String credentialUsername = "credentialUsername";
    String credentialPassword = "credentialPassword";

    homePage.createCredential(credentialUrl, credentialUsername, credentialPassword, wait);

    goToHome();

    final Credential credential = homePage.getRecentCredential(wait);
    assertEquals(credentialUrl, credential.getUrl());
    assertEquals(credentialUsername, credential.getUsername());
    assertNotEquals(credentialPassword, credential.getPassword());

    final String unencryptedCredential = homePage.getUnencryptedCredential(wait);
    assertEquals(credentialPassword, unencryptedCredential);
  }

  @Test
  public void testIfUserCanDeleteCredential() {
    String username = "deleteCredentials";
    String password = "test_pasword";
    driver.get(baseURL + SignupPage.RELATIVE_URL);
    SignupPage signupPage = new SignupPage(driver);
    signupPage.signup("Firstname", "Lastname", username, password);

    driver.get(baseURL + LoginPage.RELATIVE_URL);
    LoginPage loginPage = new LoginPage(driver);
    loginPage.login(username, password);

    HomePage homePage = new HomePage(driver);
    String credentialUrl = "credentialUrl";
    String credentialUsername = "credentialUsername";
    String credentialPassword = "credentialPassword";

    homePage.createCredential(credentialUrl, credentialUsername, credentialPassword, wait);
    goToHome();
    homePage.deleteCredential(wait);
    goToHome();
    assertFalse(homePage.isCredentialVisible(wait));
  }

  public void waitForLoad() {
    ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver)
        .executeScript("return document.readyState").equals("complete");
    wait.until(pageLoadCondition);
  }
}
