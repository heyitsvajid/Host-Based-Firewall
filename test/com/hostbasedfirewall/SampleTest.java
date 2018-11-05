package com.hostbasedfirewall;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hostbasedfirewal.Firewall;

public class SampleTest {

	static Firewall fw;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        //This method could be used to set up any test fixtures that are computationally expensive and shared by several test methods. e.g. establishing database connections 
        //Sometimes several tests need to share computationally expensive setup (like logging into a database). While this can compromise the independence of tests, sometimes it is a necessary optimization. From http://junit.sourceforge.net/javadoc/org/junit/BeforeClass.html
    	fw = new Firewall("C:\\Users\\vajid\\OneDrive\\Desktop\\rules.csv");
       
    }

   
    @Test
    public void tests() {
       
		assertEquals(true, fw.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
		assertEquals(true,fw.accept_packet("inbound", "udp", 53, "192.168.2.1"));
		assertEquals(true,fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
		assertEquals(false,fw.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
		assertEquals(false, fw.accept_packet("inbound", "udp", 24, "52.12.48.92"));
    }

}
