package ua.rabota.service;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ua.rabota.model.Vacancy;

public class VacancyParser {


	public void parse(String pageUrl, Vacancy vacancy) {
		
		Document page = null;
		
		try {
			page = Jsoup.connect(pageUrl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> vacancyUrls = new ArrayList<String>();
		
		Elements selectedVacancys = page.getElementsByAttributeValueMatching("class", "f-visited-enable ga_listing");
		
		for (Element vacancyElement : selectedVacancys) {
		
			//String name = vacancyElement.getElementsByTag("a").first().text();
			String vacancyUrl = vacancyElement.getElementsByTag("a").first().attr("href");
			vacancyUrls.add("https://rabota.ua" + vacancyUrl);
		}
		
		for (String vacancyUrl : vacancyUrls) {
			
			FinalStep finalStep = new FinalStep();
			finalStep.vacancyParse(vacancyUrl, vacancy);
			
		}
	}
}
