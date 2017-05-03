/*-------------------------*/

/*Create Controller*/

package nosi.webapps.igrp.pages.listapage;
import nosi.core.webapp.Controller;
import nosi.core.webapp.Igrp;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;

import java.io.IOException;
import java.util.ArrayList;

public class ListaPageController extends Controller {		

	public void actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		ListaPage model = new ListaPage();
		ArrayList<ListaPage.Table_1> lista = new ArrayList<>();
		Action a = new Action();
		if(Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")){
			model.load();		
			a.setEnv_fk(model.getEnv_fk());
			a.setPage(model.getPage());
			a.setPage_descr(model.getPage_descr());
		}
		for(Object obj:a.getAll() ){
			Action ac = (Action) obj;
			ListaPage.Table_1 table1 = new ListaPage().new Table_1();
			table1.setId(ac.getId());
			table1.setNome_page(ac.getPage());
			table1.setDescricao_page(ac.getPage_descr());
			table1.setVersao_page(""+ac.getVersion());
			table1.setStatus_page(""+ac.getStatus());
			lista.add(table1);
		}
		ListaPageView view = new ListaPageView(model);
		view.id.setParam(true);
		view.env_fk.setLabel("Aplica��o");
		view.env_fk.setValue(new Application().getListApps());
		view.table_1.addData(lista);
		this.renderView(view);
	}

}
