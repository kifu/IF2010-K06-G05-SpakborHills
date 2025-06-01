package org.spakbor;

public class Seeds extends Item{
    // atribut
    private Season seedsSeason;
    private int harvDur;
    // konstruktor
    /** Membuat object Seed Baru
     * syntax: Seeds(StrngCategory, String name, Season seedsSeason, int harvDur, int buyPrice, int sellPrice)
     * @param String Category
     * @param String name
     * @param Season S
     * @param int Dur
     * @param Gold Price
     */
    public Seeds(String name, Season seedsSeason , int harvDur, int buyPrice) {
       super("Seeds", name, buyPrice, buyPrice / 2);
       this.seedsSeason = seedsSeason;
       this.harvDur = harvDur;
    }

    public Seeds getSeeds(){
        return this;
    }

    /**
     * memberikan nilai Harverst Duration dari seed
     * @return int
     */
    public int getHarvestDuration(){
        return this.harvDur;
    }
    /**
     * memberikan nilai Season dari seed
     * @return Season
     */
    public Season getSeason(){
        /* memberika season dari seed */
        return this.seedsSeason;
    }

    /**
     * mengubah nilai Harverst Duration dari seed
     * @param int newDur
     */
    public void setHarvestDuration(int newDur){
        this.harvDur = newDur;
    }
    /**
     * menguban nilai Seeds season dari seed
     * @param Season newSS
     */
    public void setSeedsSeason(Season newSS){
        this.seedsSeason = newSS;
    }

    @Override
    public String toString() {
        return super.toString() + ", Season: " + seedsSeason + ", Days to Harvest: " + harvDur;
    }
}