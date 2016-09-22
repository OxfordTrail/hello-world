import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.midi.SysexMessage;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class sendPacket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<PcapIf> alldevs = new ArrayList<PcapIf>();
		StringBuilder errbuf = new StringBuilder();
		
		
		//for a list of devices in the system
		int r= Pcap.findAllDevs(alldevs, errbuf);
		if(r==Pcap.NOT_OK || alldevs.isEmpty()){
			System.err.printf("Cant read the lsit of devices, error is %s", errbuf.toString());
			return;
		}
		
		PcapIf device=alldevs.get(0);//selecting the 1st device
		
		//Open the network interface
		int snaplen= 64*1024; //capture all packets, no truncation
		int flags = Pcap.MODE_PROMISCUOUS;//capture all packtes
		int timeout = 10*1000; //10 seconds in millis
		Pcap pcap= Pcap.openLive(device.getName(), snaplen, flags, timeout,errbuf);
		
		//create a packet
		String content = "Hello";
		//byte[] a = new byte[14];
		byte[] a = content.getBytes();
		System.out.println("Contents of a "+a);
		
		Arrays.fill(a, (byte)0xff);
		System.out.println("Contents of a after filling array a : "+ a);
		
		ByteBuffer b = ByteBuffer.wrap(a);
		
		System.out.print("Byte buffer b : "+b);
		
		//send our packet
		
		if (pcap.sendPacket(b)!=Pcap.OK) {
			System.err.println(pcap.getErr());
		}
		
		//close 
		pcap.close();

	}

}
