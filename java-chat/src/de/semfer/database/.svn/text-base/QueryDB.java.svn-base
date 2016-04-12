/**
* File-Name: QueryDB.java
* Package-Name: de.semfer.database
* @since 02.08.2011 - 00:35:30
* @author Patric Vormstein
* @version 1.2.2
* Description:
* 	Erleichtert das Ausfuehren von Queries ueber eine Standardmethode
* Last Change:
* 	+ Unterscheidung fuer Single-Query eingefuehrt
* 	+ try-catch hinzugefuegt
**/
package de.semfer.database;

import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class QueryDB {
	
	private Config cfg;
	private SessionFactory factory;
	
	
	/**
    * <b>QueryDB(Config cfg)</b><p>Konstruktor fuer den Fall, dass keine SessionFactory vorliegt.</p>
    *
    * @param cfg : Uebernimmt die bereits erstellte Configuration
    */
	public QueryDB(Config cfg){
		this.cfg = cfg;
		this.factory = this.cfg.getConfig().buildSessionFactory();
	}
	
	/**
    * <b>QueryDB(Config cfg, SessionFactory factory)</b><p>Konstruktor fuer den Fall, dass bereits eine SessionFactory vorliegt.</p>
    *
    * @param cfg : Uebernimmt die bereits erstellte Configuration
    * @param factory : Uebernimmt die bereits existierende SessionFactory
    */
	public QueryDB(Config cfg, SessionFactory factory){
		this.cfg = cfg;
		this.factory = factory;
	}
	
	/**
    * <b><u>ArrayList</u> selectSQL(String sql, boolean isPramSingle)</b><p>Konstruktor fuer den Fall, dass bereits eine SessionFactory vorliegt.</p>
    *
    * @param sql : Uebergibt den SQL-String
    * @param isParamSingle : Handelt es sich um einen einzelnen Paramater in der SQL?
    * @return <u>ArrayList<Object[]></u> result<br><i>Gibt eine ArrayList<Object[]> mit dem Ergebnis zurueck</i>
    */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList selectSQL(String sql, boolean isParamSingle){
		try{
			ArrayList result;
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			
			// Übergebene Query ausfuehren
			Query query = session.createQuery(sql);
			
			// Prüfen ob es sich um einen einzelnen Param handelt
			if(isParamSingle){
				result = new ArrayList<Object>();
				for(Iterator iter = query.iterate(); iter.hasNext();){
					// Param als Object speichern
					Object row = (Object)iter.next();
					result.add(row);
				}
			}
			// Für mehrere Params
			else{
				result = new ArrayList<Object[]>();
				for(Iterator iter = query.iterate(); iter.hasNext();){
					// Params als Object-Array speichern
					Object[] row = (Object[])iter.next();
					result.add(row);
				}
			}
			
			session.getTransaction().commit();
			
			return result;
		}
		catch(Exception e){
			System.out.println("Fehler in der Query: "+e);
			
			return null;
		}

	}
	
	
	/**
    * <b><u>void</u> closeFactory()</b><p>Schliesst die SessionFactory</p>
    */
	public void closeFactory(){
		factory.close();
	}

	/**
	 * <b><u>Config</u> getCfg()</b><p>Getter fuer Config cfg</p>
	 * 
	 * @return <u>Config</u> cfg
	 */
	public Config getCfg() {
		return cfg;
	}

	/**
	 * <b><u>void</u> setCfg(Config cfg)</b><p>Setter fuer Config cfg</p>
	 * 
	 * @param cfg : Das Config-Objekt
	 */
	public void setCfg(Config cfg) {
		this.cfg = cfg;
	}

	/**
	 * <b><u>SessionFactory</u> getFactory()</b><p>Getter fuer SessionFactory factory </p>
	 * 
	 * @return <u>SessionFactory</u> factory
	 */
	public SessionFactory getFactory() {
		return factory;
	}

	/**
	 * <b><u>void</u> setFactory(SessionFactory factory)</b><p>Setter fuer SessionFactory factory</p>
	 * 
	 * @param factory : Das Factory-Objekt
	 */
	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
		
}
