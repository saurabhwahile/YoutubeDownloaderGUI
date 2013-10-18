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
	    
	    
	    
	    int listctrl=0;
	    int vidurlflag=0,vidnameflag=0,vidthumbflag=0,continueflag=1;
	    result_list[0]=new resultlist();
	    
	    while(readBufferctrl!=length)
	    {
	    	if(continueflag==1)
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
	    			continueflag=0;
	    			result_list[listctrl].VideoUrl=result_list[listctrl].VideoUrl.append("http://www.youtube.com/");
	    			while(true)
	    			{
	    				if(
	    						(readBuffer[readBufferctrl]=='"')||
	    						(readBuffer[readBufferctrl]=='&')
	    					)
	    				{break;}
	    			
	    				result_list[listctrl].VideoUrl=result_list[listctrl].VideoUrl.append(readBuffer[readBufferctrl]);
	    				readBufferctrl++;
	    			}
	    			vidurlflag=1;
	    			System.out.println(result_list[listctrl].VideoUrl);
	    		}
	    	}
	    	
	    	if(vidurlflag==1)
	    	{
	    		if(
	    				(readBuffer[readBufferctrl]=='<')&&
	    				(readBuffer[readBufferctrl+1]=='i')&&
	    				(readBuffer[readBufferctrl+2]=='m')&&
	    				(readBuffer[readBufferctrl+3]=='g')&&
	    				(readBuffer[readBufferctrl+4]==' ')&&
	    				(readBuffer[readBufferctrl+5]=='s')&&
	    				(readBuffer[readBufferctrl+6]=='r')&&
	    				(readBuffer[readBufferctrl+7]=='c')&&
	    				(readBuffer[readBufferctrl+8]=='=')&&
	    				(readBuffer[readBufferctrl+9]=='"')
	    			)
	    		{
	    			vidurlflag=0;
	    			
	    			readBufferctrl=readBufferctrl+12;
	    			
	    			/*if(
	    			      (readBuffer[readBufferctrl]=='n')&&
	    			      (readBuffer[readBufferctrl+1]=='a')&&
	    			      (readBuffer[readBufferctrl+2]=='i')&&
	    			      (readBuffer[readBufferctrl+3]=='l')&&
	    			      (readBuffer[readBufferctrl+4]=='"')&&
	    			      (readBuffer[readBufferctrl+5]==' ')&&
	    			      (readBuffer[readBufferctrl+6]=='s')&&
	    			      (readBuffer[readBufferctrl+7]=='r')&&
	    			      (readBuffer[readBufferctrl+8]=='c')&&
	    			      (readBuffer[readBufferctrl+9]=='=')
	    			    )
	    			{readBufferctrl=readBufferctrl+13;}
	    			if(
	    					(readBuffer[readBufferctrl]=='n')&&
	    					(readBuffer[readBufferctrl+1]=='a')&&
	    					(readBuffer[readBufferctrl+2]=='i')&&
	    					(readBuffer[readBufferctrl+3]=='l')&&
	    					(readBuffer[readBufferctrl+4]=='"')&&
	    					(readBuffer[readBufferctrl+5]==' ')&&
	    					(readBuffer[readBufferctrl+6]=='d')&&
	    					(readBuffer[readBufferctrl+7]=='a')&&
	    					(readBuffer[readBufferctrl+8]=='t')&&
	    					(readBuffer[readBufferctrl+9]=='a')
	    				)
	    			{readBufferctrl=readBufferctrl+20;}*/
	    	   
	    			result_list[listctrl].ThumbnailUrl=result_list[listctrl].ThumbnailUrl.append("http://");
	    			while(true)
	    			{
	    				if(readBuffer[readBufferctrl]=='"')
	    				{break;}
	    				result_list[listctrl].ThumbnailUrl=result_list[listctrl].ThumbnailUrl.append(readBuffer[readBufferctrl]);
	    				readBufferctrl++;
	    			}
		    		vidthumbflag=1;
	    			System.out.println(result_list[listctrl].ThumbnailUrl);
	    		}
	    	}
	    	
	    	if(vidthumbflag==1)
	    	{
	    		if(
	    				(readBuffer[readBufferctrl]=='t')&&
	    				(readBuffer[readBufferctrl+1]=='i')&&
	    				(readBuffer[readBufferctrl+2]=='t')&&
	    				(readBuffer[readBufferctrl+3]=='l')&&
	    				(readBuffer[readBufferctrl+4]=='e')&&
	    				(readBuffer[readBufferctrl+5]=='=')&&
	    				(readBuffer[readBufferctrl+6]=='"')
	    			)
	    		{
	    			vidthumbflag=0;
	    			readBufferctrl=readBufferctrl+7;
	    			if(
	    					(readBuffer[readBufferctrl]=='W')&&
	    					(readBuffer[readBufferctrl+1]=='a')&&
	    					(readBuffer[readBufferctrl+2]=='t')&&
	    					(readBuffer[readBufferctrl+3]=='c')&&
	    					(readBuffer[readBufferctrl+4]=='h')&&
	    					(readBuffer[readBufferctrl+5]==' ')&&
	    					(readBuffer[readBufferctrl+6]=='L')&&
	    					(readBuffer[readBufferctrl+7]=='a')&&
	    					(readBuffer[readBufferctrl+8]=='t')&&
	    					(readBuffer[readBufferctrl+9]=='e')&&
	    					(readBuffer[readBufferctrl+10]=='r')
	    				)
	    			{
	    				while(true)
	    				{
	    					readBufferctrl++;
	    					if(
	    							(readBuffer[readBufferctrl]=='t')&&
	    							(readBuffer[readBufferctrl+1]=='i')&&
	    							(readBuffer[readBufferctrl+2]=='t')&&
	    							(readBuffer[readBufferctrl+3]=='l')&&
	    							(readBuffer[readBufferctrl+4]=='e')&&
	    							(readBuffer[readBufferctrl+5]=='=')&&
	    							(readBuffer[readBufferctrl+6]=='"')
	    						)
	    					{break;}
	    				}
	    			}
	    			readBufferctrl=readBufferctrl+7;
	    			while(true)
	    			{
	    				if(readBuffer[readBufferctrl]=='"')
	    				{break;}
	    				result_list[listctrl].VideoName=result_list[listctrl].VideoName.append(readBuffer[readBufferctrl]);
	    				readBufferctrl++;
	    			}
		    		vidnameflag=1;
	    			System.out.println(result_list[listctrl].VideoName);
	    		}
	    	}
	    	
	    	if(vidnameflag==1)
	    	{
	    		vidnameflag=0;
	    		result_list[listctrl].VideoID=listctrl;
	    		listctrl++;
	    		result_list[listctrl]=new resultlist();
	    		continueflag=1;
	    	}
	    	readBufferctrl++;
	    }
	    resultlist.count--;
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
				url.setLength(0);
				type.setLength(0);
				codec.setLength(0);
    	 
				id++;
				qualitylist.count++;
				idFlag=1;
			}
			VideoURLctrl++;
		}
	}
	
}