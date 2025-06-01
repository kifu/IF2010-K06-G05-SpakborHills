package org.spakbor;

import java.util.List;

public class NPC {
    private String name;
    private int heartPoint;
    private List<Item> lovedItems;
    private List<Item> likedItems;
    private List<Item> hatedItems;
    private RelationshipStatus relationshipStatus;

    private int chattingCount;
    private int giftingCount;
    private int visitingCount;
    private int fianceDate;

    // Constructor
    public NPC(String name, List<Item> lovedItems, List<Item> likedItems, List<Item> hatedItems, RelationshipStatus relationshipStatus) {
        this.name = name;
        this.heartPoint = 0;
        this.lovedItems = lovedItems;
        this.likedItems = likedItems;
        this.hatedItems = hatedItems;
        this.relationshipStatus = RelationshipStatus.SINGLE;
        this.chattingCount = 0;
        this.giftingCount = 0;
        this.visitingCount = 0;
    }

    // Getter & Setter
    public String getName() { 
        return name; 
    }
    public int getHeartPoint() {
        return heartPoint; 
    }
    public int getChattingCount() { 
        return chattingCount; 
    }
    public int getGiftingCount() { 
        return giftingCount; 
    }
    public int getVisitingCount() { 
        return visitingCount; 
    }
    public int getFianceDate(){
        return fianceDate;
    }
    public RelationshipStatus getRelationshipStatus() { 
        return relationshipStatus; 
    }
    public List<Item> getLovedItems() { 
        return lovedItems; 
    }
    public List<Item> getLikedItems() { 
        return likedItems; 
    }
    public List<Item> getHatedItems() { 
        return hatedItems; 
    }


    public void setName(String name) { 
        this.name = name; 
    }
    public void setHeartPoint(int point) { 
        this.heartPoint = Math.max(0, Math.min(150, point));
    }
    public void setChattingCount(int count) { 
        this.chattingCount = count; 
    }
    public void setGiftingCount(int count) { 
        this.giftingCount = count; 
    }
    public void setVisitingCount(int count) { 
        this.visitingCount = count; 
    }
    public void setFianceDate(int fianceDate){
        this.fianceDate = fianceDate;
    }
    public void setRelationshipStatus(RelationshipStatus status) { 
        this.relationshipStatus = status; 
    }

    // Aksi Chat
    public void chat() {
        chattingCount++;
        System.out.println("Ngobrol bareng " + name + "...");
    }

    // Menikah
    public void marry() {
        if (relationshipStatus == RelationshipStatus.FIANCE) {
            relationshipStatus = RelationshipStatus.SPOUSE;
            System.out.println("Kamu dan " + name + " telah resmi menikah!");
        }
    }


    // Menerima Gift
    public void receiveGift(Item item) {
        giftingCount++;
        if (lovedItems.contains(item)) {
            setHeartPoint(getHeartPoint() + 25);
            System.out.println(name + " sangat suka " + item.getName() + "! (+25)");
        } else if (likedItems.contains(item)) {
            setHeartPoint(getHeartPoint() + 20);
            System.out.println(name + " suka " + item.getName() + " (+20)");
        } else if (isSpecialHated(item)) {
            setHeartPoint(getHeartPoint() - 25);
            System.out.println(name + " benci " + item.getName() + " (-25)");
        } else if (hatedItems.contains(item)) {
            setHeartPoint(getHeartPoint() - 25);
            System.out.println(name + " benci " + item.getName() + " (-25)");
        } else {
            System.out.println(name + " menerima " + item.getName() + " dengan datar.");
        }
    }

    // Untuk NPC dengan hatedItems spesial yaitu Mayor Tadi, Perry
    private boolean isSpecialHated(Item item) {
        // untuk Mayor Tadi, seluruh item bukan loved/liked adalah hated
        if (getName().equalsIgnoreCase("Mayor Tadi")) {
            return !(lovedItems.contains(item) || likedItems.contains(item));
        }
        // untuk Perry, seluruh item kategori "Fish" adalah hated
        if (getName().equalsIgnoreCase("Perry")) {
            return item.getCategory().equalsIgnoreCase("Fish");
        }
        return false;
    }

    // Propose
    public boolean acceptProposal() {
        if (heartPoint >= 150 && relationshipStatus == RelationshipStatus.SINGLE) {
            relationshipStatus = RelationshipStatus.FIANCE;
            System.out.println(name + " menerima lamaranmu >_<!");
            return true;
        } else {
            System.out.println(name + " menolak lamaranmu T_T.");
            return false;
        }
    }

    // Relationship Info (opsional, buat display status)
    public RelationshipInfo getRelationshipInfo() {
        return new RelationshipInfo(name, heartPoint, relationshipStatus);
    }
    
    public boolean isReadyToMarry(Time currentTime) {
    return relationshipStatus == RelationshipStatus.FIANCE && currentTime.getTotalMinutes() >= (fianceDate + 1440);
    }

}