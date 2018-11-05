package com.hostbasedfirewal;

public interface FirewallInterface {
	public boolean accept_packet(String direction, String protocol, int port, String ip_address);

}
