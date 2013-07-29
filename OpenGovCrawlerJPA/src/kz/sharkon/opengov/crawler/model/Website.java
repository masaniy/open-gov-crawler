package kz.sharkon.opengov.crawler.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.eclipse.persistence.annotations.JoinFetch;

import static org.eclipse.persistence.annotations.JoinFetchType.INNER;
import static org.eclipse.persistence.annotations.JoinFetchType.OUTER;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;


/**
 * The persistent class for the WEBSITE database table.
 * 
 */
@Entity
@Table(name="WEBSITE")
@NamedQueries({
	@NamedQuery(name="Website.findAll", query="SELECT w FROM Website w"),
	@NamedQuery(name="Website.findByUrl", query="SELECT w FROM Website w WHERE w.url = :url"),
	@NamedQuery(name="Website.findNotIgnoredByUrl", query="SELECT w FROM Website w WHERE w.url = :url AND w.ignored = 0"),
	@NamedQuery(name="Website.findIgnoredByUrl", query="SELECT w FROM Website w WHERE w.url = :url AND w.ignored = 1")
})
public class Website implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "WEBSITE_TABLE_GEN", table = "SEQUENCE", pkColumnName = "seq_name", valueColumnName = "seq_count", pkColumnValue = "Website", allocationSize=1, initialValue =1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WEBSITE_TABLE_GEN")
	private int id;

	private String name;

	private String url;
	
	private int ignored;
	
	@OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "website")
	private List<Webpage> pages;

	public Website() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Webpage> getPages() {
		return pages;
	}

	public void setPages(List<Webpage> pages) {
		this.pages = pages;
	}

	public int getIgnored() {
		return ignored;
	}

	public void setIgnored(int ignored) {
		this.ignored = ignored;
	}	

}