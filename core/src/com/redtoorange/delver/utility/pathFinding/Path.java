package com.redtoorange.delver.utility.pathFinding;

import com.badlogic.gdx.utils.Array;
import com.redtoorange.delver.utility.Map.Tile;

public class Path{
    public Array<Tile> tilePath = new Array<Tile>(  );
    public Array<Node> nodePath = new Array<Node>(  );

    public Path(Array<Node> nodePath){
        this.nodePath = nodePath;

        for(Node n : this.nodePath ){
            tilePath.add( n.tile );
        }
    }

    public Tile getNext(){
        Tile t = tilePath.get( 0 );
        tilePath.removeIndex( 0 );
        return t;
    }
}
