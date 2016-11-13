package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static final String HOST = "localhost";
	public static final int PORT = 4122;
	public static final int maxUser = 20;

	private ServerSocket server;
	private Socket usersListSocket;

	private ExecutorService threadService;

	private static List<User> userList;

	// private Socket socket;

	public static void main(String[] args) {
		Server server = new Server();

	}

	public Server() {
		// new sever created
		InetSocketAddress address = new InetSocketAddress(HOST, PORT);

		try {
			server = new ServerSocket();
			server.bind(address);
		} catch (IOException e) {
			System.out.println("Server initialization failed");
			e.printStackTrace();
		}

		userList = new ArrayList<User>(maxUser+1);
		threadService = Executors.newFixedThreadPool(maxUser+1);
		listenToSockets();
	}

	private void listenToSockets() {

		System.out.println("Listening is started");

				while (true) {
					try {
						User user = new User(server.accept());

						if(getUserList().size()>maxUser){
							user.sendMessage("Limit of users. Try again later.");
							user.disconnect();
							continue;
						}
						getUserList().add(user);
						threadService.execute(user);
						System.out.println("New connection: "+user.getSocket());
						//PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
						//writer.println("Welcome on the chat!");
						//readMessages();

					} catch (IOException e) {
						System.out.println("server not accepted");
						e.printStackTrace();
					}
				}
	}

	public static List<User> getUserList() {
		return userList;
	}

	public static void setUserList(List<User> userList) {
		Server.userList = userList;
	}
	
	public static User getUserByName(String name){
		for(User u: userList){
			if(u.getNickname().equals(name)){
				return u;
			}
		}
		
		return null;
		
	}

	/*
	 * private void readMessages() { Runnable run = new Runnable() {
	 * 
	 * @Override public void run() { try { BufferedReader in = new
	 * BufferedReader(new InputStreamReader(socket.getInputStream())); while
	 * (true) { String line = in.readLine(); if(line != null){
	 * System.out.println("Message from Client: "+line); }
	 * 
	 * }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * } }; threadService.execute(run); }
	 */

}
