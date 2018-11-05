package com.hostbasedfirewal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.utility.IPAddressHelper;
import com.utility.Trie;

public class Firewall implements FirewallInterface {
	private File rulesFile;
	private Trie rulesTrie = new Trie();
	private List<String[]> rules = new ArrayList<String[]>();

	/*
	 * A function, accept_packet, that takes exactly four arguments and returns
	 * a boolean: true, if there exists a rule in the file that this object was
	 * initialized with that allows traffic with these particular properties,
	 * and false otherwise
	 */
	@Override
	public boolean accept_packet(String direction, String protocol, int port, String ip_address) {

		// Converting to encoded String to check in Trie
		StringBuilder encoded = new StringBuilder();
		encoded.append(direction);
		encoded.append(protocol);
		encoded.append(port);
		encoded.append(ip_address);
		return rulesTrie.search(encoded.toString());
	}

	/*
	 * A constructor, taking a single string argument, which is a file path to a
	 * CSV file
	 */
	public Firewall(String path) throws Exception {
		// creating new file object based on path given
		this.rulesFile = new File(path);

		// creating a list of rules as given in input file if file exists
		try {
			// Get File in strea,
			Scanner inputStream = new Scanner(rulesFile);
			System.out.println("File input is valid ");

			// Read file line by line
			while (inputStream.hasNext()) {
				String data = inputStream.next();
				String[] values = data.split(",");
				rules.add(values);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Count of total rules
		System.out.println("Total Rules in Files: " + rules.size());

		// Helper method call to generate Trie
		long count = generateTrieofRules(rules);
		System.out.println("Total Rules Added in Trie: " + count);
	}

	private long generateTrieofRules(List<String[]> rules) throws Exception {
		long count = 0;
		// Looping through each line of input file
		for (String[] rule : rules) {
			// Checking if line contains atleast 4 items
			if (rule.length == 4) {
				// Temporary variables to store data
				String direction = rule[0];
				String protocol = rule[1];
				String portRange = rule[2];
				String IPAddressRange = rule[3];
				String[] portTemp = portRange.split("-");
				String[] IPTemp = IPAddressRange.split("-");

				// Get range of port if specified
				int portStart = Integer.parseInt(portTemp[0]);
				int portEnd = portStart;
				if (portTemp.length > 1) {
					portEnd = Integer.parseInt(portTemp[1]);
				}
				// Get range of IP addresses if specified
				String IPStart = IPTemp[0];
				String IPEnd = IPStart;
				if (IPTemp.length > 1) {
					IPEnd = IPTemp[1];
				}
				
				// 2 for loops to add all combinations of IP addresses and Port
				// from range
				String ipToCheck = IPStart;
				for (int ip = portStart; ip <= portEnd; ip++) {
					while (IPAddressHelper.isValidRange(IPStart, IPEnd, ipToCheck)) {
						count = count + 1;

						// Encoding string to add to Trie
						StringBuilder encoded = new StringBuilder();
						encoded.append(direction.trim());
						encoded.append(protocol.trim());
						encoded.append(ip);
						encoded.append(ipToCheck.trim());
						rulesTrie.insert(encoded.toString());
						ipToCheck = IPAddressHelper.getNextIPAddress(ipToCheck);
					}
					ipToCheck = IPStart;
				}
			} else {
				System.out.println("Invalid Input");
				continue;
			}
		}
		return count;
	}

	// Sample Test Cases
	public static void main(String[] args) {
		try {
			Firewall fw = new Firewall("src/rules.csv");

			System.out.println("Sample Testing");
			System.out.println(fw.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
			System.out.println(fw.accept_packet("inbound", "udp", 53, "192.168.2.1"));
			System.out.println(fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
			System.out.println(fw.accept_packet("outbound", "udp", 65535, "255.255.255.255"));
			System.out.println(fw.accept_packet("outbound", "udp", 1, "255.255.255.255"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
