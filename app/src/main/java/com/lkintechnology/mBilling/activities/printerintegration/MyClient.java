package com.lkintechnology.mBilling.activities.printerintegration;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class MyClient extends AsyncTask<Void, Void, Socket> {

    String response = "";
    public static Socket socket = null;
    public static final int BUFFER_SIZE = 2048;

    private PrintWriter out = null;
    private BufferedReader in = null;
    private String dstAddress = null;
    private int dstPort = 9100;
    private Context context;
    public static boolean wifiConnection=false;
    public static boolean connection=true;


    /**
     * Constructor with Host, Port and MAC Address
     */
    public MyClient(String text, Context context) {
        this.dstAddress = text;
        this.dstPort = 9100;
        this.context = context;
    }


    public void configSocket(String host) {
        this.dstAddress = host;
        this.dstPort = 9100;

    }
    

    @Override
    protected Socket doInBackground(Void... arg0) {

        String message = "";
        int charsRead = 0;
        char[] buffer = new char[BUFFER_SIZE];

        try {
            this.socket = new Socket(dstAddress, dstPort);
            Log.i("SocketConnection",this.socket+"");

            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            wifiConnection = true;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            wifiConnection = false;
            connection = false;
            e.printStackTrace();
            this.response = "UnknownHostException: " + e.toString();
            Log.e("Exception", "UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            wifiConnection = false;
            connection = false;
            e.printStackTrace();
            this.response = "IOException: " + e.toString();
            Log.e("Exception", "IOException: " + e.getMessage());
        } finally {
            if (socket != null) {
               /* try {
                    //socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    this.response = "Closing Socket: " + e.toString();
                    Log.e("Exception", "Closing Socket: " + e.getMessage());
                }*/
            }
        }
        return this.socket;
    }



    @Override
    protected void onPostExecute(Socket result) {
        try{
        if (result!=null) 
        {
        	//((WiFiActivity)context).onSuccess(this.response,this.socket,dstAddress,dstPort,out);
        } 
        else 
        {
            //((WiFiActivity)context).onError(this.response);
        }

    }
        catch (Exception ec){
          //  ((WiFiActivity)context).onError(this.response);
        }}

    public void sendDataOnSocket(String message,PrintWriter out) {
        if (message != null) {
            //Log.i("SocketOutput", out+"");
           // new MyClient(text,context).execute();
            out.write(message);
            out.flush();
        }
    }

    public void sendBytesOnSocket(byte[] btPkt, int numBytes,Socket socket) {
        try {
            socket.getOutputStream().write(btPkt, 0, numBytes);
        } catch (IOException e) {
           // showAlert("error in outputstream " + e.toString());
           // ((WiFiActivity)context).onError(this.response);
        }
    }
    
    public int disConnectWithServer()
	 {
	     int retVal = 0;
		 if (socket != null)
		 {
	            if (socket.isConnected())
	            {
	                try {
	                        socket.close();
	                        while(true)
	                        {
	                        	if(socket.isClosed() == true)
	                        	{
	                        		retVal = 1;
	                        		return retVal;
	                        	}
	                        }


	                } catch (IOException e) {
	                //	((WiFiActivity)context).onError(this.response);
	                }
	            }
	        }
		return retVal;
	    }

    public CardReader getCardReader(IAemCardScanner readerImpl){
        if (this.socket == null)
            return null;

        return new CardReader(this.socket, readerImpl);
    }
    public AEMPrinter getAemPrinter()
    {
        if (socket == null)
            return null;

        return new AEMPrinter(socket);
    }
    private void showAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int whichButton) {
                dialog.cancel();
                return ;
            }
        });
        alertDialog.create();
        alertDialog.show();
    }
}
