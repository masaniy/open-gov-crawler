package kz.sharkon.opengov.crawler;

import java.util.List;

import org.apache.log4j.Logger;

import kz.sharkon.opengov.crawler.controller.CrawlerController;
import kz.sharkon.opengov.crawler.model.Webpage;
import kz.sharkon.opengov.crawler.model.controller.DataController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerProcess /*implements Runnable*/{
	
	private static Logger logger = Logger.getLogger(CrawlerProcess.class);
	

	private String directory = "/home/masaniy/tmp";
	
	public CrawlerProcess(String directory) {
		super();
		this.directory = directory;
		// TODO Auto-generated constructor stub
	}
	
	public CrawlerProcess() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */
/*
	public static void main(String[] args) {
*/
	/*@Override*/
	public void run() {
		// TODO Auto-generated method stub
		DataController dataController = new DataController();
		List<Webpage> webPages = dataController.getNotVisitedPages();
		
		int numberOfCrawlers = 1;//webPages.size();

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

	/*@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Привет из побочного потока!");
	}*/

}
