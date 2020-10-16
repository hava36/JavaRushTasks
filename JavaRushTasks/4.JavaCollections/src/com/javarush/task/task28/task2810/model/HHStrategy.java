package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {

    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {

        int page = 0;
        List<Vacancy> list = new ArrayList<>();

        try {
            do {
                Document document = getDocument(searchString, page);

                Elements elements = document.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.isEmpty()) break;

                for (Element element: elements
                ) {
                    Elements links = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title");
                    Elements salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");
                    Elements location = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address");
                    Elements company = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer");

                    Vacancy vacancy = new Vacancy();
                    vacancy.setCompanyName(company.get(0).text());
                    vacancy.setCity(location.get(0).text());
                    vacancy.setTitle(links.get(0).text());
                    vacancy.setSiteName("hh.ua");
                    vacancy.setUrl(links.get(0).attr("href"));
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
                    .referrer("http://hh.ru/").get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


}
