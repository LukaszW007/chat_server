package commands;

import java.util.ArrayList;
import java.util.List;

import server.User;

public class CommandController {

    private User ourUser;
    private List<Command> commands;

    public CommandController(User user) {
        ourUser = user;
        commands = new ArrayList<Command>();
        init();
    }

    private void init() {
        commands.add(new CommandSetAdmin());
        commands.add(new CommandSetMute());
        commands.add(new CommandUnmute());
        commands.add(new CommandKick());
        commands.add(new CommandSend());
        commands.add(new CommandAdminLogin());
        commands.add(new CommandStatus());
        commands.add(new CommandHelp());

    }

    public boolean searchCommand(String msg) {
        if (!msg.startsWith("/"))
            return false;

        for (Command cmd : commands) {
            if (msg.matches("/" + cmd.getName() + " " + ".*") || msg.matches("/" + cmd.getName())) {


                if (msg.length() == cmd.getName().length() + 1) {
                    cmd.performAction(ourUser, new String[]{});

                } else {
                    System.out.println("debug");
                    String[] args = msg.substring(cmd.getName().length() + 2, msg.length()).split(" ");
                    cmd.performAction(ourUser, args);

                }
                return true;
            }
        }
        return false;

    }
}
