import java.io.*;
import java.net.URL;

class parser
{
	public static void get_vid_res_frm_userquery(String queryurl)throws IOException
	{
		
	    String query=new String("http://www.youtube.com/results?search_query=");
	    queryurl=queryurl.replaceAll(" ", "%20");
	    queryurl=query.concat(queryurl);
	    File file=new File("temp");
	    URL url=new URL(queryurl);
	    Downloader X=new Downloader(url,file);
	    X.run();
	}
	
	static void get_video_id()throws IOException
	{
        int length=0;
		
		File file=new File("temp");
		FileReader fread = new FileReader(file);
		while(length<file.length())
        {length++;}
		
	    char readBuffer[]=new char[length];
	    char writeBuffer[]=new char[length];
	    int readBufferctrl=0;
	    int writeBufferctrl=0;
	    
	    fread.read(readBuffer);
	    
	    
	    while(readBufferctrl!=length)
	    {
	    	if(		
	    			(readBuffer[readBufferctrl]=='w')&&
	    			(readBuffer[readBufferctrl+1]=='a')&&
	    			(readBuffer[readBufferctrl+2]=='t')&&
	    			(readBuffer[readBufferctrl+3]=='c')&&
	    			(readBuffer[readBufferctrl+4]=='h')&&
	    			(readBuffer[readBufferctrl+5]=='?')&&
	    			(readBuffer[readBufferctrl+6]=='v')&&
	    			(readBuffer[readBufferctrl+7]=='=')
	    		)
	    	{
	    		readBufferctrl=readBufferctrl+8;
	    		while(true)
	    		{
	    			if(
	    					(readBuffer[readBufferctrl]=='"')||
	    					(readBuffer[readBufferctrl]=='&')
	    				)
	    			{break;}
	    			
	    			writeBuffer[writeBufferctrl]=readBuffer[readBufferctrl];
	    			writeBufferctrl++;
	    			readBufferctrl++;
	    		}
	    		writeBuffer[writeBufferctrl]='\n';
    			writeBufferctrl++;
	    	}
	    	readBufferctrl++;
	    }
	    
	    File file2=new File("temp2");
	    FileWriter fwrite=new FileWriter(file2);
	    fwrite.write(writeBuffer,0,writeBufferctrl);
	    fwrite.close();
	    fread.close();
	    file.delete();
	    file2.renameTo(file);
	}
	

	static resultlist result_list[]=new resultlist[100];
	public static void parse_results()throws IOException
	{
		get_video_id();
        int length=0;
		
		File file=new File("temp");
		FileReader fread = new FileReader(file);
		while(length<file.length())
        {length++;}
		
	    char readBuffer[]=new char[length];
	    int readBufferctrl=0;
	    
	    fread.read(readBuffer);
	    
	    result_list[0]=new resultlist();
	    
	    StringBuffer videoIdList[]=new StringBuffer[100];
	    int videoIdListctrl=0;
	    videoIdList[0]=new StringBuffer();
	    
	    int newVideoId=0;
	    
	    while(readBufferctrl!=length-1)
	    {
	    	newVideoId=0;
	    	while(true)
	    	{
	    		if(readBuffer[readBufferctrl]=='\n')
	    		{
	    			readBufferctrl++;
	    			newVideoId=1;
	    			while(true)
	    			{
	    				if(
	    						(readBuffer[readBufferctrl]=='\n')||
	    						(readBufferctrl==length-1)
	    					)
	    				{
	    					readBufferctrl++;
	    					break;
	    				}
	    				readBufferctrl++;
	    			}
	    			break;
	    		}
	    		videoIdList[videoIdListctrl]=videoIdList[videoIdListctrl].append(readBuffer[readBufferctrl]);
	    		readBufferctrl++;
	    	}
	    	
	    	if(newVideoId==1)
	    	{
	    		videoIdListctrl++;
	    		videoIdList[videoIdListctrl]=new StringBuffer();
	    	}
	    }
	    
	    StringBuilder RawResultList[]=new StringBuilder[100];
	    Downloader X;
	    for(int i=0;i<videoIdListctrl;i++)
	    {
	    	StringBuffer temp=new StringBuffer("http://gdata.youtube.com/feeds/api/videos/");
	    	temp=temp.append(videoIdList[i]);
	    	temp=temp.append("?v=2");
	    	videoIdList[i]=temp;
	    	System.out.println(videoIdList[i]);
	    	
	    	RawResultList[i]=new StringBuilder();
	    	X=new Downloader(new URL(videoIdList[i].toString()),RawResultList[i]);
	    	X.run();
	    	
	    }
	    
	    
	    int ThumbnailFlag=0;
	    
	    for(int i=0;i<videoIdListctrl;i++)
	    {
	    	String temp=new String(RawResultList[i]);
	        int	RawResultctrl=0;
	    	while(temp.length()!=RawResultctrl)
	    	{
	    		if(
	    				(temp.charAt(RawResultctrl)=='<')&&
	    				(temp.charAt(RawResultctrl+1)=='t')&&
	    				(temp.charAt(RawResultctrl+2)=='i')&&
	    				(temp.charAt(RawResultctrl+3)=='t')&&
	    				(temp.charAt(RawResultctrl+4)=='l')&&
	    				(temp.charAt(RawResultctrl+5)=='e')&&
	    				(temp.charAt(RawResultctrl+6)=='>')
	    			)
	    		{
	    			RawResultctrl=RawResultctrl+7;
	    			result_list[i]=new resultlist();
	    			ThumbnailFlag=1;
	    			while(true)
	    			{
	    				
	    				result_list[i].VideoName=result_list[i].VideoName.append(temp.charAt(RawResultctrl));
	    				RawResultctrl++;
	    				if(
	    	    				(temp.charAt(RawResultctrl)=='<')&&
	    	    				(temp.charAt(RawResultctrl+1)=='/')&&
	    	    				(temp.charAt(RawResultctrl+2)=='t')&&
	    	    				(temp.charAt(RawResultctrl+3)=='i')&&
	    	    				(temp.charAt(RawResultctrl+4)=='t')&&
	    	    				(temp.charAt(RawResultctrl+5)=='l')&&
	    	    				(temp.charAt(RawResultctrl+6)=='e')&&
	    	    				(temp.charAt(RawResultctrl+7)=='>')
	    	    			)
	    				{
	    					break;
	    				}
	    			}
	    		}
	    			
	    		if(ThumbnailFlag==1)
		    	{
	    			if(
		    				(temp.charAt(RawResultctrl)=='<')&&
		    				(temp.charAt(RawResultctrl+1)=='m')&&
		    				(temp.charAt(RawResultctrl+2)=='e')&&
		    				(temp.charAt(RawResultctrl+3)=='d')&&
		    				(temp.charAt(RawResultctrl+4)=='i')&&
		    				(temp.charAt(RawResultctrl+5)=='a')&&
		    				(temp.charAt(RawResultctrl+6)==':')&&
		    				(temp.charAt(RawResultctrl+7)=='t')&&
		    				(temp.charAt(RawResultctrl+8)=='h')&&
		    				(temp.charAt(RawResultctrl+9)=='u')&&
		    				(temp.charAt(RawResultctrl+10)=='m')&&
		    				(temp.charAt(RawResultctrl+11)=='b')&&
		    				(temp.charAt(RawResultctrl+12)=='n')&&
		    				(temp.charAt(RawResultctrl+13)=='a')&&
		    				(temp.charAt(RawResultctrl+14)=='i')&&
		    				(temp.charAt(RawResultctrl+15)=='l')&&
		    				(temp.charAt(RawResultctrl+16)==' ')&&
		    				(temp.charAt(RawResultctrl+17)=='u')&&
		    				(temp.charAt(RawResultctrl+18)=='r')&&
		    				(temp.charAt(RawResultctrl+19)=='l')&&
		    				(temp.charAt(RawResultctrl+20)=='=')
		    			)
		    		{
		    			RawResultctrl=RawResultctrl+22;
		    			while(true)
		    			{
		    				
		    				result_list[i].ThumbnailUrl=result_list[i].ThumbnailUrl.append(temp.charAt(RawResultctrl));
		    				RawResultctrl++;
		    				if(
		    	    				(temp.charAt(RawResultctrl)=='\'')&&
		    	    				(temp.charAt(RawResultctrl+1)==' ')&&
		    	    				(temp.charAt(RawResultctrl+2)=='h')&&
		    	    				(temp.charAt(RawResultctrl+3)=='e')&&
		    	    				(temp.charAt(RawResultctrl+4)=='i')&&
		    	    				(temp.charAt(RawResultctrl+5)=='g')&&
		    	    				(temp.charAt(RawResultctrl+6)=='h')&&
		    	    				(temp.charAt(RawResultctrl+7)=='t')
		    	    			)
		    				{
		    					break;
		    				}
		    			}
		    			ThumbnailFlag=0;
		    		}
		    	}
	    		
	    		
	    	    RawResultctrl++;
	    	}
	    	
	    	videoIdList[i].delete(0, 42);
	    	videoIdList[i].delete(11, 15);
	    	result_list[i].VideoUrl=result_list[i].VideoUrl.append("http://www.youtube.com/watch?v="+videoIdList[i]);
	    	
	    	result_list[i].VideoID=i;
	    	
	    	System.out.println(result_list[i].VideoID);
	    	System.out.println(result_list[i].VideoName);
	    	System.out.println(result_list[i].VideoUrl);
	    	System.out.println(result_list[i].ThumbnailUrl);
	    }
	    resultlist.count=videoIdListctrl;
	}

	static qualitylist quality_list[]=new qualitylist[20];
	public static void quality_parser() throws IOException
	{
		int length=0;
	
		File file=new File("temp");
		FileReader fread = new FileReader(file);
		while(length<file.length())
		{length++;}
	
		char readBufferChar[]=new char[length];
		int readBufferctrl=0;
    
		fread.read(readBufferChar);
		String readBuffer=new String(readBufferChar);
		StringBuffer VideoURL=new StringBuffer();
	
		readBufferctrl=readBuffer.indexOf("yt.playerConfig = {\"assets\":");
		while(true)
		{
			if((readBuffer.charAt(readBufferctrl)=='}')&&(readBuffer.charAt(readBufferctrl+1)==';'))
			{break;}
			VideoURL.append(readBuffer.charAt(readBufferctrl));
			readBufferctrl++;
		}
    
		length=VideoURL.length();
   
		for(int k=0;k<length;k++)
		{
			if((VideoURL.charAt(k)=='%')&&(VideoURL.charAt(k+1)=='2')&&(VideoURL.charAt(k+2)=='F'))
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '/');
			}
     
			if((VideoURL.charAt(k)=='%')&&(VideoURL.charAt(k+1)=='3')&&(VideoURL.charAt(k+2)=='F'))
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '?');
			}
     
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='3'&&VideoURL.charAt(k+2)=='D')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '=');
			}	
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='2'&&VideoURL.charAt(k+2)=='5')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '%');
			}
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='2'&&VideoURL.charAt(k+2)=='6')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '&');
			}
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='3'&&VideoURL.charAt(k+2)=='A')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, ':');
			}

			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='3'&&VideoURL.charAt(k+2)=='B')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, ';');
			}
     
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='2'&&VideoURL.charAt(k+2)=='2')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, '"');
			}
     
			if(VideoURL.charAt(k)=='%'&&VideoURL.charAt(k+1)=='2'&&VideoURL.charAt(k+2)=='C')
			{
				for(int z=0;z<3;z++)
				{
					for(int l=k;l<length-1;l++)
					{
						VideoURL.setCharAt(l, VideoURL.charAt(l+1));
					}
					length--;
					VideoURL.setLength(length);
				}
				VideoURL.insert(k, ',');
			}
		}

		int VideoURLctrl=0;
		int id=0;
		int urlFlag=0,typeFlag=0,codecFlag=0,idFlag=1;
		StringBuffer url=new StringBuffer();
		StringBuffer type=new StringBuffer();
		StringBuffer codec=new StringBuffer();
    
		while(VideoURLctrl!=length-8)
		{
    	
			if(idFlag==1)
			{
				if(
						(VideoURL.charAt(VideoURLctrl)=='h')&&
						(VideoURL.charAt(VideoURLctrl+1)=='t')&&
						(VideoURL.charAt(VideoURLctrl+2)=='t')&&
						(VideoURL.charAt(VideoURLctrl+3)=='p')&&
						(VideoURL.charAt(VideoURLctrl+4)==':')&&
						(VideoURL.charAt(VideoURLctrl+5)=='/')&&
						(VideoURL.charAt(VideoURLctrl+6)=='/')
					)
				{
					idFlag=0;
					while(true)
					{
						if(
								(VideoURL.charAt(VideoURLctrl)=='\\')&&
								(VideoURL.charAt(VideoURLctrl+1)=='u')&&
								(VideoURL.charAt(VideoURLctrl+2)=='0')&&
								(VideoURL.charAt(VideoURLctrl+3)=='0')&&
								(VideoURL.charAt(VideoURLctrl+4)=='2')&&
								(VideoURL.charAt(VideoURLctrl+5)=='6')
							)
						{break;}
						url.append(VideoURL.charAt(VideoURLctrl));
						VideoURLctrl++;    
					}
					urlFlag=1;     
				}
			}
     
			if(urlFlag==1)
			{
				if(
						(VideoURL.charAt(VideoURLctrl)=='q')&&
						(VideoURL.charAt(VideoURLctrl+1)=='u')&&
						(VideoURL.charAt(VideoURLctrl+2)=='a')&&
						(VideoURL.charAt(VideoURLctrl+3)=='l')&&
						(VideoURL.charAt(VideoURLctrl+4)=='i')&&
						(VideoURL.charAt(VideoURLctrl+5)=='t')&&
						(VideoURL.charAt(VideoURLctrl+6)=='y')&&
						(VideoURL.charAt(VideoURLctrl+7)=='=')
					)
				{
					urlFlag=0;
					while(true)
					{
						if(
								(VideoURL.charAt(VideoURLctrl)=='\\')&&
								(VideoURL.charAt(VideoURLctrl+1)=='u')&&
								(VideoURL.charAt(VideoURLctrl+2)=='0')&&
								(VideoURL.charAt(VideoURLctrl+3)=='0')&&
								(VideoURL.charAt(VideoURLctrl+4)=='2')&&
								(VideoURL.charAt(VideoURLctrl+5)=='6')
							)
						{break;}
						type.append(VideoURL.charAt(VideoURLctrl));
						VideoURLctrl++;
					}
					typeFlag=1;
				}
			}

			if(typeFlag==1)
			{
				if(
						(VideoURL.charAt(VideoURLctrl)=='t')&&
						(VideoURL.charAt(VideoURLctrl+1)=='y')&&
						(VideoURL.charAt(VideoURLctrl+2)=='p')&&
						(VideoURL.charAt(VideoURLctrl+3)=='e')&&
						(VideoURL.charAt(VideoURLctrl+4)=='=')
					)
				{
					typeFlag=0;
					VideoURLctrl=VideoURLctrl+5;
					while(true)
					{
						if(
								(VideoURL.charAt(VideoURLctrl)=='\\')&&
								(VideoURL.charAt(VideoURLctrl+1)=='u')&&
								(VideoURL.charAt(VideoURLctrl+2)=='0')&&
								(VideoURL.charAt(VideoURLctrl+3)=='0')&&
								(VideoURL.charAt(VideoURLctrl+4)=='2')&&
								(VideoURL.charAt(VideoURLctrl+5)=='6')
							)
						{break;}
						codec.append(VideoURL.charAt(VideoURLctrl));
						VideoURLctrl++;
					}
					codecFlag=1;
				}
			}
     
    
			if(codecFlag==1)
			{
				codecFlag=0;
    	 
				quality_list[id]=new qualitylist(url,type,codec);
				System.out.println(quality_list[id].url);
				url.setLength(0);
				type.setLength(0);
				codec.setLength(0);
    	 
				id++;
				qualitylist.count++;
				idFlag=1;
			}
			VideoURLctrl++;
		}
		file.delete();
	}
	
}