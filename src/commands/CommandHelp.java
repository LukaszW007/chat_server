package commands;

import server.User;

/**
 * Created by Wisnia on 2016-11-09.
 */
public class CommandHelp implements Command {
    @Override
    public boolean requireAdmin() {
        return false;
    }

    @Override
    public void performAction(User user, String... args) {
        if (args.length > 0) {
            user.sendMessage(getInfo());
            return;
        }
        if (user.getAccountType() == User.AccountType.ADMIN)
            user.sendMessage("List of the commands:\n1.login - to login as an administrator\n2.setadmin - to set user as an administrator\n3.mute - to mute user\n4.unmute - to unmute user\n5.kick - kick out user from the chat\n6.send - to send private message to user\n7.status - to check your status");
        else
            user.sendMessage("List of the commands:\n1.send - to send private message to user\n2.status - to check your status");

    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getInfo() {
        return "Command not exist||use: /help";
    }
}
