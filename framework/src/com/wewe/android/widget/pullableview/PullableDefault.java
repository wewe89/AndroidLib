package com.wewe.android.widget.pullableview;


public class PullableDefault implements Pullable
{
	@Override
	public boolean canPullDown()
	{
		return true;
	}

	@Override
	public boolean canPullUp()
	{
		return true;
	}

}
