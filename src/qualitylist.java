public class qualitylist
{
	int id;
	StringBuffer url;
	StringBuffer type;
	StringBuffer codec;
	static int count=0;
	qualitylist(StringBuffer u,StringBuffer t,StringBuffer c)
	{
		id=0;
		url=new StringBuffer(u);
		type=new StringBuffer(t);
		codec=new StringBuffer(c);
	}
}