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
	
	public String reader() throws IOException {
		String stringValues = "";
		input = new Scanner(System.in);
		
		
		try {
			this.file = new RandomAccessFile(this.nameFile, "rw");
			
			while(file.getFilePointer() < file.length()) {
				stringValues = stringValues + " " + file.readLine();
				
				System.out.println("stringValues " + stringValues);
				System.out.println("stringValues " + stringValues.charAt(1));
			}
			
			
			file.close();
			input.close();
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		//String[] aux = translate(stringValues);
		
		makeArray(stringValues);
		translate();
		generateByteCode();
		
		for(int i=0; i<this.strings.length; i++){
			System.out.println(this.strings[i] + ": " + "isInstruction: " + isInstruction(this.strings[i])
					+ " :" + "isAtribution: " + isAtribution(this.strings[i]));
		}
		return stringValues;
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
	
	public void cript(String stringsCommands){
		for(int i=0; i<stringsCommands.length(); i++){
			
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
	
	/**
	 * Método que cria um ArrayList com as instruções tranduzidas
	 */
	public void translate() {
		this.byteCode = new char[3];
		
		for(int i=0; i<this.strings.length; i++) {
			if(!isInstruction(strings[i])){
				//tratar os valores
				if(strings[i].charAt(0) == 'a' || strings[i].charAt(0) == 'A'){
					this.byteCode[0] = this.strings[i].charAt(2);
				}
				if(strings[i].charAt(0) == 'b' || strings[i].charAt(0) == 'B'){
					this.byteCode[1] = this.strings[i].charAt(2);
				}
			}
			else if(isAtribution(strings[i])) {
				this.byteCode[2] = whoAtribution(strings[i]);
			}
			System.out.println("BYTE CODE");
			System.out.print(byteCode[0]);
			System.out.print(byteCode[1]);
			System.out.print(byteCode[2]);
		}
		
		this.commands.add(convertCharToString(this.byteCode));
		System.out.println("ARRAY LISTA");
		System.out.println(commands.get(0));
	}
	
	public String convertCharToString(char[] byteCode) {
		String str = "";
		
		for(int i=0; i<byteCode.length; i++) {
			str = str + byteCode[i];
		}
		
		return str;
	}
	
	public char whoAtribution(String str) {
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
			case "AnounBn": c = 'E'; break;
			case "AneBn": c = 'F'; break;
		}
		return c;
	}
	/*public String[] translate(String string) {
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
	}*/
	
	public void generateByteCode() throws IOException {
		try{
			file = new RandomAccessFile("testeHex.txt", "rw");
			
			int len = this.commands.size();
			
			for(int i=0; i<len; i++) {
				String aux = this.commands.get(i);
				for(int j=0; j<aux.length(); j++) {
					file.writeChar(aux.charAt(j));
				}				
			}
			
			file.close();
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
	}
	
}
