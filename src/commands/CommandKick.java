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
				executer.sendMessage("You are not the admin!");
				return;
			}
		}

		User otherUser = Server.getUserByName(args[0]);
		

		if (otherUser == null) {
			executer.sendMessage("User doesn't exist");
			return;
		}
		

		otherUser.kick("<b> Kicked out by " + executer.getFullName()+ ".</b>");
		executer.sendMessage("<b> You kicked out " + args[0] + " from the chat</b>");
	
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
