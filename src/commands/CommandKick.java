package commands;

import server.Server;
import server.User;
import server.User.AccountType;

public class CommandKick implements Command {

	@Override
	public boolean requireAdmin() {
		return true;
	}

	@Override
	public void performAction(User executer, String... args) {
		
		if (args.length != 1) {
			executer.sendMessage(getInfo());
			return;
		}
		if (requireAdmin()) {
			if (executer.getAccountType() != AccountType.ADMIN) {
				executer.sendMessage("Musisz byc administratorem, aby wywolac funckje");
				return;
			}
		}

		User otherUser = Server.getUserByName(args[0]);
		

		if (otherUser == null) {
			executer.sendMessage("Taki uzytkownik nie istnieje!");
			return;
		}
		

		otherUser.kick("<b> Zostal wyrzucony z chatu przez " + executer.getFullName()+ ".</b>");
		executer.sendMessage("<b> Wyrzucie " + args[0] + " z czatu.</b>");
	
	}

	@Override
	public String getName() {
		return "kick";
	}

	@Override
	public String getInfo() {
		return "Wpisz /kick nick - aby kogos wyrzucic";
	}

}
