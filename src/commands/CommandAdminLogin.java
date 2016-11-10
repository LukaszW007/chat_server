package commands;

import server.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Wisnia on 2016-11-09.
 */
public class CommandAdminLogin implements Command,ActionListener{
    @Override
    public boolean requireAdmin() {
        return false;
    }

    @Override
    public void performAction(User executer, String... args) {
        if(args.length>0){
            executer.sendMessage(getInfo());
            return;
        }
        String pass = JOptionPane.showInputDialog(this,"password");
        if(pass==null)return;
        if(pass.equals("a")){//admiN&Password2016
            executer.setAccountType(User.AccountType.ADMIN);
                executer.sendMessage("<b>You logged in as Administrator</b>");

                return;

        }else{
            executer.sendMessage("<b>Password is incorrect</b>");
            return;
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
