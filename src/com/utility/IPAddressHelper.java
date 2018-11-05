package com.utility;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class contains methods to generate IP Address(IPV4) Sequence.
 */
public class IPAddressHelper {

	// Converting IP address to corresponding Long representation
	// Reference: https://github.com/nguyensjsu/cmpe281-heyitsvajid/projects/1
	private static long ipToLong(InetAddress ip) {
		byte[] octets = ip.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	// Checking if IP address belongs to range
	// Reference: https://github.com/nguyensjsu/cmpe281-heyitsvajid/projects/1
	public static boolean isValidRange(String ipStart, String ipEnd, String ipToCheck) {
		try {
			long ipLo = ipToLong(InetAddress.getByName(ipStart));
			long ipHi = ipToLong(InetAddress.getByName(ipEnd));
			long ipToTest = ipToLong(InetAddress.getByName(ipToCheck));
			return (ipToTest >= ipLo && ipToTest <= ipHi);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Function to generate next IP address
	public static String getNextIPAddress(String ipAddress) throws Exception {

		// Splitting to get 4 integers
		String[] elements = ipAddress.split("\\.");
		if (elements != null && elements.length == 4) {
			Integer part1 = Integer.parseInt(elements[0]);
			Integer part2 = Integer.parseInt(elements[1]);
			Integer part3 = Integer.parseInt(elements[2]);
			Integer part4 = Integer.parseInt(elements[3]);

			// Starting from the Last part and increasing its count by 1. If in
			// range then return this new IP, otherwise check for next number.
			if (part4 < 255) {
				String ip = part1 + "." + part2 + "." + part3 + "." + (++part4);
				return ip;
			} else if (part4 == 255) {
				if (part3 < 255) {
					String ip = part1 + "." + part2 + "." + (++part3) + "." + (0);
					return ip;
				} else if (part3 == 255) {
					if (part2 < 255) {
						String ip = part1 + "." + (++part2) + "." + (0) + "." + (0);
						return ip;
					} else if (part2 == 255) {
						if (part1 < 255) {
							String ip = (++part1) + "." + (0) + "." + (0) + "." + (0);
							return ip;
						} else if (part1 == 255) {
							throw new Exception("IP Range Exceeded -> " + ipAddress);
						}
					}
				}
			}
		}

		return null;
	}
}