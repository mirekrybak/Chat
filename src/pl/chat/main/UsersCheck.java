package pl.chat.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class UsersCheck {
    private Set<String> users;
    private boolean imported = false;

    public Set<String> getUsers() {
        return users;
    }

    public void downloadNicksFromServer(BufferedReader reader) {
        String nick;
        users = new TreeSet<>();

        //      ===   IMPORT LIST NICKS FROM SERVER   ===
        try {
            while (!(nick = reader.readLine()).equals("endOfList")) {
                users.add(nick);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //      ===   LIST NICKS IMPORTED   ===
        imported = true;
    }

    public boolean checkNickMultiply(String checkNick) {
        if (checkNick.length() > 0) {
            for (String s : users) {
                if (checkNick.equals(s)) {
                    System.out.println(checkNick + "   -   I S T N I E J E   ! !");   //  system message
                    return true;
                }
            }
        }

        System.out.println(checkNick + "   -   U N I K A L N Y   ! ! !");               //  system message
        return false;
    }
}
