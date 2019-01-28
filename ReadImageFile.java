package skin.datamining;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ReadImageFile {
	
	public double [][][] skin = new double[256][256][256];
	public double[][][] nonskin= new double [256][256][256];
	public double[][][] prob= new double [256][256][256];
	
	String mask_path ="F:\\eclipse\\SkinDetection\\mask\\";
	String image_path="F:\\eclipse\\SkinDetection\\image\\ibtd\\";
	
	String path1,path2;
	int width,width2,height,height2;
	public void getData() throws IOException
	{
		
		File file,file2;
		BufferedImage br,br2;
		for(int i=0;i<256;i++)
		{
			for(int j=0;j<256;j++)
			{
				for(int k=0;k<256;k++)
				{
					skin[i][j][k]=0.0;
					nonskin[i][j][k]=0.0;
					prob[i][j][k]=0.0;
				}
			}
		}
		
		for(int i=0;i<555;i++)
		{
			String s =String.format("%04d",i);
			path1 = mask_path+s+".bmp";
			path2=image_path+s+".jpg";
			file= new File(path1);
			file2=new File(path2);
			
			br=ImageIO.read(file);
			br2=ImageIO.read(file2);
			
	        height=br.getHeight();
	        width=br.getWidth();
	        
	        height2=br2.getHeight();
	        width2 = br2.getWidth();
	        
	        for(int w=0;w<width;w++)
	        {
	        	for(int h=0;h<height;h++)
	        	{
	        		Color c= new Color(br.getRGB(w, h));
	        		Color c2 = new Color(br2.getRGB(w,h));
	        		
	        		if(c.getGreen()>=255 && c.getRed()>=255 && c.getBlue()>=255)
	        		{
	        			nonskin[c2.getRed()][c2.getGreen()][c2.getBlue()]++;
	        		}
	        		
	        		else
	        		{
	        			skin[c.getRed()][c.getGreen()][c.getBlue()]++;
	        		}
	        		
	        		
	        	}
	        }
			
			
			
			
		}  
		
		 BufferedWriter write=null;

	        File f=new File("F:\\eclipse\\SkinDetection\\src\\skin\\datamining\\train.txt");
	        write = new BufferedWriter (new FileWriter(f)); 
		
		  for(int i=0;i<256;i++) {
	            for(int j=0;j<256;j++){
	                for(int k=0;k<256;k++){
	                    if(nonskin[i][j][k]!=0.0)
	                         prob[i][j][k]=skin[i][j][k]/nonskin[i][j][k];
	                    
	                    else if(nonskin[i][j][k]==0.0&&skin[i][j][k]==0.0) {
	                    	prob[i][j][k]=0.0;
	                    }
	                    else if(skin[i][j][k]>0.0) {
	                    	
	                    	prob[i][j][k]=0.2;
	                    }
	                    
	                    write.write("["+ i+" "+j+" "+k+"] ="+ prob[i][j][k]+"\r\n");
	                    
	                 //  System.out.println( prob[i][j][k]);

	                }
	            }
	        }
		  
		  
		  String p1 ="F:\\eclipse\\SkinDetection\\src\\skin\\datamining\\1.jpg";
		  BufferedImage bi = ImageIO.read(new File(p1));
		  int wr=bi.getWidth();
		  int hr=bi.getHeight();
		  
		   for (int i = 0; i < wr; i++) {
	            for (int j = 0; j < hr; j++) {
	                Color c = new Color(bi.getRGB(i, j));

	                if(prob[c.getRed()][c.getGreen()][c.getBlue()]>=0.2)
	                {
	                  Color cc = new Color(c.getRed(), c.getGreen(),c.getBlue());
	                 //  bi.setRGB(i,j,cc.getRGB());

	                }
	             
	                else
	                {
	                    Color cc = new Color(255,255,255);
	                    bi.setRGB(i,j,cc.getRGB());

	                }

	            }
	            

	        ImageIO.write(bi, "jpg", new File("F:\\eclipse\\SkinDetection\\src\\skin\\datamining\\nnn.jpg"));

	    }
		
		
	}

}
