package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPageT {

    private final JavascriptExecutor jsExecutor;

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement pass;

    @FindBy(id = "buttonSignUp")
    private WebElement submitBtn;





    public SignUpPageT(WebDriver driver) {

        PageFactory.initElements(driver, this);

        jsExecutor = (JavascriptExecutor) driver;

    }



    public void signUp(String firstName,String lastName,String userName,String password) {



        jsExecutor.executeScript("arguments[0].value='"+ firstName +"';", firstname);


        jsExecutor.executeScript("arguments[0].value='"+ lastName +"';", lastname);


        jsExecutor.executeScript("arguments[0].value='"+ userName +"';", username);

        jsExecutor.executeScript("arguments[0].value='"+ password +"';", pass);


        jsExecutor.executeScript("arguments[0].click();", submitBtn);




    }



}
