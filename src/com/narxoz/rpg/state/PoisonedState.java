package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState{
    private int turnsLeft = 2;
    @Override
    public String getName() {
        return "Poisoned State";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower*0.9);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage*1.2);
    }

    @Override
    public void onTurnStart(Hero hero) {
        hero.takeDamage(5);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsLeft--;
        if (turnsLeft == 0) {
            hero.setState(DefaultState.getInstance());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}
