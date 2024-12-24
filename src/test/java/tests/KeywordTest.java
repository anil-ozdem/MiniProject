package tests;

import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.GooglePage;
import pages.YandexPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeywordTest {


    //1. <browser> ayaga kaldir.
    //2. <browser> için çerezleri temizle.
    //3. <searchEngine> arama motoruna git.
    //4. <keyword> için arama yap.
    //5. Ilk 10 sonucu parse et. (örnek: url, title, description). Bu sonuçlari bir structured data olarak sakla. Arama sonuçlarinda bazen öneriler, embedded youtube videolari veya benzer sonuçlar gibi degerler gelebilir, lütfen bunlari atla, sadece normal metin arama sonuçlarini parse et.
    //6. 3 - 5 arasindaki adimlari diger arama motoru için de yap.
    //7. Iki arama motoru içerisindeki kayitlari karsilastir ve ikisinde de olanları listele.

    @Test
    public void aramaTesti() throws IOException {
        //1. <browser> ayaga kaldir.
        //2. <browser> için çerezleri temizle.
        Driver.getDriver().manage().deleteAllCookies();
        //3. <searchEngine> arama motoruna git.
        Driver.getDriver().get(ConfigReader.getProperty("googleUrl"));


        //4. <keyword> için arama yap.
        GooglePage googlePage = new GooglePage();
        googlePage.searchBox.sendKeys(ConfigReader.getProperty("aranacakKelime") + Keys.ENTER);
        ReusableMethods.bekle(1);

        //5. Ilk 10 sonucu parse et. (örnek: url, title, description). Bu sonuçlari bir structured data olarak sakla.
        // Arama sonuçlarinda bazen öneriler, embedded youtube videolari veya benzer sonuçlar gibi degerler gelebilir, lütfen bunlari atla,
        // sadece normal metin arama sonuçlarini parse et.
        List<WebElement> aramaSonucuTitleList = googlePage.aramaSonucuTitle;
        String dosyaYolu = "src/test/java/MiniProjectExcel.xlsx";

        System.out.println("");
        System.out.println("------------- Google Arama Motoru Sonuclari -------------");
        System.out.println("");
        System.out.println("Arama sonucunda bulunan ilk 10 Title : \n" + ReusableMethods.listedekiBosluklariSil(aramaSonucuTitleList));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Google Arama Sonucu Title", aramaSonucuTitleList);


        List<WebElement> aramaSonucuUrlList = googlePage.aramaSonucuUrl;
        System.out.println("");
        System.out.println("Arama sonucunda bulunan ilk 10 Url : \n" + ReusableMethods.listedekiBosluklariSil(aramaSonucuUrlList));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Google Arama Sonucu Url", aramaSonucuUrlList);


        List<WebElement> aramaSonucuDescriptionList = googlePage.aramaSonucuDescription;
        System.out.println("");
        System.out.println("Arama sonucunda bulunan ilk 10 Description : \n" + ReusableMethods.listedekiBosluklariSil(aramaSonucuDescriptionList));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Google Arama Sonucu Description", aramaSonucuDescriptionList);


        //6. 3 - 5 arasindaki adimlari diger arama motoru için de yap.
        System.out.println("");
        System.out.println("------------- Yandex Arama Motoru Sonuclari -------------");


        // <searchEngine> arama motoruna git.
        Driver.getDriver().get(ConfigReader.getProperty("yandexUrl"));

        // <keyword> için arama yap.
        YandexPage yandexPage = new YandexPage();

        yandexPage.aramaKutusu.sendKeys(ConfigReader.getProperty("aranacakKelime") + Keys.ENTER);

        // Ilk 10 sonucu parse et.

        List<WebElement> yandexAramaSonucuTitle = yandexPage.yandexAramaSonucuTitle;
        System.out.println("");
        System.out.println("Yandex arama sonucunda bulunan ilk 10 Title : \n" + ReusableMethods.listedekiBosluklariSil(yandexAramaSonucuTitle));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Yandex Arama Sonucu Title", yandexAramaSonucuTitle);


        List<WebElement> yandexAramaSonucuUrl = yandexPage.yandexAramaSonucuUrl;
        System.out.println("");
        System.out.println("Yandex arama sonucunda bulunan ilk 10 Url : \n" + ReusableMethods.listedekiBosluklariSil(yandexAramaSonucuUrl));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Yandex Arama Sonucu Url", yandexAramaSonucuUrl);


        List<WebElement> yandexAramaSonucuDescription = yandexPage.yandexAramaSonucuDescription;
        System.out.println("");
        System.out.println("Yandex arama sonucunda bulunan ilk 10 Description : \n" + ReusableMethods.listedekiBosluklariSil(yandexAramaSonucuDescription));
        ReusableMethods.exceleYazdirma(dosyaYolu, "Yandex Arama Sonucu Descrpiton", yandexAramaSonucuDescription);


        //7. Iki arama motoru içerisindeki kayitlari karsilastir ve ikisinde de olanlari listele.
        System.out.println("");
        List<String> ortakTitleList = ReusableMethods.ortakElementleriBulma(aramaSonucuTitleList, yandexAramaSonucuTitle);
        System.out.println("İki arama moturunda bulunan ortak Title'lar : \n" + ortakTitleList);

        List<String> ortakUrlList = ReusableMethods.ortakElementleriBulma(aramaSonucuUrlList, yandexAramaSonucuUrl);
        System.out.println("İki arama moturunda bulunan ortak Url'lar : \n" + ortakUrlList);

        List<String> ortakDescriptionList = ReusableMethods.ortakElementleriBulma(aramaSonucuDescriptionList, yandexAramaSonucuDescription);
        System.out.println("İki arama moturunda bulunan ortak Description'lar : \n" + ortakDescriptionList);

        Driver.quitDriver();


    }

}
