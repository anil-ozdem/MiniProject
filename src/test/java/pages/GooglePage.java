package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;


public class GooglePage {
    public GooglePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[@name='q']")
    public WebElement searchBox;

    @FindBy(xpath = "//*[@*='VuuXrf']")
    public List<WebElement> aramaSonucuTitle;

    @FindBy(tagName = "cite")
    public List<WebElement> aramaSonucuUrl;

    @FindBy(xpath = "//*[@*='LC20lb MBeuO DKV0Md']")
    public List<WebElement> aramaSonucuDescription;

    


}
