package compiler;

import java.io.IOException;

import java.util.ArrayList;

public class Main {
	public static final String PORTA_COM = "COM6";
	
	public static void main(String[] args) throws Exception {
		ArrayList<String> commands = new ArrayList<String>();
		Compiler compiler = new Compiler("testeUla.ula");
		
		commands = compiler.reader();

		ProcessBuilder pb;
		Process p;

		for(int i=0; i<commands.size(); i++){	
			pb = new ProcessBuilder("envia.exe",PORTA_COM,commands.get(i));
			p = pb.start();
	        p.waitFor( );
	      }
	
	}

}
