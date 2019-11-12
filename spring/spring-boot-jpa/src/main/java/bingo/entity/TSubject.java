package bingo.entity;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_subject database table.
 *
 */
@Entity
@Table(name="t_subject")
@NamedQuery(name="TSubject.findAll", query="SELECT t FROM TSubject t")
public class TSubject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Lob
	private String description;

	private String link;

	private String title;

	private int userid;

	public TSubject() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}