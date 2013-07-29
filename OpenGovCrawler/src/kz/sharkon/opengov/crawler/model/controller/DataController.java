package kz.sharkon.opengov.crawler.model.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import kz.sharkon.opengov.crawler.model.Webpage;
import kz.sharkon.opengov.crawler.model.Website;

public class DataController {

	private static final String PERSISTENCE_UNIT_NAME = "OpenGovCrawlerJPA";

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;
	private static EntityManager em = factory.createEntityManager();
			
	public Webpage setWebpage(Webpage webpage) {
		
		em.getTransaction().begin();
		em.persist(webpage);
		em.getTransaction().commit();
		return webpage;
	}

	public Website setWebsite(Website website) {
		em.getTransaction().begin();
		em.persist(website);
		em.getTransaction().commit();
		return website;
	}

	public Webpage getNotVisitedPageByURL(String url) {
		Query query = em.createNamedQuery("Webpage.findNotVisitedByUrl");
		query.setParameter("url", url);
		Webpage result = null;
		try {
			result = (Webpage) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

	public List<Webpage> getNotVisitedPages() {
		Query query = em.createNamedQuery("Webpage.findNotVisited");
		List<Webpage> result = query.getResultList();
		return result;
	}

	public Website getWebsiteByURL(String url) {
		Query query = em.createNamedQuery("Website.findByUrl");
		query.setParameter("url", url);
		Website result = null;
		try {
			result = (Website) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}
	
	public Website getNotIgnoredSiteByURL(String url) {
		Query query = em.createNamedQuery("Website.findNotIgnoredByUrl");
		query.setParameter("url", url);
		Website result = null;
		try {
			result = (Website) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

	public Webpage getWebpageByURL(String url) {
		// TODO Auto-generated method stub
		Query query = em.createNamedQuery("Webpage.findByUrl");
		query.setParameter("url", url);
		Webpage result = null;
		try {
			result = (Webpage) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}
}
