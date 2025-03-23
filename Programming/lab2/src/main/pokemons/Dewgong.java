package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.physical.AquaJet;
import src.main.moves.special.FrostBreath;
import src.main.moves.status.DoubleTeam;
import src.main.moves.status.Rest;

public class Dewgong extends Seel {
    public Dewgong(String name, int level) {
        super(name, level);
        setType(Type.WATER, Type.ICE);
        setStats(90, 70, 80, 70, 95, 70);
        setMove(new Rest(), new DoubleTeam(), new AquaJet(), new FrostBreath());
    }
}
