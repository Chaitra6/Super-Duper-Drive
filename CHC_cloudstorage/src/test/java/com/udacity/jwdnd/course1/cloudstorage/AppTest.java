package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Allows us to test class function without the need of deploying
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest {

    protected static WebDriver driver;

    @LocalServerPort
    private int port;

//    private String baseURL;

    private static final String firstName="Andrew";

    private static final String lastName="Karl";

    private static final String userName="andrew";

    private static final String password="karl06";



//    ______________  Fields for Credential Test _____________________

    public static final String URL = "https://spring.io/";

    public static final String cred_USERNAME = "Bruce";

    public static final String cred_PASSWORD = "Wayne";


    public static final String edit_URL = "https://spring.io/projects/";

    public static final String edit_USERNAME = "Batman";

    public static final String edit_PASSWORD = "Wayne1";







    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }


    // before each of the test
    @BeforeEach
    public void beforeEach(){

        this.driver =new ChromeDriver();

    }


    // After each test close chrome
    @AfterEach
    public void afterEach(){
        if(this.driver != null){
            driver.quit();
        }
    }


    public void signup(){
        driver.get("http://localhost:" + this.port + "/signup");

        SignUpPageT signUpPage = new SignUpPageT(driver);

        // pass in the variable values
        signUpPage.signUp(firstName,lastName,userName,password);

    }



    public void login(){
        driver.get("http://localhost:" + this.port + "/login");
        LoginPageT loginPage = new LoginPageT(driver);
        loginPage.login(userName,password);
    }


    public HomePageT getHomePage(){
        signup();
        login();
        return new HomePageT(driver);
    }



    @Test
    public void testRedirection() {
        // Create a test account
        signup();

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }


    @Test
    public void unauthorisedAccess(){


        driver.get("http://localhost:"+this.port+"/signup");
        assertEquals("Sign Up",driver.getTitle() );


        driver.get("http://localhost:"+this.port+"/login");
        assertEquals("Login",driver.getTitle() );


        driver.get("http://localhost:" + this.port + "/home");
        assertFalse(driver.getTitle() == "Home");

    }


// verifies for testRedirection() Test
    @Test
    public void testSignupAndLogin() throws InterruptedException {

        // first signup with the static string details
        signup();

        // Login with the details username and password details mentioned above
        login();

        // We should be now in Home page after successful Login
        assertEquals("Home", driver.getTitle());

        HomePageT homePage = new HomePageT(driver);
        Thread.sleep(4000);
        homePage.logout();

        // try to go to home page
        driver.get("http://localhost:" + this.port + "/home");


        // as we have logged out, we should be on Login Page
        assertFalse(driver.getTitle() == "Home");
        assertEquals("Login", driver.getTitle());

    }





//    ________ Credential Tests _______


    // Helper Functions


    private void create_Verify_Credential(String url, String username, String password, HomePageT homePage) throws InterruptedException {
    create_Cred(url, username, password, homePage);
    homePage.openCredentialTab();
    Credential credential = homePage.getCredential();

    Assertions.assertEquals(url, credential.getUrl());

    Assertions.assertEquals(username, credential.getUsername());

    Assertions.assertNotEquals(password, credential.getPassword());
}

    private void create_Cred(String url, String username, String password, HomePageT homePage) throws InterruptedException {
       // opens credential Tab
        homePage.openCredentialTab();
        homePage.addNewCred();

        //set the fiels as in argument
        set_Cred_Fields(url, username, password, homePage);
        Thread.sleep(3000);

        // Save credential by save button click
        homePage.saveCredChanges();
        Thread.sleep(3000);

        // Open credential Tab
        homePage.openCredentialTab();
    }

    private void set_Cred_Fields(String url, String username, String password, HomePageT homePage) {
        // Sets Url, Password and Username
        homePage.credentialUrlSetter(url);
        homePage.credentialUsernameSetter(username);
        homePage.credentialPasswordSetter(password);
    }





    @Test
    public void createCredTest() {
        // Thread.sleep pauses thread while the previous line executes/loads page completely
        HomePageT hp = getHomePage();
        try{
            Thread.sleep(3000);
            create_Verify_Credential(URL, cred_USERNAME, cred_PASSWORD,hp);
            Thread.sleep(3000);

            //delete the cred created
            hp.delCred();

            //logout
            hp.logout();

        }catch (InterruptedException ex){
            throw new RuntimeException("Credential Could Not Be Created" + ex.getCause());
        }

    }


    @Test
    public void updateCredentialTest() {
        HomePageT homePage = getHomePage();

        try{
            create_Verify_Credential(URL, cred_USERNAME, cred_PASSWORD, homePage);

            Credential credential = homePage.getCredential();

            String firstEncryptPassword = credential.getPassword();

            homePage.editCred();

            // Set values from the above mentioned variables
            set_Cred_Fields(edit_URL, edit_USERNAME, edit_PASSWORD, homePage);

            homePage.saveCredChanges();

            homePage.openCredentialTab();

            Credential modifiedCredential = homePage.getCredential();


            // Assertions
            Assertions.assertEquals(edit_URL, modifiedCredential.getUrl());

            Assertions.assertEquals(edit_USERNAME, modifiedCredential.getUsername());

            Assertions.assertNotEquals(edit_PASSWORD, modifiedCredential.getPassword());

            Assertions.assertNotEquals(firstEncryptPassword, modifiedCredential.getPassword());

            // Delete the Credential
            homePage.delCred();

            // Logout from Home Page
            homePage.logout();


        }catch (InterruptedException ex){
            throw new RuntimeException("Credential Could Not Be Updated" + ex.getCause());
        }



    }



    @Test
    public void deleteCredentialTest()  {
        HomePageT homePage = getHomePage();
        try{
            // Add 3 credentials
            create_Cred(URL, cred_USERNAME, cred_PASSWORD, homePage);
            create_Cred(edit_URL, edit_USERNAME, edit_PASSWORD, homePage);
            create_Cred("http://www.google.com/", "google", "imana", homePage);

            // no cred returns false as Credentials exist
            Assertions.assertFalse(homePage.noCred(driver));

            // Now Deleting all the 3 credentials that was added
            homePage.delCred();
            homePage.openCredentialTab();
            homePage.delCred();
            homePage.openCredentialTab();
            homePage.delCred();
            homePage.openCredentialTab();

            // noCred returns True as there are no Credentials
            Assertions.assertTrue(homePage.noCred(driver));

            homePage.logout();


        }catch (InterruptedException ex){
            throw new RuntimeException("Credential Could Not Be Deleted" + ex.getCause());
        }




    }








    //    ________ Notes Tests _______



      // Note Helper Functions


    private void createNote(String noteTitle, String noteDescription, HomePageT homePage) {
        // open Note tab
        homePage.openNotesTab();

        // Open add note modal
        homePage.addNewNote();

        // Set Note Title and Description
        homePage.setNoteTitle(noteTitle);
        homePage.noteDescSetter(noteDescription);

        // Click on save that saves the Note
        homePage.saveNoteChange();


        homePage.openNotesTab();
    }


    private void deleteNote(HomePageT homePage) {
        // Deletes Not from the given Home Page
        homePage.delNote();
    }




    @Test
    public void createNoteReadTest()  {
        try{
            // Custom title and Description
            String noteTitle = "I am Thanos";
            String noteDescription = "I Am Inevitable...!";


            HomePageT homePage= getHomePage();

            // Checks if you are in Home Page
            assertEquals("Home", driver.getTitle());

            // call createNote helper function - which adds a note
            createNote(noteTitle,noteDescription,homePage);
            homePage.openNotesTab();
            homePage = new HomePageT(driver);

            // get the inserted note from home page
            Note note = homePage.getNote();

            Assertions.assertEquals(noteTitle, note.getNoteTitle());
            Assertions.assertEquals(noteDescription, note.getNoteDescription());

            deleteNote(homePage);
            Thread.sleep(3000);
            homePage.logout();

        }catch (InterruptedException ex){
            throw new RuntimeException("Note Could Not Be Created" + ex.getCause());
        }

    }


    @Test
    public void noteUpdateTest() {
        String noteTitle = "I am Thor";
        String noteDescription = "I Went For The Head.";

        HomePageT homePage = getHomePage();

        // call createNote helper function - which adds a note
        createNote(noteTitle, noteDescription, homePage);
        homePage.openNotesTab();

        homePage = new HomePageT(driver);

        // a click on edit button
        homePage.editNote();

        // Modify the  Note Title and Description
        String modifiedTitle = "I am Loki";
        homePage.noteTitleModify(modifiedTitle);

        String modifiedDesc = "Love Is A Dagger.";
        homePage.noteDescModify(modifiedDesc);

        homePage.saveNoteChange();
        homePage.openNotesTab();

        // check for modified Title and Desc Assertions
        Note note = homePage.getNote();
        Assertions.assertEquals(modifiedTitle, note.getNoteTitle());
        Assertions.assertEquals(modifiedDesc, note.getNoteDescription());


    }


    @Test
    public void noteDeleteTest()  {
        try{
            String noteTitle = "I am Tony Stark";
            String noteDescription = "Genius, billionaire, playboy, philanthropist.";

            HomePageT homePage = getHomePage();

            // call createNote helper function - which adds a note
            createNote(noteTitle, noteDescription, homePage);
            homePage.openNotesTab();
            homePage = new HomePageT(driver);
            Thread.sleep(2000);

            // Assert False as we do have a note
            Assertions.assertFalse(homePage.notNotes(driver));

            Thread.sleep(2000);
            // Delete the note created
            deleteNote(homePage);

            Thread.sleep(2000);
            // Assert Tru as no notes exist
            Assertions.assertTrue(homePage.notNotes(driver));


        }catch (InterruptedException ex){
            throw new RuntimeException("Note Could Not Be Deleted" + ex.getCause());
        }

    }


    @Test
    public void testBadUrl() {
        // Create a test account
//        doMockSignUp("URL","Test","UT","123");
//        doLogIn("UT", "123");


        // Sign-Up and Login

        signup();

        login();

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");

        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }

    @Test
    public void testLargeUpload(){
        // Sign-Up and Login
        HomePageT homePage = getHomePage();

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadBtn"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("successMessage")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }







}
