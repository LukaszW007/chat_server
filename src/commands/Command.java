package commands;

import server.User;

public interface Command {

    boolean requireAdmin();

    void performAction(User user, String... args);

    String getName();

    String getInfo();

}
