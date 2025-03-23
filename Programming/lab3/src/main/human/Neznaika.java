package main.human;

import main.exceptions.WrongPersontoHelpException;
import main.interfaces.Helpable;
import main.human.records.HelpHistory;
import java.time.LocalDateTime;

public class Neznaika extends Human implements Helpable {

    @Override
    public void helpFriend(Human person) {
        if (person instanceof Ponchik ponchik) {
            ponchik.restoreHealth();
            ponchik.setMood("Thankful");
            System.out.println(name + " helped " + ponchik.getName() + ". Ponchik's health now : " + ponchik.getHealthPoints() + ", mood: " + ponchik.getMood());
            HelpHistory helpHistory = new HelpHistory(this.getName(), person.getName(), LocalDateTime.now());
            System.out.println(helpHistory);
        } else {
            throw new WrongPersontoHelpException("Neznaika, go and help Ponchik! ");
        }
    }
}
