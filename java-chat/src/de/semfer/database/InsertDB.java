/**
* File-Name: InsertDB.java
* Package-Name: de.semfer.database
* @since 02.08.2011 - 19:14:35
* @author Patric Vormstein
* @version 1.0.1
* Description:
*	Erleichtert den Insert von Tupeln
* Last Change:
*
**/
package de.semfer.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InsertDB {
	
	private Config cfg;
	private SessionFactory factory;
	
	/**
    * <b>InsertDB(Config cfg)</b><p>Konstruktor fuer den Fall, dass keine SessionFactory vorliegt.</p>
    *
    * @param cfg : Uebernimmt die bereits erstellte Configuration
    */
	public InsertDB(Config cfg){
		this.cfg = cfg;
		this.factory = this.cfg.getConfig().buildSessionFactory();
	}
	
	/**
    * <b>InsertDB(Config cfg, SessionFactory factory)</b><p>Konstruktur fuer den Fall, dass bereits eine SessionFactory vorliegt.</p>
    *
    * @param cfg : Uebernimmt die bereits erstellte Configuration
    * @param factory : Uebernimmt die bereits existierende SessionFactory
    */
	public InsertDB(Config cfg, SessionFactory factory){
		this.cfg = cfg;
		this.factory = factory;
	}
	
	
	/**
    * <b><u>boolean</u> insertObj(Object obj)</b><p>Methode um das Insert zu erleichtert. Speichert ein Objekt in die Datenbank.</p>
    *
    * @param obj : Uebergibt das Objekt, welches in die Datenbank gespeichert werden soll. (Entity-Objekt!)
    * @return <u>boolean</u> true : Wenn Insert erfolgreich<br><u>boolean</u> false : Wenn Exception
    */
	public boolean insertObj(Object obj){
		try{
			Session session = factory.getCurrentSession();
			session.beginTransaction();	
			session.save(obj);
			session.getTransaction().commit();
			
			return true;
		}
		catch(Exception e){
			System.out.println("Fehler beim Insert: "+e);
			
			return false;
		}
	}
	
	/**
    * <b><u>void</u> closeFactory()</b><p>Schliesst die SessionFactory.</p>
    */
	public void closeFactory(){
		factory.close();
	}

	/**
	 * <b><u>Config</u> getCfg()</b><p>Getter fuer Config cfg.</p>
	 * 
	 * @return <u>Config</u> cfg
	 */
	public Config getCfg() {
		return cfg;
	}

	/**
	 * <b><u>void</u> setCfg(Config cfg)</b><p>Setter fuer Config cfg.</p>
	 * 
	 * @param cfg : Das Config-Objekt
	 */
	public void setCfg(Config cfg) {
		this.cfg = cfg;
	}

	/**
	 * <b><u>SessionFactory</u> getFactory()</b><p>Getter fuer SessionFactory factory.</p>
	 * 
	 * @return <u>SessionFactory</u> factory
	 */
	public SessionFactory getFactory() {
		return factory;
	}

	/**
	 * <b><u>void</u> setFactory(SessionFactory factory)</b><p>Setter fuer SessionFactory factory.</p>
	 * 
	 * @param factory : Das Factory-Objekt
	 */
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
}
