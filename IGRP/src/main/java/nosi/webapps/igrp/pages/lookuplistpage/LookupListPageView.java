package nosi.webapps.igrp.pages.lookuplistpage;

import nosi.core.webapp.Model;
import nosi.core.webapp.View;
import nosi.core.gui.components.*;
import nosi.core.gui.fields.*;
import static nosi.core.i18n.Translator.gt;

public class LookupListPageView extends View {

	public Field sectionheader_1_text;
	public Field associar_pagina;
	public Field associar_documentos;
	public Field env_fk;
	public Field page_descr;
	public Field page;
	public Field checkbox;
	public Field checkbox_check;
	public Field obrigatorio;
	public Field obrigatorio_check;
	public Field tipo;
	public Field nome;
	public Field descricao_documento;
	public Field type_doc_desc;
	public Field type_doc;
	public Field descricao;
	public Field nome_pagina;
	public Field id;
	public Field taskid;
	public Field processid;
	public IGRPSectionHeader sectionheader_1;
	public IGRPTabContent tabcontent_1;
	public IGRPForm form_1;
	public IGRPFormList formlist_1;
	public IGRPTable table_1;
	public IGRPForm form_2;

	public IGRPToolsBar toolsbar_1;
	public IGRPButton btn_gravar;
	public IGRPButton btn_pesquisar;

	public LookupListPageView(){

		this.setPageTitle("Lista de Pagina Para Lookup");
			
		sectionheader_1 = new IGRPSectionHeader("sectionheader_1","");

		tabcontent_1 = new IGRPTabContent("tabcontent_1","");

		form_1 = new IGRPForm("form_1","Filtro");

		formlist_1 = new IGRPFormList("formlist_1","");

		table_1 = new IGRPTable("table_1","");

		form_2 = new IGRPForm("form_2","");

		sectionheader_1_text = new TextField(model,"sectionheader_1_text");
		sectionheader_1_text.setLabel(gt(""));
		sectionheader_1_text.setValue(gt(""));
		sectionheader_1_text.propertie().add("type","text").add("name","p_sectionheader_1_text").add("maxlength","4000");
		
		associar_pagina = new TextField(model,"associar_pagina");
		associar_pagina.setLabel(gt("Associar Página"));
		associar_pagina.propertie().add("name","p_associar_pagina").add("type","button").add("img","fa-file-powerpoint-o").add("request_fields","").add("refresh_submit","false").add("refresh_components","").add("adbcli","").add("notvalidatefields","false").add("maxlength","50");
		
		associar_documentos = new TextField(model,"associar_documentos");
		associar_documentos.setLabel(gt("Associar Documentos"));
		associar_documentos.propertie().add("name","p_associar_documentos").add("type","button").add("img","fa-file-word-o").add("request_fields","").add("refresh_submit","false").add("refresh_components","").add("adbcli","").add("notvalidatefields","false").add("maxlength","50");
		
		env_fk = new ListField(model,"env_fk");
		env_fk.setLabel(gt("Aplicação"));
		env_fk.propertie().add("name","p_env_fk").add("type","select").add("multiple","false").add("maxlength","30").add("required","false").add("disabled","false").add("domain","").add("java-type","").add("tags","false").add("load_service_data","false").add("tooltip","false").add("disable_copy_paste","false");
		
		page_descr = new TextField(model,"page_descr");
		page_descr.setLabel(gt("Título"));
		page_descr.propertie().add("name","p_page_descr").add("type","text").add("maxlength","100").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false").add("disablehtml","true").add("inputmask","").add("tooltip","false").add("disable_copy_paste","false");
		
		page = new TextField(model,"page");
		page.setLabel(gt("Code"));
		page.propertie().add("name","p_page").add("type","text").add("maxlength","100").add("required","false").add("readonly","false").add("disabled","false").add("placeholder",gt("")).add("desclabel","false").add("disablehtml","true").add("inputmask","").add("tooltip","false").add("disable_copy_paste","false");
		
		checkbox = new CheckBoxField(model,"checkbox");
		checkbox.setLabel(gt("Ativo?"));
		checkbox.propertie().add("name","p_checkbox").add("type","checkbox").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","int").add("check","true").add("desc","true");
		
		checkbox_check = new CheckBoxField(model,"checkbox_check");
		checkbox_check.propertie().add("name","p_checkbox").add("type","checkbox").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","int").add("check","true").add("desc","true");
		
		obrigatorio = new CheckBoxField(model,"obrigatorio");
		obrigatorio.setLabel(gt("Obrigatório?"));
		obrigatorio.propertie().add("name","p_obrigatorio").add("type","checkbox").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","int").add("check","true").add("desc","true");
		
		obrigatorio_check = new CheckBoxField(model,"obrigatorio_check");
		obrigatorio_check.propertie().add("name","p_obrigatorio").add("type","checkbox").add("maxlength","30").add("required","false").add("readonly","false").add("disabled","false").add("java-type","int").add("check","true").add("desc","true");
		
		tipo = new ListField(model,"tipo");
		tipo.setLabel(gt("Tipo"));
		tipo.propertie().add("name","p_tipo").add("type","select").add("multiple","false").add("domain","").add("maxlength","30").add("required","false").add("disabled","false").add("java-type","").add("delimiter",";").add("tags","false").add("load_service_data","false").add("desc","true");
		
		nome = new PlainTextField(model,"nome");
		nome.setLabel(gt("Nome"));
		nome.propertie().add("name","p_nome").add("type","plaintext").add("disable_output_escaping","false").add("html_class","").add("maxlength","30").add("desc","true");
		
		descricao_documento = new PlainTextField(model,"descricao_documento");
		descricao_documento.setLabel(gt("Descrição"));
		descricao_documento.propertie().add("name","p_descricao_documento").add("type","plaintext").add("disable_output_escaping","false").add("html_class","").add("maxlength","30").add("desc","true");
		
		type_doc = new HiddenField(model,"type_doc");
		type_doc.setLabel(gt(""));
		type_doc.propertie().add("name","p_type_doc").add("type","hidden").add("maxlength","250").add("java-type","String").add("tag","type_doc").add("desc","true");
		
		descricao = new TextField(model,"descricao");
		descricao.setLabel(gt("Título"));
		descricao.propertie().add("name","p_descricao").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		nome_pagina = new TextField(model,"nome_pagina");
		nome_pagina.setLabel(gt("Code"));
		nome_pagina.propertie().add("name","p_nome_pagina").add("type","text").add("maxlength","30").add("showLabel","true").add("group_in","");
		
		id = new HiddenField(model,"id");
		id.setLabel(gt(""));
		id.propertie().add("name","p_id").add("type","hidden").add("maxlength","30").add("showLabel","true").add("group_in","").add("java-type","").add("tag","id");
		
		taskid = new HiddenField(model,"taskid");
		taskid.setLabel(gt(""));
		taskid.propertie().add("name","p_taskId").add("type","hidden").add("maxlength","30").add("java-type","").add("tooltip","false").add("disable_copy_paste","false").add("tag","taskid");
		
		processid = new HiddenField(model,"processid");
		processid.setLabel(gt(""));
		processid.propertie().add("name","p_processId").add("type","hidden").add("maxlength","30").add("java-type","").add("tooltip","false").add("disable_copy_paste","false").add("tag","processid");
		

		toolsbar_1 = new IGRPToolsBar("toolsbar_1");

		btn_gravar = new IGRPButton("Gravar","igrp","LookupListPage","gravar","submit_ajax","primary|fa-save","","");
		btn_gravar.propertie.add("type","specific").add("rel","gravar").add("refresh_components","formlist_1");

		btn_pesquisar = new IGRPButton("Pesquisar","igrp","LookupListPage","pesquisar","submit_ajax","primary|fa-search","","");
		btn_pesquisar.propertie.add("id","button_a6fd_a12c").add("type","form").add("class","primary").add("rel","pesquisar").add("refresh_components","table_1");

		
	}
		
	@Override
	public void render(){
		
		sectionheader_1.addField(sectionheader_1_text);

		tabcontent_1.addField(associar_pagina);
		tabcontent_1.addField(associar_documentos);

		form_1.addField(env_fk);
		form_1.addField(page_descr);
		form_1.addField(page);


		formlist_1.addField(checkbox);
		formlist_1.addField(checkbox_check);
		formlist_1.addField(obrigatorio);
		formlist_1.addField(obrigatorio_check);
		formlist_1.addField(tipo);
		formlist_1.addField(nome);
		formlist_1.addField(descricao_documento);
		formlist_1.addField(type_doc);

		table_1.addField(descricao);
		table_1.addField(nome_pagina);
		table_1.addField(id);

		form_2.addField(taskid);
		form_2.addField(processid);

		toolsbar_1.addButton(btn_gravar);
		form_1.addButton(btn_pesquisar);
		this.addToPage(sectionheader_1);
		this.addToPage(tabcontent_1);
		this.addToPage(form_1);
		this.addToPage(formlist_1);
		this.addToPage(table_1);
		this.addToPage(form_2);
		this.addToPage(toolsbar_1);
	}
		
	@Override
	public void setModel(Model model) {
		
		associar_pagina.setValue(model);
		associar_documentos.setValue(model);
		env_fk.setValue(model);
		page_descr.setValue(model);
		page.setValue(model);
		checkbox.setValue(model);
		obrigatorio.setValue(model);
		tipo.setValue(model);
		nome.setValue(model);
		descricao_documento.setValue(model);
		type_doc.setValue(model);
		descricao.setValue(model);
		nome_pagina.setValue(model);
		id.setValue(model);
		taskid.setValue(model);
		processid.setValue(model);	

		formlist_1.loadModel(((LookupListPage) model).getFormlist_1());
		table_1.loadModel(((LookupListPage) model).getTable_1());
		}
}