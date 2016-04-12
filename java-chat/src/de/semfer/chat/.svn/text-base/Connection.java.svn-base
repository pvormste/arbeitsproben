/**
* File: Connection.java
* Package: de.semfer.chat
* @since 10.08.2011 - 15:32:36
* @author Jennifer Ulges<br>Patric Vormstein
* @version 1.1
* Description:
* 	Beinhaltet und verwaltet die bestehenden Verbindungen
* Last Change:
* 	+ TimeOut hinzugefügt
**/
package de.semfer.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * Class: Connection.java
 * @author Jennifer Ulges<br>Patric Vormstein
 *
 */
public final class Connection extends Thread {

	/** Chatserver und Client-Socket der Connection */
	private Chatserver server;
	private Socket client;
	
	/** Input- und Outputstream für alle auszutauschenden Informationen zwischen Client und Server */
	private ObjectOutputStream out;
	private ObjectInputStream in;
		
	/** Username der Connection */
	private String userName;
	
	
	/**
	 * <b>Connection(Chatserver server, Socket client)</b><p>Konstruktor.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param server : Chatserver
	 * @param client : Client-Socket
	 */
	public Connection(Chatserver server, Socket client){
		
		// Zuweisung der übergebenen Parameter zu den Objektvariablen
		this.server = server;
		this.client = client;
		
		// Input-,Outputstream und Username werden initialisiert 
		try{
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
			userName = "";
		}catch(IOException e){
			
			// Client-Socket wird geschlossen
			try{
				client.close();	
			}catch(IOException ex){
				System.err.println("Fehler beim Erzeugen des Streams:");
				ex.printStackTrace();
			}
	
		}
		
		// Thread bzw. Connection selbst wird gestartet
		this.start();
	}
	
	/**
	 * <b><u>void</u> run()</b><p>Thread-Endlos-Schleife</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 */
	public void run(){
		// String vom Inputstream
		String line;
		
		try{
			// TimeOut auf 10 Minuten setzen (600.000 ms)
			this.client.setSoTimeout(600000);
			
			// solange Thread nicht unterbrochen ist
			while(!this.isInterrupted()){
				
				try {
					// String aus dem InputStream wird gelesen
					line = in.readObject().toString();
				} catch (ClassNotFoundException e) {
					line = null;
					System.err.println("Fehler beim Lesen der Klasse des Streams:");
					e.printStackTrace();
				}
				// (ausgelöst durch entsprechenden Trigger) wenn ein User sich verbindet wird der Username zur aktuellen UserList hinzugefügt 
				// und die aktualisierte UserList an alle anderen User verschickt.
				if(line != null && line.startsWith(Chatserver.TRIGGER_CONNECT)){
					userName = line.substring(25);
					server.getUserList().addElement(userName);
					server.sendUserList(userName);
				}
				// (ausgelöst durch entsprechenden Trigger) wenn ein User den Chat verlässt wird sein Username aus der UserList entfernt
				// und die aktualisierte UserList an alle anderen User verschickt
				// Thread wird unterbrochen
				else if(line != null && line.startsWith(Chatserver.TRIGGER_DISCONNECT)){
					closeConnection();					
				}
				// String aus dem InputStream wird als Nachricht inkl. Username an alle Clients verschickt
				else if(line != null){
					server.broadcast(userName, line);
				}
			}
		}catch(SocketTimeoutException ex){
			// Dem Client den TimeOut bekannt machen
			server.sendTimeOut(userName);
			closeConnection();
			System.out.println("Socket TimeOut");
		}catch(IOException e){
			System.err.println("Fehler beim Lesen des Streams: ");	
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <b><u>void</u> closeConnection()</b><p>Methode um die Verbindung zu schließen.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.1
	 */
	private void closeConnection(){
		server.getUserList().removeElement(userName);
		server.getConnections().removeElement(this);
		server.removeFromUserList(userName);
		this.interrupt();
	}

	/**
	 * <b><u>Socket</u> getClient()</b><p>Getter für den Client-Socket</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>Socket</u> client
	 */
	public Socket getClient() {
		return client;
	}

	/**
	 * <b><u>ObjectOutputStream</u> getOut()</b><p>Getter für den ObjectOutputStream</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>ObjectOutputStream</u> out
	 */
	public ObjectOutputStream getOut() {
		return out;
	}

	/**
	 * <b><u>String</u> getUserName()</b><p>Getter für userName</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.1
	 * @return <u>String</u> userName
	 */
	public String getUserName() {
		return userName;
	}
	
	
}
