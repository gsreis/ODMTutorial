package com.ibm.convertcsv;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class ConvertCSV {
	
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader("/Users/glaucoreis/Desktop/odm-workspace/ConvertCSV/src/com/ibm/santander/variables.csv"));
		String lido = reader.readLine();
		String nome_classe = "THBReceptivo";
		String classe_java = "package com.ibm.santander;\r\n";
		classe_java += "public class " + nome_classe + " {\r\n";
		do { 
			lido = reader.readLine();
			if (lido == null) break;
			String[] split = lido.split(";");
			String tipo;
			if (split[1].equals("string"))
				tipo = " String ";
			else if (split[1].equals("integer"))
				tipo = " int ";
			else 
				tipo = " double ";
			classe_java += "private " +  tipo + " " + split[0] + ";\r\n";
				
		} while(lido != null);
		classe_java += "}\r\n";
		FileOutputStream fout = new FileOutputStream("/Users/glaucoreis/Desktop/odm-workspace/ConvertCSV/src/com/ibm/santander/" + nome_classe + ".java");
		fout.write(classe_java.getBytes());
		fout.flush();
		fout.close();
		reader.close();
	}

}
