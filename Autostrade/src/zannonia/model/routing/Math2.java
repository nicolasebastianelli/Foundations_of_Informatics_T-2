package zannonia.model.routing;

public final class Math2
{
	public static double round(double value, int decimalPosition)
	{
		int returnCost = (int) Math.round(value * Math.pow(10, decimalPosition));
		return (double)returnCost / Math.pow(10, decimalPosition);
	}
	
	public static float round(float value, int decimalPosition)
	{
		int returnCost = (int) Math.round(value * Math.pow(10, decimalPosition));
		return (float)returnCost / (float)Math.pow(10, decimalPosition);
	}
}
