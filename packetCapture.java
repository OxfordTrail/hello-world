import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.lan.Ethernet;

public class packetCapture {
	public static void main(String[] args){
	  java.util.List<PcapIf> allDevs = new ArrayList<PcapIf>();
		StringBuilder errbuf = new StringBuilder();//for errors
		
		int r = Pcap.findAllDevs(allDevs, errbuf);  
        if (r == Pcap.NOT_OK || allDevs.isEmpty()) {  
            System.err.printf("Can't read list of devices, error is %s", errbuf  
                .toString());  
            return;  
        }  
		PcapIf device = allDevs.get(1); //selecting the device, 0 to 4(in most cases)
		
		//Opening the selected device
		int  snaplen =64*1024; //capture all packet,no truncation	
		int flags= Pcap.MODE_PROMISCUOUS; // capture all packets
	    int timeout =10*1000; //10 seconds in milisecs
	    Pcap pcap =Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
	    
	    if (pcap== null) {
			System.err.printf("Error while opening device for capture: ", errbuf.toString());
			return;
			}
	    
	    //packet handler which will receive packets from lipcap loop
	   PcapPacketHandler<String> jPacketHandler =new PcapPacketHandler<String>() {

		@Override
		public void nextPacket(PcapPacket packet, String user) {
			// TODO Auto-generated method stub
			System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n", new Date(packet.getCaptureHeader().timestampInMillis()),packet.getCaptureHeader().caplen(),packet.getCaptureHeader().wirelen(),user);
			System.out.println(packet.getHeaderIdByIndex(Ethernet.ID));
		}
		   
	};
	
	//capture 10 packets
	pcap.loop(10, jPacketHandler,"jnetPcap :)");
	String ofile = "tmp-capture-file.cap";
	PcapDumper dumper = pcap.dumpOpen(ofile); //output file
	JBufferHandler<PcapDumper> dumpHandler = new JBufferHandler<PcapDumper>() {

		@Override
		public void nextPacket(PcapHeader header, JBuffer buffer, PcapDumper dumper) {
			
			dumper.dump(header, buffer);
			
		}
		
	};
	pcap.loop(10, dumpHandler,dumper);
	File file = new File(ofile);
	System.out.printf("%s file has %d bytes in it! \n", ofile,file.length());
	dumper.close();
	
	//closing the pcap handler
	pcap.close();
	}
	
}
