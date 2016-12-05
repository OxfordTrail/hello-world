package jpcapPrograms;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.packet.TCPPacket;

public class offlinePacket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JpcapCaptor captor =JpcapCaptor.openFile("tcp.cap");
			
			for (int i = 1; i <=1; i++) {
				TCPPacket tcpPacket = (TCPPacket) captor.getPacket();
				System.out.println(tcpPacket.header+"\n"+tcpPacket.caplen+"\n"+tcpPacket.len+"\n"+tcpPacket.sec+"\n"+tcpPacket.data+"\n"+tcpPacket.usec+"\n"+tcpPacket.header);
				
				System.out.println("Ip properties");
				System.out.println(tcpPacket.d_flag+"\n"+tcpPacket.dont_frag+"\n"+tcpPacket.dst_ip+"\n"+tcpPacket.flow_label+"\n"+tcpPacket.hop_limit+"\n"+tcpPacket.ident+"\n"+tcpPacket.length+"\n"+tcpPacket.more_frag+"\n"+tcpPacket.offset+"\n"+tcpPacket.option+"\n"+tcpPacket.priority+"\n"+tcpPacket.priority+"\n"+tcpPacket.r_flag+"\n"+tcpPacket.rsv_frag+"\n"+tcpPacket.rsv_tos+"\n"+tcpPacket.src_ip+"\n"+tcpPacket.t_flag+"\n"+tcpPacket.version);
				
				System.out.println("TCP properties");
				System.out.println(tcpPacket.src_port+"\n"+tcpPacket.dst_port+"\n"+tcpPacket.sequence+"\n"+tcpPacket.ack_num+"\n"+tcpPacket.urg+"\n"+tcpPacket.ack+"\n"+tcpPacket.psh+"\n"+tcpPacket.rst+"\n"+tcpPacket.syn+"\n"+tcpPacket.fin+"\n"+tcpPacket.rsv1+"\n"+tcpPacket.rsv2+"\n"+tcpPacket.window+"\n"+tcpPacket.urg);
			    
				int src_port=443;
				int dst_port=60485;
				long sequence=tcpPacket.sequence;//do somethig
				long ack_num=tcpPacket.ack_num;//do somethig
				boolean urg=false;
				boolean ack=true;
				boolean psh=true;
				boolean rst=false;
				boolean syn=false;
				boolean fin=false;
				boolean rsv1=false;
				boolean rsv2=false;
				int window=493;
				int urgent=10;//urgent_pointer
				
				TCPPacket packet= new TCPPacket(src_port, dst_port, sequence, ack_num, urg, ack, psh, rst, syn, fin, rsv1, rsv2, window, urgent);
				packet.header=tcpPacket.header;
				packet.data=tcpPacket.data;
				packet.option=tcpPacket.header;
				
				System.out.println(tcpPacket.header + " == " +packet.header);
				System.out.println(tcpPacket.data + "==" +packet.data);
				System.out.println(packet.option);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
	}

}
