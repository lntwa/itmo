package main.human.records;
import java.time.LocalDateTime;

public record HelpHistory(String helperName, String helpedName, LocalDateTime timeOfHelp) {

    @Override
    public String toString() {
        return "HelpHistory {" +
                "helperName = '" + helperName + '\'' +
                ", helpedName = " + helpedName +
                ", timeOfHelp = " + timeOfHelp +
                '}';
    }
}
