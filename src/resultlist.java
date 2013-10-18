

public class resultlist
{
	int VideoID;
	StringBuffer VideoName;
	StringBuffer VideoUrl;
	StringBuffer ThumbnailUrl;
	static int count;
	resultlist()
	{
		VideoID=0;
		VideoName=new StringBuffer();
		VideoUrl=new StringBuffer();
		ThumbnailUrl=new StringBuffer();
		count++;
	}
}