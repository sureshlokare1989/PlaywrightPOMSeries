package com.qa.opencart.base;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseTest {

    protected PlaywrightFactory pf;
    protected Page page;
    protected Properties prop;

    protected HomePage homePage;
    protected LoginPage loginPage;

    @BeforeClass(alwaysRun = true)
    public void setup(){
        pf = new PlaywrightFactory();
        prop = pf.init_prop();
        page = pf.initBrowser(prop);
        homePage = new HomePage(page);
        System.out.println("Browser initialized successfully");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        page.context().browser().close();
    }
}
