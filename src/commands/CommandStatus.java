package commands;

import server.User;

/**
 * Created by Wisnia on 2016-11-09.
 */
public class CommandStatus implements Command {
    @Override
    public boolean requireAdmin() {
        return false;
    }

    @Override
    public void performAction(User user, String... args) {
        if(args.length>0){
            user.sendMessage(getInfo());
            return;
        }
        user.sendMessage(user.getAccountType().toString());
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getInfo() {
        return "Command not exist||use: /status";
    }
}
