package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

  @FindBy(css = "h1.display-5")
  private WebElement resultStateTitle;

  @FindBy(css = "a")
  private WebElement backLink;

  public ResultPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public boolean isSuccess(WebDriverWait wait) {
    wait.until(ExpectedConditions.visibilityOf(resultStateTitle));
    return "Success".equals(resultStateTitle.getText());
  }

  public void goToHome(WebDriverWait wait) {
    wait.until(ExpectedConditions.elementToBeClickable(backLink));
    backLink.click();
  }

}
