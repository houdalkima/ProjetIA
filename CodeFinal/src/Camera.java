
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Camera {
	//Declaration de la camera
	 private DatagramSocket dsocket;
	 private DatagramPacket packet;
	 InetAddress serveur;
	 
	 //Port de la camera 
	 private final int portCam = 8888;
	 
	 //IP de la caméra
	 private final String IP = "192.168.1.255";
	 
		public String[] coordsCamera(){
			/** Methode retournant les coordonnees de la caméra sous la forme d'une liste de String
			/* Exemple de @return :
			 * {0;88;178,1;56;149,2;278;96}
			 */
			 
			
	        try {
	        serveur = InetAddress.getByName(IP);
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
