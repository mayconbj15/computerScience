package compiler;

import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.Scanner;
import java.util.ArrayList;

public class Compiler {
	public RandomAccessFile file;
	public String nameFile;
	Scanner input;
	ArrayList<String> strings;
	
	public Compiler(String nameFile) {
		this.nameFile = nameFile;
		this.strings = new ArrayList<String>();
	}
	
	public String reader() throws IOException {
		String stringValues = "";
		input = new Scanner(System.in);
		
		try {
			this.file = new RandomAccessFile(this.nameFile, "rw");
			
			while(file.getFilePointer() < file.length()) {
				stringValues = stringValues + " " + file.readLine();
				
				//esperar para processar o comando
				
				/*if(file.readChar() == '\n') {
					System.out.println("QUEBRA");
				}*/
				System.out.println("stringValues " + stringValues);
				System.out.println("stringValues " + stringValues.charAt(1));
			}
			
			
			file.close();
			input.close();
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		String[] aux = translate(stringValues);
		System.out.println("A = " + aux[0]);
		System.out.println("B = " + aux[1]);
		
		return stringValues;
	}
	
	public String[] translate(String string) {
		int i=1;
		int len = string.length();

		String[] values = new String[2];
		
		while(i < len) {
			if(i < len && string.charAt(i) == 'a') {
				//atribui o valor de a
				System.out.println("A");
				i+=2;
				values[0] = Character.toString(string.charAt(i));
				
				i+=3; //pula para a outra instrução
			}
			
			if(i < len && string.charAt(i) == 'b') {
				//atribui o valor de b
				System.out.println("B");
				i+=2;
				values[1] = Character.toString(string.charAt(i));
				
				i+=3; //pula para a outra instrução
			}
			
			
			if(i < len && string.charAt(i) != 'a' && string.charAt(i) != 'b') {
				i+=5; //pula para próxima instrução
			}
		}
		
		//values[0] = Integer.toHexString(Integer.parseInt(values[0]));
		//values[1] = Integer.toHexString(Integer.parseInt(values[1]));
	
		return values;
	}
	
	public void generateByteCode(String stringValues, String stringCommands) {
		
	}
	
}
