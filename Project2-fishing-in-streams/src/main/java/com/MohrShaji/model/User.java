package com.MohrShaji.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hibernateuser")
public class User {
	@Id @GeneratedValue
	@Column(name = "id")
	private int user_id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int role_id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "resolver",referencedColumnName = "id")
	private List<Reimbursement> reimbursements;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "author",referencedColumnName = "id")
	private List<Reimbursement> author;

	@Transient
	private List<Integer> reimbursementIds;

	@Transient
	private List<Integer> authorIds;


	{
		this.reimbursementIds = new ArrayList<>();
		this.authorIds = new ArrayList<>();
	}

	public User() {
		//No-arg constructor
	}
	
	public User(String username, String password, String firstname, String lastname, String email,
			int role_id) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.role_id = role_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public void setReimbursements(List<Reimbursement> reimbursements) {
		this.reimbursements = reimbursements;
	}

	public void setAuthor(List<Reimbursement> author) {
		this.author = author;
	}

	public List<Integer> getReimbursementIds() {
		return reimbursementIds;
	}

	public void setReimbursementIds(List<Integer> reimbursementIds) {
		this.reimbursementIds = reimbursementIds;
	}

	public List<Integer> getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(List<Integer> authorIds) {
		this.authorIds = authorIds;
	}

	public void prepForGson() {
		if (reimbursements != null) {
			for (Reimbursement r : reimbursements) {
				reimbursementIds.add(r.getId());
			}
			reimbursements = null;
		}

		if (author != null) {
			for (Reimbursement r : author) {
				authorIds.add(r.getId());
			}
			author = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + role_id;
		result = prime * result + user_id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role_id != other.role_id)
			return false;
		if (user_id != other.user_id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", email=" + email + ", role_id=" + role_id + "]";
	}
}