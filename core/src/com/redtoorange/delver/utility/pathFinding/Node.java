package com.redtoorange.delver.utility.pathFinding;

import com.redtoorange.delver.Tile;
import com.badlogic.gdx.utils.Array;

public class Node
{
    public Array<Node> neighbors;
    public int treeX, treeY;
    public Tile tile;

    public Node(int treeX, int treeY, Tile tile)
    {
        this.tile = tile;
        this.treeX = treeX;
        this.treeY = treeY;
        neighbors = new Array<Node>();
    }

    //Diagnostic
//    public String toString()
//    {
//        String s = "";
//
//        if(tile != null)
//            s += tile.toString();
//
//        return s;
//    }
}
