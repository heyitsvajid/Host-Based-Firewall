# Host-Based-Firewall in Java


## Goal

* The goal is to implement a Firewall class which on the basis of given set of firewall rules in the form of direction, protocol, port, and IP address, determines that a network packet will be accepted by the firewall or not if input match with at least one of the given rules.


## Project Structure
![Project Structure](/images/project_structure.PNG)


* As shown in above image,project consists of mainly 3 packages and 4 classes and 1 interface.
*	**Package 1:** utility: contains two helper classes.

*	**Package 2:** hostbasedfirewall: contains actual implementation.

*	**Package 3:** test: contains junit test cases for testing of the implementation.

*	**Dependency:** This project requires junit-4.10.jar to run the application.

## Classes

### Trie 
- This is a utility class which implements trie data structure. This class was implemented by me before starting this project. It implements trie data structure using HashMap of character at each node. It contains mainly two methods. 
- **searchNode(String str):** This methods is used to search if the string is present in the Trie.  
- **insert(String word):** This methods is used to add the string in the Trie.  
- **Why Trie?** As in real life scenarios there will be millions of rules and there is little difference between two IP addresses if they are near. For Eg. 192.168.1.1 and 192.168.1.2 just has 1 differnet character. Hence Trie will save memory. As the data is similar, we will have greater memory savings. So the approach i took was that based on input rules line from CSV, I generated all possible rules pair with given port range and IP address range, the I created a encoded string where in I append all 4 parameters and generated a new String. Then added all these combination in trie. 
- Time Complexity: Search : O(M) where M is the length of a key
- Time Complexity: Insert : O(k) where k is the length of a key
  
  
> StringBuilder encoded = new StringBuilder();  
>	encoded.append(direction.trim());  
> encoded.append(protocol.trim());  
> encoded.append(ip);  
> encoded.append(IPStart.trim());  
> rulesTrie.insert(encoded.toString());  

  
### IPAddressHelper
- This class is a utility for IP address related calculations.It contains mainly two methods. 
- **isValidRange(String ipStart, String ipEnd, String ipToCheck):** As input CSV might contain range of IP addresses, I used this to check if I have reached from start IP address to the end of the range. To check if IP is between the start and end range, I used this method. [Reference](https://gist.github.com/madan712/6651967).
- **String getNextIPAddress(String ipAddress):** This method is used along with compareIP method. I add all the IP address in the TRIE data strructure and use this method to generate the next IP address in sequence.  


### Firewall
- This is the actual class which follows the interface as provided in programming assignment. This includes mainly two methods.
- **Firewall(String path):** This is the constructor of class which takes in the String argument as path of the rules csv file. Then it iterates over file and get all the lines in the List. Post file reading, all the data is added to the Trie data structure with the help of utility method in the Firewall class. This utility methods checks if the rule is for single IP adn Single port of range and adds the data accordingly.
- **accept_packet(String direction, String protocol, int port, String ip_address):** This method based on input checks if it mathdes with any of the given rules from CSV files and returns a boolean reponse. 

### FirewallInterface
- Used as a contract to implement Firewall remove hard dependency for clients on specific implementation.

### SampleTest
- Contains Junit test cases to check Firewall implementation.

### Analysis
- Initially I thought of using simple HashMap on IP address as key and storing combination of port, direction and protocol as a custom Object. Later I realised that as there can be range of IP addresses and also port, the amount of space required to store IP addresses will be very high. For Eg. If the range is given as 0.0.0.0 to 255.255.255.255 will be 2 to the power 32. And also there can be multiple rules for different IP addresses so later on I decided to use Trie datastructure to improve space complexity.

### Possible Optimization
- The program initially takes time of the IP address range or Port range is very high to generate a Trie DS and store all rules in it. One possible solution I can think of is to implement Threading mechanism at the start to improve the speed of generating rules Trie. This can be done by dividing the Trie into parts for range of IP addresses and then store in the corresponding trie.
- IP address range calculations can be improved.

### Teams Interested In  
**1. Platform Team **  
**2. Policy Team **

As you must have seen in my resume, to give a brief overview, I'm currently pursuing Master's in Software Engineering at SJSU with focus on Enterprise Software Technlogies and Cloud Computing. I am taking courses related to distributed systems and cloud technologies where I am getting hands-on experience on how to develop scalable multi tenant products and leverage NoSQL databases like redis, mongo db, riak. I have also gain experience in MERN(MongoDB ExpressJs ReactJs NodeJs)  stack recently and have developed two projects last semester. In these projects, I extensively leveraged the cloud technologies like AWS Load Balancer to provide high performance and availability. I used MongoDB sharded and replicated database cluster to improve query performance with high requests scenario. 
Apart from this I also have around 2 years of professional experience of developing robust code for high-volume businesses. As the Platfrom and policy team mainly work on high availability, caching and persistence layers, API support, background jobs, reporting
infrastructure, cluster management, authentication and authorization and leverage Postgres as database and Redis as caching layer, I feel that my previous experience and knowledge gained through Masters will help me succeed and excel in.

## 📝 Author
[<img src="https://github.com/heyitsvajid/heyitsvajid.github.io/blob/master/img/profile.jpg" align="right" height="100">](https://github.com/heyitsvajid)

##### Vajid Kagdi <kbd> [Github](https://github.com/heyitsvajid) / [LinkedIn](https://www.linkedin.com/in/heyitsvajid) / [E-Mail](mailto:vajid9@gmail.com)</kbd>
