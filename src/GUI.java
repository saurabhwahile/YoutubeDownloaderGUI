import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

class GUI extends parser
{
	JFrame frame=new JFrame("Youtube Downloader");
	static String DownloadURL;
	static String DownloadNAME;
	static String DownloadFORMAT;
	
	GUI()
	{
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	Button button_Search = new Button("Search");
	TextField textfield_Query = new TextField(50);
	void getQuery()
	{
		JPanel panel=new JPanel();
		JLabel label_EnterSearchTerm = new JLabel("Enter Search Term");
		
		button_Search.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==button_Search)
				{
					try 
					{
						parser.get_vid_res_frm_userquery(textfield_Query.getText());
						ShowResults();
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
				}
				
			}
			
		});
		
		panel.add(label_EnterSearchTerm);
		panel.add(textfield_Query);
		panel.add(button_Search);
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
    }
	
	void ShowResults()
	{
		try 
		{
			parser.parse_results();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		final JButton button_result[]=new JButton[parser.result_list.length];
		JPanel panel=new JPanel();
		
		File file[]=new File[resultlist.count];
		URL url[]=new URL[resultlist.count];
		Downloader X[]=new Downloader[resultlist.count];
		for(int i=0;i<resultlist.count;i++)
        {
			file[i]=new File("\\temp\\"+result_list[i].VideoID+".jpg");
		    try 
		    {
				url[i]=new URL(result_list[i].ThumbnailUrl.toString());
			} 
		    catch (MalformedURLException e) 
		    {
				e.printStackTrace();
			}
		    X[i]=new Downloader(url[i],file[i]);
		    X[i].run();
        }
		
		
		ImageIcon icon[]=new ImageIcon[resultlist.count];
		for(int i=0;i<resultlist.count;i++)
        {icon[i]=new ImageIcon("\\temp\\"+result_list[i].VideoID+".jpg");}
        
		JLabel lab[]=new JLabel[resultlist.count];
		for(int i=0;i<resultlist.count;i++)
        {lab[i]=new JLabel(result_list[i].VideoName.toString(),icon[i],JLabel.LEFT);}
		
		for(int i=0;i<resultlist.count;i++)
        {lab[i].setIcon(icon[i]);}

		for(int i=0;i<resultlist.count;i++)
        {
			panel.add(lab[i]);
			button_result[i] = new JButton("Download");
			button_result[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
			panel.add(button_result[i]);
			button_result[i].addActionListener(new ActionListener() 
			{ 
				  public void actionPerformed(ActionEvent e) 
				  { 
					  for(int i=0;i<resultlist.count;i++)
				       {
				    	   if(button_result[i]==e.getSource())
				    	   {
				    		   try 
				    			{
				    				Downloader download=new Downloader(new URL(parser.result_list[i].VideoUrl.toString()),new File("temp"));
				    	    		DownloadNAME=new String(parser.result_list[i].VideoName.toString());
				    				download.run();
				    				ShowQuality();
				    			} catch (MalformedURLException excep) 
				    			{
				    				excep.printStackTrace();
				    			} 
				    	   }
				       }
				  } 
			} );
        }
        
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
	}

	public void ShowQuality()
	{
		try 
		{
			parser.quality_parser();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		final JButton button_quality[]=new JButton[parser.quality_list.length];
		
		final JFrame frame=new JFrame("Select Quality");
		frame.setLayout(new FlowLayout(FlowLayout.LEFT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel=new JPanel();
		
		JLabel lab[]=new JLabel[qualitylist.count];
		for(int i=0;i<qualitylist.count;i++)
        {
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=hd1080"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/webm;+codecs=\"vp8.0,+vorbis\"")))
			{
				lab[i]=new JLabel("1080p Webm File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=hd1080"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/mp4;+codecs=\"avc1.64001F,+mp4a.40.2\"")))
			{
				lab[i]=new JLabel("1080p MP4 File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=hd720"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/webm;+codecs=\"vp8.0,+vorbis\"")))
			{
				lab[i]=new JLabel("720p Webm File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=hd720"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/mp4;+codecs=\"avc1.64001F,+mp4a.40.2\"")))
			{
				lab[i]=new JLabel("720p MP4 File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=large"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/webm;+codecs=\"vp8.0,+vorbis\"")))
			{
				lab[i]=new JLabel("480p Webm File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=large"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/x-flv")))
			{
				lab[i]=new JLabel("480p FLV File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=medium"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/webm;+codecs=\"vp8.0,+vorbis\"")))
			{
				lab[i]=new JLabel("320p Webm File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=medium"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/x-flv")))
			{
				lab[i]=new JLabel("320p FLV File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=medium"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/mp4;+codecs=\"avc1.42001E,+mp4a.40.2\"")))
			{
				lab[i]=new JLabel("320p MP4 File");
			}
			else
			if((parser.quality_list[i].type.toString().equalsIgnoreCase("quality=small"))&&(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/x-flv")))
			{
				lab[i]=new JLabel("240p FLV File");
			}
			else
			{lab[i]=new JLabel(" "+parser.quality_list[i].type.toString()+" codec="+parser.quality_list[i].codec);}
		}
		
		
		for(int i=0;i<qualitylist.count;i++)
        {
			panel.add(lab[i]);
			button_quality[i] = new JButton("Download");
			button_quality[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
			panel.add(button_quality[i]);
	        button_quality[i].addActionListener(new ActionListener() 
			{ 
				  public void actionPerformed(ActionEvent e) 
				  { 
					  for(int i=0;i<qualitylist.count;i++)
				       {
				    	   if(button_quality[i]==e.getSource())
				    	   {
				    		   frame.setVisible(false);
				    		   
				    		   DownloadURL=new String(parser.quality_list[i].url.toString());
				    		   if(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/webm;+codecs=\"vp8.0,+vorbis\""))
				    		   {DownloadFORMAT=new String(".webm");}
				    		   else
				    		   if(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/mp4;+codecs=\"avc1.64001F,+mp4a.40.2\""))
				        	   {DownloadFORMAT=new String(".mp4");}
				    		   else
				    		   if(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/x-flv"))
				               {DownloadFORMAT=new String(".flv");}
				    		   else
				    		   if(parser.quality_list[i].codec.toString().equalsIgnoreCase("video/mp4;+codecs=\"avc1.42001E,+mp4a.40.2\""))
				               {DownloadFORMAT=new String(".mp4");}
				    		   else
				    		   {DownloadFORMAT=new String(".mp4");}

				    		   @SuppressWarnings("unused")
				    		   ThreadedDownload download=new ThreadedDownload();
				    		   @SuppressWarnings("unused")
				    		   ThreadedDisplayProgress update=new ThreadedDisplayProgress();
				    	   }
				       }
				  } 
			} );
        }
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		frame.add(scrollPane);
		frame.pack();
        frame.setVisible(true);
	}
	
}

class ThreadedDownload extends GUI implements Runnable
{
	public static Downloader X;
	Thread downloadthread;
	ThreadedDownload()
	{
		downloadthread=new Thread(this,"Download Thread");
		downloadthread.start();
	}
	
	@Override
	public void run() 
	{
		try 
		{
			X=new Downloader(new URL(DownloadURL),new File(DownloadNAME+DownloadFORMAT));
			X.run();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
	}
	
}

class ThreadedDisplayProgress extends ThreadedDownload implements Runnable
{
	Thread showprogress;
	ThreadedDisplayProgress()
	{
		showprogress=new Thread(this,"Show Progress Thread");
		showprogress.start();
	}
	@Override
	public void run() 
	{
		JFrame frame = new JFrame("Download Progress");
		JPanel panel = new JPanel();
		JProgressBar pb = new JProgressBar(0, 100);
		pb.setValue(0);
		pb.setStringPainted(true);
		   
		panel.add(pb);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		while(X.isRunning())
		{
			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if(X.isProgressUpdated())
			{pb.setValue(X.getProgressPercent());}
		}
		frame.setVisible(false);
	}
}