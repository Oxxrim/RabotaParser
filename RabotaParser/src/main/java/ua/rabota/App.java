package ua.rabota;

import ua.rabota.model.Vacancy;
import ua.rabota.service.NavigationStep;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		String[] vacancys = { "https://rabota.ua/jobsearch/vacancy_list?rubricIds=8&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=362&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=431&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=430&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=9&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=438&parentId=1",
				"https://rabota.ua/jobsearch/vacancy_list?rubricIds=439&parentId=1" };
		String[] languages = {".NET", "1C", "C/C++", "Flash", "JAVA", "PHP", "Python"};
		Vacancy vacancy = new Vacancy();
		NavigationStep nav = new NavigationStep();
		for (int i = 0; i < languages.length; i++) {
			vacancy.setLanguage(languages[i]);
			nav.runParser(vacancys[i], vacancy);
		}
		
	}
}
