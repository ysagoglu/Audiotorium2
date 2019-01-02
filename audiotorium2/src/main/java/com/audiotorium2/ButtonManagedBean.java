package com.audiotorium2;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audiotorium2.controller.MusicController;
import com.audiotorium2.controller.UserController;
import com.audiotorium2.entity.User;
import com.audiotorium2.utility.SessionUtils;

@ManagedBean
@RequestScoped
@Component
public class ButtonManagedBean {

	@Autowired
	UserController userService;
	
	@Autowired
	MusicController musicController;
	
	public String username;
	public String password;
	public String email;
	public String name;
	public String surname;
	public Date birthDate;
	private String gender;
	private boolean accept;
	private String phoneNumber;
	private String message;
	
	public String login(){
		// Do any action that you would.
		System.out.println(username + " " + password);
		message = null;
		User user = null;
		
		try {
			user = userService.login(username, password);
		} catch (Exception e) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
			e.printStackTrace();
		}
		
		// Returns outcome
		
		if(user != null) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("id", user.getId());
			session.setAttribute("displayname", user.getName());
			name = user.getName();
			return "menu.xhtml";
		}
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found"));
		return "login.xhtml";
	}
	
	public String signUp() {
		System.out.println(username + " " + password + " " + email + " " + birthDate + " " + name + " " + surname);
		
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setBirthDate(birthDate);
		user.setSurname(surname);
		user.setName(name);
		user.setGender(gender);
		
		if(!accept) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Licence agreement must be checed"));
			return null;
		}
		
		try {
			userService.signUp(user);
			clearall();
			return "login.xhtml?faces-redirect=true&includeViewParams=true&message=User account has been created successfully";
		} catch (Exception e) {
			
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
			
			return e.getMessage();
		}
		
		
	}
	public String toSignUp() {
		clearall();
		return "signup.xhtml";
	}
	
	public void clearall() {
		// TODO Auto-generated method stub
		username = null;
		password = null;
		email = null;
		name = null;
		surname = null;
		birthDate = null;
		gender = null;
		accept = false;
		phoneNumber = null;
		
	}

	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.setAttribute("id", null);
		session.setAttribute("displayname", null);
		clearall();
		return "login.xhtml";
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean getAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
