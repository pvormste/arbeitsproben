/**
* File: Log.java
* Package: de.semfer.chat
* @since 14.08.2011 - 23:19:56
* @author Patric Vormstein
* @version 1.0
* Description:
* 	Zeigt das Log seit Login an
* Last Change:
* 	
**/
package de.semfer.chat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Class: Log.java
 * @author Patric Vormstein
 *
 */
public class Log extends JFrame implements ActionListener {

	/** UID des Log */
	private static final long serialVersionUID = -6262352217173115920L;
	
	/** Der Owner dieses Logs */
	private ChatFrame owner;
	
	/** Startzeit des Logs */
	private String logStart;
	
	/** Der gesammelte LogText */
	private StringBuilder logText;
	
	/** JPanel */
	private JPanel panel; 
	
	/** Der Log-Ausgabebereich */
	private JTextArea logArea;
	
	/** ScrollPane für logArea */
	private JScrollPane scroll;
	
	/** Der Okay-Button */
	private JButton okay;
	
	/** Der Clear-Button */
	private JButton clear;

	/**
	 * <b>Log(ChatFrame owner, String logStart)</b><p>Konstruktor.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param owner : Der BesitzerFrame dieses Logs
	 * @param logStart : Die Startzeit des Logs
	 */
	public Log(ChatFrame owner, String logStart){
		// Grundgerüst
		this.owner = owner;
		this.logStart = logStart;
		this.setSize(600, 500);
		this.setTitle("Log (Beginn: "+logStart+")");
		this.setLayout(null);
		this.setUndecorated(true);

		// Das darzustellende Panel mit dem Rahmen
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Dein Log (Beginn: "+logStart+")"));
		panel.setBounds(0, 0, 600, 500);
		panel.setBackground(Color.getHSBColor(0.75f, 0, 0.85f));
		panel.setLayout(null);
		this.add(panel);
		
		// Der Logtext
		logText = new StringBuilder();
		
		// Die Logarea zum späteren Rauskopieren des Logs
		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		logArea.setBackground(Color.WHITE);
		
		// ScrollPane zum herumscrollen
		scroll = new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 30, 580, 400);
		panel.add(scroll);
		
		// Der Okay-Button zum schließen es Fenster
		okay = new JButton("OK");
		okay.setBounds(40, 450, 100, 30);
		okay.addActionListener(this);
		panel.add(okay);
		
		// Der Clear-Button zul löschen des Logs
		clear = new JButton("Clear");
		clear.setBounds(460, 450, 100, 30);
		clear.addActionListener(this);
		panel.add(clear);
		
		// Mittig darstellen
		this.setLocationRelativeTo(null);
		
	}
	
	/** ActionListener wenn actionPerformed */
	public void actionPerformed(ActionEvent e){
		// Unsichtbar machen
		if(e.getSource() == okay){
			this.setVisible(false);
		}
		// Clearen
		if(e.getSource() == clear){
			clearLog();
		}
	}
	
	/**
	 * <b><u>void</u> clearLog()</b><p>Löscht den Inhalt des Logs und setzt das Beginn-Datum auf die aktuelle Uhrzeit.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 */
	protected void clearLog(){
		logText.delete(0, logText.length());
		logArea.setText(logText.toString());
		logArea.revalidate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
		logStart = sdf.format(new Date());
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Dein Log (Beginn: "+logStart+")"));
		
		this.setTitle("Dein Log (Beginn: "+logStart+")");
		
		owner.say(1, "Log-Clear erfolgreich durchgeführt");
	}

	/**
	 * <b><u>StringBuilder</u> getLogText()</b><p>Getter für logText.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>StringBuilder</u> logText
	 */
	public StringBuilder getLogText() {
		return logText;
	}

	/**
	 * <b><u>void</u> setLogText(StringBuilder logText)</b><p>Setter für logText.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @param logText : logText setzen
	 */
	public void setLogText(StringBuilder logText) {
		this.logText = logText;
	}

	/**
	 * <b><u>JTextArea</u> getLogArea()</b><p>Getter für logArea.</p>
	 *
	 * @author Patric Vormstein
	 * @since 1.0
	 * @return <u>JTextArea</u> logArea
	 */
	public JTextArea getLogArea() {
		return logArea;
	}
	
	

}
