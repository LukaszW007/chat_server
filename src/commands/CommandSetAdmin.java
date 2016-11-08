package commands;

import server.Server;
import server.User;
import server.User.AccountType;

public class CommandSetAdmin implements Command{

	@Override
	public boolean requireAdmin() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void performAction(User executer,String... args) {
		if(args.length!=1){
			executer.sendMessage(getInfo());
			return;
		}
		if(requireAdmin()){
			if(executer.getAccountType() !=AccountType.ADMIN){
				executer.sendMessage("You are not the admin!");
				
				return;
			}
		}
		
		User otherUser = Server.getUserByName(args[0]);
		
		if(otherUser==null){
			executer.sendMessage("User doesn't exist");
			return;
		}
		otherUser.setAccountType(AccountType.ADMIN);
		otherUser.sendMessage("<b> You are admin now!</b>");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setadmin";
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "Command not exist||use: /setadmin <nick>";
	}

}
