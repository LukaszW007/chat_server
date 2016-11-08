package commands;

import server.Server;
import server.User;
import server.User.AccountType;

public class CommandSend implements Command{

	@Override
	public boolean requireAdmin() {
		return false;
	}

	@Override
	public void performAction(User executer, String... args) {
	 
	
		if (requireAdmin()) {
			if (executer.getAccountType() != AccountType.ADMIN) {
				executer.sendMessage("Musisz byc administratorem, aby wywolac to funckje");
				return;
			}
		}

		User otherUser = Server.getUserByName(args[0]);
		

		if (otherUser == null) {
			executer.sendMessage("Taki uzytkownik nie istnieje!");
			return;
		}


		
		StringBuilder builder = new StringBuilder(); 
		for(int i = 1; i < args.length; i++){ 
			builder.append(args[i]);
			builder.append(" ");
		}
		 otherUser.sendMessage(executer.getFullName() + " <b style='color:red;'> > </b> " + builder.toString());
		 executer.sendMessage(executer.getFullName() + " <b style='color:red;'> > </b> " + builder.toString());
		// otherUser.sendMessage("Aby odpowiedzi, uyj /send " + executer.getNickname() + " 'tresc wiadomosci'");
		
	}

	@Override
	public String getName() {
		return "send";
	}

	@Override
	public String getInfo() {
		return "Wpisz w czat /send nick 'a tutaj tresc wiadomosci'";
	}

}