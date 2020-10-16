package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoikrugStrategy implements Strategy {

    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?q=java+%s&page=%d";


    @Override
    public List<Vacancy> getVacancies(String searchString) {

        int page = 0;
        List<Vacancy> list = new ArrayList<>();

        try {
            do {
                Document document = getDocument(searchString, page);
                Elements vacanciesHtmlList = document.getElementsByClass("job");
                if (vacanciesHtmlList.isEmpty()) break;
                for (Element element : vacanciesHtmlList) {
                    Elements title = element.getElementsByClass("title");
                    Elements links = title.get(0).getElementsByTag("a");
                    Elements locations = element.getElementsByClass("location");
                    Elements companyName = element.getElementsByClass("company_name");
                    Elements salary = element.getElementsByClass("count");

                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName("moikrug.ru");
                    vacancy.setTitle(links.get(0).text());
                    vacancy.setUrl("https://moikrug.ru" + links.get(0).attr("href"));
                    vacancy.setCity(locations.size() > 0 ? locations.get(0).text() : "");
                    vacancy.setCompanyName(companyName.get(0).text());
                    vacancy.setSalary(salary.size() > 0 ? salary.get(0).text() : "");
                    list.add(vacancy);
                }
                page++;
            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;

    }

    protected Document getDocument(String searchString, int page) throws IOException {
        Document document = null;
        try {
            document = Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 YaBrowser/20.9.0.933 Yowser/2.5 Safari/537.36")
                    .referrer("http://moikrug.ru").get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
