package com.narxoz.rpg.floor;
import com.narxoz.rpg.combatant.behaviors.CombatBehavior;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.HeroState;

import java.util.List;
public class CombatEngine {
    public static FloorResult executeCombat(
            List<Hero> party,
            List<Monster> enemies,
            CombatBehavior behavior,
            TowerFloor floor
    ){
        int damageTaken = 0;
        int round = 0;

        while(isPartyAlive(party) && areEnemiesAlive(enemies)) {
            round++;
            System.out.println("\nRound " + round + ":");

            //heroes turn
            executeHeroesTurn(party, enemies);
            if(enemies.isEmpty()) {
                System.out.println("‼ All enemies are defeated ‼");
                break;
            }

            //enemy turn
            damageTaken += executeEnemyTurn(party, enemies, behavior);
        }

        return createResult(party, damageTaken, floor);
    }


    private static void executeHeroesTurn(List<Hero> party, List<Monster> enemies){
        for(Hero h : party) {
            if (!h.isAlive()) {
                continue;
            }
            Monster target;
            if(!enemies.isEmpty()) {
                target = enemies.get(0);    //get hero's target
            }
            else{
                throw new IllegalStateException("Enemies array is empty");
            }
            HeroState currState = h.getState(); //variable for hero's state
            currState.onTurnStart(h);           //call onTurnStart() method

            //check if her can act
            if (!currState.canAct()) {
                System.out.println(h.getName() + " can not act...");
                continue;
            }

            int dmg = currState.modifyOutgoingDamage(h.getAttackPower()); //calculate outgoing dmg
            System.out.println("\t►[" + target.getName() + "] received " + dmg + " from " + h.getName()); //log message
            target.takeDamage(dmg);     //monster takes damage
            currState.onTurnEnd(h);     //call onTurnEnd() method

            // check if target is still alive
            if (!target.isAlive()) {
                System.out.println("\t[" + target.getName() + "] is dead.");
                enemies.remove(target);
                if (enemies.isEmpty()) {
                    break;
                } //check if at least one enemy is still alive
            }
        }
    }

    private static int executeEnemyTurn(List<Hero> party, List<Monster> enemies, CombatBehavior behavior){
        int totalDamage = 0; //variable to add all dealt damage in one turn

        for(Monster m : enemies){
            if(!m.isAlive()){ continue;}
            Hero target;
            if(!party.isEmpty()) {
                 target = party.get(0);
            }
            else{
                throw new IllegalStateException("Heroes array is empty");
            }
            //get first alive hero
            for(Hero h : party){
                if(h.isAlive()){
                    target = h;
                    break;
                }
            }
            int incomingDMG = target.getState().modifyIncomingDamage(m.getAttackPower())- target.getDefense(); //calculate incoming damage
            incomingDMG = Math.max(incomingDMG, 0);
            totalDamage += incomingDMG;         //increment taken damage
            target.takeDamage(incomingDMG);     //apply damage to the hero

            //apply monster-specific effects
            behavior.onMonsterAttack(m, target);

            logCombat(target, incomingDMG, m); //log if hero died
        }
        return totalDamage;
    }

    private static boolean isPartyAlive(List<Hero> party){
        for(Hero h : party){
            if(h.isAlive()){
                return true;
            }
        }
        return false;
    }
    private static boolean areEnemiesAlive(List<Monster> enemies){
        for(Monster m : enemies){
            if(m.isAlive()){
                return true;
            }
        }
        return false;
    }

    private static void logCombat(Hero target, int damage, Monster attacker) {
        System.out.println("\t►" + target.getName() + " received " + damage
                + " from [" + attacker.getName() + "] ► ("
                + target.getHp() + "/" + target.getMaxHp() + ")");
        if (!target.isAlive()) {
            System.out.println("\t" + target.getName() + " fainted");
        }
    }

    private static FloorResult createResult(List<Hero> party, int damageTaken, TowerFloor floor) {
        boolean victory = isPartyAlive(party);
        String summary = victory ? "Heroes conquered " + floor.getFloorName() : "Party was defeated on the \"" + floor.getFloorName() + "\"";
        return new FloorResult(victory, damageTaken, summary);
    }
}
