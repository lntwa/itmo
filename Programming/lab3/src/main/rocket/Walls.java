package main.rocket;

import main.interfaces.Protectable;

public class Walls extends Chamber implements Protectable {
    @Override
    public void absorbImpact(int impactReceived) {
        CurrentState = CurrentState - impactReceived + protectionLevel;
        if (CurrentState < 0) {
            throw new IllegalArgumentException("Pookie dookie has to fix Walls of the Rocket!");
        }

        System.out.println("Walls damage absorbed: " + impactReceived);
        System.out.println("Current protection level is " + CurrentState);
    }
}
