package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.physical.AquaJet;
import src.main.moves.status.DoubleTeam;
import src.main.moves.status.Rest;

public class Seel extends Pokemon {
    public Seel(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(65, 45, 55, 45, 70, 45);
        setMove(new Rest(), new DoubleTeam(), new AquaJet());
    }
}
