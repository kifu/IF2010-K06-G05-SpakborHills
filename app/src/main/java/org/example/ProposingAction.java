// ProposingAction.java (Modifikasi)
package org.example;
public class ProposingAction extends Action {
    private static final int ENERGY_COST_IF_ACCEPTED = 10;
    private static final int ENERGY_COST_IF_REJECTED_NOT_SINGLE = 20;
    private static final int ENERGY_COST_FOR_VALIDATION = 20;
    private static final int TIME_COST_MINUTES = 60;
    private static final int REQUIRED_HEART_POINTS = 150;

    private NPC target;
    private Item proposalRingItem;
    private String failureReason;
    private boolean wasProposalAccepted;

    public ProposingAction(Player player, WorldState world, NPC target, Item proposalRingItem) {
        super(player, world);
        this.target = target;
        this.proposalRingItem = proposalRingItem;
    }

    @Override
    protected boolean validate() {
        if (player.getPartner() != null) {
            this.failureReason = "Kamu sudah memiliki pasangan (" + player.getPartner().getName() + 
                                 " - " + player.getPartner().getRelationshipStatus() + 
                                 ")! Tidak bisa melamar lagi.";
            return false;
        }

        if (proposalRingItem == null) {
            this.failureReason = "Item Proposal Ring tidak terdefinisi dalam game.";
            return false;
        }
        if (player.getInventory().getItemQuantity(this.proposalRingItem) <= 0) {
            this.failureReason = "Kamu tidak memiliki Proposal Ring! Beli dulu sana.";
            return false;
        }
        if (target.getHeartPoint() < REQUIRED_HEART_POINTS) {
            this.failureReason = target.getName() + " belum cukup dekat untuk dilamar. Tingkatkan heart points dulu!";
            return false;
        }
        if (player.getEnergy() < ENERGY_COST_FOR_VALIDATION) {
            this.failureReason = "Energi tidak cukup untuk melamar. Istirahat dulu.";
            return false;
        }
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println(player.getName() + " mengumpulkan keberanian dan melamar " + target.getName() + "...");
        if (target.getRelationshipStatus() == RelationshipStatus.SINGLE) {
            this.wasProposalAccepted = true;
        } else {
            this.wasProposalAccepted = false;
        }
    }

    @Override
    protected void applyEffects() {
        int dayBefore = world.getCurrentTime().getDay();
        world.getCurrentTime().advanceMinutes(TIME_COST_MINUTES);
        
        if (this.wasProposalAccepted) {
            target.setRelationshipStatus(RelationshipStatus.FIANCE);
            target.setFianceDate(world.getCurrentTime().getTotalMinutes());
            player.setEnergy(player.getEnergy() - ENERGY_COST_IF_ACCEPTED);
            // Proposal Ring bisa dipakai berkali-kali.
            player.setPartner(this.target);
        } else {
            player.setEnergy(player.getEnergy() - ENERGY_COST_IF_REJECTED_NOT_SINGLE);
        }

        if (world.getCurrentTime().getDay() > dayBefore) {
            world.handleNewDay();
        }
    }

    @Override
    protected String getSuccessMessage() {
        if (this.wasProposalAccepted) {
            return target.getName() + " menerima lamaranmu >_<! Kalian sekarang bertunangan! (-" + ENERGY_COST_IF_ACCEPTED + " Energi)";
        } else {
            return target.getName() + " saat ini tidak bisa menerima lamaran karna dia ngga single. Lamaranmu ditolak. (-" + ENERGY_COST_IF_REJECTED_NOT_SINGLE + " Energi)";
        }
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}