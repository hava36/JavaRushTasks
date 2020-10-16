package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {

    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replaceAll("[.]", "/") + "/vacancies.html";

    private Controller controller;

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document document = null;
        try {
            document = getDocument();
            Elements templateHidden = document.getElementsByClass("template");
            Element template = templateHidden.clone().removeClass("template").removeAttr("style").get(0);
            Elements prevVacancies = document.getElementsByClass("vacancy");
            for (Element element : prevVacancies
                 ) {
                if (!element.hasClass("template")) {
                    element.remove();
                }
            }
            for (Vacancy vacancy : vacancies) {
                Element vacancyElement = template.clone();
                Element link = vacancyElement.getElementsByAttribute("href").get(0);
                link.appendText(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());
                Element city = vacancyElement.getElementsByClass("city").get(0);
                city.appendText(vacancy.getCity());
                Element company = vacancyElement.getElementsByClass("companyName").get(0);
                company.appendText(vacancy.getCompanyName());
                Elements salaryEls = vacancyElement.getElementsByClass("salary");
                Element salary = salaryEls.get(0);
                salary.appendText(vacancy.getSalary());
                templateHidden.before(vacancyElement.outerHtml());
            }
            return document.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Some exception occurred";
    }

    private void updateFile(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            fos.write(filename.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }

}
