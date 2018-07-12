package com.lkintechnology.mBilling.activities.printerintegration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class ImageHandler 
{
//	Bitmap bitmap;
	Context context;
	
	short Filetype;
	int Totalsize;
	int Reserved;
	int Byte_Offset;
	int Size_infoheader;
	public int nHorizontalLen;// As Long 'of bitmap (in pixels)
    public int nVeritcalLen;// As Long 'of bitmap (in pixels)
    public short bitplanes;// As Integer '16-bit Int  Number of bitplanes (should be 1)
    public short bits_per_pixel;//  As Integer '(should be 1, 4, 8, or 24)
    public int compressionType;// As Long
    public int actNoOfBytes;// As Long 'Actual number of bytes in bitmap ,only necessary if compression is used
    public int HzPixelPerMeter;// As Long 'Number of horizontal pixels per meter
    public int VtPixelPerMeter;// As Long 'Number of Vertical pixels per meter
    public int NoOfColorUsed;// As Long ' Number of colors actually used
    public int NoOfImptColor;// As Long 'Number of colors that are really important (helps when reducing the number of colors)

	public ImageHandler(Context context) 
	{
//		this.bitmap = bitmap;
		this.context = context;
	}

	byte[] getMonoChromeImagePacket(Bitmap bitmap, byte image_alignment)
	{
//		Bitmap s = getResizedBitmap(bitmap, 384, 510);
//		Bitmap bwBitmap = ConvertToBlackAndWhite(bitmap);
		BitmapConvertor convertor = new BitmapConvertor(context);
		String status = convertor.convertBitmap(bitmap, "my_monochrome_image");

		
		if (!status.contentEquals("Success")) 
		{
			return null;
		}

		byte[] imageBytes = getImageBytes();
		byte[] imagePacket = null;
		
		if(imageBytes != null)
		{
			imagePacket = getImagePacket(imageBytes, convertor.mDataWidth, convertor.mHeight, image_alignment);
		}
		
		return imagePacket;
	}
	
	private byte[]  getImagePacket(byte[] bytes, int width, int height, byte image_alignment)
	{
		byte mode = (byte)0x64;
		if(height <= 255)
		{
			mode = (byte)0x64;
		}else if(height <= 510)
		{
			mode = (byte)0x65;
		}else if(height <= 756)
		{
			mode = (byte)0x66;
		}else if(height <= 1020)
		{
			mode = (byte)0x67;
		}
		
		 CopyBufToStructBMP(bytes);
		 
		 long actualWidth = width;
		 long offset = Byte_Offset;
		 long actualHeight = height;
		 long totalSize = Totalsize;
		 int reWidth = (int) ((bytes.length - Byte_Offset) / actualHeight);
		 long pad = reWidth * 8 - actualWidth;
		 
		 byte[] dataWithoutHeader = new byte[bytes.length - 62];
		 
		 for (long i = offset; i < bytes.length; i++)
         {
             dataWithoutHeader[(int)(i - 62)] = bytes[(int)i];
         }
		 
        byte[] imagePacket = new byte[dataWithoutHeader.length + 6];
		 
		 imagePacket[0] = 0x1B;//[ESC]
         imagePacket[1] = 0x2A;// *
         imagePacket[2] = mode;// m  
         imagePacket[3] = image_alignment;// align Left align
         imagePacket[4] = (byte) (reWidth);
         imagePacket[5] = (byte) (actualHeight);
         
         int a = 0;
         int b = 0;
         
       byte[][] temp = new byte[(int)actualHeight][(int)reWidth];
         
         for (int i = 0; i < actualHeight; i++)
         {
             boolean flag = true;
             for (int j = 0; j < reWidth; j++)
             {
                 if (j < actualWidth / 8)
                 {
                     temp[i][j] = (byte)(0xFF ^ dataWithoutHeader[dataWithoutHeader.length - (reWidth) * (i + 1) + j]);
                 }
                 else
                 {
                     int c = (int)actualWidth % 8;
                     if (c != 0 && flag)
                     {
                         byte byt = (byte)(0xFF << (8 - c));
                         temp[i][j] = (byte)(byt ^ dataWithoutHeader[dataWithoutHeader.length - (reWidth) * (i + 1) + j]);
                         byt = temp[i][j];
                         flag = false;

                     }
                     else
                     {
                         temp[i][j] = dataWithoutHeader[dataWithoutHeader.length - (reWidth) * (i + 1) + j];
                     }
                 }
                 imagePacket[a + 6] = temp[i][j];
                 a++;

             }
         }
		 
		return imagePacket;
	}
	
	private void CopyBufToStructBMP(byte[] RawBuf) 
	{
		int idx;

        idx =0;

        //2-bytes
        Filetype = (short)(256 * RawBuf[idx+1] + RawBuf[idx]);
        idx = idx + 2;

        //4-bytes
        Totalsize = (int)(256 * (256 * (256 * RawBuf[idx+3] + RawBuf[idx+2]) + RawBuf[idx+1]) + RawBuf[idx]);
        idx = idx + 4;

        //4-bytes
        Reserved = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;

        //4-bytes
        Byte_Offset = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        Size_infoheader = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        nHorizontalLen = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        nVeritcalLen = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]) & 0x000000FF;
        idx = idx + 4;

        //2-bytes
        bitplanes = (short)(256 * RawBuf[idx+1] + RawBuf[idx ]);
        idx = idx + 2;

        //2-bytes
        bits_per_pixel = (short)(256 * RawBuf[idx+1] + RawBuf[idx ]);
        idx = idx + 2;

        //4-bytes
        compressionType = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        actNoOfBytes = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        HzPixelPerMeter = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
        //4-bytes
        VtPixelPerMeter = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;

        //4-bytes
        NoOfColorUsed = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;

        //4-bytes
        NoOfImptColor = (int)(256 * (256 * (256 * RawBuf[idx + 3] + RawBuf[idx + 2]) + RawBuf[idx + 1]) + RawBuf[idx]);
        idx = idx + 4;
	}
	
	private byte[] getImageBytes() 
	{
//		String imagePath = Environment.getExternalStorageDirectory() + "/"
//				+ "my_monochrome_image.bmp";
		try 
		{
//			File file = new File(imagePath);
//			FileInputStream fin = new FileInputStream(file);
			
			FileInputStream fin = context.openFileInput("my_monochrome_image.bmp");

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];

			for (int readNum; (readNum = fin.read(buf)) != -1;) 
			{
				byte[] temp = new byte[readNum];
				for (int j = 0; j < readNum; j++) 
				{
					temp[j] = buf[j];
				}
				bos.write(temp, 0, readNum);
			}

			byte[] bytes = bos.toByteArray();

			fin.close();
			
			return bytes;
		} 
		catch (FileNotFoundException e) 
		{
			return null;
		} 
		catch (IOException e) 
		{
			return null;
		}
	}
	
	private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);

		return resizedBitmap;
	}
	
	private Bitmap ConvertToBlackAndWhite(Bitmap sampleBitmap) 
	{
		ColorMatrix bwMatrix = new ColorMatrix();
		bwMatrix.setSaturation(0);
		final ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(
				bwMatrix);
		Bitmap rBitmap = sampleBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Paint paint = new Paint();
		paint.setColorFilter(colorFilter);
		Canvas myCanvas = new Canvas(rBitmap);
		myCanvas.drawBitmap(rBitmap, 0, 0, paint);
		return rBitmap;
	}
}
