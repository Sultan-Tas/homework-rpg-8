package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState{
    @Override
    public String getName() {
        return "Stunned State";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage*1.5);
    }

    @Override
    public void onTurnStart(Hero hero) {
        return;
    }

    @Override
    public void onTurnEnd(Hero hero) {
        hero.setState(StateFactory.createHeroState(StateType.DEFAULT));
    }

    @Override
    public boolean canAct() {
        return false;
    }
}
