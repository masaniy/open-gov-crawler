package kz.sharkon.opengov.crawler.model;

import java.io.Serializable;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import org.eclipse.persistence.annotations.JoinFetch;
import static org.eclipse.persistence.annotations.JoinFetchType.INNER;
import static org.eclipse.persistence.annotations.JoinFetchType.OUTER;


/**
 * The persistent class for the site database table.
 * 
 */
@Entity
@Table(name="WEBPAGE")
@NamedQueries({
		@NamedQuery(name="Webpage.findAll", query="SELECT s FROM Webpage s"),
		@NamedQuery(name="Webpage.findByUrl", query="SELECT s FROM Webpage s WHERE s.url = :url"),
		@NamedQuery(name="Webpage.findNotVisitedByUrl", query="SELECT s FROM Webpage s WHERE s.url = :url AND s.visited = 0"),
		@NamedQuery(name="Webpage.findNotVisited", query="SELECT s FROM Webpage s WHERE s.visited = 0")
})
public class Webpage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name = "WEBPAGE_TABLE_GEN", table = "SEQUENCE", pkColumnName = "seq_name", valueColumnName = "seq_count", pkColumnValue = "Webpage", allocationSize=1, initialValue =1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WEBPAGE_TABLE_GEN")
	private int id;

	private String url;
	
	private int visited;
	
	private int status;
	
	@ManyToOne(fetch = LAZY, cascade = ALL)
	@JoinColumn(name = "website_id", referencedColumnName = "id")
	private Website website;

	public Webpage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getVisited() {
		return visited;
	}

	public void setVisited(int visited) {
		this.visited = visited;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}