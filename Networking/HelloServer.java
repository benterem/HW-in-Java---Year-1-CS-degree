// Ben Terem 309981512
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class HelloServer {

	public static final String ERR_MESSAGE = "IO Error!";
	public static final String LISTEN_MESSAGE = "Listening on port: ";
	public static final String HELLO_MESSAGE = "hello ";
	public static final String BYE_MESSAGE = "bye"; 
	ServerSocket server = null;

	public ServerSocket getServerSocket() {
        return server;
	}
	
	/**
	 * Listen on the first available port in a given list.
	 * 
	 * <p>Note: Should not throw exceptions due to ports being unavailable</p> 
	 *  
	 * @return The port number chosen, or -1 if none of the ports were available.
	 *   
	 */
	public int listen(List<Integer> portList) throws IOException {
		ServerSocket s = null;
		for(Integer port : portList){
			try{
				s = new ServerSocket(port);
			}catch(Exception e){
				if(!portList.iterator().hasNext()){
					return -1;
				}
				s = null;
				continue;
				}
			this.server = s;
		}
        return this.server.getLocalPort();
	}

	
	/**
	 * Listen on an available port. 
	 * Any available port may be chosen.
	 * @return The port number chosen.
	 */
	public int listen() throws IOException {
		this.server = new ServerSocket(0);
        return this.server.getLocalPort();
	}


	/**
	 * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by the port number (and a newline) to sysout.
	 * 	  If there's an IOException at this stage, exit the method.
	 * 
	 * 2. Run in a loop; 
	 * in each iteration of the loop, wait for a client to connect,
	 * then read a line of text from the client. If the text is {@link #BYE_MESSAGE}, 
	 * send {@link #BYE_MESSAGE} to the client and exit the loop. Otherwise, send {@link #HELLO_MESSAGE} 
	 * to the client, followed by the string sent by the client (and a newline)
	 * After sending the hello message, close the client connection and wait for the next client to connect.
	 * 
	 * If there's an IOException while in the loop, or if the client closes the connection before sending a line of text,
	 * send the text {@link #ERR_MESSAGE} to sysout, but continue to the next iteration of the loop.
	 * 
	 * *: in any case, before exiting the method you must close the server socket. 
	 *  
	 * @param sysout a {@link PrintStream} to which the console messages are sent.
	 * 
	 * 
	 */
	public void run(PrintStream sysout) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		Socket user = null;
		int port = 0;
		boolean exit = false;
		try {
			port = this.listen();
			sysout.println(HELLO_MESSAGE + port +"\n");
			sysout.flush();
			sysout.close();
		}catch (IOException e){
			return;
		}
		while(!exit){
			try {
				user = server.accept(); // wait for client to connect
				br = new BufferedReader(new InputStreamReader(user.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(user.getOutputStream()));
				String line = br.readLine(); // read a line of text from client
				//BYE message case
				if(line.equals(BYE_MESSAGE)){
					bw.write(BYE_MESSAGE);
					bw.flush();
					exit = true;
					continue;
				}

				bw.write(HELLO_MESSAGE + '\n');
				bw.write(line + '\n');
				bw.flush();

			}catch(IOException e){
				sysout.println(ERR_MESSAGE + "\n");
				sysout.flush();
				sysout.close();
				break;
			}finally {
				// Close connections
				try {
					if (br != null) {
						br.close();
					}
					if (bw != null) {
						bw.close();
					}
					if (user != null) {
						user.close();
					}
				}

				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) {
		HelloServer server = new HelloServer();
		server.run(System.err);
	}

}
