/**
* File-Name: User.java
* Package-Name: de.semfer.database
* @since 01.08.2011 - 23:00:00
* @author Jennifer Ulges, Patric Vormstein
* @version 1.0
* Description:
*   Erstellt die User-Tabellen fuer MySQL
* Last Change:
*   + Encryptor eingefuehrt
*
**/
package de.semfer.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.jasypt.hibernate.type.EncryptedStringType;

@Entity
@Table(name="user")
@SecondaryTable(name="user_info")
@TypeDef(
    name="encryptedString", 
    typeClass=EncryptedStringType.class, 
    parameters= {
        @Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
    }
)
public class User {
	
	private int id;
	private String userName, password, email, prename, surname;
	
	public User(){
		userName = password = email = prename = surname = null;		
	}
	
	public User(String userName, String password, String email, String prename, String surname){
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.prename = prename;
		this.surname = surname;
	}
	
	@Id
	@TableGenerator (name="id", table="id_user", pkColumnName="id_key", pkColumnValue="id_value", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Type(type = "encryptedString")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Type(type = "encryptedString")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(table="user_info")
	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	
	@Column(table="user_info")
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	
}
