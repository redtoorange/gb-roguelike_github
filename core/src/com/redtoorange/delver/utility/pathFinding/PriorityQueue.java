package com.redtoorange.delver.utility.pathFinding;

import com.badlogic.gdx.utils.Array;

public class PriorityQueue {

	private Array<Node> elementsNode = new Array<Node>(  );
	private Array<Float> elementsPriority = new Array<Float>(  );

	public int Count(){
		return elementsNode.size;
	}

	public void Enqueue(Node n, float priority){
		elementsPriority.add( priority );
		elementsNode.add( n );
	}

	public Node Dequeue()
	{
		int bestIndex = 0;

		for ( int i = 0; i < elementsPriority.size; i++) {
			if ( elementsPriority.get( i ) < elementsPriority.get( bestIndex)) {
				bestIndex = i;
			}
		}

		Node bestItem = elementsNode.get( bestIndex);

		elementsPriority.removeIndex( bestIndex );
		elementsNode.removeIndex( bestIndex );

		return bestItem;
	}
}
