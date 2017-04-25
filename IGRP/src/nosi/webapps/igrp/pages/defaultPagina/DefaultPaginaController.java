package nosi.webapps.igrp.pages.defaultPagina;

import nosi.core.webapp.Controller;
import java.io.IOException;
import nosi.core.webapp.*;
/**
 * @author Marcel Iekiny
 * Apr 15, 2017
 */
public class DefaultPaginaController extends Controller{
	
	public void actionIndex(@RParam(rParamName = "cod") String cod, @RParam(rParamName = "name") String name) throws IOException, IllegalArgumentException, IllegalAccessException{
	
	DefaultPaginaModel model = new DefaultPaginaModel();
	model.load();
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