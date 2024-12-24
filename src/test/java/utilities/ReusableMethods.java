package utilities;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReusableMethods {

    public static void bekle(int saniye) {

        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            System.out.println("Thread.sleep calismadi");
        }


    }

    public static List<String> stringListeyeDonustur(List<WebElement> webElementList) {
        List<String> tumListeStr = new ArrayList<>();

        for (WebElement eachBaslik : webElementList) {

            tumListeStr.add(eachBaslik.getText());
        }

        return tumListeStr;
    }

    public static List<String> listedekiBosluklariSil(List<WebElement> webElementList){
        List<String> temizlenmisListe = new ArrayList<>();
        for (WebElement element : webElementList) {
            String text = element.getText().trim();
            if (!text.isEmpty() && !text.equals("")) {
                temizlenmisListe.add(text);
            }
        } return temizlenmisListe;

    }

    public static void exceleYazdirma(String dosyaYolu, String baslik, List<WebElement> webElementList) throws IOException {
        // WebElement listesi kontrolü
        if (webElementList == null || webElementList.isEmpty()) {
            return;
        }


        File file = new File(dosyaYolu);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileInputStream fileInputStream = new FileInputStream(dosyaYolu);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sayfa1 = workbook.getSheet("Sayfa1");
        if (sayfa1 == null) {
            sayfa1 = workbook.createSheet("Sayfa1");
        }


        Row ilkSatir = sayfa1.getRow(0);
        if (ilkSatir == null) {
            ilkSatir = sayfa1.createRow(0);
        }


        int sutunNumarasi = ilkSatir.getLastCellNum();
        if (sutunNumarasi == -1) {
            sutunNumarasi = 0;
        }


        sayfa1.setColumnWidth(sutunNumarasi, 8000);


        CellStyle baslikStili = workbook.createCellStyle();
        Font baslikFontu = workbook.createFont();
        baslikFontu.setBold(true);
        baslikStili.setFont(baslikFontu);


        Cell baslikHucresi = ilkSatir.createCell(sutunNumarasi);
        baslikHucresi.setCellValue(baslik);
        baslikHucresi.setCellStyle(baslikStili);


        int satirNumarasi = 1;
        for (WebElement eachElement : webElementList) {
            String text = eachElement.getText();
            if (text != null && !text.trim().isEmpty()) {
                Row satir = sayfa1.getRow(satirNumarasi);
                if (satir == null) {
                    satir = sayfa1.createRow(satirNumarasi);
                }
                satir.createCell(sutunNumarasi).setCellValue(text.trim());
                satirNumarasi++;
            }
        }

        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(dosyaYolu);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();


    }

    public static List<String> ortakElementleriBulma(List<WebElement> list1, List<WebElement> list2) {
        List<String> ortakListe = new ArrayList<>();

        for (WebElement eleman1 : list1) {
            String eleman1Text = eleman1.getText().trim().toLowerCase(Locale.ENGLISH);

            for (WebElement eleman2 : list2) {
                String eleman2Text = eleman2.getText().trim().toLowerCase(Locale.ENGLISH);

                if (!eleman1Text.isEmpty() && !eleman2Text.isEmpty() && eleman1Text.equals(eleman2Text)) {
                    ortakListe.add(eleman1.getText());
                    break;
                }
            }
        }

        return ortakListe;
    }

    public static void urlIleWindowDegistir(WebDriver driver, String hedefUrl) {
        Set<String> tumWindowWhdSeti = driver.getWindowHandles();
        for (String eachWhd : tumWindowWhdSeti) {

            driver.switchTo().window(eachWhd);

            if (driver.getCurrentUrl().equals(hedefUrl)) {
                break;
            }

        }
    }

    public static void titleIleWindowDegistir(WebDriver driver, String hedefTitle) {
        Set<String> tumWindowWhdSeti = driver.getWindowHandles();
        for (String eachWhd : tumWindowWhdSeti) {

            driver.switchTo().window(eachWhd);

            if (driver.getTitle().equals(hedefTitle)) {
                break;
            }

        }
    }

    public static void tumSayfaScreenshotIsimli(WebDriver driver, String raporIsmi) {
        // 1.adim tss objesi olusturalim
        TakesScreenshot tss = (TakesScreenshot) driver;

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/" + raporIsmi + ".jpeg");


        // 3.adim screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Ekran resmi kaydedilemedi");
        }

    }

    public static void tumSayfaScreenshotTarihli(WebDriver driver) {

        // once tarih etiketi olusturalim
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String tarihEtiketi = ldt.format(format1);

        // 1.adim tss objesi olusturalim
        TakesScreenshot tss = (TakesScreenshot) driver;

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/TumSayfaSS_" + tarihEtiketi + ".jpeg");


        // 3.adim screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Ekran resmi kaydedilemedi");
        }

    }

    public static void tumSayfaScreenshotIsimVeTarihli(WebDriver driver, String isim) {

        // once tarih etiketi olusturalim
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String tarihEtiketi = ldt.format(format1);

        // 1.adim tss objesi olusturalim
        TakesScreenshot tss = (TakesScreenshot) driver;

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/" + isim + "_" + tarihEtiketi + ".jpeg");


        // 3.adim screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = tss.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Ekran resmi kaydedilemedi");
        }

    }

    public static void webElementScreenshotIsimli(WebElement targetElement, String raporismi) {
        // 1.adim screenshot alacagimiz webelementi locate edip kaydedelim
        //        biz yukarda Logout butonunu locate ettik

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/" + raporismi + ".jpeg");


        // 3.adim webElement'i kullanarak screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = targetElement.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Fotograf cekilemedi");
        }
    }

    public static void webElementScreenshotTarihli(WebElement targetElement) {

        // once tarih etiketi olusturalim
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String tarihEtiketi = ldt.format(format1);


        // 1.adim screenshot alacagimiz webelementi locate edip kaydedelim
        //        biz yukarda Logout butonunu locate ettik

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/Webelement" + tarihEtiketi + ".jpeg");


        // 3.adim webElement'i kullanarak screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = targetElement.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Fotograf cekilemedi");
        }
    }

    public static void webElementScreenshotTarihVeIsimli(WebElement targetElement, String raporIsmi) {

        // once tarih etiketi olusturalim
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyMMdd_HHmmss");
        String tarihEtiketi = ldt.format(format1);


        // 1.adim screenshot alacagimiz webelementi locate edip kaydedelim
        //        biz yukarda Logout butonunu locate ettik

        // 2.adim resmi kaydedecegimiz File'i olusturalim
        File asilResim = new File("target/screenshots/" + raporIsmi + "_" + tarihEtiketi + ".jpeg");


        // 3.adim webElement'i kullanarak screenshot'i alip gecici bir dosya olarak kaydedelim
        File geciciDosya = targetElement.getScreenshotAs(OutputType.FILE);

        // 4.adim gecici dosyayi asil dosyaya kopyalayalim
        try {
            FileUtils.copyFile(geciciDosya, asilResim);
        } catch (IOException e) {
            System.out.println("Fotograf cekilemedi");
        }
    }

    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
}