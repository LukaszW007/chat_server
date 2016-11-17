package commands;

import server.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;


/**
 * Created by Wisnia on 2016-11-09.
 */
public class CommandAdminLogin implements Command, ActionListener {
    private ExecutorService threadServiceCommands;

    @Override
    public boolean requireAdmin() {
        return false;
    }

    @Override
    public void performAction(User executor, String... args) {
        if (executor.getAccountType() == User.AccountType.ADMIN) {
            executor.sendMessage("<b> You are already administrator! </b>");
            return;
        }
        if (args.length > 1) {
            executor.sendMessage(getInfo());
            return;
        }
        if (args.length == 0) {
            executor.sendMessage("#adminlogindialoginput");
            return;
        }

        if (args.length == 1) {
            String pass = args[0];
            if (pass == null) return;
            if (pass.equals("admiN&Password2016")) {  //admiN&Password2016
                executor.setAccountType(User.AccountType.ADMIN);
                executor.sendMessage("<b>You logged in as Administrator</b>");

                return;

            } else {
                executor.sendMessage("<b>Password is incorrect</b>");
                return;
            }
        }


    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getInfo() {
        return "Command not exist||use: /login";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
