package ua.rabota.service;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ua.rabota.model.Vacancy;


public class NavigationStep  {

	public void runParser(String url, Vacancy vacancy) {
		Document content = null;

		try{
			content = Jsoup.connect(url).get();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		ArrayList<String> pageUrls = new ArrayList<String>();
		pageUrls.add(url);
		
		if(!content.getElementsByAttributeValueMatching("class", "f-text-royal-blue fd-merchant f-pagination").isEmpty()) {
			Element paganation = content.getElementsByAttributeValueMatching("class", "f-text-royal-blue fd-merchant f-pagination").first();
			Element countOfPages = paganation.getElementById("content_vacancyList_gridList_pagerInnerTable");
			Elements counts = countOfPages.getElementsByTag("dd");
			
			counts.remove(counts.size() - 1);
			String count = counts.last().text();
			count = count.substring(1, count.length());
			
			for (int i = 2; i <= Integer.parseInt(count); i++) {
				pageUrls.add(url + "&pg=" + i);
			}
		}
		
		for (String link : pageUrls) {
			VacancyParser parser = new VacancyParser();
			parser.parse(link, vacancy);
		}
	}
}
