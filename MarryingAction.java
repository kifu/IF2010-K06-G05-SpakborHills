public class MarryingAction extends Action {
    private static final int ENERGY_COST = 80;
    private static final int MARRIAGE_HOUR = 22;
    private static final int MARRIAGE_MINUTE = 0;

    private NPC target;
    private Item proposalRingItem; 
    private String failureReason;

    public MarryingAction(Player player, WorldState world, NPC target, Item proposalRingItem) {
        super(player, world);
        this.target = target;
        this.proposalRingItem = proposalRingItem; // Formalitas doang
    }

    @Override
    protected boolean validate() {
        if (target == null) {
            this.failureReason = "Target NPC untuk menikah tidak valid.";
            return false;
        }

        // 
        if (player.getPartner() == null) {
            this.failureReason = "Kamu tidak punya tunangan untuk dinikahi!";
            return false;
        }

        // 
        if (player.getPartner() != this.target) {
            this.failureReason = "Kamu hanya bisa menikahi tunanganmu saat ini, " + player.getPartner().getName() + ", bukan " + this.target.getName() + "!";
            return false;
        }
        
        // Target harus berstatus FIANCE
        if (this.target.getRelationshipStatus() != RelationshipStatus.FIANCE) {
            // Jika partner pemain statusnya bukan FIANCE 
            this.failureReason = this.target.getName() + " bukan tunanganmu!";
            if (this.target.getRelationshipStatus() == RelationshipStatus.SPOUSE) {
                 this.failureReason = "Kamu sudah menikah dengan " + this.target.getName() + "!";
            }
            return false;
        }
        
        // Validasi cincin
        if (proposalRingItem == null) {
            this.failureReason = "Item Proposal Ring tidak terdefinisi dalam game (untuk cek formalitas).";
            return false;
        }
        if (player.getInventory().getItemQuantity(this.proposalRingItem) <= 0 && player.getPartner().getRelationshipStatus() == RelationshipStatus.FIANCE ) {
            this.failureReason = "Kamu tidak memiliki Proposal Ring untuk upacara!";
            return false;
        }


        if (!this.target.isReadyToMarry(world.getCurrentTime())) {
            this.failureReason = "Belum waktunya menikah dengan " + this.target.getName() + ". Tunggu sehari setelah bertunangan.";
            return false;
        }
        
        if (player.getEnergy() < ENERGY_COST) {
            this.failureReason = "Energi tidak cukup untuk upacara pernikahan. Istirahat dulu.";
            return false;
        }
        
        return true;
    }

    @Override
    protected void performAction() {
        System.out.println("Persiapan upacara pernikahan dengan " + target.getName() + " dimulai...");
        System.out.println("Waktu akan diatur ke pukul " + MARRIAGE_HOUR + ":" + String.format("%02d", MARRIAGE_MINUTE) + " untuk upacara.");
    }

    @Override
    protected void applyEffects() {
        world.getCurrentTime().skipTo(MARRIAGE_HOUR, MARRIAGE_MINUTE);
        target.marry(); 
        player.setLocation("House"); 
        player.setEnergy(player.getEnergy() - ENERGY_COST);
    }

    @Override
    protected String getSuccessMessage() {
        return "Selamat! Kamu dan " + target.getName() + " telah resmi menikah! Time skip ke  " +
               MARRIAGE_HOUR + ":" + String.format("%02d", MARRIAGE_MINUTE) +
               ". Player dikembalikan ke " + player.getLocation() + ".";
    }

    @Override
    protected String getFailureMessage() {
        return this.failureReason;
    }
}