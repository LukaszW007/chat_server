package commands;

import server.Server;
import server.User;
import server.User.AccountType;

public class CommandUnmute implements Command {

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
				executer.sendMessage("Musisz byæ administratorem, aby wywolac funckje");
				return;
			}
		}

		User otherUser = Server.getUserByName(args[0]);
		

		if (otherUser == null) {
			executer.sendMessage("Taki uzytkownik nie istnieje!");
			return;
		}
		
		otherUser.setMute(false);
		otherUser.sendMessage("<b>Zostac odmutowany!</b>");
		executer.sendMessage("<b> Odmutowac " + otherUser.getFullName() + ".</b>");
	}

	@Override
	public String getName() {
		return "unmute";
	}

	@Override
	public String getInfo() {
		return "Wpisz /unmute nick - aby kogos zmutowac";
	}

}