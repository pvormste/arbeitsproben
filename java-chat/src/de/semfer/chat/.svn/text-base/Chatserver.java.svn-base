/**
* File: Chatserver.java
* Package: de.semfer.chat
* @since 10.08.2011 - 14:50:42
* @author Jennifer Ulges<br>Patric Vormstein
* @version 1.2
* Description:
* 	Der Server des Chats
* Last Change:
* 	+ Trigger in Nachrichten für Usernamen-Trennung von Messages
* 	+ TimeOut-Sending
**/
package de.semfer.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.DefaultListModel;

/**
 * Class: Chatserver.java
 * @author Jennifer Ulges<br>Patric Vormstein
 *
 */
public final class Chatserver implements Runnable {
	
	/** Port für den Serversocket */
	protected static final int PORT = 8767;
	
	//******************************
	// Triggers
	//******************************
	
	/** Trigger für einen User der sich verbindet */
	protected static final String TRIGGER_CONNECT = "+m0Qq-=vL&gm]4EC&+B&0niH8";
	
	/** Trigger für einen User der sich trennt */
	protected static final String TRIGGER_DISCONNECT = "B*qZoDf$U+LPS]$}PN};y)h-Y";
	
	/** Trigger für neün User */
	protected static final String TRIGGER_NEWUSER = "VS}*$Kf)wg9=8Pbe6!41X;XQG";
	
	/** Trigger für User entfernen */
	protected static final String TRIGGER_REMOVEUSER = "sVOhZ.DvsXh4;Q!4}=ve[8s0e";
	
	/** Trigger für TimeOut */
	protected static final String TRIGGER_TIMEOUT = "5P01ge]SQgrL=n)wP]mT-E>pb";
	 
	/** Trigger für Usernamen-Trennung von Nachrichten */
	protected static final String TRIGGER_MSG = " $§%??;$%§&%$§&?%$ ";
	
	//******************************
	// - - end
	//
	// Konstanten
	//******************************
	
	protected static final String SYS_MSG = "~~ System";
	
	//******************************
	// -- end
	//******************************
	
	/** ServerSocket, listener für Verbindungen */
	private ServerSocket listen;
	
	/** Vector-Liste mit aktuellen Verbindungen */
	private Vector<Connection> connections;

	/** Collection mit Namen der User im Chat */
	private DefaultListModel<String> userList;
	
	/** Server-Thread */
	private Thread connect;
	
	
	/**
	 * <b>static void main(String[] args)</b><p>Startet den Chatserver</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @param args
	 */
	public static void main(String[] args)
	{
	    new Chatserver();
	}

	/**
	 * <b>Chatserver()</b><p>Konstruktor.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 */
	public Chatserver(){
		
		// Versuchen den ServerSocket zu binden
		try{
			listen = new ServerSocket(PORT);	
		}catch(IOException e){
			System.err.println("Fehler beim Erzeugen der Sockets:"+e);
			e.printStackTrace();
			System.exit(1);	
		}
		
		// Initialisierungen
		connections = new Vector<Connection>();
		userList = new DefaultListModel<String>();
		
		// Thread initialisieren und starten
		connect = new Thread(this);
		connect.start();
	}
		
	/**
	 * <b><u>void</u> run()</b><p>Thread-Endlos-Schleife</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 */
	public void run() {
		
		// Versucht die Verbindungen abzuhören
		try{
			System.out.println("Server läuft ...");
			
			// Endlosschleife für Verbindungsaufnahme
			while(true){
				// Akzeptiert eingehende Anfragen
				Socket client = listen.accept();
				
				// Erstellt eine Verbindungsklasse zwischen ServerSocket und Client,
				// die dann in den Vector eingefügt wird.
				Connection c = new Connection(this, client);
				connections.addElement(c);
			}
			
		}catch(IOException e){
			System.err.println("Fehler beim Warten auf Verbindungen:");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Server stoppt ...");
	}
	
	/**
	 * <b><u>void</u> broadcast(String userName, String msg)</b><p>Verschickt Username und Nachricht an alle Clients.</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @last_update 1.2
	 * @param userName : Username des Senders
	 * @param msg : Die Nachricht die übermittelt werden soll
	 */
	protected void broadcast(String userName, String msg){
		
		// Alle Connections
		Connection all;
		
		// For-Schleife läuft alle aktuellen Verbindungen im Vektor durch und 
		// verschickt die übergebene Nachricht und den Usernamen an alle Clients
		for(int i = 0; i < connections.size(); i++){
			all = (Connection) connections.elementAt(i);
			try {
				all.getOut().writeObject(userName+":"+TRIGGER_MSG+msg);
				// Nachricht direkt abschicken
				all.getOut().flush();
			} catch (IOException e) {
				System.err.println("Fehler beim Verteilen der Nachricht:");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * <b><u>void</u> sendUserList(String userName)</b><p>Verschickt die User-Liste aus dem DefaultListModel an den kürzlich
	 * online gekommenen User.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param userName : User, an den die vollständige User-Liste geschickt werden soll.
	 */
	protected void sendUserList(String userName){
		// Letztes Element aus dem Vector greifen
		Connection last = connections.lastElement();
		
		// User-Liste mit Trigger direkt verschicken
		try {
			last.getOut().writeObject(TRIGGER_CONNECT+userList);
			last.getOut().flush();
		} catch (IOException e) {
			System.err.println("Fehler beim Verschicken der vollständigen User-Liste:");
			e.printStackTrace();
		}		
		
		// Methode addToUserList aufrufen, damit die anderen Clients ebenfalls aktualisiert werden
		addToUserList(userName);
	}
	
	
	/**
	 * <b><u>void</u> addToUserList(String userName)</b><p>Diese Methode verschickt den zuletzt verbundenen User an die 
	 * anderen Clients.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param userName : Username, der in die Liste der anderen Clients eingefügt werden soll.
	 */
	private void addToUserList(String userName){
		// Restlichen Clients
		Connection others;
		
		// For-Schleife verschickt den letzten Usernamen inkl. Trigger an alle anderen
		for(int i = 0; i < connections.size()-1; i++){
			others = (Connection) connections.elementAt(i);
			try {
				others.getOut().writeObject(TRIGGER_NEWUSER+userName);
				others.getOut().flush();
			} catch (IOException e) {
				System.err.println("Fehler beim Verschicken des letzten Users:");
				e.printStackTrace();
			}	
		}
		
		// Systemnachricht, dass ein neuer User online gekommen ist
		broadcast(SYS_MSG, userName+" hat den Chat betreten!");
	}
	
	/**
	 * <b><u>void</u> removeFromUserList(String userName)</b><p>Verschickt den zu entfernenden Usernamen an alle verbliebene Clients.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param userName : Username, der aus der Liste entfernt werden soll.
	 */
	protected void removeFromUserList(String userName){
		// Alle verbliebenen Connections
		Connection all;
		
		// For-Schleife verschickt Usernamen inkl. Trigger an alle verbliebenen Connections
		for(int i = 0; i < connections.size(); i++){
			all = (Connection) connections.elementAt(i);
			try {
				all.getOut().writeObject(TRIGGER_REMOVEUSER+userName);
				all.getOut().flush();
			} catch (IOException e) {
				System.err.println("Fehler beim Verschicken des gegangenen Users:");
				e.printStackTrace();
			}	
		}
		
		// Systemnachricht, dass User den Chat verlassen hat
		broadcast(SYS_MSG, userName+" hat den Chat verlassen!");
	}
	
	/**
	 * <b><u>void</u> sendTimeOut()</b><p>Verschickt dem User die Bekanntgabe des TimeOuts</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.1
	 * @param userName : Der User, der den TimeOut hat
	 */
	protected void sendTimeOut(String userName){
		// Alle Connections ablaufen
		for(Connection con : connections){
			// Wenn Name gleich, dann TimeOut bekannt machen
			if(con.getUserName().equals(userName)){
				try {
					con.getOut().writeObject(TRIGGER_TIMEOUT);
					con.getOut().flush();
				} catch (IOException e) {
					System.err.println("IOException / Error while sending TimeOut:");
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * <b><u>Vector</u> getConnections()</b><p>Getter für den Vector aller Connections</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>Vector</u> connections
	 */
	public Vector<Connection> getConnections() {
		return connections;
	}


	/**
	 * <b><u>DefaultListModel</u> getUserList()</b><p>Getter für UserList</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>DefaultListModel</u> userList
	 */
	public DefaultListModel<String> getUserList() {
		return userList;
	}
}
