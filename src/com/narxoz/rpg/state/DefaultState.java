package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class DefaultState implements HeroState{
    private static final DefaultState instance = new DefaultState();
    public static HeroState getInstance(){
        return instance;
    }
    @Override
    public String getName() {
        return "Default State";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        return;
    }

    @Override
    public void onTurnEnd(Hero hero) {
        return;
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
