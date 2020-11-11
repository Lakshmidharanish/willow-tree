package com.willowtree.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.willowtree.pagefactory.NameGamePage;

public class BaseTest {
	
	public WebDriver driver;
	public Properties property;
	public NameGamePage nameGame;
	public WebDriverWait wait;
	public String nameToBeClicked;
	public ArrayList<String> nameList;
	public int namePosition ;
	
	public int photoListSize;
	public int attemptCounter;
	public int correctCounter;
	public int streakCounter;
	

	@BeforeMethod(alwaysRun = true)
	public void InitializePageFactory() throws FileNotFoundException, IOException {
		nameToBeClicked=null;
		nameList = new ArrayList<String>();
		
		property = new Properties();
		attemptCounter=0;
		correctCounter=0;
		streakCounter=0;
		
		property.load(new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/testdata.properties"));
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/com/willowtree/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 20,100);
		nameGame = new NameGamePage(driver);
	}


	 @AfterMethod(alwaysRun = true)	 
	 public void closeBrowser() {
		 nameToBeClicked=null;
		 nameList.clear();
		
		 attemptCounter=0;
		 correctCounter=0;
		 streakCounter=0;
		 driver.close();
		 driver.quit();
	 }
	 
	 public void updateCounters(int attempt,int correct,int streak) {
			
		 attemptCounter=attempt;
		 correctCounter=correct;
		 streakCounter=streak;
	}
	 
	public void verifyCounters(String message) {
		Assert.assertEquals(nameGame.attempts.getText(), Integer.toString(attemptCounter));
		Assert.assertEquals(nameGame.correct.getText(),Integer.toString(correctCounter));
		Assert.assertEquals(nameGame.streak.getText(), Integer.toString(streakCounter));
		System.out.print(message+": ");
		System.out.println(attemptCounter+" Attempts "+correctCounter+" Correct "+streakCounter+" Streak\n");
		
	}
	
	public void setCurrentNamePosition() {
		
		nameToBeClicked = nameGame.question_Name.getText();
		nameList.clear();
		for(int i=0;i<photoListSize;i++)
		{
			String currentName =nameGame.list_photoName.get(i).getText();


			
			
			if(nameToBeClicked.equalsIgnoreCase(currentName)) {
				namePosition=i;
			}
		}
	}
	
	public void verifyNameAndPhotosUpdated() {
		
		Assert.assertFalse(nameToBeClicked.equals(nameGame.question_Name.getText()));
		
		for(int i=0;i<nameGame.list_photoName.size();i++) {
			
			String currentName = nameGame.list_photoName.get(i).getText();
			Assert.assertFalse(nameList.contains(currentName));
		}
		
	}
}
