package org.spakbor;

import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private int totalIncome;
    private int totalExpenditure;
    private double averageSeasonIncome;
    private double averageSeasonExpenditure;
    private int totalDaysPlayed;

    private Map<String, NPCData> npcStatus;

    private int cropsHarvested;

    private FishCaughtDetails fishCaught;

    public Statistics() {
        this.totalIncome = 0;
        this.totalExpenditure = 0;
        this.averageSeasonIncome = 0.0;
        this.averageSeasonExpenditure = 0.0;
        this.totalDaysPlayed = 0;
        this.npcStatus = new HashMap<>();
        this.cropsHarvested = 0;
        this.fishCaught = new FishCaughtDetails();
    }

    // Getters and Setters
    public int getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(int totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(int totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public double getAverageSeasonIncome() {
        return averageSeasonIncome;
    }

    public void setAverageSeasonIncome(double averageSeasonIncome) {
        this.averageSeasonIncome = averageSeasonIncome;
    }

    public double getAverageSeasonExpenditure() {
        return averageSeasonExpenditure;
    }

    public void setAverageSeasonExpenditure(double averageSeasonExpenditure) {
        this.averageSeasonExpenditure = averageSeasonExpenditure;
    }

    public int getTotalDaysPlayed() {
        return totalDaysPlayed;
    }

    public void setTotalDaysPlayed(int totalDaysPlayed) {
        this.totalDaysPlayed = totalDaysPlayed;
    }

    public Map<String, NPCData> getNpcStatus() {
        return npcStatus;
    }

    public void updateNpcStatus(String npcName, NPCData npcData) {
        this.npcStatus.put(npcName, npcData);
    }

    public int getCropsHarvested() {
        return cropsHarvested;
    }

    public void setCropsHarvested(int cropsHarvested) {
        this.cropsHarvested = cropsHarvested;
    }

    public FishCaughtDetails getFishCaught() {
        return fishCaught;
    }

    public void setFishCaught(FishCaughtDetails fishCaught) {
        this.fishCaught = fishCaught;
    }


    public static class NPCData {
        private String relationshipStatus; 
        private int chattingFrequency;     
        private int giftingFrequency;      
        private int visitingFrequency;     

        public NPCData(String relationshipStatus, int chattingFrequency, int giftingFrequency, int visitingFrequency) {
            this.relationshipStatus = relationshipStatus;
            this.chattingFrequency = chattingFrequency;
            this.giftingFrequency = giftingFrequency;
            this.visitingFrequency = visitingFrequency;
        }

        public String getRelationshipStatus() {
            return relationshipStatus;
        }

        public void setRelationshipStatus(String relationshipStatus) {
            this.relationshipStatus = relationshipStatus;
        }

        public int getChattingFrequency() {
            return chattingFrequency;
        }

        public void setChattingFrequency(int chattingFrequency) {
            this.chattingFrequency = chattingFrequency;
        }

        public int getGiftingFrequency() {
            return giftingFrequency;
        }

        public void setGiftingFrequency(int giftingFrequency) {
            this.giftingFrequency = giftingFrequency;
        }

        public int getVisitingFrequency() {
            return visitingFrequency;
        }

        public void setVisitingFrequency(int visitingFrequency) {
            this.visitingFrequency = visitingFrequency;
        }

        @Override
        public String toString() {
            return "NPCData{" +
                   "relationshipStatus='" + relationshipStatus + '\'' +
                   ", chattingFrequency=" + chattingFrequency +
                   ", giftingFrequency=" + giftingFrequency +
                   ", visitingFrequency=" + visitingFrequency +
                   '}';
        }
    }
    
    public static class FishCaughtDetails {
        private int common;
        private int regular;
        private int legendary;

        public FishCaughtDetails() {
            this.common = 0;
            this.regular = 0;
            this.legendary = 0;
        }

        public int getCommon() {
            return common;
        }

        public void setCommon(int common) {
            this.common = common;
        }

        public void addCommon(int count) {
            this.common += count;
        }

        public int getRegular() {
            return regular;
        }

        public void setRegular(int regular) {
            this.regular = regular;
        }

        public void addRegular(int count) {
            this.regular += count;
        }

        public int getLegendary() {
            return legendary;
        }

        public void setLegendary(int legendary) {
            this.legendary = legendary;
        }

        public void addLegendary(int count) {
            this.legendary += count;
        }

        public int getTotalFishCaught() {
            return this.common + this.regular + this.legendary;
        }

        @Override
        public String toString() {
            return "FishCaughtDetails{" +
                   "common=" + common +
                   ", regular=" + regular +
                   ", legendary=" + legendary +
                   ", total=" + getTotalFishCaught() +
                   '}';
        }
    }

    // Demo
    public static void main(String[] args) {
        Statistics playerStats = new Statistics();

        // Mengatur beberapa statistik dasar
        playerStats.setTotalIncome(50000);
        playerStats.setTotalExpenditure(15000);
        playerStats.setTotalDaysPlayed(120); // Misal 2 musim (60 hari/musim)
        playerStats.setAverageSeasonIncome(25000); // Dihitung manual atau dari logika game
        playerStats.setAverageSeasonExpenditure(7500); // Dihitung manual atau dari logika game
        playerStats.setCropsHarvested(350);

        // Menambahkan status NPC
        NPCData npcAlexStatus = new NPCData("Sahabat", 50, 10, 20);
        playerStats.updateNpcStatus("Alex", npcAlexStatus);

        NPCData npcBellaStatus = new NPCData("Kenalan", 15, 2, 5);
        playerStats.updateNpcStatus("Bella", npcBellaStatus);

        // Menambahkan ikan yang ditangkap
        playerStats.getFishCaught().addCommon(50);
        playerStats.getFishCaught().addRegular(20);
        playerStats.getFishCaught().addLegendary(2);

        // Menampilkan statistik
        System.out.println("Statistik Pemain:");
        System.out.println("--------------------------");
        System.out.println("Total Pendapatan: " + playerStats.getTotalIncome() + " gold");
        System.out.println("Total Pengeluaran: " + playerStats.getTotalExpenditure() + " gold");
        System.out.println("Rata-rata Pendapatan per Musim: " + playerStats.getAverageSeasonIncome() + " gold");
        System.out.println("Rata-rata Pengeluaran per Musim: " + playerStats.getAverageSeasonExpenditure() + " gold");
        System.out.println("Total Hari Bermain: " + playerStats.getTotalDaysPlayed() + " hari");
        System.out.println("Tanaman Dipanen: " + playerStats.getCropsHarvested());

        System.out.println("\nStatus NPC:");
        for (Map.Entry<String, NPCData> entry : playerStats.getNpcStatus().entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nIkan Ditangkap:");
        System.out.println("- Common: " + playerStats.getFishCaught().getCommon());
        System.out.println("- Regular: " + playerStats.getFishCaught().getRegular());
        System.out.println("- Legendary: " + playerStats.getFishCaught().getLegendary());
        System.out.println("- Total Ikan: " + playerStats.getFishCaught().getTotalFishCaught());
    }
}
