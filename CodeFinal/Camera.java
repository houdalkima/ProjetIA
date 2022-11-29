import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Camera {
	//Declaration de la camera
	 private DatagramSocket dsocket;
	 private DatagramPacket packet;
	 private byte[] buffer;
	 InetAddress serveur;
	 
	 //Port de la camera 
	 private final int portCam = 8888;
	 
		public String[] coordsCamera(){
			// Wait to receive a datagram
			
	        try {
	        serveur = InetAddress.getByName("192.168.1.255");
		    dsocket = new DatagramSocket(portCam);

		      byte[] buffer = new byte[2048];

		      packet = new DatagramPacket(buffer, buffer.length);
		      
	        	while (true) {
	    	        dsocket.receive(packet);

	    	        String msg = new String(buffer, 0, packet.getLength());
	    	        String []coords = msg.split("\n");
	    	        
	    	        if (coords.length != 0)
	    	        	return coords;
	    	       
	    	        packet.setLength(buffer.length);
	        	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
			return null;      
		}
		
}
