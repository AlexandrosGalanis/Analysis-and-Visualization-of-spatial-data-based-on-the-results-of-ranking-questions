import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class XXFile {
	
	private BufferedReader m_Reader;
	private BufferedWriter m_Writer;
	
	public void openRFile(String sFile) {
		try {
			m_Reader = new BufferedReader(new FileReader(sFile));
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	public void openWFile(String sFile) {
		try {
			m_Writer = new BufferedWriter(new FileWriter(sFile));
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}		
	}

	public void closeRFile() {
		try {
			m_Reader.close();
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}		
	}

	public void closeWFile() {
		try {
			m_Writer.close();
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}		
	}
	
	public String readLine() {
		String sLine = null;
		try {
			if ((sLine = m_Reader.readLine()) != null)
				return sLine;
			else 
				return null;
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
			return null;
		}		
	}
	
	public void writeLine(String sLine) {
		try {
			m_Writer.write(sLine);
			m_Writer.newLine();
			m_Writer.flush();
		}
		catch (IOException ioex) {
			ioex.printStackTrace();
		}		
	}
}