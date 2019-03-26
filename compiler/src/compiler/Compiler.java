package compiler;

import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Compiler {
	public RandomAccessFile file;
	public String nameFile;
	
	public Compiler(String nameFile) {
		this.nameFile = nameFile;
	}
	
	public String reader() throws IOException {
		String aux = "";
		
		try {
			this.file = new RandomAccessFile(this.nameFile, "rw");
			
			while(file.getFilePointer() < file.length()) {
				aux = aux + file.readLine();
				if(file.readChar() == '\n') {
					System.out.println("QUEBRA");
				}
			}
			
			System.out.println(aux);
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}catch(EOFException eof) {
			System.out.println("FIM ARQUIVO");
		}
		
		return aux;
	}
	
	
}
