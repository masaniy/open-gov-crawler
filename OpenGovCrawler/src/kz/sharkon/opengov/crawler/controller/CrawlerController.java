package kz.sharkon.opengov.crawler.controller;

import java.util.List;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import kz.sharkon.opengov.crawler.Crawler;
import kz.sharkon.opengov.crawler.CrawlerProcess;
import kz.sharkon.opengov.crawler.model.Webpage;
import kz.sharkon.opengov.crawler.model.controller.DataController;

public class CrawlerController {

	private static Logger logger = Logger.getLogger(CrawlerController.class);
	static CrawlerProcess process;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String directory = "/tmp";
		if(args!=null && args.length>0){
			if(args[0].equals("-d")){
				directory = args[1];
			}
		}
		/*if(directory==null){
			process = new CrawlerProcess();
		}else{
			process = new CrawlerProcess(directory);
		}
		process.run();*/
		
		DataController dataController = new DataController();
		List<Webpage> webPages = dataController.getNotVisitedPages();
		
		int numberOfCrawlers = webPages.size();

		CrawlConfig config = new CrawlConfig();
		logger.info("Директория для индексации:"+directory);
		config.setCrawlStorageFolder(directory);
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
			logger.error("CrawlController error", e);
		}

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		for (Webpage webPage : webPages) {
			controller.addSeed(webPage.getUrl());
		}
		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(Crawler.class, numberOfCrawlers);

		/*Thread thread = new Thread(process);
		//thread.setDaemon(true);
		thread.start();*/

		logger.info("Индексация завершена");
	}

}
