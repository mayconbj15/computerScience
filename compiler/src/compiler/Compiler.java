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
	ArrayList<String> commands;
	public String[] strings;
	public char[] byteCode;
	
	public Compiler(String nameFile) {
		this.nameFile = nameFile;
		this.commands = new ArrayList<String>();
	}
	
	public ArrayList<String> reader() throws IOException {
		String stringValues = "";
		input = new Scanner(System.in);
		
		
		try {
			this.file = new RandomAccessFile(this.nameFile, "rw");
			
			while(file.getFilePointer() < file.length()) {
				stringValues = stringValues + file.readLine();
				
				System.out.println("stringValues " + stringValues);
			}
			
			
			file.close();
			input.close();
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		//String[] aux = translate(stringValues);
		
		makeArray(stringValues);
		takeOffSpaces();
		System.out.println("Later make Array\n");
		for(int i=0; i<this.strings.length; i++) {
			System.out.print(this.strings[i] + " " + "tam: " + this.strings[i].length());
			System.out.println();
		}
		System.out.println();
		for(int i=0; i<this.strings[0].length(); i++) {
			System.out.print(this.strings[i]);
		}
		System.out.println();
		translate();
		generateByteCode();
		
		for(int i=0; i<this.strings.length; i++){
			System.out.println(this.strings[i] + ": " + "isInstruction: " + isInstruction(this.strings[i])
					+ " :" + "isAtribution: " + isAtribution(this.strings[i]));
		}
		System.out.println("ARRAY LISTA\n");
		for(int i=0; i<this.commands.size(); i++) {
			System.out.println(this.commands.get(i));
		}
		return this.commands;
	}
	
	public void takeOffSpaces() {
		for(int i=0; i<this.strings.length; i++) {
			if(haveSpaces(strings[i]))
				this.strings[i] = noSpace(this.strings[i]);
		}
	}
	
	public boolean haveSpaces(String str) {
		boolean haveSpace = false;
		
		for(int i=0; i<str.length() && haveSpace == false; i++) {
			if(str.charAt(i) == ' ')
				haveSpace = true;
		}
		return haveSpace;
	}
	
	public String noSpace(String str) {
		String newString;
		char[] vetAux = new char[str.length()-1];
		
		int j=0;
		for(int i=0; i<str.length(); i++ ) {
			System.out.println(str + " " + str.length());
			
			if(str.charAt(i) != ' ') {
				vetAux[j] = str.charAt(i);
				j++;
			}
		}
		
		newString = convertCharToString(vetAux);
		
		return newString;
	}
	/**
	 * Método que separa cada instrução e cada atribuição
	 * @param stringValues
	 */
	public void makeArray(String stringValues){
		this.strings = stringValues.split(";");
		
		for(int i=0; i<this.strings.length; i++){
			System.out.print(this.strings[i] + " ");
		}
		
		System.out.println();
	}
	
	/**
	 * Método que cria um ArrayList com as instruções tranduzidas
	 */
	public void translate() {
		char a = ' ';
		char b = ' ';
		
		this.byteCode = new char[3];
		
		for(int i=0; i<this.strings.length; i++) {
			if(!isInstruction(strings[i])){
				//tratar os valores
				if(this.strings[i].charAt(0) == 'a' || this.strings[i].charAt(0) == 'A'){
					if(strings[i].length() == 3) //um numero entre 0 e 9
						a = this.strings[i].charAt(2);
					else if(strings[i].length() == 4) //numero entre A e F
						a = toHex(this.strings[i].substring(2));
				}
				else if(strings[i].charAt(0) == 'b' || strings[i].charAt(0) == 'B'){
					if(strings[i].length() == 3) //um numero entre 0 e 9
						b = this.strings[i].charAt(2);
					else if(strings[i].length() == 4) //numero entre A e F
						b = toHex(this.strings[i].substring(2));
				}
			}
			else if(isInstruction(strings[i])) {
				//descobrir qual atribuição e então criar um código hexa
				System.out.println("TRANSLATE INSTRUCTION");
				System.out.println(this.strings[i]);
				
				this.byteCode[0] = a;
				this.byteCode[1] = b;
				this.byteCode[2] = whoInstruction(strings[i]);
				
				this.commands.add(convertCharToString(this.byteCode));
				
				System.out.println("instruction");
				System.out.println(this.byteCode[2]);
				System.out.println("BYTE CODE");
				System.out.print(byteCode[0]);
				System.out.print(byteCode[1]);
				System.out.print(byteCode[2]);
				System.out.println();
			}
		}
		
	
		
		
	}
	
	public boolean isInstruction(String instruction){
		boolean isInstruction = true;
		
		for(int i=0; i<instruction.length(); i++){
			if(instruction.charAt(i) == '='){
				isInstruction = false;
			}
		}
		
		return isInstruction;
	}
	
	public boolean isAtribution(String command){
		boolean isAtribution = false;
		
		for(int i=0; i<command.length(); i++){
			if(command.charAt(i) == '='){
				isAtribution = true;
			}
		}
		return isAtribution;
	}
	
	
	public String convertCharToString(char[] byteCode) {
		String str = "";
		
		for(int i=0; i<byteCode.length; i++) {
			str = str + byteCode[i];
		}
		
		return str;
	}
	
	public char toHex(String str) {
		char newChar = ' ';
		
		switch(str) {
			case "10": newChar = 'A';
			case "11": newChar = 'B';
			case "12": newChar = 'C';
			case "13": newChar = 'D';
			case "14": newChar = 'E';
			case "15": newChar = 'F';
		}
		
		return newChar;
	}
	
	public char whoInstruction(String str) {
		char c = ' ';
	
		switch(str) {
			case "zeroL": c = '0'; break;
			case "umL": c = '1'; break;
			case "An": c = '2'; break;
			case "Bn": c = '3'; break;
			case "AouB": c = '4'; break;
			case "AeB": c = '5'; break;
			case "AxorB": c = '6'; break;
			case "AnandB": c = '7'; break;
			case "AnorB": c = '8'; break;
			case "AxnorB": c = '9'; break;
			case "AnouB": c = 'A'; break;
			case "AouBn": c = 'B'; break;
			case "AneB": c = 'C'; break;
			case "AeBn": c = 'D'; break;
			case "AnouBn": c = 'E'; break;
			case "AneBn": c = 'F'; break;
		}
		return c;
	}

	public void generateByteCode() throws IOException {
		try{
			file = new RandomAccessFile("testeHex.hex", "rw");
			
			int len = this.commands.size();
			
			for(int i=0; i<len; i++) {
				String aux = this.commands.get(i);
				for(int j=0; j<aux.length(); j++) {
					file.write(aux.charAt(j));
				}
				file.writeChar('\n');
			}
			
			file.close();
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
	}
	
}
