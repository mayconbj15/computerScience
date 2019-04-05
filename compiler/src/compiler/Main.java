package compiler;

import java.io.IOException;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		ArrayList<String> commands = new ArrayList<String>();
		Compiler compiler = new Compiler("teste.txt");
		
		
		commands = compiler.reader();

		
	
	}

}
