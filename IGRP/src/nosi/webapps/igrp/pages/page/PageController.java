/**
 * @author: Emanuel Pereira
 * 
 * Apr 20, 2017
 *
 *
 */
/*Create Controller*/

package nosi.webapps.igrp.pages.page;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import nosi.core.config.Config;
import nosi.core.webapp.Controller;
import nosi.core.webapp.FlashMessage;
import nosi.core.webapp.Igrp;
import nosi.core.webapp.RParam;
import nosi.core.webapp.Response;
import nosi.core.webapp.helpers.CompilerHelper;
import nosi.core.webapp.helpers.FileHelper;
import nosi.webapps.igrp.dao.Action;
import nosi.webapps.igrp.dao.Application;
import nosi.webapps.igrp.dao.Transaction;

public class PageController extends Controller {		

	public Response actionIndex() throws IOException{
		Page model = new Page();
		String id = Igrp.getInstance().getRequest().getParameter("id");
		if(id!=null){
			Action a = new Action();
			a = a.findOne(Integer.parseInt(id));
			if(a!=null){
				model.setAction_descr(a.getAction_descr());
				model.setEnv_fk(a.getApplication().getId());
				model.setP_action(a.getAction());
				model.setP_page_descr(a.getPage_descr());
				model.setPage(a.getPage());
				model.setP_id(a.getId());
				model.setP_version(a.getVersion());
				model.setP_xsl_src(a.getXsl_src());
//				model.setP_db_connection(a.getDb_connection());
//				model.setP_flg_internet(a.getFlg_internet());
//				model.setP_flg_menu(a.getFlg_menu());
//				model.setP_flg_offline(a.getFlg_offline());
//				model.setP_flg_transaction(a.getFlg_transaction());
//				model.setP_img_src(a.getImg_src());
//				model.setP_page_type(a.getPage_type());
//				model.setP_proc_name(a.getProc_name());
//				model.setP_self_fw_id(a.getSelf_fw_id());
//				model.setP_self_id(a.getSelf_id());
				model.setP_status(a.getStatus());
			}
		}
		PageView view = new PageView(model);
		view.env_fk.setValue(new Application().getListApps());
		view.version.setValue(Config.getVersions());
		return this.renderView(view);
	}
	
	public Response actionEditar(@RParam(rParamName = "id")String id) throws IOException, IllegalArgumentException, IllegalAccessException{
		Page model = new Page();
		
		Action action = new Action();
		action = action.findOne(Integer.parseInt(id));
		
		model.setEnv_fk(action.getApplication().getId());
		model.setP_version(action.getVersion());
		model.setPage(action.getPage());
		model.setAction_descr(action.getPage_descr());
		
		if(Igrp.getInstance().getRequest().getMethod().equals("POST")){
			model.load();
			Application app = new Application();
			action.setApplication(app.findOne(model.getEnv_fk()));
			action.setVersion(model.getP_version());
			action.setPage(model.getPage());
			action.setPage_descr(model.getAction_descr());
			action = action.update();
			if(action!=null)
				Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.SUCCESS, "P�gina atualizada com sucesso.");
			else
				Igrp.getInstance().getFlashMessage().addMessage(FlashMessage.ERROR, "Error ao atualizar a p�gina.");
			return this.redirect("igrp", "page", "editar", new String[]{"id"}, new String[]{action.getId() + ""});
		}
		
		PageView view = new PageView(model);
		
		view.env_fk.setValue(new Application().getListApps());
		view.version.setValue(Config.getVersions());
		view.sectionheader_1_text.setValue("Gest�o de P�gina - Atualizar");
		view.btn_gravar.setLink("editar&id="+id);
		
		return this.renderView(view);
	}
	
	public Response actionGravar() throws IOException, IllegalArgumentException, IllegalAccessException{
		Page model = new Page();
		if(Igrp.getInstance().getRequest().getMethod().toUpperCase().equals("POST")){
			model.load();			
			Application app = new Application();
			Action action = new Action();
			action.setAction("index");
			action.setApplication(app.findOne(model.getEnv_fk()));
			action.setAction_descr(model.getAction_descr());
//			action.setTable_name(model.getP_table_name());
//			action.setFlg_transaction(model.getP_flg_transaction());
//			action.setDb_connection(model.getP_db_connection());
			action.setPage_descr(model.getAction_descr());
//			action.setFlg_internet(model.getP_flg_transaction());
//			action.setFlg_menu(model.getP_flg_menu());
//			action.setFlg_offline(model.getP_flg_offline());
//			action.setEnv_fk(model.getEnv_fk());
//			action.setSelf_fw_id(model.getP_self_id());
//			action.setImg_src(model.getP_img_src());
//			action.setXsl_src(model.getP_xsl_src());
//			action.setSelf_fw_id(model.getP_self_fw_id());
			action.setPage(nosi.core.gui.page.Page.getPageName(model.getPage()));
//			action.setPage_type(model.getP_page_type());
//			action.setProc_name(model.getP_proc_name());
			action.setStatus(model.getP_status());
			action.setVersion(model.getP_version());
			if(model.getP_id()!=0){
				action = action.update();
			}else{
				action = action.insert();
			}
			if(action!=null){
				String json = "{\"rows\":[{\"columns\":[{\"size\":\"col-md-12\",\"containers\":[]}]}],\"plsql\":{\"instance\":\"\",\"table\":\"\",\"package\":\"nosi.webapps."+action.getApplication().getDad().toLowerCase()+".pages\",\"html\":\""+action.getPage()+"\",\"replace\":false,\"label\":false,\"biztalk\":false,\"subversionpath\":\"\"},\"css\":\"\",\"js\":\"\"}";
				String path_xsl = Config.getBasePathXsl()+Config.getResolvePathXsl(action.getApplication().getDad(), action.getPage(), action.getVersion());		
				FileHelper.save(path_xsl, action.getPage()+".json", json);
				if(FileHelper.fileExists(Config.getProject_loc())){
					FileHelper.save(Config.getProject_loc()+"/WebContent/images"+"/"+"IGRP/IGRP"+action.getVersion()+"/app/"+action.getApplication().getDad().toLowerCase()+"/"+action.getPage().toLowerCase(),action.getPage()+".json",json);
				}
				Igrp.getInstance().getFlashMessage().addMessage("success","Opera��o efetuada com sucesso");
			}else{
				Igrp.getInstance().getFlashMessage().addMessage("error","Falha ao tentar efetuar esta opera��o");
			}
		}
		return this.redirect("igrp", "page", "index");
	}
	
	public Response actionEliminar() throws IOException{
		String id = Igrp.getInstance().getRequest().getParameter("id");
		Action ac = new Action();
		if(ac.delete(Integer.parseInt(id)))
			Igrp.getInstance().getFlashMessage().addMessage("success","Opera��o efetuada com sucesso");
		else
			Igrp.getInstance().getFlashMessage().addMessage("error","Falha ao tentar efetuar esta opera��o");
		return this.redirect("igrp","lista-page","index");
	}
	
	//Save page generated
	public PrintWriter actionSaveGenPage() throws IOException, ServletException{
		Igrp.getInstance().getResponse().setContentType("text/xml");		
		String p_id = Igrp.getInstance().getRequest().getParameter("p_id_objeto");
		Action ac = new Action().findOne(Integer.parseInt(p_id));
		if(ac!=null){			
			Part fileJson = Igrp.getInstance().getRequest().getPart("p_data");
			Part fileXml = Igrp.getInstance().getRequest().getPart("p_page_xml");
			Part fileXsl = Igrp.getInstance().getRequest().getPart("p_page_xsl");
			String javaCode = FileHelper.convertToString(Igrp.getInstance().getRequest().getPart("p_page_java"));		
			String path_class = Igrp.getInstance().getRequest().getParameter("p_package");
			path_class = path_class.replace(".","/") + "/" +ac.getPage().toLowerCase();
			String path_xsl = Config.getBasePathXsl()+Config.getResolvePathXsl(ac.getApplication().getDad(), ac.getPage(), ac.getVersion());//Config.getPathXsl()  +""+"/"+"images"+"/"+"IGRP"+"/"+"IGRP"+Config.getPageVersion()+"/"+"app"+"/"+ac.getEnv().getDad()+"/"+ac.getPage().toLowerCase();			
			String path_xsl_work_space = Config.getProject_loc()+"/WebContent/"+"images"+"/"+"IGRP"+"/"+"IGRP"+ac.getVersion()+"/"+"app"+"/"+ac.getApplication().getDad()+"/"+ac.getPage().toLowerCase();			
			String path_class_work_space = Config.getProject_loc() +"/src/"+ path_class;
			path_class = Config.getBasePathClass()+ path_class;
			this.processJson(fileJson,ac);
			if(fileJson!=null && fileXml!=null && fileXsl!=null && javaCode!=null && javaCode!="" && path_xsl!=null && path_xsl!=""  && path_class!=null && path_class!=""){
				String[] partsJavaCode = javaCode.toString().split(" END ");
				if(
						FileHelper.save(path_class,ac.getPage()+".java", partsJavaCode[0]+"*/") && // save model
						FileHelper.save(path_class,ac.getPage()+"View.java","/*"+partsJavaCode[1]+"*/") && // save view
						FileHelper.save(path_class,ac.getPage()+"Controller.java","/*"+partsJavaCode[2]) && // save controller
						FileHelper.save(path_xsl,ac.getPage()+".xml", fileXml) && // save xml
						FileHelper.save(path_xsl,ac.getPage()+".xsl", fileXsl) && // save xsl
						FileHelper.save(path_xsl,ac.getPage()+".json", fileJson) && // save json
						CompilerHelper.compile(path_class,ac.getPage()+".java") && //Compile model
						CompilerHelper.compile(path_class,ac.getPage()+"View.java") && //Compile controller
						CompilerHelper.compile(path_class,ac.getPage()+"Controller.java") //Compile view
				){
					if(FileHelper.fileExists(Config.getProject_loc())){
						if(!FileHelper.fileExists(path_class_work_space)){//check directory
							FileHelper.createDiretory(path_class_work_space);//create directory if not exist
						}
						FileHelper.save(path_class_work_space,ac.getPage()+".java", partsJavaCode[0]+"*/"); // save model
						FileHelper.save(path_class_work_space,ac.getPage()+"View.java","/*"+partsJavaCode[1]+"*/"); // save view
						FileHelper.save(path_class_work_space,ac.getPage()+"Controller.java","/*"+partsJavaCode[2]); // save controller
						FileHelper.save(path_xsl_work_space,ac.getPage()+".xml", fileXml) ; // save xml
						FileHelper.save(path_xsl_work_space,ac.getPage()+".xsl", fileXsl) ; // save xsl
						FileHelper.save(path_xsl_work_space,ac.getPage()+".json", fileJson); // save json
					}
					ac.setId(Integer.parseInt(p_id));
					ac.setXsl_src(ac.getApplication().getDad().toLowerCase()+"/"+ac.getPage().toLowerCase()+"/"+ac.getPage()+".xsl");
					ac.update();
					return Igrp.getInstance().getResponse().getWriter().append("<messages><message type=\"success\">Opera��o efectuada com sucesso</message></messages>");
				}
			}
		}
		return Igrp.getInstance().getResponse().getWriter().append("<messages><message type=\"error\">Opera��o falhada</message></messages>");
	}
	
	//Read json and extract transactions
	private void processJson(Part fileJson,Action ac) throws IOException {
		if(fileJson!=null){
			JSONObject objJson;
			try {
				objJson = new JSONObject(FileHelper.convertToString(fileJson));
				JSONArray rows = objJson.getJSONArray("rows");				
				for(int i=0;i<rows.length();i++){
					JSONArray collumns;
					try{
						collumns = rows.getJSONObject(i).getJSONArray("columns");
						for(int j=0;j<collumns.length();j++){
							JSONArray containers;
							try{
								containers = collumns.getJSONObject(j).getJSONArray("containers");
								for(int h=0;h<containers.length();h++){
									JSONArray fields;
									try{
										fields = containers.getJSONObject(h).getJSONArray("fields");
										this.processTransactions(fields,ac);
									}catch (JSONException e) {
									}
									JSONArray contextMenu;
									try{
										contextMenu = containers.getJSONObject(h).getJSONArray("contextMenu");
										this.processTransactions(contextMenu,ac);
									}catch (JSONException e) {
									}
								}
							}catch (JSONException e) {
							}
						}
					}catch (JSONException e) {
					}
				}	
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Extract transactions
	private void processTransactions(JSONArray fields,Action ac) {
		for(int i=0;i<fields.length();i++){
			JSONObject p;
			try{
				p = fields.getJSONObject(i).getJSONObject("properties");
				try{
					if(p.get("transaction")!=null && p.get("transaction").toString().equals("true")){
						this.saveTransaction(p.get("name").toString(),p.get("label").toString(),p.get("action").toString(),p.get("tag").toString(),ac);
					}
				}catch (JSONException e) {
				}
			}catch (JSONException e) {
			}
		}
	}

	//Save transactions
	private void saveTransaction(String name, String label, String action, String tag,Action ac) {
		if(ac!=null && name!=null && tag!=null){
			Transaction t = new Transaction();
			String code = ac.getApplication().getDad().toLowerCase()+"_"+ac.getPage()+"_"+tag;
			t = t.find().andWhere("code", "=", code).one();
			if(t==null){
				t = new Transaction(code, label, 1, ac.getApplication());
				t = t.insert();
			}
		}
	}

	public void actionPublishGenPage() throws IOException{
		
	}

	//list all page of an application
	public PrintWriter actionListPage() throws IOException{
		String p_dad = Igrp.getInstance().getRequest().getParameter("amp;p_dad");
		String json = "[";
		Action a = new Action();
		List<Action> actions = a.findAll(a.getCriteria().where(
					a.getBuilder().equal(a.getRoot().join("application").get("dad"), p_dad)
				));
		if(actions!=null){
			for(Action ac:actions){
				json += "{";
				json += "\"action\":\""+ac.getAction() +"\",";
				json += "\"app\":\""+ac.getApplication().getDad() +"\",";
				json += "\"page\":\""+ac.getPage() +"\",";
				json += "\"id\":\""+ac.getId() +"\",";
				json += "\"description\":\""+(ac.getPage_descr()!=null?ac.getPage_descr():ac.getPage()) +"\",";
				json += "\"link\":\""+Config.getResolvePathXsl(ac.getApplication().getDad(), ac.getPage(), ac.getVersion())+"/"+ac.getPage()+".xsl\"";
				json += "},";
			}
		}
		json = json.substring(0, json.length()-1);
		json += "]";
		Igrp.getInstance().getResponse().setContentType("application/json");
		return Igrp.getInstance().getResponse().getWriter().append(json);
	}
	
	//get detail page
	public PrintWriter actionDetailPage() throws IOException{
		String p_id = Igrp.getInstance().getRequest().getParameter("amp;p_id");		
		Action ac = new Action().findOne(Integer.parseInt(p_id));		
		String json = "{";
		if(ac!=null){
				json += "\"action\":\""+ac.getAction() +"\",";
				json += "\"action_descr\":\""+ac.getAction_descr() +"\",";
				json += "\"app\":\""+ac.getApplication().getDad() +"\",";
				json += "\"page\":\""+ac.getPage() +"\",";
				json += "\"id\":\""+ac.getId() +"\",";
				json += "\"filename\":\""+Config.getResolvePathXsl(ac.getApplication().getDad(), ac.getPage(), ac.getVersion())+"/"+ac.getPage()+".xsl\",";
				json += "\"page_descr\":\""+ac.getPage_descr() +"\"";
			}
		json += "}";
		Igrp.getInstance().getResponse().setContentType("application/json");
		return Igrp.getInstance().getResponse().getWriter().append(json);
	}
	
	public PrintWriter actionImageList() throws IOException{
		String param = Igrp.getInstance().getRequest().getParameter("amp;name");
		String menu = "";
		if(param == "menu"){
			menu = "[\"themes/default/img/icon/menu/CVM_agente.png\",\"themes/default/img/icon/menu/CVM_cell.png\",\"themes/default/img/icon/menu/CVM_data.png\",\"themes/default/img/icon/menu/CVM_gestor_agente.png\",\"themes/default/img/icon/menu/CVM_pontos_venda.png\",\"themes/default/img/icon/menu/CVM_spots.png\",\"themes/default/img/icon/menu/CVM_torre.png\",\"themes/default/img/icon/menu/Minhas_tarefas.png\",\"themes/default/img/icon/menu/Registo_distribuicao.png\",\"themes/default/img/icon/menu/Registo_extracao.png\",\"themes/default/img/icon/menu/Tarefas_concluidas.png\",\"themes/default/img/icon/menu/abono.png\",\"themes/default/img/icon/menu/accao_topologia.png\",\"themes/default/img/icon/menu/alerta.png\",\"themes/default/img/icon/menu/alteracao_PIN.png\",\"themes/default/img/icon/menu/autotanque.png\",\"themes/default/img/icon/menu/bancos.png\",\"themes/default/img/icon/menu/basemaps.png\",\"themes/default/img/icon/menu/bloco_notas_privado.png\",\"themes/default/img/icon/menu/bonificados.png\",\"themes/default/img/icon/menu/cabimento.png\",\"themes/default/img/icon/menu/clientes.png\",\"themes/default/img/icon/menu/colocacoes.png\",\"themes/default/img/icon/menu/componentes.png\",\"themes/default/img/icon/menu/condecoracao.png\",\"themes/default/img/icon/menu/confirmacao_PIN.png\",\"themes/default/img/icon/menu/consultas.png\",\"themes/default/img/icon/menu/conta-corrente.png\",\"themes/default/img/icon/menu/conteudos.png\",\"themes/default/img/icon/menu/context_menu.png\",\"themes/default/img/icon/menu/contrato.png\",\"themes/default/img/icon/menu/contribuicoes.png\",\"themes/default/img/icon/menu/descendentes.png\",\"themes/default/img/icon/menu/desenpenho.png\",\"themes/default/img/icon/menu/dessalinizadora.png\",\"themes/default/img/icon/menu/dique_1.png\",\"themes/default/img/icon/menu/disponibilidade.png\",\"themes/default/img/icon/menu/dividas.png\",\"themes/default/img/icon/menu/documentos.png\",\"themes/default/img/icon/menu/duplicar.png\",\"themes/default/img/icon/menu/enquadramento.png\",\"themes/default/img/icon/menu/espelhos.png\",\"themes/default/img/icon/menu/est.especies.png\",\"themes/default/img/icon/menu/est.fiscalizacao.png\",\"themes/default/img/icon/menu/estabelecimento.png\",\"themes/default/img/icon/menu/estast.performance-global.png\",\"themes/default/img/icon/menu/estatistica-bonificados.png\",\"themes/default/img/icon/menu/estatistica-financeira.png\",\"themes/default/img/icon/menu/estatistica.png\",\"themes/default/img/icon/menu/estatistica_contratos.png\",\"themes/default/img/icon/menu/etapas.png\",\"themes/default/img/icon/menu/exames.png\",\"themes/default/img/icon/menu/fim.png\",\"themes/default/img/icon/menu/flag_eng.png\",\"themes/default/img/icon/menu/flag_france.png\",\"themes/default/img/icon/menu/flg_port.png\",\"themes/default/img/icon/menu/fotografias.png\",\"themes/default/img/icon/menu/historico.png\",\"themes/default/img/icon/menu/historico_clinico.png\",\"themes/default/img/icon/menu/identificacao-.png\",\"themes/default/img/icon/menu/identificacao.png\",\"themes/default/img/icon/menu/idioma.png\",\"themes/default/img/icon/menu/info-menu-.png\",\"themes/default/img/icon/menu/info-menu.png\",\"themes/default/img/icon/menu/iniciar.png\",\"themes/default/img/icon/menu/internamento.png\",\"themes/default/img/icon/menu/investidores.png\",\"themes/default/img/icon/menu/layers.png\",\"themes/default/img/icon/menu/legenda.png\",\"themes/default/img/icon/menu/m_BAU.png\",\"themes/default/img/icon/menu/m_alerta_caducidade.png\",\"themes/default/img/icon/menu/m_alerta_prazos_.png\",\"themes/default/img/icon/menu/m_caixas.png\",\"themes/default/img/icon/menu/m_calendario.png\",\"themes/default/img/icon/menu/m_categoria.png\",\"themes/default/img/icon/menu/m_classificacao.png\",\"themes/default/img/icon/menu/m_empresa.png\",\"themes/default/img/icon/menu/m_empresa_.png\",\"themes/default/img/icon/menu/m_error.png\",\"themes/default/img/icon/menu/m_especies.png\",\"themes/default/img/icon/menu/m_est.licenca.png\",\"themes/default/img/icon/menu/m_est.trofeus.png\",\"themes/default/img/icon/menu/m_fiscalizacao.png\",\"themes/default/img/icon/menu/m_fontenario.png\",\"themes/default/img/icon/menu/m_frequencia_estimativa.png\",\"themes/default/img/icon/menu/m_furos.png\",\"themes/default/img/icon/menu/m_gerencia.png\",\"themes/default/img/icon/menu/m_guia.png\",\"themes/default/img/icon/menu/m_integracao.png\",\"themes/default/img/icon/menu/m_licenca.png\",\"themes/default/img/icon/menu/m_licenca_ambiental.png\",\"themes/default/img/icon/menu/m_lista.png\",\"themes/default/img/icon/menu/m_mapa.png\",\"themes/default/img/icon/menu/m_material.png\",\"themes/default/img/icon/menu/m_movimentos.png\",\"themes/default/img/icon/menu/m_outras-licencas.png\",\"themes/default/img/icon/menu/m_pesquisa_licenca_.png\",\"themes/default/img/icon/menu/m_pesquisa_mapa.png\",\"themes/default/img/icon/menu/m_pesquisa_projecto.png\",\"themes/default/img/icon/menu/m_ponto.fscalizacao.png\",\"themes/default/img/icon/menu/m_proj_investimento.png\",\"themes/default/img/icon/menu/m_reservatorio.png\",\"themes/default/img/icon/menu/m_taxas.png\",\"themes/default/img/icon/menu/m_transportes.png\",\"themes/default/img/icon/menu/m_trofeus.png\",\"themes/default/img/icon/menu/mapa_menu.png\",\"themes/default/img/icon/menu/marcacoes.png\",\"themes/default/img/icon/menu/menu_lista.png\",\"themes/default/img/icon/menu/meta-type.png\",\"themes/default/img/icon/menu/morada.png\",\"themes/default/img/icon/menu/movimentos.png\",\"themes/default/img/icon/menu/nascente.png\",\"themes/default/img/icon/menu/nivel_habilitacao.png\",\"themes/default/img/icon/menu/notas.png\",\"themes/default/img/icon/menu/notificacoes-.png\",\"themes/default/img/icon/menu/notificacoes.png\",\"themes/default/img/icon/menu/obitos.png\",\"themes/default/img/icon/menu/observacoes.png\",\"themes/default/img/icon/menu/origem.png\",\"themes/default/img/icon/menu/outdoor-menu.png\",\"themes/default/img/icon/menu/partilhados.png\",\"themes/default/img/icon/menu/partilhar.png\",\"themes/default/img/icon/menu/penas.png\",\"themes/default/img/icon/menu/perda_bonificacao.png\",\"themes/default/img/icon/menu/perda_bonificacao_2.png\",\"themes/default/img/icon/menu/permissao.png\",\"themes/default/img/icon/menu/pino_amarelo-(digital).png\",\"themes/default/img/icon/menu/pino_amarelo.png\",\"themes/default/img/icon/menu/pino_preto-(digital).png\",\"themes/default/img/icon/menu/pino_preto.png\",\"themes/default/img/icon/menu/pino_verde-(digital).png\",\"themes/default/img/icon/menu/pino_verde.png\",\"themes/default/img/icon/menu/pino_vermelho-(digital).png\",\"themes/default/img/icon/menu/pino_vermelho.png\",\"themes/default/img/icon/menu/pino_vermelho_ponto-preto-(digital).png\",\"themes/default/img/icon/menu/pino_vermelho_ponto-preto.png\",\"themes/default/img/icon/menu/poco_1.png\",\"themes/default/img/icon/menu/poco_2.png\",\"themes/default/img/icon/menu/poco_3.png\",\"themes/default/img/icon/menu/prestacoes.png\",\"themes/default/img/icon/menu/processos.png\",\"themes/default/img/icon/menu/qualidade_agua2.png\",\"themes/default/img/icon/menu/qualidade_agua4.png\",\"themes/default/img/icon/menu/regime_trab.png\",\"themes/default/img/icon/menu/registos_ligacao.png\",\"themes/default/img/icon/menu/registos_tratamento.png\",\"themes/default/img/icon/menu/regras_topologia.png\",\"themes/default/img/icon/menu/reinicializacao_PIN.png\",\"themes/default/img/icon/menu/renovacoes.png\",\"themes/default/img/icon/menu/retiradas.png\",\"themes/default/img/icon/menu/saneamento_ETAR_.png\",\"themes/default/img/icon/menu/saneamento_UDR.png\",\"themes/default/img/icon/menu/saneamento_reg_equip_recolha.png\",\"themes/default/img/icon/menu/saneamento_reg_recolha.png\",\"themes/default/img/icon/menu/saneamento_tratamento_residuos.png\",\"themes/default/img/icon/menu/seg_social.png\",\"themes/default/img/icon/menu/seguros.png\",\"themes/default/img/icon/menu/sis_abastecimento.png\",\"themes/default/img/icon/menu/sis_abastecimento2.png\",\"themes/default/img/icon/menu/sis_abastecimento3.png\",\"themes/default/img/icon/menu/sis_abastecimento4.png\",\"themes/default/img/icon/menu/tarefas.png\",\"themes/default/img/icon/menu/tarefas_pendentes.png\",\"themes/default/img/icon/menu/taxas.png\",\"themes/default/img/icon/menu/tema.png\",\"themes/default/img/icon/menu/tipo_cor.png\",\"themes/default/img/icon/menu/tipo_energia.png\",\"themes/default/img/icon/menu/tipo_equipamento.png\",\"themes/default/img/icon/menu/tipo_identificacao.png\",\"themes/default/img/icon/menu/tipo_tratamento.png\",\"themes/default/img/icon/menu/tipos.png\",\"themes/default/img/icon/menu/tratamento.png\",\"themes/default/img/icon/menu/tratamento_residuos.png\",\"themes/default/img/icon/menu/tratamento_residuos2.png\",\"themes/default/img/icon/menu/ultimas_consultas.png\",\"themes/default/img/icon/menu/ultimos_exames.png\",\"themes/default/img/icon/menu/ultimos_internamentos.png\"]";
		}else{
			menu = "[\"themes/default/img/icon/tools-bar/Book_phones.png\",\"themes/default/img/icon/tools-bar/Folha_C.png\",\"themes/default/img/icon/tools-bar/Folha_F.png\",\"themes/default/img/icon/tools-bar/Folha_M.png\",\"themes/default/img/icon/tools-bar/Folha_RC.png\",\"themes/default/img/icon/tools-bar/Folha_RF.png\",\"themes/default/img/icon/tools-bar/Folha_S.png\",\"themes/default/img/icon/tools-bar/PDF_C.png\",\"themes/default/img/icon/tools-bar/PDF_F.png\",\"themes/default/img/icon/tools-bar/PDF_M-.png\",\"themes/default/img/icon/tools-bar/PDF_M.png\",\"themes/default/img/icon/tools-bar/PDF_RC.png\",\"themes/default/img/icon/tools-bar/PDF_RF.png\",\"themes/default/img/icon/tools-bar/PDF_S.png\",\"themes/default/img/icon/tools-bar/activar.png\",\"themes/default/img/icon/tools-bar/add-temp.png\",\"themes/default/img/icon/tools-bar/add.png\",\"themes/default/img/icon/tools-bar/alterar-assinatura.png\",\"themes/default/img/icon/tools-bar/alterar-digital.png\",\"themes/default/img/icon/tools-bar/alterar-foto.png\",\"themes/default/img/icon/tools-bar/apply.png\",\"themes/default/img/icon/tools-bar/assumir_tarefas.png\",\"themes/default/img/icon/tools-bar/avaliar.png\",\"themes/default/img/icon/tools-bar/back.png\",\"themes/default/img/icon/tools-bar/balcoes.png\",\"themes/default/img/icon/tools-bar/calendario.png\",\"themes/default/img/icon/tools-bar/cancel.png\",\"themes/default/img/icon/tools-bar/circulo.png\",\"themes/default/img/icon/tools-bar/clientes_tb.png\",\"themes/default/img/icon/tools-bar/close.png\",\"themes/default/img/icon/tools-bar/contas.png\",\"themes/default/img/icon/tools-bar/contrato_tb.png\",\"themes/default/img/icon/tools-bar/ctx-acount.png\",\"themes/default/img/icon/tools-bar/ctx-attachment.png\",\"themes/default/img/icon/tools-bar/ctx-close.png\",\"themes/default/img/icon/tools-bar/ctx-delete.png\",\"themes/default/img/icon/tools-bar/ctx-demote.png\",\"themes/default/img/icon/tools-bar/ctx-details.png\",\"themes/default/img/icon/tools-bar/ctx-payment.png\",\"themes/default/img/icon/tools-bar/ctx_benefic.png\",\"themes/default/img/icon/tools-bar/ctx_disponive.png\",\"themes/default/img/icon/tools-bar/ctx_documents.png\",\"themes/default/img/icon/tools-bar/ctx_family.png\",\"themes/default/img/icon/tools-bar/ctx_group.png\",\"themes/default/img/icon/tools-bar/ctx_house.png\",\"themes/default/img/icon/tools-bar/ctx_info.png\",\"themes/default/img/icon/tools-bar/ctx_mudar_prop.png\",\"themes/default/img/icon/tools-bar/ctx_process.png\",\"themes/default/img/icon/tools-bar/ctx_selecionado.png\",\"themes/default/img/icon/tools-bar/ctx_text_list.png\",\"themes/default/img/icon/tools-bar/delete.png\",\"themes/default/img/icon/tools-bar/desativar.png\",\"themes/default/img/icon/tools-bar/disable.png\",\"themes/default/img/icon/tools-bar/distribuir.png\",\"themes/default/img/icon/tools-bar/document-excel.png\",\"themes/default/img/icon/tools-bar/document-pdf.png\",\"themes/default/img/icon/tools-bar/down.png\",\"themes/default/img/icon/tools-bar/edit.png\",\"themes/default/img/icon/tools-bar/emitir_factura.png\",\"themes/default/img/icon/tools-bar/enable.png\",\"themes/default/img/icon/tools-bar/entrar.png\",\"themes/default/img/icon/tools-bar/enviar_notificacoes.png\",\"themes/default/img/icon/tools-bar/enviar_roxo.png\",\"themes/default/img/icon/tools-bar/enviar_verde.png\",\"themes/default/img/icon/tools-bar/error.png\",\"themes/default/img/icon/tools-bar/et-add.png\",\"themes/default/img/icon/tools-bar/excel.png\",\"themes/default/img/icon/tools-bar/exportar.png\",\"themes/default/img/icon/tools-bar/familiares.png\",\"themes/default/img/icon/tools-bar/filtro.png\",\"themes/default/img/icon/tools-bar/fim.png\",\"themes/default/img/icon/tools-bar/gerencia.png\",\"themes/default/img/icon/tools-bar/grosso.png\",\"themes/default/img/icon/tools-bar/help.png\",\"themes/default/img/icon/tools-bar/imagem.png\",\"themes/default/img/icon/tools-bar/importar.png\",\"themes/default/img/icon/tools-bar/indutria.png\",\"themes/default/img/icon/tools-bar/iniciar_processo.png\",\"themes/default/img/icon/tools-bar/invoice.png\",\"themes/default/img/icon/tools-bar/keepass.png\",\"themes/default/img/icon/tools-bar/key__pencil.png\",\"themes/default/img/icon/tools-bar/key_arrow.png\",\"themes/default/img/icon/tools-bar/key_delete.png\",\"themes/default/img/icon/tools-bar/key_go.png\",\"themes/default/img/icon/tools-bar/key_plus.png\",\"themes/default/img/icon/tools-bar/keys.png\",\"themes/default/img/icon/tools-bar/liberar_tarefa.png\",\"themes/default/img/icon/tools-bar/lista.png\",\"themes/default/img/icon/tools-bar/mail.png\",\"themes/default/img/icon/tools-bar/mapa.png\",\"themes/default/img/icon/tools-bar/modulos.png\",\"themes/default/img/icon/tools-bar/mostrar.png\",\"themes/default/img/icon/tools-bar/new_modulo.png\",\"themes/default/img/icon/tools-bar/new_page.png\",\"themes/default/img/icon/tools-bar/next.png\",\"themes/default/img/icon/tools-bar/novo_cliente.png\",\"themes/default/img/icon/tools-bar/novo_contrato.png\",\"themes/default/img/icon/tools-bar/ocultar.png\",\"themes/default/img/icon/tools-bar/pagar.png\",\"themes/default/img/icon/tools-bar/pdf.png\",\"themes/default/img/icon/tools-bar/percentage.png\",\"themes/default/img/icon/tools-bar/perda_bonificacao.png\",\"themes/default/img/icon/tools-bar/preview.png\",\"themes/default/img/icon/tools-bar/print.png\",\"themes/default/img/icon/tools-bar/printer.png\",\"themes/default/img/icon/tools-bar/publish.png\",\"themes/default/img/icon/tools-bar/reenviar_notficacoes.png\",\"themes/default/img/icon/tools-bar/refresh.png\",\"themes/default/img/icon/tools-bar/regularizar.png\",\"themes/default/img/icon/tools-bar/retalho.png\",\"themes/default/img/icon/tools-bar/save.png\",\"themes/default/img/icon/tools-bar/search.png\",\"themes/default/img/icon/tools-bar/self-service.png\",\"themes/default/img/icon/tools-bar/send.png\",\"themes/default/img/icon/tools-bar/sitemap.png\",\"themes/default/img/icon/tools-bar/sms.png\",\"themes/default/img/icon/tools-bar/start.png\",\"themes/default/img/icon/tools-bar/switch.png\",\"themes/default/img/icon/tools-bar/tab.png\",\"themes/default/img/icon/tools-bar/tb_acercar.png\",\"themes/default/img/icon/tools-bar/tb_agregado-seriado.png\",\"themes/default/img/icon/tools-bar/tb_agregado-trocar.png\",\"themes/default/img/icon/tools-bar/tb_apartment.png\",\"themes/default/img/icon/tools-bar/tb_building.png\",\"themes/default/img/icon/tools-bar/tb_caderno.png\",\"themes/default/img/icon/tools-bar/tb_categoria.png\",\"themes/default/img/icon/tools-bar/tb_classification.png\",\"themes/default/img/icon/tools-bar/tb_close.png\",\"themes/default/img/icon/tools-bar/tb_community-equipment.png\",\"themes/default/img/icon/tools-bar/tb_distanciar.png\",\"themes/default/img/icon/tools-bar/tb_document.png\",\"themes/default/img/icon/tools-bar/tb_entrega_cartao.png\",\"themes/default/img/icon/tools-bar/tb_evaluation.png\",\"themes/default/img/icon/tools-bar/tb_habitacao.png\",\"themes/default/img/icon/tools-bar/tb_historico.png\",\"themes/default/img/icon/tools-bar/tb_housing-complex.png\",\"themes/default/img/icon/tools-bar/tb_imoveis.png\",\"themes/default/img/icon/tools-bar/tb_janela-dupla.png\",\"themes/default/img/icon/tools-bar/tb_licenca.png\",\"themes/default/img/icon/tools-bar/tb_link.png\",\"themes/default/img/icon/tools-bar/tb_lista.png\",\"themes/default/img/icon/tools-bar/tb_livrete.png\",\"themes/default/img/icon/tools-bar/tb_market.png\",\"themes/default/img/icon/tools-bar/tb_medir.png\",\"themes/default/img/icon/tools-bar/tb_medir_area.png\",\"themes/default/img/icon/tools-bar/tb_observacoes.png\",\"themes/default/img/icon/tools-bar/tb_print_actualizar.png\",\"themes/default/img/icon/tools-bar/tb_processos.png\",\"themes/default/img/icon/tools-bar/tb_refresh.png\",\"themes/default/img/icon/tools-bar/tb_search_land.png\",\"themes/default/img/icon/tools-bar/tb_statistically.png\",\"themes/default/img/icon/tools-bar/tb_vista-anterior.png\",\"themes/default/img/icon/tools-bar/tb_vista-inicial.png\",\"themes/default/img/icon/tools-bar/tb_zoom-janela.png\",\"themes/default/img/icon/tools-bar/transferir.png\",\"themes/default/img/icon/tools-bar/turismo.png\",\"themes/default/img/icon/tools-bar/view.png\"]";
		}
		Igrp.getInstance().getResponse().setContentType("application/json");
		return Igrp.getInstance().getResponse().getWriter().append(menu);
	}
	
	//Extracting reserve code inserted by programmer
	public PrintWriter actionPreserveUrl() throws IOException{
		Igrp.getInstance().getResponse().setContentType("text/xml");
		String type = Igrp.getInstance().getRequest().getParameter("type");
		String page = Igrp.getInstance().getRequest().getParameter("page");
		String app = Igrp.getInstance().getRequest().getParameter("app");
		String ac = Igrp.getInstance().getRequest().getParameter("ac");
		String your_code = "";
		if(type!=null && page!=null && app!=null && !page.equals("") && !app.equals("") && !type.equals("")){
			String basePath = Config.getProject_loc()+"/src/nosi/webapps/"+app.toLowerCase()+"/pages/"+page.toLowerCase();
			String controller = FileHelper.readFile(basePath, page+"Controller.java");
			if(controller!=null && !controller.equals("")){
				if(type.equals("c_import")){
					int start = controller.indexOf(Config.RESERVE_CODE_IMPORP_PACKAGE_CONTROLLER);
					int end = controller.indexOf(Config.RESERVE_CODE_END);
					your_code = (start!=-1 && end!=-1)?controller.substring(start+Config.RESERVE_CODE_IMPORP_PACKAGE_CONTROLLER.length(), end):"";
				}else if(type.equals("c_actions")){
					int start = controller.indexOf(Config.RESERVE_CODE_ACTIONS_CONTROLLER);
					int end =  start!=-1? controller.indexOf(Config.RESERVE_CODE_END,start):-1;
					your_code = (start!=-1 && end!=-1)?controller.substring(start+Config.RESERVE_CODE_ACTIONS_CONTROLLER.length(),end):"";
				}else if(ac!=null && !ac.equals("") && type.equals("c_on_action")){
					String actionName = "action"+ac;
					int start_ = controller.indexOf(actionName);
					int start = start_!=-1?controller.indexOf(Config.RESERCE_CODE_ON_ACTIONS_CONTROLLER,start_):-1;
					int end = start!=-1?controller.indexOf(Config.RESERVE_CODE_END,start):-1;
					your_code = (start!=-1 && start_!=-1 && end!=-1)?controller.substring(start+Config.RESERCE_CODE_ON_ACTIONS_CONTROLLER.length(), end):"";
				}
			}
		}
		your_code = StringEscapeUtils.escapeXml(your_code);
		return Igrp.getInstance().getResponse().getWriter().append("<your_code>"+your_code+"</your_code>");
	}
	
	public void actionListService(){
		
	}
	
	//View page with xml
	public PrintWriter actionVisualizar() throws IOException{
		Igrp.getInstance().getResponse().setContentType("text/xml");
		String p_id = Igrp.getInstance().getRequest().getParameter("id");
		Action ac = new Action().findOne(Integer.parseInt(p_id));	
		if(ac!=null){			
			String filename = Config.getResolvePathXsl(ac.getApplication().getDad(), ac.getPage(), ac.getVersion())+"/"+ac.getPage()+".xml";
			ServletContext context = Igrp.getInstance().getServlet().getServletContext();
			InputStream inputStrem = context.getResourceAsStream(filename);
	        if (inputStrem != null) {
	            InputStreamReader inputSReader = new InputStreamReader(inputStrem);
	            BufferedReader reader = new BufferedReader(inputSReader);
	            PrintWriter writer = Igrp.getInstance().getResponse().getWriter();
	            String text;
	            while ((text = reader.readLine()) != null) {
	                writer.println(text);
	            }
	            reader.close();
	            inputSReader.close();
	           return writer;
	        }
		}
		return null;
	}

}
