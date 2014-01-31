import java.net.*;
import java.io.*;

public class ReleaseMessage extends Thread
{

	//private final String socketAddress = "trifid.icj.me";
	private final int socketPort = 4444;
	private final FwlinksBot[] bots;

	public void run()
	{
		bots[0].printLog("starting socket server on port " + socketPort);

		while(true)
		{
			try
			(
				// configure sockets
				ServerSocket serverSocket = new ServerSocket(socketPort, 0, InetAddress.getByName(null));
				Socket clientSocket = serverSocket.accept(); 
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					// new message
					bots[0].printLog("new session message: " + inputLine);

					for (FwlinksBot bot : bots)
					{
						// send to each channel
						for(String channel : bot.getChannels())
						{
							bot.sendMessage(channel, "[Update] " + inputLine);
						} // for
					} // for
				} // while
			} // try
			catch (IOException e)
			{
				System.out.println(e.getMessage());
			} // catch
		} // while
	} // run

	public ReleaseMessage(FwlinksBot[] requiredBots)
	{
		bots = requiredBots;
	} // ReleaseMessage

} // ReleaseMessage