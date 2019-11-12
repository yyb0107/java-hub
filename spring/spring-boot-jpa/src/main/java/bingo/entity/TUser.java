package bingo.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_user database table.
 * 
 */
@Entity
@Table(name="t_user")
@NamedQuery(name="TUser.findAll", query="SELECT t FROM TUser t")
public class TUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userid;

	private String username;

	public TUser() {
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "TUser{" +
				"userid=" + userid +
				", username='" + username + '\'' +
				'}';
	}
}