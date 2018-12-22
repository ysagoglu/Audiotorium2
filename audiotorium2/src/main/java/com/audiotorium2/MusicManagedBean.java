package com.audiotorium2;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audiotorium2.controller.MusicController;
import com.audiotorium2.utility.SessionUtils;

@ManagedBean
@RequestScoped
@Component
@ViewScoped
public class MusicManagedBean {
	
	@Autowired
	MusicController musicController;
	

	public void getFavoriteList() {
		
	}

}
