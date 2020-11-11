package com.willowtree.pagefactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class NameGamePage extends PageFactory{
	
	public NameGamePage(WebDriver driver) {
	PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
	}
	
	
	@FindBy(xpath="//div[@class='header']/h1")
	public WebElement pageHeader;
	
	
	@FindBy(xpath="//h1[@class='text-center']")
	public WebElement question;
	
	@FindBy(id="name")
	public WebElement question_Name;
	
	@FindBy(xpath="//div[@id='gallery']/div/div")
	public List<WebElement> list_Photo;	
	
	@FindBy(xpath="//div[@class='name']")
	public List<WebElement> list_photoName;	
	
	@FindBy(className="attempts")
	public WebElement attempts;
	
	@FindBy(className="correct")
	public WebElement correct;
	
	@FindBy(className="streak")
	public WebElement streak;
	
}
