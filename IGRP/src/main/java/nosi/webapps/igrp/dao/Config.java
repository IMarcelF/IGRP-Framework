package nosi.webapps.igrp.dao;
/*
  @author: Emanuel Pereira
 * 29 Jun 2017
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tbl_config")
public class Config extends IGRPBaseActiveRecord<Config> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187302633317523568L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable=false,unique=true)
	private String name;
	@Column(nullable=false)
	private String value;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="env_fk",foreignKey=@ForeignKey(name="CONFIG_FK"),nullable=true)
	private Application application;
	
	public Config(){}
	
	public Config(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public static String getBaseUrlActiviti() {
		return new Config().find().andWhere("name", "=", "url_ativiti_connection").one().getValue();
	}

}