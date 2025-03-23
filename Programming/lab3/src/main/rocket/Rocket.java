package main.rocket;
import java.util.Arrays;

public class Rocket {
    protected int CurrentState;
    protected int Speed;
    protected String[] MissionLogs;

    public Rocket() {
        this.MissionLogs = new String[3];
    }

    public void setCurrentState(int currentState) {
        this.CurrentState = currentState;
    }

    public int getCurrentState() {
        return CurrentState;
    }

    public void setSpeed(int speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative!");
        } else {
            System.out.println("Gaining speed! ");
            this.Speed = speed;
        }
    }

    public int getSpeed() {
        return Speed;
    }

    public void setMissionLogs(String[] missionLogs) {
        if (missionLogs == null) {
            throw new IllegalArgumentException("Mission logs cannot be null.");
        }
    }

    public String[] getMissionLogs() {
        return MissionLogs;
    }

    public void addMissionLog(String log) {
        for (int i = 0; i < MissionLogs.length; i++) {
            if (MissionLogs[i] == null) {
                MissionLogs[i] = log;
                return;
            }
        }
        System.out.println("Mission log is full. Cannot add: " + log);
    }

    public void displayMissionLogs() {
        System.out.println("Mission Logs: ");
        boolean hasLogs = false;
        for (String log : MissionLogs) {
            if (log != null) {
                System.out.println(log);
                hasLogs = true;
            }
        }
        if (!hasLogs) {
            System.out.println("No mission logs available. ");
        }
    }
}
