package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;





public class HomePageT {

    @FindBy(id = "logout-Btn")
    private WebElement logoutBtn ;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(name = "addNote")
    private WebElement addNoteBtn;

    @FindBy(id = "addCredentialBtn")
    private WebElement addCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleTxt;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTxt;

    @FindBy(name = "saveChanges")
    private WebElement saveChangesButton;

    @FindBy(name = "note-Table-Title")
    private WebElement noteTableTitle;

    @FindBy(name = "note-Table-Description")
    private WebElement noteTableDescription;

    @FindBy(id = "noteEditBtn")
    private WebElement noteEditButton;

    @FindBy(id = "editCredential")
    private WebElement editCredentialButton;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionTxtModify;

    @FindBy(name = "noteDeleteBtn")
    private WebElement deleteNoteButton;

    @FindBy(id = "deleteCredential")
    private WebElement deleteCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlTxt;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameTxt;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordTxt;

    @FindBy(id = "save-Credential")
    private WebElement saveCredentialChangesButton;

    @FindBy(id = "credentialUrl")
    private WebElement credentialUrlTbl;

    @FindBy(id = "credentialUsername")
    private WebElement credentialUsernameTbl;

    @FindBy(id = "credentialPassword")
    private WebElement credentialPasswordTbl;

    private final WebDriverWait wait;

    // to prevent execution fail
    private final JavascriptExecutor jsExecutor;


    public HomePageT(WebDriver driver) {


        PageFactory.initElements(driver, this);

        jsExecutor = (JavascriptExecutor) driver;

        wait = new WebDriverWait(driver, 1000);
    }




    public void logout() {
        jsExecutor.executeScript("arguments[0].click();", logoutBtn);
    }

    public boolean isElementExist(By locatorKey, WebDriver driver) {
        try {

            // if element found Return True
            driver.findElement(locatorKey);

            return true;

        } catch (NoSuchElementException ex) {

            ex.getLocalizedMessage();
            return false;
        }
    }




    public void addNewNote() {
        jsExecutor.executeScript("arguments[0].click();", addNoteBtn);
    }

    public void editNote() {
        jsExecutor.executeScript("arguments[0].click();", noteEditButton);
    }

    public void delNote() {
        jsExecutor.executeScript("arguments[0].click();", deleteNoteButton);
    }

    public void setNoteTitle(String noteTitle) {
        jsExecutor.executeScript("arguments[0].value='" + noteTitle + "';", noteTitleTxt);
    }

    public void noteTitleModify(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleTxt)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleTxt)).sendKeys(newNoteTitle);
    }

    public void noteDescModify(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionTxtModify)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionTxtModify)).sendKeys(newNoteDescription);
    }


    public void openNotesTab() {
        jsExecutor.executeScript("arguments[0].click();", navNotesTab);
    }

    public void noteDescSetter(String noteDescription) {
        jsExecutor.executeScript("arguments[0].value='"+ noteDescription +"';", noteDescriptionTxt);
    }

    public void saveNoteChange() {
        jsExecutor.executeScript("arguments[0].click();", saveChangesButton);
    }

    public boolean notNotes(WebDriver driver) {
        return !isElementExist(By.name("note-Table-Title"), driver) && !isElementExist(By.name("note-Table-Description"), driver);
    }

    public Note getNote() {

        String title = wait.until(ExpectedConditions.elementToBeClickable(noteTableTitle)).getText();

        String desc = noteTableDescription.getText();

        return new Note(title, desc);
    }





    public void addNewCred() {
        jsExecutor.executeScript("arguments[0].click();", addCredentialButton);
    }

    public void editCred() {
        jsExecutor.executeScript("arguments[0].click();", editCredentialButton);
    }

    public void delCred() {
        jsExecutor.executeScript("arguments[0].click();", deleteCredential);
    }

    public void uploadFile() {
        jsExecutor.executeScript("arguments[0].click();", fileUpload);
    }


    public void credentialUrlSetter(String url) {
        jsExecutor.executeScript("arguments[0].value='" + url + "';", credentialUrlTxt);
    }

    public void credentialUsernameSetter(String username) {
        jsExecutor.executeScript("arguments[0].value='" + username + "';", credentialUsernameTxt);
    }

    public void credentialPasswordSetter(String password) {
        jsExecutor.executeScript("arguments[0].value='" + password + "';", credentialPasswordTxt);
    }

    public Credential getCredential() {

        String url = wait.until(ExpectedConditions.elementToBeClickable(credentialUrlTbl)).getText();

        String usrname = credentialUsernameTbl.getText();

        String pass = credentialPasswordTbl.getText();

        return new Credential(url, usrname, pass);
    }


    public void openCredentialTab() {
        jsExecutor.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void saveCredChanges() {
        jsExecutor.executeScript("arguments[0].click();", saveCredentialChangesButton);
    }



    public boolean noCred(WebDriver driver) {

        return !isElementExist(By.id("credentialUrl"), driver) && !isElementExist(By.id("credentialUsername"), driver) && !isElementExist(By.id("credentialPassword"), driver);

    }








}

