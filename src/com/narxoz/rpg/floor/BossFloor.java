package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.DefaultState;
import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.StunnedState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossFloor extends TowerFloor {
    private List<Monster> enemies;
    Random rand = new Random();

    @Override
    protected void setup(List<Hero> party) {
        enemies = new ArrayList<>();
        enemies.add(new Monster("Monster King", 400, 30));
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;
        int round = 0;
        boolean enemyIsAlive = true;
        boolean partyIsAlive = false;
        for (Hero h : party) {
            if(h.isAlive()){
                partyIsAlive = true;
                break;
            }
        }
        while(partyIsAlive & enemyIsAlive){
            round++;
            System.out.println("\nRound " + round + ":");
            for(Hero h : party){
                if(h.isAlive()){
                    Monster target = enemies.get(0);    //get hero's target
                    HeroState currState = h.getState(); //variable for hero's state
                    currState.onTurnStart(h);           //call onTurnStart() method

                    //check if her can act
                    if(!currState.canAct()){
                        System.out.println(h.getName() + " can not act...");
                        continue;
                    }

                    int dmg = currState.modifyOutgoingDamage(h.getAttackPower()); //calculate outgoing dmg
                    System.out.println("\t►[" + target.getName() + "] received " + dmg + " from " + h.getName()); //log message
                    target.takeDamage(dmg);     //monster takes damage
                    currState.onTurnEnd(h);     //call onTurnEnd() method
                    if(!target.isAlive()){
                        System.out.println("\t[" + target.getName() + "] is dead.");
                        enemies.remove(target);
                    } // check if target is still alive
                }
                //check if enemy is still alive
                enemyIsAlive = false;
                for(Monster m : enemies){
                    if(m.isAlive()){
                        enemyIsAlive = true;
                        break;
                    }
                }
            }
            for(Monster m : enemies){
                if(m.isAlive()){
                    Hero target = party.get(0);
                    //get first alive hero
                    for(Hero h : party){
                        if(h.isAlive()){
                            target = h;
                            break;
                        }
                    }
                    int incomingDMG = target.getState().modifyIncomingDamage(m.getAttackPower())- target.getDefense(); //calculate incoming damage
                    damageTaken += incomingDMG;         //increment taken damage
                    target.takeDamage(incomingDMG);     //apply damage to the hero

                    //roll for a chance to stun a character
                    if(rand.nextDouble() < 0.2){
                        target.setState(new StunnedState());
                        System.out.println("\t‼ ►" + target.getName() + " was stunned!");
                    }
                    System.out.println("\t►" + target.getName() + " received " + incomingDMG + " from [" + m.getName() + "]"); //log message
                    if(!target.isAlive()){
                        System.out.println("\t" + target.getName() + " fainted");
                    } //log if hero died
                }
                //check if party is still alive
                partyIsAlive = false;
                for(Hero h : party){
                    if(h.isAlive()){
                        partyIsAlive = true;
                        break;
                    }
                }
            }
        }

        if(partyIsAlive){
            //return success result
            return new FloorResult(true, damageTaken, "Heroes conquered " + getFloorName());
        }
        else{
            //return failure result
            return new FloorResult(false, damageTaken, "The final stop was " + getFloorName());
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        for(Hero h : party){
            h.setState(DefaultState.getInstance());
        }
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Loot:");
        System.out.println("You received 160g");
        System.out.println("You got [Magical Jewels]");
    }

    @Override
    protected String getFloorName() {
        return "Boss Room floor";
    }
}
