package org.spakbor;

// Template Method Action

public abstract class Action {
    protected Player player;
    protected WorldState world;

    public Action(Player player, WorldState world) {
        this.player = player;
        this.world = world;
    }

    public final void execute() {
        if (validate()) {
            performAction();
            applyEffects();
            System.out.println(getSuccessMessage());
        } else {
            System.out.println(getFailureMessage());
        }
    }

    protected abstract boolean validate();
    protected abstract void performAction();
    protected abstract void applyEffects();
    protected abstract String getSuccessMessage();
    protected abstract String getFailureMessage();
}