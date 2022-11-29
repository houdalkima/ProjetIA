import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import lejos.hardware.Button;

public class CameraProf {
	public static void main(String args[]) 
	  {
	    try 
	    {
	      int port = 8888;
	      InetAddress serveur = InetAddress.getByName("192.168.1.255");
	      DatagramSocket dsocket = new DatagramSocket(port);

	      byte[] buffer = new byte[2048];

	      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	      while (true) 
	      {
	        dsocket.receive(packet);

	        String msg = new String(buffer, 0, packet.getLength());

	        String[] palets = msg.split("\n");
	        
	        for (int i = 0; i < palets.length; i++) 
	        {
	        	String[] coord = palets[i].split(";");
	        	int x = Integer.parseInt(coord[1]);
	        	int y = Integer.parseInt(coord[2]);
	        
	        	System.out.println(Integer.toString(x) + " / " + Integer.toString(x) );
	        }
	        packet.setLength(buffer.length);
	      }
	     
	    } 
	    catch (Exception e) 
	    {
	      System.err.println(e);
	    }
	  }
}
