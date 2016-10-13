package com.redtoorange.delver.utility.pathFinding;

import com.badlogic.gdx.utils.Array;
import com.redtoorange.delver.utility.Map.Map;
import com.redtoorange.delver.utility.Map.Tile;

import java.util.HashMap;

public class Tree {
	public Node[][] nodes;
	Map map;

	//Convert tiles to nodes
	public Tree( Tile[][] tiles, Map map ) {
		this.map = map;
		initializeNodeArray( tiles );

		//initializeTree
		initializeTree( tiles );

		//buildTree neightbors
		buildTree( tiles );
	}

	private void initializeNodeArray( Tile[][] tiles ) {
		nodes = new Node[tiles.length][];
		for ( int i = 0; i < tiles.length; i++ ) {
			nodes[i] = new Node[tiles[i].length];
		}
	}

	private void initializeTree( Tile[][] tiles ) {
		for ( int x = 0; x < tiles.length; x++ ) {
			for ( int y = 0; y < tiles[x].length; y++ ) {
				Node n = new Node( x, y, tiles[x][y] );
				n.tile = tiles[x][y];
				nodes[x][y] = n;
			}
		}
	}

	//give nodes neighbors
	private void buildTree( Tile[][] tiles ) {
		for ( int x = 0; x < tiles.length; x++ ) {
			for ( int y = 0; y < tiles[x].length; y++ ) {
				Node n = nodes[x][y];	//Focus Node
				Node pN;				//potential neighbor
				//Left
				if( x > 0 ) {
					pN = nodes[x - 1][y];
					if(pN != null)
						n.neighbors.add(pN);
				}
				//Right
				if( x < tiles.length - 1 ) {
					pN = nodes[x + 1][y];
					if(pN != null)
						n.neighbors.add(pN);
				}

				//up
				if( y < tiles[x].length - 1 ) {
					pN = nodes[x][y + 1] ;
					if(pN != null)
						n.neighbors.add(pN);
				}

				//down
				if( y > 0 ) {
					pN = nodes[x][y - 1];
					if(pN != null)
						n.neighbors.add(pN);
				}
			}
		}
	}

	public Tile getTileFromNode( int x, int y ) {
		return nodes[x][y].tile;
	}

	public Node getNodeFromTile( Tile t ) {
		for ( int x = 0; x < nodes.length; x++ ) {
			for ( int y = 0; y < nodes[x].length; y++ ) {
				if( nodes[x][y].tile == t ) return nodes[x][y];
			}
		}
		return null;
	}

	//traverse tree to find path
	public Array<Node> path( Node origin, Node destination ) {
		return null;
	}

	//Manhattan Distance Formula
	private float heuristic( Node a, Node b ) {
		return ( Math.abs( a.treeX - b.treeX ) + Math.abs( a.treeY - b.treeY ) );
	}

	public Path aStarSearch( Tile startingTile, Tile goalTile) {
		Node start = getNodeFromTile( startingTile );
		Node goal = getNodeFromTile( goalTile );

		PriorityQueue frontier = new PriorityQueue( );

		frontier.Enqueue( start, 0 );

		HashMap<Node, Node> cameFrom = new HashMap<Node, Node>( );
		cameFrom.put( start, start );

		HashMap<Node, Float> costSoFar = new HashMap<Node, Float>( );
		costSoFar.put( start, 0.0f );

		while ( frontier.Count( ) > 0 ) {
			Node current = frontier.Dequeue();

			if(current == goal)
				break;

			for(Node next : current.neighbors){
				float newCost = costSoFar.get( current ) + map.cost( current.tile, next.tile );

				if(!costSoFar.containsKey( next ) || newCost < costSoFar.get( next )) {
					costSoFar.put( next, newCost );
					float priority = newCost + heuristic( next, goal );

					frontier.Enqueue( next, priority );
					cameFrom.put( next, current);
				}
			}
		}

		Node n = cameFrom.get( goal );
		Array<Node> path = new Array<Node>(  );
		path.add( goal );

		while(n != start){
			path.add( n );
			n = cameFrom.get( n );
		}

		path.reverse();
		return new Path(path);
	}
}