package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class YandexPage {
    public YandexPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[@id='text']")
    public WebElement aramaKutusu;

    @FindBy(xpath = "//*[@class='OrganicHost-TitleText Path-Item path__item organic__greenurl']")
    public List<WebElement> yandexAramaSonucuTitle;

    @FindBy(xpath = "//*[@class='OrganicHost-DescriptionText']")
    public List<WebElement> yandexAramaSonucuUrl;

    @FindBy(xpath = "//*[@class='OrganicTitleContentSpan organic__title']")
    public List<WebElement> yandexAramaSonucuDescription;


}
