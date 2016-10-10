package com.redtoorange.delver.utility;

public class ChanceRange{
	float min, max;

	public ChanceRange(float min, float max)
	{
		this.min = min;
		this.max = max;
	}

	public boolean inRange(float c)
	{
		return c > min && c < max;
	}
}
