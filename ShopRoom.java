import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopRoom here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopRoom extends Room
{
    public ShopRoom(int level)
    {    
        super(level, new Board(shopBuild));
        alert("SHOP", Color.WHITE, getHeight()-200);
        GameData.exportData();
        alert("Game Saved...", Color.GREEN);
    }
}
