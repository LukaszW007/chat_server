package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import commands.CommandController;

public class User extends Thread {

	public static final int maxWarnings = 3;

	private Socket userSocket;
	private String nickname;
	private AccountType accountType;
	private String postfix;
	private int warnings;
	private boolean isMuted;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private CommandController commandController;
	private Date date;

	public User(Socket localSocket) {
		userSocket = localSocket;
		accountType = AccountType.ADMIN;
		init();
	}

	private void init() {
		try {
			printWriter = new PrintWriter(getSocket().getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

		} catch (IOException e) {
			System.out.println("Inicialization stream's error");
			e.printStackTrace();
		}
		commandController = new CommandController(this);
	}

	public boolean canWrite() {
		return !isMuted() && getWarningsCount() < maxWarnings;
	}

	public void setMute(boolean mute) {
		isMuted = mute;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void addWarnings() {
		warnings++;
	}

	public int getWarningsCount() {
		return warnings;
	}

	public String getFullName() {
		if (postfix != null)
			return nickname + " (" + postfix + ")";
		return nickname;
	}

	public void setPrefix(String prefix) {
		this.postfix = prefix;
	}

	public String getPrefix() {
		return postfix;
	}

	public void setAccountType(AccountType type) {
		accountType = type;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public Socket getSocket() {
		return userSocket;
	}

	public void sendMessage(String msg) {
		if (msg == null)
			throw new NullPointerException();
		printWriter.println(msg);
	}

	public void disconnect() {
		if (Server.getUserList().contains(this)) {
			Server.getUserList().remove(this);
			sendMessagetoAll(getFullName() + " is disconnected");
			try {
				getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		return !getSocket().isClosed();
	}

	private String getMessage() {
		try {
			return bufferedReader.readLine();
		} catch (IOException e) {
			disconnect();
			return null;
		}
	}
	
	public void kick(String msg) {
		sendMessage(msg);
		try {
			getSocket().close();
		} catch (IOException e) {
			System.out.println("Error during kick");
		}
}
	
	private String getTime() {
		return new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
	}
	

	@Override
	public void run() {

		if (getNickname() == null) {
			sendMessage("Type in your nickname");
			setNickname(getMessage());
			sendMessage("Your nick is: " + getNickname());
		}
		while (true) {
			if (!isConnected()) {
				break;
			}
			String input = getMessage();
			// command engine is run
			// if( )
			if (input != null) {
				if (commandController.searchCommand(input))
					continue;
				sendMessagetoAll("<b>("+getTime()+") "+getFullName() + ":</b> " + input);

			}
		}
	}

	private void sendMessagetoAll(String msg) {
		for (User user : Server.getUserList()) {
			user.sendMessage(msg);
		}
	}

	public enum AccountType {
		ADMIN(), USER();
	}

}
