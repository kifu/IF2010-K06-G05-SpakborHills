package org.example;

public enum HouseLayoutType {
    DEFAULT,         // SingleBed + TV
    WITH_STOVE,      // SingleBed + TV + Stove
    WITH_QUEEN,      // SingleBed + TV + QueenBed
    WITH_KING,       // SingleBed + TV + KingBed
    STOVE_QUEEN,     // SingleBed + TV + Stove + QueenBed
    STOVE_KING,      // SingleBed + TV + Stove + KingBed
    QUEEN_KING,      // SingleBed + TV + QueenBed + KingBed
    ALL              // Semua furniture
}