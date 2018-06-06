package ua.rabota.service;

import java.io.IOException;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ua.rabota.dao.VacancyDAO;
import ua.rabota.model.Vacancy;

public class FinalStep {

	public void vacancyParse(String vacancyUrl, Vacancy vacancy) {

		vacancy.setUrl(vacancyUrl);
		
		String[] findId = vacancyUrl.split("/");
		String id = findId[findId.length - 1].replaceAll("[^0-9]", "");
		vacancy.setId(id);

		Document vacancyPage = null;

		try {
			vacancyPage = Jsoup.connect(vacancyUrl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vacancyPage.toString().contains("d_content") && vacancyPage.toString().contains("d-ph-0")) {
			

			Element content = vacancyPage.getElementsByAttributeValueMatching("class", "d_content").first();

			String name = content.getElementsByTag("h1").first().text();

			vacancy.setName(name);

			Element description = content.getElementsByAttributeValueMatching("class", "d-items").first();
			
			String company = description.getElementById("d-ph-0")
					.getElementsByAttributeValueMatching("class", "d-ph-value").first().text();

			vacancy.setCompany(company);

			String info = description.getElementById("d-ph-1").text() + "\n"
					+ description.getElementById("d-ph-2").text() + "\n" + description.getElementById("d-ph-3").text();
			
			vacancy.setInfo(info);

//			Elements requirments = description.getElementsByTag("p");
			Element requirments = content.getElementsByClass("d_des").last()/*getElementsByAttributeValueMatching("class", "d_des").last()*/;
			String requirmentsText = requirments.text();
			String text = StringUtils.substringAfter(requirmentsText, description.getElementById("d-ph-3").text());
//			String text = StringUtils.substringAfter(requirments.text(), info);
//			for (Element infoElement : requirments) {
//				text += infoElement.text() + "\n";
//			}

			vacancy.setDescription(text);

			VacancyDAO vacancyDAO = new VacancyDAO();

			try {
				vacancyDAO.save(vacancy);
			} catch (Exception e) {
				// TODO: handle exception
				
			}
			
			
		}else if(vacancyPage.toString().contains("f-vacancy-inner-wrapper")) {
			Element content = vacancyPage.getElementsByAttributeValueMatching("class", "f-vacancy-inner-wrapper").first();
			
			String name = content.getElementsByAttributeValueMatching("class", "f-vacname-holder fd-beefy-ronin f-text-black").first().text();
			
			vacancy.setName(name);
			
			String company = content.getElementsByAttributeValueMatching("class", "f-vacancy-title ").first().text();
			
			Elements info = content.getElementsByAttributeValueMatching("class", "fd-thin-farmer").first().getElementsByTag("li");
			
			String information = "";
			
			for (Element infoElement : info) {
				information += infoElement.text() + "\n";
			}
			vacancy.setInfo(information);
			
			String description = content.getElementsByAttributeValueMatching("class", "f-vacancy-description").last().text();
			
			vacancy.setDescription(description);

			VacancyDAO vacancyDAO = new VacancyDAO();

			try {
				vacancyDAO.save(vacancy);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
}
