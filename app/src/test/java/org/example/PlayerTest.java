package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("Rama", "Male", "Spakbor Farm");
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals("Rama", player.getName());
        assertEquals("Male", player.getGender());
        assertEquals(Player.MAX_ENERGY, player.getEnergy());
        assertEquals("Spakbor Farm", player.getFarmName());
        assertNull(player.getPartner());
        assertEquals(500, player.getGold());
        assertEquals("Farm", player.getLocation());
    }

    @Test
    public void testEnergyLimits() {
        player.setEnergy(150);
        assertEquals(Player.MAX_ENERGY, player.getEnergy());

        player.setEnergy(-50);
        assertEquals(Player.MIN_ENERGY, player.getEnergy());

        player.setEnergy(50);
        assertEquals(50, player.getEnergy());
    }

    @Test
    public void testGoldOperations() {
        player.addGold(300);
        assertEquals(800, player.getGold());

        player.subtractGold(200);
        assertEquals(600, player.getGold());

        player.subtractGold(1000); // should not go negative
        assertEquals(600, player.getGold());
    }

    @Test
    public void testLocationOperations() {
        player.setLocation("Town");
        assertEquals("Town", player.getLocation());

        Location loc = new Location("Lake");
        player.setLocation(loc);
        assertEquals("Lake", player.getLocation());
    }

    @Test
    public void testPartnerAssignment() {
        List<Item> dummyItems = List.of(
        new DummyItem("Resource", "Firewood", 20, 10),
        new DummyItem("Resource", "Coal", 15, 8),
        new DummyItem("Crop", "Potato", 50, 25),
        new DummyItem("Crop", "Wheat", 30, 15),
        new DummyItem("Crop", "Hot Pepper", 40, 20)
    );
    NPC caroline = NPCFactory.createCaroline(dummyItems);
    player.setPartner(caroline);
    assertEquals("Caroline", player.getPartner().getName());

    }


    @Test
    public void testHasItemInInventory() {
        Item item = new DummyItem("Food", "Apple", 50, 25);
        player.getInventory().addItem(item, 1);

        assertTrue(player.hasItemInInventory("Apple"));
        assertFalse(player.hasItemInInventory("Banana"));
    }

    @Test
    public void testToStringContent() {
        String desc = player.toString();
        assertTrue(desc.contains("Player: Rama"));
        assertTrue(desc.contains("Gold: 500g"));
        assertTrue(desc.contains("Energy: 100"));
        assertTrue(desc.contains("Belum Punya Pasangan"));
    }
}