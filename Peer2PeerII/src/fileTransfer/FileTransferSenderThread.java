package fileTransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FileTransferSenderThread extends Thread {
	private Socket socket;
	private File toSend;

	public FileTransferSenderThread(Socket s, File file){
		this.socket = s;
		this.toSend = file;
	}
	
	public void run(){
		byte[] bytes = new byte[(int) toSend.length()];
		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(new FileInputStream(toSend));
			bis.read(bytes, 0, bytes.length);
		    OutputStream os = socket.getOutputStream();
		    os.write(bytes, 0, bytes.length);
		    os.flush();
		    socket.close();
		    bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
