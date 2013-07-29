package kz.sharkon.opengov.crawler.controller;

import java.util.List;

import kz.sharkon.opengov.crawler.Crawler;
import kz.sharkon.opengov.crawler.model.Webpage;
import kz.sharkon.opengov.crawler.model.controller.DataController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String crawlStorageFolder = "/home/masaniy/tmp";
		DataController dataController = new DataController();
		List<Webpage> webPages = dataController.getNotVisitedPages();
		
		int numberOfCrawlers = 1;//webPages.size();

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(1000);
		config.setResumableCrawling(true);
		//config.setMaxPagesToFetch(5);
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		CrawlController controller = null;
		try {
			controller = new CrawlController(config, pageFetcher,
					robotstxtServer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		/*for (Webpage webPage : webPages) {
			controller.addSeed(webPage.getUrl());
		}*/
		controller.addSeed(webPages.get(0).getUrl());
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(Crawler.class, numberOfCrawlers);
	}

}
