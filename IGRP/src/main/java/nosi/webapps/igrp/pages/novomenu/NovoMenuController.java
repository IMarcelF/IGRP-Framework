package nosi.webapps.igrp.pages.novomenu;

import nosi.core.webapp.Controller;
import nosi.core.webapp.databse.helpers.ResultSet;
import nosi.core.webapp.databse.helpers.QueryInterface;
import java.io.IOException;
import nosi.core.webapp.Core;
import nosi.core.webapp.Response;
/* Start-Code-Block (import) */
/* End-Code-Block */
/*----#start-code(packages_import)----*/
import nosi.core.webapp.Igrp;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Config;
import nosi.webapps.igrp.dao.Menu; 

import nosi.core.webapp.helpers.IgrpHelper;
import nosi.core.config.ConfigApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;

import org.json.JSONArray;
import org.json.JSONObject;

import static nosi.core.i18n.Translator.gt;
/*----#end-code----*/
		
public class NovoMenuController extends Controller {
	public Response actionIndex() throws IOException, IllegalArgumentException, IllegalAccessException{
		NovoMenu model = new NovoMenu();
		model.load();
		NovoMenuView view = new NovoMenuView();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		view.env_fk.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.action_fk.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.self_id.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		view.target.setQuery(Core.query(null,"SELECT 'id' as ID,'name' as NAME "));
		  ----#gen-example */
		/*----#start-code(index)----*/

		int id = model.getId();
      if (Core.isNotNullOrZero(id)) {
			// If its a update it will enter here and the value p_id is from the GET url
			Menu menu = new Menu().findOne(id);

			if (null != menu.getMenu())
				model.setSelf_id(menu.getMenu().getId());
			model.setStatus(menu.getStatus());
			model.setFlg_base(menu.getFlg_base());
			// Sets the target, Self_, other page, popup...
			model.setTarget(menu.getTarget());
			model.setOrderby(menu.getOrderby());
			model.setTitulo(menu.getDescr());
            model.setEnv_fk(menu.getApplication().getId());
			if (Core.isNotNullOrZero(model.getAction_fk())){
				if (menu.getAction().getId() != model.getAction_fk()) 
					model.setTitulo(getPageTituleByID(model));
			} else{
             
              	if (menu.getAction() != null){                   
                   model.setAction_fk(menu.getAction().getId());
                }                 
            }        

		} else {
			int app =model.getApp();

			String dad = Core.getCurrentDad();
			if (!"igrp".equalsIgnoreCase(dad) && !"igrp_studio".equalsIgnoreCase(dad)) {
				app = Core.findApplicationByDad(dad).getId();
				view.env_fk.propertie().add("disabled", "true");

			}
			
			if (Core.isNotNullOrZero(model.getApp())){              
              model.setEnv_fk(model.getApp());
            }else{
               model.setApp(app);
              model.setEnv_fk(app); 
            }
             
				
			// New menu by default opens in the same window
			model.setTarget("_self");
			model.setOrderby(39);
			model.setStatus(1);
			model.setFlg_base(0);
			if (Core.isNotNullOrZero(model.getAction_fk())) {
				model.setTitulo(getPageTituleByID(model));
			}
		}

		HashMap<String, String> targets = new HashMap<>();
		targets.put(null, gt("-- Selecionar --"));
		targets.put("_self", gt("Mesma página"));
		targets.put("_blank", gt("Popup"));
		targets.put("_newtab", gt("New tab"));
		targets.put("modal", gt("Modal"));
		targets.put("right_panel", gt("Right Panel"));
		targets.put("submit", gt("Submit"));
		targets.put("confirm", gt("Confirm"));
		view.env_fk.setValue(new Application().getListApps()); // Prompt
		if (Core.isNotNull(model.getEnv_fk())) { 
			if(model.getGlobal_acl() == 1) 
				view.action_fk.setValue(IgrpHelper.toMap(this.loadPermissionAcl(model.getEnv_fk()), "type_fk", "description")); 
			else 
				view.action_fk.setValue(new Action().getListActions(model.getEnv_fk())); 
			
			view.self_id.setValue(new Menu().getListPrincipalMenus(model.getEnv_fk())); 
		}
		view.target.setValue(targets); // prompt
		view.link.setVisible(false);

		if (Core.isNotNullOrZero(id)) {
			// view.btn_gravar.setLink("gravar&p_id=" + id);
			view.sectionheader_1_text.setValue("Gestão Menu - Atualizar");
		} else
			view.status.setValue(1);

		/*----#end-code----*/
		view.setModel(model);
		return this.renderView(view);	
	}
	
	public Response actionGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		NovoMenu model = new NovoMenu();
		model.load();
		/*----#gen-example
		  EXAMPLES COPY/PASTE:
		  INFO: Core.query(null,... change 'null' to your db connection name, added in Application Builder.
		  this.addQueryString("p_id","12"); //to send a query string in the URL
		  return this.forward("igrp","NovoMenu","index",this.queryString()); //if submit, loads the values
		  Use model.validate() to validate your model
		  ----#gen-example */
		/*----#start-code(gravar)----*/
		int id = model.getId();

		Menu menu;
		if (Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")) {

			if (Core.isNotNullOrZero(id)) {
				// UPDATE menu will enter here
				menu = new Menu().findOne(id);
			} else {
				// NEW menu will enter here
				menu = new Menu();
			}
			int app = Core.isNotNullOrZero(model.getEnv_fk()) ? model.getEnv_fk() : Core.getParamInt("p_app");
			menu.setDescr(model.getTitulo());
			menu.setApplication(Core.findApplicationById(app));
			menu.setFlg_base(model.getFlg_base());
			menu.setOrderby(model.getOrderby());
			menu.setStatus(model.getStatus());
			menu.setTarget(model.getTarget());
			if (Core.isNotNullOrZero(model.getAction_fk())) {
				
				System.out.println(model.getGlobal_acl() + " " + model.getGlobal_acl_check()); 
				
				if(model.getGlobal_acl() == 1) { 
					int action_global_acl_id = model.getAction_fk(); 
					PermissionAcl acl = getPermissionAclByTypeFk(action_global_acl_id, app); 
					if(acl != null && acl.getLink() != null && !acl.getLink().isEmpty()) 
						menu.setLink(acl.getLink()); 
				}else {
					// Is not a parent because has a action 
					menu.setAction(new Action().findOne(model.getAction_fk())); 
				}
				
			}
			if (Core.isNotNullOrZero(model.getSelf_id())) {
				// Parent id was choose in the select/combobox
				menu.setMenu(new Menu().findOne(model.getSelf_id()));
			} else if (Core.isNotNullOrZero(model.getAction_fk()))
				menu.setMenu(menu);

			if (Core.isNotNullOrZero(id)) {
				// UPDATE menu will enter here
				menu = menu.update();
				if (menu != null)
					Core.setMessageSuccess("Menu atualizado com sucesso.");
				else {
					Core.setMessageError("Erro ao atualizar menu.");
				}
			} else {
				// NEW menu will enter here
				if (Core.isNotNull(menu.getAction())) {
//					Menu is son or ophan
					if (Core.isNotNull(new Menu().find().andWhere("application.id", "=", menu.getApplication().getId())
							.andWhere("action", "=", menu.getAction().getId()).andWhere("descr", "=", menu.getDescr())
							.one())) {
//						Menu already exist
						Core.setMessageWarning("NMMSG1");
						return this.forward("igrp", "NovoMenu", "index");
					}

				} else {
					// Menu is Parent
					if (Core.isNotNull(new Menu().find().andWhere("application.id", "=", menu.getApplication().getId())
							.andWhere("descr", "=", menu.getDescr()).one())) {
						Core.setMessageWarning("NMMSG1");
						return this.forward("igrp", "NovoMenu", "index");
					}

				}
				// New can be add if reach here
				menu = menu.insert();
				if (menu != null) {
					Core.setMessageSuccess();
				} else {
					Core.setMessageError();
				}
			}

		}
		if (Core.isNotNullOrZero(id)) {
			return this.forward("igrp", "NovoMenu", "index");
		} else if (Core.isNotNullOrZero(model.getEnv_fk())) {
			this.addQueryString("p_app", model.getEnv_fk());
			return this.redirect("igrp", "NovoMenu", "index", this.queryString());
		}

		/*----#end-code----*/
		return this.redirect("igrp","NovoMenu","index", this.queryString());	
	}
	
		
		
/*----#start-code(custom_actions)----*/
	private String getPageTituleByID(NovoMenu model) {
		// System.out.println(model.getAction_fk());
		if (Core.isNotNull(model.getAction_fk())) {
			final Action actionOne = new Action().find().andWhere("id", "=", model.getAction_fk()).one();
			if (Core.isNotNull(actionOne))
				return actionOne.getPage_descr();
		}
		return "";
	}
	
	public Response actionIndex_() throws IOException, IllegalArgumentException, IllegalAccessException{
		String r = "<?xml version = \"1.0\" encoding = \"utf-8\"?>"; 
		
		NovoMenuView view = new NovoMenuView(); 
		
		String param = Core.getParam("p_global_acl"); 
		int p_env_fk = Core.getParamInt("p_env_fk"); 
		int p_global_acl = Core.toInt(param, 0); 
		
		if(p_global_acl == 1) { 
			HashMap<Integer, String> targets = new HashMap<>();
			targets.put(null, gt("-- Selecionar --"));
			
			List<PermissionAcl> acls = loadPermissionAcl(p_env_fk); 
			for(PermissionAcl obj : acls) {
				if(obj.getType().equals("PAGE")) 
					targets.put(obj.getType_fk(), "" + obj.getDescription());
			}
			
			r += Core.remoteComboBoxXml(targets, view.action_fk, null);
		}else {
			r += Core.remoteComboBoxXml(new Action().getListActions(p_env_fk), view.action_fk, null);
		}
		
		return this.renderView(r);
	} 
	
	public List<PermissionAcl> loadPermissionAcl(int envFk){
		List<PermissionAcl> acls = new ArrayList<PermissionAcl>(); 
		
		Config config = new Config().find().andWhere("name", "=", "" + this.IGRPWEB_INSTANCE_NAME).one(); 
		Application env = new Application().findOne(envFk); 
		
		Properties settings =  ConfigApp.getInstance().loadConfig("common", "main.xml"); 
		String endpoint = settings.getProperty("igrp.acl.permissionacl.url"); 
		String token = settings.getProperty("igrp.acl.permissionacl.token"); 
		
		if(config == null || env == null || endpoint == null || endpoint.isEmpty() || token == null || token.isEmpty()) 
			return acls; 
		
		Client client = ClientBuilder.newClient(); 
		WebTarget webTarget = client.target(endpoint); 
		Invocation.Builder invocationBuilder  = webTarget.request().header(HttpHeaders.AUTHORIZATION, token); 
		javax.ws.rs.core.Response response  = invocationBuilder.get(); 
		String json = response.readEntity(String.class); 
		client.close(); 
		
		JSONObject obj = new JSONObject(json);
		JSONObject Entries = obj.optJSONObject("Entries"); 
		if(Entries != null) {
			JSONArray Entry = Entries.optJSONArray("Entry");
			if(Entry != null) { 
				for(int i=0 ; i< Entry.length() ; i++) {
					JSONObject r = Entry.optJSONObject(i); 
					PermissionAcl acl = new PermissionAcl(); 
					try { acl.setEnv_fk(r.getString("ENV_FK"));  } catch (Exception e) {}
					try { acl.setEnv_owner_fk(r.getString("ENV_OWNER_FK"));  } catch (Exception e) {}
					try { acl.setStatus(r.getString("STATUS"));  } catch (Exception e) {}
					try { acl.setType(r.getString("TYPE"));  } catch (Exception e) {}
					try { acl.setType_fk(r.getInt("TYPE_FK"));  } catch (Exception e) {}
					try { acl.setDescription(r.getString("description"));  } catch (Exception e) {}
					try { acl.setLink(r.getString("link"));  } catch (Exception e) {}
					acls.add(acl); 
				}
			}
		}
		
		return acls;
	}
	
	public PermissionAcl getPermissionAclByTypeFk(int type_fk, int envFk) {
		PermissionAcl acl = null; 
		List<PermissionAcl> acls = loadPermissionAcl(envFk); 
		for(PermissionAcl obj : acls) {
			if(obj.getType().equals("PAGE") && obj.getType_fk() == type_fk) {
				acl = obj;
				break;
			}
		}
		return acl; 
	}
	
	public static class PermissionAcl{
		
		private String env_fk; 
		private String env_owner_fk; 
		private String status; 
		private String type; 
		private int type_fk; 
		private String description; 
		private String link;
		
		public String getEnv_fk() {
			return env_fk;
		}
		public void setEnv_fk(String env_fk) {
			this.env_fk = env_fk;
		}
		public String getEnv_owner_fk() {
			return env_owner_fk;
		}
		public void setEnv_owner_fk(String env_owner_fk) {
			this.env_owner_fk = env_owner_fk;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getType_fk() {
			return type_fk;
		}
		public void setType_fk(int type_fk) {
			this.type_fk = type_fk;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		@Override
		public String toString() {
			return "PermissionAcl [env_fk=" + env_fk + ", env_owner_fk=" + env_owner_fk + ", status=" + status
					+ ", type=" + type + ", type_fk=" + type_fk + ", description=" + description + ", link=" + link
					+ "]";
		} 
		
	}
	
	public final String IGRPWEB_INSTANCE_NAME = "IGRPWEB_INSTANCE_NAME";
	
	/*----#end-code----*/
}
