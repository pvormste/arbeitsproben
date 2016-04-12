/**
* File: StartUp.java
* Package: de.semfer.chat
* @since 10.08.2011 - 16:22:37
* @author Patric Vormstein
* @version 1.1
* Description: 
* 	- Log-In Applet
* Last Change:
* 	+ stop() und start() sinnvoll gemacht
**/
package de.semfer.chat;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Class: StartUp.java
 * @author Jennifer Ulges<br>Patric Vormstein
 *
 */
public final class StartUp extends JApplet {
	
	/** UID des Applets */
	private static final long serialVersionUID = -3249404795445560020L;
	
	/**	Chatserver-Socket */
	private Socket socket;
	
	/**	eigentliches ChatFrame */
	private ChatFrame chatFrame;
	
	/**	Textfeld zur Eingabe des Nicknamens */
	private JTextField nickname;
	
	/**	Button zum Einloggen */
	private JButton enter;
	private boolean isLoggedIn = false;

		
	/**
	 * <b><u>void</u> init()</b><p>Methode zur Initialisierung des JApplets StartUp</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * 
	 */
	public void init(){
		try {
			//invoke für das GUI des StartUp
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					initGUI();
				}
			});
		} catch (InvocationTargetException|InterruptedException e) {
			System.err.println("Error while init:");
			e.printStackTrace();
		}
	}
	
	/**
	 * <b><u>void</u> start()</b><p>Start-Methode des JApplets StartUp</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @last_update 1.1
	 * 
	 */
	public void start(){
		chatFrame = null;
	}
	
	/**
	 * <b><u>void</u> stop()</b><p>Stop-Methode des JApplets StartUp</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * @last_update 1.1
	 * 
	 */
	public void stop(){
		if(chatFrame != null){
			chatFrame.say(4, "Verbindung zum Server verloren (Applet-Stop)");
		}
	}
	
	/**
	 * <b><u>void</u> initGUI()</b><p>Methode zur GUI-Erstellung</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * 
	 */
	private void initGUI(){
		//Container für alle Swingkomponenten des StartUps (Initialisierung, Layout)
		Container content = getContentPane();
		content.setLayout(null);
		
		//Initialisierung des Eingabefelds für den Nicknamen (Positon, Größe, KeyListener) und Hinzufügen zum content
		nickname = new JTextField();
		nickname.setBounds(20,40,160,30);
		content.add(nickname);
		//KeyListener: beim Drücken von Enter, wenn textfeld nicht leer buttonClick() ausführen
		nickname.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if((e.getKeyCode() == KeyEvent.VK_ENTER)&&(!nickname.getText().equals(""))){
					buttonClick();
				}
			}

		});
		
		//Initialisierung des Einlogg-Buttons (Position, Größe, ActionListener) und Hinzufügen zum content
		enter = new JButton("Los geht's!");
		enter.setBounds(20, 100, 160, 40);
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Bei Klick auf den Button die buttonClick()-Methode
				buttonClick();
			}
		});
		content.add(enter);
	}
	
	/**
	 * <b><u>void</u> buttonClick()</b><p>Methode die bei Klick des Buttons 'Los geht's' ausgeführt wird</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * 
	 */
	private void buttonClick(){
		//wenn Nickname nicht leer und der User nicht bereits eingeloggt ist wird isLoggedIn auf true gesetzt
		if(!nickname.getText().equals("") && !isLoggedIn){
			isLoggedIn = true;
			
			try {
				//Verbindung zum Chatserver-Socket
				socket = new Socket(this.getCodeBase().getHost(), Chatserver.PORT);
			} catch (IOException e) {
				System.err.println("Error while connecting to socket:");
				e.printStackTrace();
			}
			//invoke für das GUI des ChatFrame, Methode buildChatFrame() wird aufgerufen
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					buildChatFrame();
				}
			});
		}
	}
	
	/**
	 * <b><u>void</u> buildChatFrame()</b><p>Methode zur Erstellung und sichtbar-Machen des ChatFrames</p>
	 *
	 * @author Jennifer Ulges<br>Patric Vormstein
	 * @since 1.0
	 * 
	 */
	private void buildChatFrame(){
		//Initialisierung des ChatFrames mit übergabe aller wichtigen Daten an den Konstruktor
		chatFrame = new ChatFrame(nickname.getText(), socket, this);
		chatFrame.setVisible(true);
	}

	/**
	 * <b><u>void</u> setLoggedIn()</b><p>Setter für isLoggedIn.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param isLoggedIn : isLoggedIn setzen
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
