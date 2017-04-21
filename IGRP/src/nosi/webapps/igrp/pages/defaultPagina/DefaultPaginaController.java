package nosi.webapps.igrp.pages.defaultPagina;

import nosi.core.webapp.Controller;
import java.io.IOException;
import nosi.core.webapp.*;
/**
 * @author Marcel Iekiny
 * Apr 15, 2017
 */
public class DefaultPaginaController extends Controller{
	
	public void actionIndex(@QSParam(qsParamName = "cod") int cod, @QSParam(qsParamName = "name") String name) throws IOException, IllegalArgumentException, IllegalAccessException{
	
	DefaultPaginaModel model = new DefaultPaginaModel();
	/*
	 * Start put your other model here ...
	 * */
	
	/*
	 * To refer a POST/GET parameter just put:
	 * 		String xpto = IGRP.getInstance().getRequest().getParameter("parameter_name");
	 * 		String []xpto = IGRP.getInstance().getRequest().getParameterValues("parameter_name")
	 * */
	
	/*
	 * Start put some business logic here ...
	 * */
	
	this.renderView(
			new DefaultPaginaView().
			addModel("model1", model).
			addModel("model2", new String("Iekiny Marcel")), true
			);
	}
	
	public void actionGravar(){
		System.out.println("Gravado");
	}
	
	public void actionPublicar(){
		System.out.println("Publicado");
	}
}
