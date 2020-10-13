// Ben Terem 309981512
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HelloClient {

	Socket clientSocket;

	public static final int COUNT = 10;

	/**
	 * Connect to a remote host using TCP/IP and set {@link #clientSocket} to be the
	 * resulting socket object.
	 * 
	 * @param host remote host to connect to.
	 * @param port remote port to connect to.
	 * @throws IOException
	 */
	public void connect(String host, int port) throws IOException {
		try{
			clientSocket = new Socket(host, port);

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Perform the following actions {@link #COUNT} times in a row: 1. Connect
	 * to the remote server (host:port). 2. Write the string in myname (followed
	 * by newline) to the server 3. Read one line of response from the server,
	 * write it to sysout (without the trailing newline) 4. Close the socket.
	 * 
	 * Then do the following (only once): 1. send
	 * {@link HelloServer#BYE_MESSAGE} to the server (followed by newline). 2.
	 * Read one line of response from the server, write it to sysout (without
	 * the trailing newline)
	 * 
	 * If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
	 * (followed by newline) to sysout. If the error is inside the loop,
	 * continue to the next iteration of the loop. Otherwise exit the method.
	 * 
	 * @param sysout
	 * @param host
	 * @param port
	 * @param myname
	 */
	public void run(PrintStream sysout, String host, int port, String myname) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		for(int i = 0; i < COUNT; i++){
			try {
				connect(host, port); // Connect to server
				br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				bw.write(myname + "\n"); //Write myname to server
				bw.flush();
				String line = br.readLine(); //Read response and print with sysout
				sysout.println(line);
				if(i == COUNT - 1){
					try {
						bw.write(HelloServer.BYE_MESSAGE + "\n");
						bw.flush();
						String line2 = br.readLine();
						sysout.println(line2);
					} catch (IOException e) {
						sysout.println(HelloServer.ERR_MESSAGE + "\n");
						sysout.flush();
						sysout.close();
						return;
					}
				}
			} catch (IOException e) {
				sysout.println(HelloServer.ERR_MESSAGE + "\n");
				sysout.flush();
				break;
			} finally {
					try {
						if (br != null) {
							br.close();
						}
						if (bw != null) {
							bw.close();
						}
						if(clientSocket != null) {
							clientSocket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
