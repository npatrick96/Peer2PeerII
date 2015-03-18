package fileTransfer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileTransferReceiverThread extends Thread {
	private Socket socket;
	private File toWrite;
	
	public FileTransferReceiverThread(Socket s, File file){
		this.socket = s;
		this.toWrite = file;
	}
	
	public void run(){
		byte[] bytes = new byte[1024];
	    InputStream is;
		try {
			is = socket.getInputStream();
		    FileOutputStream fos = new FileOutputStream(toWrite);
		    BufferedOutputStream bos = new BufferedOutputStream(fos);
		    int bytesRead = is.read(bytes, 0, bytes.length);
		    bos.write(bytes, 0, bytesRead);
		    bos.close();
		    socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
