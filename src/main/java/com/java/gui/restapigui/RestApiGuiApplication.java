package com.java.gui.restapigui;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class RestApiGuiApplication {

	public static void main(String[] args) {
		RestApi r = new RestApi();
		r.setContentPane(r.panelMain);
		r.setTitle("REST API");
		r.setSize(1050, 700);
		r.setVisible(true);
		r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
