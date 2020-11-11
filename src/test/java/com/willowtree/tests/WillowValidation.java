package com.willowtree.tests;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WillowValidation extends BaseTest{


	@Test(alwaysRun=true, priority=0, groups= {"WillowTree", "VerifyHeader"})
	public void VerifyHeader() throws Throwable {
	
		
		driver.get(property.getProperty("URL"));
		System.out.println("Driver Launched");
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(nameGame.pageHeader)));
		Thread.sleep(5000);
			/* 
			 * Scenario 1: Verify Header and Question displayed
			 */
		String actualHeader =nameGame.pageHeader.getText();
		String expectedHeader ="name game";	
		System.out.print("Verify Page Header: ");
		Assert.assertEquals(actualHeader, expectedHeader);	
		System.out.println(actualHeader);
		System.out.print("Verify Question :");
		Assert.assertTrue(nameGame.question.getText().trim().contains("who is"));
		System.out.print("who is ");
		Assert.assertTrue(nameGame.question_Name.isDisplayed());
		System.out.println(nameGame.question_Name.getText()+"?");
	
		/* 
		 * Scenario 2: Verify 'Attempt' counter is incremented
		 */
		int random = ThreadLocalRandom.current().nextInt(0,5);
		nameGame.list_Photo.get(random).click();
		Thread.sleep(5000);
	
		if(nameGame.list_Photo.get(random).getAttribute("class").contains("wrong")) {
			updateCounters(1, 0, 0);
			verifyCounters("Verify Only attempt counter incremented for wrong photo selection");
			
		}else {
			
			updateCounters(1, 1, 1);
			verifyCounters("Verify All counters incremented for Correct photo selection");
			
		}		
	
	
	}
	
	@Test(alwaysRun=true, priority=1 ,groups= {"WillowTree", "VerifyCounters"})
	public void VerifyCounters() throws Throwable {
	
		
		driver.get(property.getProperty("URL"));
		System.out.println("Driver Launched\n");
		
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(nameGame.pageHeader)));
		Thread.sleep(5000);
		
		photoListSize=nameGame.list_photoName.size();
		setCurrentNamePosition();
		
		/* 
		 * Scenario 1: Verify 'attempt' counter is incremented on incorrect selection
		*/
		
		int attempt=0;
		System.out.println("Scenario 1");
		clickwrong:for(int j=0;j<photoListSize;j++) {
	
			if(!(j==namePosition)) {
			nameGame.list_Photo.get(j).click();
			Thread.sleep(5000);
			attempt=attempt+1;
			updateCounters(attemptCounter+1, correctCounter, streakCounter);
			
			verifyCounters("Attempt "+attempt+": Verify attempt counter incremented for wrong photo selection");
			}else {
				
				continue clickwrong;
			}
		
		
		}
		
		/* 
		 * Scenario 2: Verify 'All' counters are incremented on correct selection
		*/
		System.out.println("Scenario 2");
		nameGame.list_Photo.get(namePosition).click();
		Thread.sleep(5000);
		updateCounters(attemptCounter+1, correctCounter+1, streakCounter+1);		
		verifyCounters("Verify 'All' counters are incremented on correct photo selection after 4 wrong selection");
		
		verifyNameAndPhotosUpdated();
	
		/*
		 * Scenario 3: Verify 'Streak' counter is incremented on next correct selection plus attempt and correct counter incremented
		 */
		System.out.println("Scenario 3");
		setCurrentNamePosition();
		nameGame.list_Photo.get(namePosition).click();
		Thread.sleep(5000);
		updateCounters(attemptCounter+1, correctCounter+1, streakCounter+1);
		verifyCounters("Verify 'Streak' counter is incremented on concurrent correct selection,also attempt and correct counter incremented");
		
		/*
		 * Scenario 4: Verify 'Streak' counter is reseted to Zero on next wrong selection and attempt counter incremented, and correct counter remains same
		 */
		System.out.println("Scenario 4");
		setCurrentNamePosition();
		clickwrong1:for(int m=0;m<photoListSize;m++) {
			
			if(!(m==namePosition)) {
			nameGame.list_Photo.get(m).click();
			Thread.sleep(5000);
			updateCounters(attemptCounter+1, correctCounter, 0);
			verifyCounters("Verify 'Streak' counter is reset to Zero on next wrong selection and attempt counter incremented, and correct counter remains same");
			break clickwrong1;
			}else {
				
				continue clickwrong1;
			}
		
		
		}	
		
		/* 
		 * Scenario 5: Verify 'All' counters are incremented on correct selection after Streak count is reset
		*/
		System.out.println("Scenario 5");
		nameGame.list_Photo.get(namePosition).click();
		Thread.sleep(5000);
		updateCounters(attemptCounter+1, correctCounter+1, streakCounter+1);		
		verifyCounters("Verify 'All' counters are incremented on correct selection after Streak count is reset");
		verifyNameAndPhotosUpdated();
		
		/* 
		 * Extra Validation.
		 * Scenario 6: Verify Counter are not incremented when clicking same wrong photo again
		*/
		
		System.out.println("Scenario 6 : Extra validation");
		setCurrentNamePosition();
		clickwrong2:for(int m=0;m<photoListSize;m++) {
			
			if(!(m==namePosition)) {
			nameGame.list_Photo.get(m).click();
			Thread.sleep(5000);
			updateCounters(attemptCounter+1, correctCounter, 0);
			verifyCounters("Select a Wrong Photo : Attempt 1");
			
			// Click the same Wrong Photo again
			nameGame.list_Photo.get(m).click();
			Thread.sleep(5000);
			updateCounters(attemptCounter, correctCounter, streakCounter);
			verifyCounters("Select a Wrong Photo : Attempt 2 - Verify Counter are not incremented when clicking same wrong photo again");
			
			break clickwrong2;
			}else {
				
				continue clickwrong2;
			}
		
		
		}	
		

}
}