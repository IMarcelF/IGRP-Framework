package nosi.core.config;

import java.util.Properties;

import nosi.core.gui.components.IGRPButton;
import nosi.core.gui.components.IGRPToolsBar;
import nosi.core.xml.XMLWritter;

public class Config {
	
<<<<<<< HEAD
	/*public static final String PATH_CLASS = "C:/Users/isaias.nunes/git/IGRP-Framework/IGRP/src/";
	public static final String PATH_XSL = "C:/Users/isaias.nunes/git/IGRP-Framework/IGRP/WebContent/images/IGRP/IGRP2.3/app/";
	
	public static final String LINK_IMG = "/IGRP/images/IGRP/IGRP2.3";*/
	//""
=======
>>>>>>> branch 'master' of https://github.com/IMarcelF/IGRP-Framework.git
	public static String TITLE = "";
	public static String target = "";
	
	public static String getHeader(){
		XMLWritter xml = new XMLWritter();
		xml.setElement("tamplate", "");
		xml.setElement("title", TITLE);
		xml.setElement("version",getVersion());
		xml.setElement("link",getLink());
		xml.setElement("link_img",getLinkImg());
		if(!target.equals("")){
			xml.setElement("target", target);
		}
		xml.startElement("site");
			xml.setElement("welcome_note",getWelcomeNote());
			xml.setElement("footer_note", getFooterName());
			xml.setElement("user_name", "red-igrp");
			IGRPToolsBar button = new IGRPToolsBar("button");
			button.addButton(new IGRPButton("Sair", "webapps?r=igrp", "login", "logout", "_self", "exit.png","",""));
			xml.addXml(button.toXmlButton());
		xml.endElement();
		xml.setElement("app", "igrp");
		xml.setElement("page", "form");
		xml.startElement("plsql");
			xml.setElement("action", "1");
			xml.setElement("package_db", "FORM_DESIGNER_DB");
			xml.setElement("package_html", "FORM_DESIGNER_HTML");
			xml.setElement("package_instance", "");
			xml.setElement("with_replace", "false");
			xml.setElement("with_label", "false");
			xml.setElement("with_biztalk", "false");
			xml.setElement("dynamic_menu", "false");
			xml.setElement("copy_menu", "false");
			xml.setElement("package_copy_db", "");
			xml.setElement("package_copy_html", "");
		xml.endElement();
		xml.startElement("navigation");
		xml.writeAttribute("url", "webapps?");
		xml.writeAttribute("prm_app", "prm_app");
		xml.writeAttribute("prm_page", "prm_page");
		xml.writeAttribute("prm_action", "r");
		xml.endElement();
		
		xml.startElement("top_menu");
		xml.writeAttribute("file", getLinkTopMenu());
		xml.endElement();
		
		xml.startElement("slide-menu");
		xml.writeAttribute("file",getLinkSileMenu());
		xml.endElement();
		target = "";
		TITLE = "";
		return xml.toString();
	}
	
	public static String getHeaderHome(){
		XMLWritter xml = new XMLWritter();
		xml.setElement("tamplate", "");
		xml.setElement("title", TITLE);
		xml.setElement("version",getVersion());
		xml.setElement("link",getLink());
		xml.setElement("link_img",getLinkImg());
		xml.startElement("site");
			xml.setElement("welcome_note",getWelcomeNote());
			xml.setElement("footer_note", getFooterName());
			xml.setElement("user_name", "red-igrp");
			IGRPToolsBar button = new IGRPToolsBar("button");
			button.addButton(new IGRPButton("Sair", "webapps?r=igrp", "login", "logout", "_self", "exit.png","",""));
			xml.addXml(button.toXmlButton());
		xml.endElement();
		xml.setElement("app", "igrp");
		xml.setElement("page", "form");
		xml.startElement("plsql");
			xml.setElement("action", "1");
			xml.setElement("package_db", "FORM_DESIGNER_DB");
			xml.setElement("package_html", "FORM_DESIGNER_HTML");
			xml.setElement("package_instance", "");
			xml.setElement("with_replace", "false");
			xml.setElement("with_label", "false");
			xml.setElement("with_biztalk", "false");
			xml.setElement("dynamic_menu", "false");
			xml.setElement("copy_menu", "false");
			xml.setElement("package_copy_db", "");
			xml.setElement("package_copy_html", "");
		xml.endElement();

		target = "";
		TITLE = "";
		return xml.toString();
	}
	
	public static Properties getConfig(){
		Properties configs = new Properties();
		for(Object c: new nosi.webapps.igrp.dao.Config().getAll()){
			nosi.webapps.igrp.dao.Config obj = (nosi.webapps.igrp.dao.Config) c;
			configs.put(obj.getName(),obj.getValue());
		}
		return configs;
	}
	
	public static String getPathClass(){
		return getConfig().get("path_class")!=null? getConfig().get("path_class").toString():"";
	}
	public static String getPathXsl(){
		return getConfig().get("path_xsl")!=null? getConfig().get("path_xsl").toString():"";
	}
	public static String getLinkImg(){
		return getConfig().get("link_img")!=null? getConfig().get("link_img").toString():"";
	}
	public static String getLink(){
		return getConfig().get("link")!=null? getConfig().get("link").toString():"";
	}
	public static String getVersion(){
		return getConfig().get("version")!=null? getConfig().get("version").toString():"";
	}
	public static String getLinkSileMenu(){
		return getConfig().get("link_slide_menu")!=null? getConfig().get("link_slide_menu").toString():"igrp/menu/myMenu";
	}
	public static String getLinkTopMenu(){
		return getConfig().get("link_top_menu")!=null? getConfig().get("link_top_menu").toString():"";
	}
	public static String getFooterName(){
		return getConfig().get("footer_name")!=null? getConfig().get("footer_name").toString():"";
	}
	public static String getWelcomeNote(){
		return getConfig().get("welcome_note")!=null? getConfig().get("welcome_note").toString():"";
	}
	
	public static String getPageVersion(){
		return "2.3";
	}
}
