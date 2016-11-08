package commands;

import server.Server;
import server.User;
import server.User.AccountType;

public class CommandSetMute implements Command {

	@Override
	public boolean requireAdmin() {
		return true;
	}

	@Override
	public void performAction(User executer, String... args) {
		if (args.length >0) {
			executer.sendMessage(getInfo());
			return;
		}
		if (requireAdmin()) {
			if (executer.getAccountType() != AccountType.ADMIN) {
				executer.sendMessage("You are not the admin!");
				return;
			}
		}
		
		for (String user : args) {
			User otherUser = Server.getUserByName(user);

			if (otherUser == null) {
				executer.sendMessage("User doesn't exist!");
				continue;
			}

			if (executer.getNickname().equals(otherUser.getNickname())) {
				executer.sendMessage("You cannot mute yourself");
				continue;
			}

			if (otherUser.isMuted()) {
				executer.sendMessage("User is already muted");
				continue;
			}
			otherUser.setMute(true);
			otherUser.sendMessage("<b>You are muted by admin</b>");
			executer.sendMessage("<b> Muted " + otherUser.getFullName() + ".</b>");

}
		
//		int found = 0;i ta
//		for (User user : Server.getUserList()) {
//			for (int i = 0; args[i] != null; i++) {
//				if(user.getName() == args[i]&&user.getAccountType()==AccountType.ADMIN){
//					executer.sendMessage("This user is admin. You cannot mute him.");
//				}
//				if (user.getName() == args[i]&& !user.isMuted()) {
//					user.setMute(true);
//					user.sendMessage("You are muted by admin");
//					found++;
//				}
//				if (user.getName() == args[i]&& user.isMuted()) {
//					executer.sendMessage("User is already muted");
//					found++;
//				}
//				if (found == 0) {
//					executer.sendMessage("User doesn't exist");
//				}
//
//			}
//
//		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "mute";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Command not exist||use: /mute <nick1> <nick2>";
	}

}
