/*-------------------------*/

/*Create Controller*/

package nosi.webapps.igrp.pages.novomenu;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Igrp;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Menu;
import nosi.webapps.igrp.dao.Organization;
import nosi.webapps.igrp.dao.ProfileType;

import java.io.IOException;



public class NovoMenuController extends Controller {		

	public void actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		NovoMenu model = new NovoMenu();
		NovoMenuView view = new NovoMenuView(model);
		
		if(Igrp.getInstance().getRequest().getMethod() == "POST"){
			model.load();
			
			
		}
		
		Object []applications =  new Application().getAll();
		Object []actions =  new Action().getAll();
		Object []menus =  new Menu().getAllPrincipalMenu();
		
		view.aplicacao.addOption("-- Aplica��o --", ""); // Prompt
		for(Object application : applications){ 
			Application obj = (Application)application;
			view.aplicacao.addOption(obj.getName(), obj.getId());
		}
		
		/*
		view.pagina.addOption("-- Pagina --", ""); // Prompt
		/*for(Object action : actions){ 
			Action obj = (Action)action;
			view.pagina.addOption(obj.getPage_descr(), obj.getId());
		}
		*/
		/*view.menu_principal.addOption("-- Menu base --", ""); // Prompt
		for(Object menu : menus){ 
			Menu obj = (Menu)menu;
			view.menu_principal.addOption(obj.getDescr(), obj.getId());
		}*/
		
		view.target.addOption("-- Target --", ""); // prompt
		view.target.addOption("Mesma P�gina", "_self");
		
		this.renderView(view);
	}
	
}
