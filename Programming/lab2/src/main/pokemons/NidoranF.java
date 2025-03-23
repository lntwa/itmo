package src.main.pokemons;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
import src.main.moves.special.Thunderbolt;
import src.main.moves.status.TailWhip;

public class NidoranF extends Pokemon {
    public NidoranF(String name, int level) {
        super(name, level);
        setType(Type.POISON);
        setStats(55, 47, 52, 40, 40, 41);
        setMove(new TailWhip(), new Thunderbolt());
    }
}
