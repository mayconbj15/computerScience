package compiler;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Compiler compiler = new Compiler("teste.txt");
		Compiler compiler1 = new Compiler("teste1.txt");
		
		
		
		String aux = compiler.reader();
		String aux1 = compiler1.reader();
		
		//String[] vetAux = new String[aux.length()];
		String[] vetAux1 = new String[aux1.length()];
		
		//vetAux = aux.split(";");
		vetAux1 = aux1.split(";");
		
		System.out.println("QUEBRADAS");
		//System.out.println(aux.length());
		/*for(int i=0; i<aux.length(); i++) {
			System.out.println(vetAux[i]);
		}*/
	}

}
