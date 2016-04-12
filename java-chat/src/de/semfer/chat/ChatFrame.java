/**
* File: ChatFrame.java
* Package: de.semfer.chat
* @since 10.08.2011 - 17:07:23
* @author Patric Vormstein
* @version 1.5
* Description:
* 	 ChatFrame(Fenster) in dem der eingentliche Chat stattfindet
* Last Change:
* 	+ Sauberer Austieg, falls Applet-Stop
* 	+ Popupmenü
* 	+ Textformatierung eingefügt
* 	+ TextArea mit TextPane ersetzt
**/
package de.semfer.chat;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 * Class: ChatFrame.java
 * @author Jennifer Ulges<br>Patric Vormstein
 *
 */
public final class ChatFrame extends JFrame implements Runnable {
	
	/** UID des Frames */
	private static final long serialVersionUID = 5038896512881122760L;
	
	/** Objektvariablen des ChatFrames: Nickname und Serversocket */
	private String name;
	private Socket socket;
	
	/** Input- und Outputstream für alle auszutauschenden Informationen zwischen Client und Server */
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/** Chat-Interne Klassen */
	private Log log;
	
	/** Swing-Komponenten zu Darstellung des ChatFrames */
	private JTextField inputfield;
	private JTextPane outputArea;
	private StyledDocument doc;
	private JList<String> userListArea;
	private JScrollPane scrollList, scrollOut;
	private JPopupMenu popup;
	
	/** Thread für den ChatFrame */
	private Thread thread;
	
	/** StartUp des ChatFrames */
	private StartUp parent;
	

	/**
	 * <b>ChatFrame(String, Socket, StartUp)</b><p>Konstruktor.</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @param nickname : Nickname des eingeloggten Users
	 * @param socket : Chatserver-Socket
	 * @param parent : StartUp von dem aus der ChatFrame geöffnet wurde
	 * 
	 */
	public ChatFrame(String nickname, Socket socket, StartUp parent){
		
		//Zuweisung der übergebenen Parameter an die Objektvariablen
		this.name = nickname;
		this.socket = socket;
		this.parent = parent;
		
		//Initialisierung des Eingabfelds und Hinzufügen des KeyListeners
		inputfield = new JTextField();
    	inputfield.addKeyListener(new NewKeyAdapter());
    	inputfield.setFont(new Font("Dialog", Font.PLAIN, 14));
    	inputfield.setBackground(Color.white);
    	inputfield.requestFocus();
    	inputfield.setCaretPosition(0);
    	inputfield.setFocusable(true);
    	
    	
    	//Initialisierung des Ausgabefeldes (Schriftformat, nicht editierbar)
		outputArea = new JTextPane();
		outputArea.setEditable(false);	
		outputArea.setBackground(Color.black);
		outputArea.addMouseListener(new NewMouseAdapter());
		outputArea.setContentType("text/html");
		outputArea.setFocusable(false);
		doc = outputArea.getStyledDocument();
		
		//Initialisierung des Listenfeldes für die UserList (Mehrfachauswahl, Hinzufügen des KeyListeners)
		userListArea = new JList<String>();
		userListArea.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		userListArea.setModel(new DefaultListModel<String>());
		userListArea.addKeyListener(new NewKeyAdapter());
		userListArea.setFocusable(false);
		userListArea.setFont(new Font("SansSerif", Font.BOLD, 16));
		userListArea.setForeground(Color.white);
		userListArea.setBackground(Color.black);
		userListArea.setFixedCellWidth(150);
		
		//Initialisierung der ScrollPanes
		scrollList = new JScrollPane(userListArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOut = new JScrollPane(outputArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		try{
			//Initialisierung der Streams und direktes Senden des UserNamen inkl. Trigger
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			say(1, "Verbindung zum Server erfolgreich hergestellt");	
			out.writeObject(Chatserver.TRIGGER_CONNECT+name);
			out.flush();
			
		}catch(IOException e){
			e.printStackTrace();
			say(3, "Verbindung zum Server fehlgeschlagen!");	
		}
	
		//WindowListener, beim Schließen des ChatFrames soll die Methode closeFrame() ausgeführt werden
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing (WindowEvent e){
				 closeFrame();
			}
		});
		
		//Setzen der Parameter für den ChatFrame (Titel, Fenstergröße, Position, Layout)
		this.setTitle("(Online) Nickname: "+name);
		this.setSize(950,700);
		this.setLocationRelativeTo(null); 
		this.setLayout(new GridBagLayout());
		
		//********************************************************************************
		// GridBagConstraints
		//********************************************************************************
		
		//GridBagConstraints (gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady)
		GridBagConstraints con = new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0, 7);

		//Eingabefeld wird in den Frame eingefuegt
		this.add(inputfield, con);
		
		//Ausgabefeld wird in den Frame eingefuegt
		con.gridy = 1;
		con.weightx = 500;
		con.weighty = 500;
		con.fill = GridBagConstraints.BOTH;
		con.ipady = 0;
		this.add(scrollOut, con);
		
		//UserList wird in den Frame eingefuegt
		con.anchor = GridBagConstraints.EAST;
		con.gridx = 2;
		con.gridheight = 2;
		con.weightx = 1;
		con.weighty = 500;
		con.fill = GridBagConstraints.VERTICAL;
		con.ipadx = 0;
		con.ipady = 0;
		this.add(scrollList, con);
		
		//********************************************************************************
		// -- ende
		//
		// Popupmenü festlegen
		//********************************************************************************
		popup = new JPopupMenu();
		
		// "Log Anzeigen" PopupItem
		popup.add(new AbstractAction("Log anzeigen"){
			/** UID für Log Anzeigen(PopupItem) */
			private static final long serialVersionUID = 4371240149939765667L;
			@Override
			public void actionPerformed(ActionEvent e) {
				log.getLogArea().setText(log.getLogText().toString());
				log.setVisible(true);
			}	
		});
		
		// "Log löschen" PopupItem
		popup.add(new AbstractAction("Log löschen"){
			/** UID für Log löschen(PopupItem) */
			private static final long serialVersionUID = 5635501142583928324L;
			@Override
			public void actionPerformed(ActionEvent e) {
				log.clearLog();
			}	
		});
		
		// Seperator
		popup.addSeparator();
		
		// "Hilfe" PopupItem
		popup.add(new AbstractAction("Hilfe"){
			/** UID für Hilfe(PopupItem) */
			private static final long serialVersionUID = 7994849967892467880L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
			}	
		});
		this.add(popup);
		
		//********************************************************************************
		//- - ende
		//
		// Log
		//********************************************************************************
		
		// Log erstellen mit Log-Startzeit
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
		log = new Log(this, sdf.format(new Date()));
		
		//********************************************************************************
		//- - ende
		//********************************************************************************

		//Thread wird initialisiert und mit minimaler Priorität gestartet
		if(thread == null){	
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}
	}
	
	/**
	 * <b><u>void</u> run()</b><p>Thread-Run-Methode zur Bearbeitung der Ausgabefelder im ChatFrame</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @last_update 1.3
	 */
	public void run(){
		
		// String vom Inputstream
		String line;
		
		try{
			// solange Thread nicht unterbrochen ist
			while(!thread.isInterrupted()){
				
				try {
					// String aus dem InputStream wird gelesen
					line = in.readObject().toString();
				} catch (ClassNotFoundException e) {
					line = null;
					System.err.println("Fehler beim Stream-Read: "+e);
				}
				
				// (ausgelöst durch entsprechenden Trigger) wenn ein User den Chat verlässt wird UserListArea aktualisiert
				if(line != null && line.equals(Chatserver.TRIGGER_TIMEOUT)){
					say(2,"Verbindung zum Server verloren (TimeOut)");
				}
				// (ausgelöst durch entsprechenden Trigger) wenn ein User den Chat verlässt wird UserListArea aktualisiert
				else if(line != null && line.startsWith(Chatserver.TRIGGER_REMOVEUSER)){
					userListArea.setModel(removeUser(line));
					userListArea.revalidate();
				}
				// (ausgelöst durch entsprechenden Trigger) wenn ein neuer User in den Chat eintritt wird UserListArea aktualisiert
				else if(line != null && line.startsWith(Chatserver.TRIGGER_NEWUSER)){
					userListArea.setModel(newUser(line));
					userListArea.revalidate();
				}
				// (ausgelöst durch entsprechenden Trigger) wenn ein User sich verbindet wird UserListArea aktualisiert
				else if(line != null && line.startsWith(Chatserver.TRIGGER_CONNECT)){
					userListArea.setModel(userModel(line));
					userListArea.revalidate();
				}
				//eingegangene Nachricht wird im Ausgabefeld sichtbar gemacht
				else if(line != null){	
					// Name und Message in ein Array
					String msg[] = parseMessage(line);
					// Message Style für das StyledDocument
					Style style = doc.addStyle("Der Style", null);
					
					// Systemnachrichten gold färben
					if(msg[0].equals(Chatserver.SYS_MSG+":")){
						StyleConstants.setForeground(style, Color.getHSBColor(215, 65, 85));
					}
					else{
						StyleConstants.setForeground(style, Color.WHITE);
					}
					
					// Style einstellen
					StyleConstants.setAlignment(style, 0);
					StyleConstants.setFontSize(style, 13);
					StyleConstants.setBold(style, Boolean.TRUE);
					doc.insertString(doc.getLength(), msg[0], style);
					
					// Nicht mehr fett für die Nachricht
					StyleConstants.setBold(style, Boolean.FALSE);
					doc.insertString(doc.getLength()," "+msg[1]+"\n\n", style);
					
					// Text ins Log schreiben
					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
					log.getLogText().append("["+sdf.format(new Date())+"] "+msg[0]+" "+msg[1]+"\n");
					
					// Autoscroll
					outputArea.setCaretPosition(outputArea.getDocument().getLength());
					
				}
			}
		}catch(IOException | BadLocationException e){	
			 say(2, "Verbindung zum Server abgebrochen");	
		}
		
	}
	
	/**
	 * <b><u>void</u> closeFrame()</b><p>Methode dienst zum korrekten Schließen des ChatFrames und aller offener Streams und Verbindungen</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * 
	 */
	protected void closeFrame(){
		try{
			//Trigger für Diconnenct inkl. Name wird direkt verschickt
			out.writeObject(Chatserver.TRIGGER_DISCONNECT+name);
			out.flush();
			//Beide Streams und der Serversocket werden geschlossen
			out.close();
			in.close();
			socket.close();		
		}catch(IOException e){
			System.err.println("Fehler beim Schließen:");
			e.printStackTrace();
		}
		
		// Log disposen
		log.dispose();
		
		//Thread wird unterbrochen und auf null gesetzt
		if((thread != null) && thread.isAlive()){
			thread.interrupt();
			thread = null;
		}
		//User ist ausgeloggt, setLoggedIn wird zu false
		parent.setLoggedIn(false);
		
		//Fenster ChatFrame verschwindet
		this.dispose();
		
	}
	
	/**
	 * <b><u>void</u> say(int option, String msg)</b><p>Dient zur Ausgabe allgemeiner Systemnachrichten an alle Clients</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @last_update 1.3
	 * @param option : <br>1 - Connect <br> 2 - Verbindungsabbruch/TimeOut <br> 3 - Keine verbindung hergestellt <br>
	 * 4 - Applet-Stop
	 * @param msg
	 */
	protected void say(int option, String msg){
		// Verhalten bei 1 - Connect
		if(option == 1){
			try {
				// Style auf grün einstellen
				Style style = doc.addStyle("Connect Nachricht", null);
				StyleConstants.setAlignment(style, 0);
				StyleConstants.setFontSize(style, 12);
				StyleConstants.setForeground(style, Color.GREEN);
				StyleConstants.setBold(style, Boolean.TRUE);
				doc.insertString(doc.getLength(), "*** "+msg+" ***\n\n", style);
			} catch (BadLocationException e) {
				System.err.println("BadLocationException / Error while Correct-Connected-Output:");
				e.printStackTrace();
			}
		}
		// Verhalten bei 2 - Verbindungsabbruch/TimeOut oder 4 - Applet-Stop
		else if(option == 2 || option == 4){
			inputfield.setEditable(false);
			inputfield.setBackground(Color.YELLOW);
			inputfield.setText(">> "+msg);
			this.setTitle("(Offline) Nickname: "+name);
			
			// Zusatz, falls Option 4, damit die Connection geclosed wird
			if(option == 4 && !socket.isClosed()){
				//Trigger für Diconnenct inkl. Name wird direkt verschickt
				try {
					out.writeObject(Chatserver.TRIGGER_DISCONNECT+name);
				} catch (IOException e) {
					System.err.println("IOException / Error while sending Disconnect in say()-method:");
					e.printStackTrace();
				}
			}
		}
		// Verhalten bei 3 - Keine hergestellte Verbindung
		else if(option == 3){
			inputfield.setEditable(false);
			inputfield.setBackground(Color.RED);
			inputfield.setText(">> "+msg);
			this.setTitle("(Not Connected) Nickname: "+name);
		}
	}
	
	
	/**
	 * <b><u>String[]</u> parseMessage(String line)</b><p>Filtert den Usernamen und die Nachricht um später fett und normal 
	 * zu schreiben.</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.3
	 * @param line : Vollständige Input-Nachricht
	 * @return <u>String[]</u> array
	 */
	private String[] parseMessage(String line){
		String array[] = line.split(Pattern.quote(Chatserver.TRIGGER_MSG));
				
		return array;
	}
	
	/**
	 * <b><u>DefaultListModel</u> userModel(String line)</b><p>Erstellung der UserList </p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param line
	 * @return <u>DefaultListModel</u>
	 * 
	 */
	private DefaultListModel<String> userModel(String line){
		//übergebener String (z.B. TRIGGER[Anna, Klaus, Peter, ...]) wird so bearbeitet, 
		//dass nur noch die einzelnen Usernamen in ein String-Array gespeichert werden
		String[] array = line.substring(26, line.length()-1).split(Pattern.quote(", "));
		DefaultListModel<String> newModel = (DefaultListModel<String>) userListArea.getModel();
		
		//String-Array der UserNamen wird innerhalb der For-Schleife nach und nach in das DefaultListModel geschrieben
		for(int i = 0; i < array.length; i++){
			newModel.addElement(array[i]);
		}
		
		return newModel;
	}
	
	/**
	 * <b><u>DefaultListModel</u> newUser()</b><p>Methode dient dem Hinzufügen eines neu eingeloggten Usernamens zu UserList</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param line
	 * @return <u>DefaultListModel</u>
	 * 
	 */
	private DefaultListModel<String> newUser(String line){
		
		//Name wird aus dem gesendeten String herausgefiltert
		String userName = line.substring(25);
		
		//Liste wird aus der JList ausgelesen...
		DefaultListModel<String> newModel = (DefaultListModel<String>) userListArea.getModel();
		
		//...und der entsprechende Username wird der Liste hinzugefügt
		newModel.addElement(userName);
		
		return newModel;
	}
	
	/**
	 * <b><u>DefaultListModel</u> removeUser()</b><p>Methode dient zum Entfernen eines Usernamens aus der UserList</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param line
	 * @return <u>DefaultListModel</u>
	 * 
	 */
	private DefaultListModel<String> removeUser(String line){
		
		//Name wird aus dem gesendetetn String herausgefiltert
		String userName = line.substring(25);
		
		//Liste wird aus der JList ausgelesen...
		DefaultListModel<String> newModel = (DefaultListModel<String>) userListArea.getModel();
		
		//..und der entsprechende Username wird aus der Lite entfernt
		newModel.removeElement(userName);
		
		return newModel;
	}
	
	/**
	 * KeyAdapter zur einfachen Realisierung der KeyEvents
	 * @author Patric Vormstein
	 * @class NewKeyAdapter
	 */
	class NewKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			//wenn Enter gedrückt wird soll der nicht leere Text aus dem Eingabefeld ohne führende Leerzeichen 
			//an den OutputStream direkt übergeben werden / Realisierung des Nachrichten-Sendens
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				JTextField o = (JTextField)e.getSource();
				String inp = o.getText();
				if(!inp.trim().equals("")){
					try {
						out.writeObject(inp.trim());
						out.flush();
					} catch (IOException ex) {
						System.err.println("Fehler beim Stream-Write: "+e);
					}
				}
				//Eingabefeld wird wieder geleert
				inputfield.setText("");
			}
			
			if(e.getKeyCode() == KeyEvent.VK_F1){
				userListArea.clearSelection();
			}
		}
		
	}
	
	/**
	 * Erlaubt das Interpretieren von MouseEvents<br>
	 * 
	 * @class NewMouseAdapter
	 * @author Patric Vormstein
	 *
	 */
	class NewMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e){
			// wird die linke Mousetaste innerhalb der OutputArea gedrückt, so soll die Auswahl
			// in der JList wieder aufgehoben werden
			if(e.getSource() == outputArea && e.getButton() == MouseEvent.BUTTON1){
				userListArea.clearSelection();
			}
			// Popup zeigen?
			checkPopop(e);
		}
		public void mousePressed(MouseEvent e){
			// Popup zeigen?
			checkPopop(e);
		}
		public void mouseReleased(MouseEvent e){
			// Popup zeigen?
			checkPopop(e);
		}
		
		/**
		 * <b><u>void</u> checkPopup(MouseEvent e)</b><p></p>
		 *
		 * @author Patric Vormstein
		 * @since 1.4
		 * @param e : MouseEvent, bzw, der Auslöser
		 */
		private void checkPopop(MouseEvent e){
			// Öffnen des Popup-Menüs, wenn Rechtsklick in der Outputarea getätigt wurde
			if(e.getSource() == outputArea && e.isPopupTrigger()){
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}

}
