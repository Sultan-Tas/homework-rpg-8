package com.narxoz.rpg.state;

import java.util.HashMap;
import java.util.Map;

public class StateFactory {
    private static Map<StateType, HeroState> stateRegistry = new HashMap<>();

    static{
        stateRegistry.put(StateType.DEFAULT, new DefaultState());
        stateRegistry.put(StateType.POISONED, new PoisonedState(3));
        stateRegistry.put(StateType.STUNNED, new StunnedState());
    }

    public static HeroState createHeroState(StateType type){
        return stateRegistry.get(type);
    }
    public static HeroState createPoisonedState(int duration){
        return new PoisonedState(duration);
    }
}
