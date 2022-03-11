package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;





public class LoginPageT {

    private final JavascriptExecutor jsExecutor;

    @FindBy(css="#inputUsername")
    private WebElement username;

    @FindBy(css="#inputPassword")
    private WebElement pass;

    @FindBy(css="#submit-button")
    private WebElement submitButton;


    public LoginPageT(WebDriver webDriver){

        PageFactory.initElements(webDriver, this);

        jsExecutor = (JavascriptExecutor) webDriver;
    }


    public void login(String userName, String password) {

        jsExecutor.executeScript("arguments[0].value='"+ userName +"';", username);

        jsExecutor.executeScript("arguments[0].value='"+ password +"';", pass);

        jsExecutor.executeScript("arguments[0].click();", submitButton);

    }

}
