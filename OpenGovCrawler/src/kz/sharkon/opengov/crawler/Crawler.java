package kz.sharkon.opengov.crawler;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;

import kz.sharkon.opengov.crawler.model.Webpage;
import kz.sharkon.opengov.crawler.model.Website;
import kz.sharkon.opengov.crawler.model.controller.DataController;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar"
					+ "|gz|ico|doc|docx|xls|xlsx|ppt|pptx))$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		String domain = url.getDomain();
		if (url.getSubDomain() != null && !url.getSubDomain().equals("")) {
			if (url.getSubDomain().endsWith(".")) {
				domain = url.getSubDomain() + domain;
			} else {
				domain = url.getSubDomain() + "." + domain;
			}
		}
		Website website = null;
		Webpage webpage = null;
		if (!FILTERS.matcher(href).matches() && domain.endsWith(".kz")) {
			String[] names = domain.trim().split(".");
			if (names.length > 1) {
				DataController dataController = new DataController();
				domain = names[names.length - 2].trim() + "."
						+ names[names.length - 1].trim();
				website = dataController.getWebsiteByURL(domain);
				if (website == null) {
					website = new Website();
					website.setUrl(domain);
					website = dataController.setWebsite(website);
				}
				if (website.getIgnored() == 1) {
					website = null;
				} else {
					if (names.length > 2){
						int prefix = 0;
						if(names[0].trim().equals("wap") || names[0]
									.trim().equals("www") || names[0]
											.trim().equals("m")) {
							prefix = 1;
						}
						domain = "";
						for (int i = prefix; i < names.length - 1; i++) {
							domain += names[i] + ".";
						}
						domain += "kz";
					}
				}
				if (website != null) {
					website = dataController.getWebsiteByURL(domain);
					if (website == null) {
						website = new Website();
						website.setUrl(domain);
						website = dataController.setWebsite(website);
					}
				}
				if (website.getIgnored() == 0) {
					webpage = dataController.getWebpageByURL(href);
					if (webpage == null) {
						webpage = new Webpage();
						webpage.setUrl(href);
						webpage.setVisited(0);
						webpage.setWebsite(website);
						dataController.setWebpage(webpage);
					}
					if (webpage.getVisited() != 0) {
						webpage = null;
					}
				} else {
					website = null;
				}
			}
		}
		return website != null && webpage != null;
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		DataController dataController = new DataController();
		Webpage webpage = dataController.getWebpageByURL(url);
		webpage.setVisited(1);
		dataController.setWebpage(webpage);
		// if (page.getParseData() instanceof HtmlParseData) {
		// HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		// String text = htmlParseData.getText();
		// String html = htmlParseData.getHtml();
		// List<WebURL> links = htmlParseData.getOutgoingUrls();

		// StringReader reader = new StringReader(html);
		/*
		 * try { Source source=new Source(reader); List<Element> elemets =
		 * source.getAllElementsByClass("more "); for (Element element :
		 * elemets) { System.out.println(element.getContent().toString()); } }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * for (WebURL webURL : links) { Webpage webPage = new Webpage();
		 * 
		 * webPage.setUrl(webURL.getURL()); webPage.setVisited(0); String
		 * siteURL = webURL.getSubDomain()+webURL.getDomain(); Website website =
		 * siteController.getSiteByURL(siteURL); if(website==null){ website =
		 * new Website(); website.setUrl(url); website =
		 * siteController.setWebsite(website); } webpage.setWebsite(website);
		 * siteController.setWebpage(webPage); }
		 */
		// }
	}

	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode,
			String statusDescription) {
		DataController dataController = new DataController();
		Webpage webpage = dataController.getWebpageByURL(webUrl.getURL());
		if (webpage != null) {
			webpage.setStatus(statusCode);
			dataController.setWebpage(webpage);
		}

	}
}
