/**
* File-Name: GeneralInfo.java
* Package-Name: de.semfer.database
* @since 03.08.2011 - 00:50:37
* @author Patric Vormstein
* @version 1.0
* Description:
*	Tabellenklasse für allgemeine Infos
* Last Change:
*
**/
package de.semfer.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="general_info")
public class GeneralInfo {

	private int id;
	private String siteName, version;
	
	/**
	 * <b>GeneralInfo()</b><p>Konstruktor</p>
	 */
	public GeneralInfo(){
		siteName = version = null;
	}
	
	/**
	 * <b>GeneralInfo(String siteName, String version)</b><p>Konstruktor mit Parametern</p>
	 * 
	 * @param siteName : Seitenname
	 * @param version : Version
	 */
	public GeneralInfo(String siteName, String version){
		this.siteName = siteName;
		this.version = version;
	}

	/**
	 * <b><u>int</u> getID()</b><p>ID Getter</p>
	 * 
	 * @return <u>int</u> id
	 */
	@Id
	public int getId() {
		return id;
	}

	/**
	 * <b><u>void</u> setID(int id)</b><p>ID Setter</p>
	 * 
	 * @param id : ID setzen
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * <b><u>String</u> getSiteName()</b><p>Seitnnamen Getter</p>
	 *
	 * @return <u>String</u> siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * <b><u>void</u> setSiteName(String siteName)</b><p>Seitennamen Setter</p>
	 * 
	 * @param siteName : Seitennamen setzen
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * <b><u>String</u> getVersion()</b><p>Version Getter</p>
	 * 
	 * @return <u>String</u> version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <b><u>void</u> setVersion(String version)</b><p>Version Setter</p>
	 * 
	 * @param version : Version setzen
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
}
