package ua.rabota.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ua.rabota.dao.VacancyDAO;
import ua.rabota.model.Vacancy;

public class FinalStep {

	public void vacancyParse(String vacancyUrl, Vacancy vacancy) {

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
			
//			if(vacancyUrl.equals("https://rabota.ua/company794/vacancy7208034")) {
//				System.out.println("now");
//			}
			Element content = vacancyPage.getElementsByAttributeValueMatching("class", "d_content").first();

			String name = content.getElementsByTag("h1").first().text();

			vacancy.setName(name);

			Element description = content.getElementsByAttributeValueMatching("class", "d-items").first();
			
			String company = description.getElementById("d-ph-0")
					.getElementsByAttributeValueMatching("class", "d-ph-value").first().text();

			vacancy.setCompany(company);

			String info = description.getElementById("d-ph-1").text() + " "
					+ description.getElementById("d-ph-2").text() + " " + description.getElementById("d-ph-3").text();

			vacancy.setInfo(info);

			Elements requirments = description.getElementsByTag("p");
			String text = "";
			for (Element infoElement : requirments) {
				text += infoElement.text() + "\n";
			}

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
