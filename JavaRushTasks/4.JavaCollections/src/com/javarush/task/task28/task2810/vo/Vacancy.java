package com.javarush.task.task28.task2810.vo;

public class Vacancy {


    String title, salary, city, companyName, siteName, url;

    @Override
    public int hashCode() {

        int hashCode = (title != null) ? title.hashCode() : 0;
        hashCode += 31 * ((salary != null) ? salary.hashCode() :0);
        hashCode += 31 * ((companyName != null) ? companyName.hashCode() :0);
        hashCode += 31 * ((siteName != null) ? siteName.hashCode() :0);
        hashCode += 31 * ((url != null) ? url.hashCode() :0);
        hashCode += 31 * ((city != null) ? city.hashCode() :0);

        return hashCode;

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        Vacancy vacancy = (Vacancy) obj;

        return this.title.equals(vacancy.getTitle()) &&
                this.salary.equals(vacancy.getSalary()) &&
                this.city.equals(vacancy.getCity()) &&
                this.companyName.equals(vacancy.getCompanyName()) &&
                this.siteName.equals(vacancy.getSiteName()) &&
                this.url.equals(vacancy.getUrl()) ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
