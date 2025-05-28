package main;
public class Tiles{
    private Tiletype type;
    private boolean isWatered;
    private Seeds plantedItem;
    private int daysGrown;

    public enum Tiletype{
        Soil, Tilled, Planted
    }
    public Tiles(){
        this.type = Soil;
        this.isWatered = True;
        this.plantedItem = null;
        this.daysGrown = 0;
    }
    public Tiles(TileType type, boolean isWatered, Seeds plantedItem, int daysGrown){
        this.type = type;
        this.isWatered = isWatered;
        this.plantedItem = plantedItem;
        this.daysGrown = daysGrown;
    }
    /**
     * mengembalikan tipe tile yang dipijak
     * @return Tiletype 
     */
    public Tiletype getType(){
        return this.type;
    }
    /**
     * memengubah tipe tile pada posisi player yang dipijak 
     * @param Tiletype
     */
    public void setType(Tiletype newtype){
        this.type = newtype;
    }
    /**
     * memberikan nilai boolean pada kondisi isWatered
     * @return boolean 
     */
    public boolean isWatered(){
        return this.isWatered;
    }
    /**
     * mengubah kondisi isWatered tanah
     * @param boolean 
     */
    public void setIsWatered(boolean status){
        this.isWatered = status;
    }
    /**
     * memengembalikan item yang ditanam pada tiled lad
     * @return Tiletype 
     */
    public Tiletype getPlanted(){
        return this.plantedItem;
    }
    /**
     * menunjukan progress harian dari tile
     * @return int 
     */
    public int daysGrown(){
        return this.daysGrown;
    }

    public void incrementDays(){
        this.daysGrown++;
    }
    
    public boolean isHarvestable(){
        if (this.daysGrown == this.plantedItem.getHarvestDuration()){
            this.daysGrown = 0;
            return True;
        }
    
    }

}