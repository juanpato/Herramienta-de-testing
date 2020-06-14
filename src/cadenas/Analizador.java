package cadenas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;


import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
// fan-out: el número de métodos llamados por un cierto método.
// fan-in: es la cantidad de llamadas al metodo por otros metodos.
public class Analizador {
	
	private String path;
	private Scanner lector;
	private int cantidadTotalLineas=0;
	private int cantidadLineasComentadas=0;
	private int cantidadLineasBlanco=0;
	private int lineaSoloComentario=0;
	private int cantidadSoloCodigo=0;
	private double procentajeComentarios=0.0;
	private ArrayList<String> metodos;
	private int longitud=0;
	private double volumen=0;
	private double esfuerzo=0;


	public Analizador(String path) {
		this.path = path;
		metodos=new ArrayList<String>();
	}
	

	public boolean analizar() {
		ArrayList<String> codigo = new ArrayList<String>();
		if(!path.endsWith(".java")) {
			System.out.println("Error:NO ES .JAVA");
			return false;
		}
		cantidadTotalLineas = 0;
		cantidadLineasComentadas = 0;
		cantidadLineasBlanco = 0;
		lineaSoloComentario = 0;
		cantidadSoloCodigo = 0;
		procentajeComentarios = 0.0;
		try {
			 lector = new Scanner(new File(path));
			 while(lector.hasNextLine()) {
				 boolean comentada=false;
				 String  linea = lector.nextLine();
				 codigo.add(linea);
				 linea=borraComillas(linea);
				 boolean isEmpty = linea == null || linea.trim().length() == 0;
				 if(isEmpty) {
					 cantidadLineasBlanco++;
				 }
				 if(linea != null && linea.contains("//")) {
					 if(linea.trim().substring(0, 2).equals("//")) {
						 lineaSoloComentario++;
						 comentada=true;
					 }
				 }
					linea=linea.trim();
					
					if (linea.contains("public") || linea.contains("private") && !comentada) {
						if (linea.contains("static")) {
							int indice = linea.indexOf("(");
							if (indice != -1) {
								int indiceINI=linea.indexOf("static");
								String cadena = linea.substring(indiceINI+6, indice).trim() + "()";
								metodos.add(cadena);
							}
						} else {
							int indice = linea.indexOf("(");
							if (indice != -1) {
								int indiceINI;
								String cadena = new String();
								
								if(linea.contains("public")) {
									indiceINI=linea.indexOf("public");
									cadena=linea.substring(indiceINI+6, indice).trim() + "()";
										
								}
								else { //private
									indiceINI=linea.indexOf("private");
									cadena=linea.substring(indiceINI+7, indice).trim() + "()";
								}
								metodos.add(cadena);
							}
						}
					}
				 cantidadTotalLineas++;
			 }
			 
			 lector.close();
			 cantidadLineasComentadas=contarComentarios(codigo);
			 cantidadSoloCodigo=cantidadTotalLineas-lineaSoloComentario-cantidadLineasBlanco;
			 procentajeComentarios=(double)((double)((double)cantidadLineasComentadas/(double)cantidadSoloCodigo))*100;
			 
			 
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	} 

	public int contarComentarios(ArrayList<String> codigo) {
		boolean comentado = false;
		int cantidadDeComentarios = 0;
		for (int i = 0; i < codigo.size(); i++) {
			String sinComillas = borraComillas(codigo.get(i));
			char[] linea = sinComillas.toCharArray();
			if (sinComillas.contains("//")) {
				cantidadDeComentarios++;
			}
			for (int j = 0; j < linea.length; j++) {
				if (linea[j] == '/') {
					if (j + 1 < linea.length) {
						if (linea[j + 1] == '*') {
							comentado = true;
						}
					}
				}
				if (linea[j] == '*') {
					if (j + 1 < linea.length) {
						if (linea[j + 1] == '/') {
							comentado = false;
							j = j + 2;
							cantidadDeComentarios++;
						}
					}
				}
			}

			if (comentado) {
				cantidadDeComentarios++;
			}
		}
		return cantidadDeComentarios;
	}

	public boolean analizarMetodo(ArrayList<String> metodoCompleto) {
		cantidadTotalLineas = 0;
		cantidadLineasComentadas = 0;
		cantidadLineasBlanco = 0;
		lineaSoloComentario = 0;
		cantidadSoloCodigo = 0;
		procentajeComentarios = 0.0;
		String linea = new String();
		for (int i = 0; i < metodoCompleto.size(); i++) {
			linea = metodoCompleto.get(i);
			linea=borraComillas(linea);
			boolean isEmpty = linea == null || linea.trim().length() == 0;
			 if(isEmpty) {
				 cantidadLineasBlanco++;
			 }
			 if(linea != null && linea.contains("//")) {
				 if(linea.trim().substring(0, 2).equals("//")) {
					 lineaSoloComentario++;
				 }
			 }
			 cantidadTotalLineas++;	
		}
		cantidadLineasComentadas=contarComentarios(metodoCompleto);
		cantidadSoloCodigo=cantidadTotalLineas-lineaSoloComentario-cantidadLineasBlanco;
		procentajeComentarios=(double)((double)((double)cantidadLineasComentadas/(double)cantidadSoloCodigo))*100;
		return true;
	}

	public String todoCodigoAString() {
		String codigo = new String();
		if (!path.endsWith(".java")) {
			System.out.println("Error:NO ES .JAVA");
			return codigo;
		}
		try {
			lector = new Scanner(new File(path));
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				codigo += linea + "\n";
			}
			return codigo;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return codigo;
		}
	}
	public ArrayList<String> borrarComentarios(ArrayList<String> metodo){
		ArrayList<String> codigoCompleto = new ArrayList<String>();
		String lineaFinal = new String();
		boolean cerro = false;
		for(int i=0; i<metodo.size();i++) {
			String linea = metodo.get(i);
			linea = linea.trim();
			linea = borraComillas(linea);
			if (linea.contains("/*")) {
				while (linea.contains("/*")) {
					int indice1 = linea.indexOf("/*");
					cerro = false;
					int indice2 = indice1;
					while (indice2 + 2 < linea.length() && !linea.substring(indice2, indice2 + 2).equals("*/"))
						indice2++;
					if (indice1 != 0) {
						lineaFinal = lineaFinal.concat(linea.substring(0, indice1));
					}
					if (linea.substring(indice2, indice2 + 2).equals("*/"))
						cerro = true;
					if (cerro == true)
						linea = linea.substring(indice2 + 2);
					for (; i<metodo.size() && cerro == false; i++) {
						linea = metodo.get(i);
						if (linea.contains("*/")) {
							int indice = linea.indexOf("*/");
							linea = linea.substring(indice + 2);
							cerro = true;
							i--;
						}
					}
					
				}
				lineaFinal = lineaFinal.concat(linea);
				codigoCompleto.add(lineaFinal);
			} else {
				if(linea.contains("//")) {
					if(linea.length()>=2 && !linea.trim().substring(0, 2).equals("//")) {
						String lineaNueva=linea.substring(0, linea.indexOf("//"));
						codigoCompleto.add(lineaNueva);
					} 
				}
				else
					codigoCompleto.add(linea);
				
			}
		}
		for(int i=0;i<codigoCompleto.size();i++)
			System.out.println(codigoCompleto.get(i));
		return codigoCompleto;
	}
	public ArrayList<String> buscarMetodo(String metodo) {
		ArrayList <String> metodoCompleto=new ArrayList<String>();
		if(!path.endsWith(".java")) {
			System.out.println("Error:NO ES .JAVA");
			return metodoCompleto;
		}	
		try {
			lector = new Scanner(new File(path));	
			boolean listo=false;
			while (lector.hasNextLine() && listo==false) {
				String linea = lector.nextLine();
				linea=borraComillas(linea);
				if (linea.contains(metodo)) {
					if ((linea.contains("public") || linea.contains("private")) && !linea.contains("class")) {
							int llavesAbren = 1;
							int llavesCierran = 0;
							boolean flag=false;
							if(linea.contains("{")) 
								flag=true;									
							while (lector.hasNextLine() && llavesAbren != llavesCierran) {
								if(linea.contains("public")) {
									int indice=linea.indexOf("public");
									linea=linea.substring(indice);
								}
								if(linea.contains("private")) {
									int indice=linea.indexOf("private");
									linea=linea.substring(indice);
								}
								metodoCompleto.add(linea);
								linea = lector.nextLine();
								for (int y = 0; y < linea.length() && llavesAbren != llavesCierran; y++) {
									if (linea.charAt(y) == '{') {
										if (flag==true) 
											llavesAbren++;
										else
											flag=true;
									}
									if (linea.charAt(y) == '}') {
										llavesCierran++;
									}
								}
								listo=true;
							}
							if(llavesAbren == llavesCierran) {
								int indice=linea.indexOf("}");
								metodoCompleto.add(linea.substring(0,indice+1));
							}
						}
					}
				}
			
			lector.close();
//			for(int i=0;i<metodoCompleto.size();i++)
//				System.out.println(metodoCompleto.get(i));
			return metodoCompleto;
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return metodoCompleto;
		}
	}
	
	public int complejidad(ArrayList<String> metodoCompleto) {
		int complejidad = 1;
		String linea = new String();
		int cantAND = 0;
		int cantOR = 0;
		boolean noComentada=true;
		for (int i = 0; i < metodoCompleto.size(); i++) {
			cantAND = cantOR = 0;
			noComentada=true;
			linea = metodoCompleto.get(i).trim();
			if(linea.length()>=2 && linea.substring(0,2).equals("//"))
				noComentada=false;
			if (linea != null && noComentada==true) {
					if(linea.split("if", -1).length!=0)
						complejidad+=linea.split("if", -1).length-1;
					if(linea.split("for", -1).length!=0)
						complejidad+=linea.split("for", -1).length-1;
					if(linea.split("while", -1).length!=0)
						complejidad+=linea.split("while", -1).length-1;
					if(linea.split("case", -1).length!=0)
						complejidad+=linea.split("case", -1).length-1;
					if(linea.split("default", -1).length!=0)
						complejidad+=linea.split("default", -1).length-1;
					if(linea.split("catch", -1).length!=0)
						complejidad+=linea.split("catch", -1).length-1;

					for (int y = 0; y < linea.length(); y++) {
						if (linea.charAt(y) == '&' && y!=0)
							cantAND++;
						if (linea.charAt(y) == '|' && y!=0)
							cantOR++;
					}
					complejidad += cantAND / 2;
					complejidad += cantOR / 2;
				}
			}
		
		return complejidad;
	}
	public String borraComillas(String linea) {
		int comillas=0;
		boolean flag=true;
		int indiceINI=-1;
		int indiceFIN=-1;
		char [] lineaArray=linea.toCharArray();
		for(int i=0; i<linea.length();i++) {
			if(lineaArray[i]==34) {
				comillas++;
				if(flag) {
					indiceINI=i;
					flag=false;
				}
			}
			if(!flag && comillas%2==0) {
				indiceFIN=i;
				flag=true;
				for(int j=indiceINI; j<=indiceFIN;j++) {
					lineaArray[j]=' ';
				}
			}
		}
		String nuevaLinea = new String(lineaArray);
		return nuevaLinea;
	}
	public ArrayList<String> codigoToArray(){//pasa el codigo a un arrayList de <String> y sin comentarios ni comillas
		ArrayList <String> codigoCompleto=new ArrayList<String>();
		if(!path.endsWith(".java")) {
			System.out.println("Error:NO ES .JAVA");
			return codigoCompleto;
		}	
		try {
			lector = new Scanner(new File(path));	
			boolean cerro=false;
			while (lector.hasNextLine()) {
				String linea = lector.nextLine();
				linea=linea.trim();
				linea=borraComillas(linea);
				String lineaFinal = new String();
				if (linea.contains("/*")) {
					while (linea.contains("/*")) {
						int indice1 = linea.indexOf("/*");
						cerro = false;				
						int indice2 = indice1;
						while (indice2+2< linea.length() && !linea.substring(indice2, indice2 + 2).equals("*/"))
							indice2++;
						if (indice1 != 0) {
							lineaFinal = lineaFinal.concat(linea.substring(0, indice1));
						}
						if (linea.substring(indice2, indice2 + 2).equals("*/"))
							cerro = true;
						if(cerro==true)
							linea = linea.substring(indice2 + 2);
						while (lector.hasNextLine() && cerro==false) {
							linea=lector.nextLine();
							if(linea.contains("*/")){		
								int indice=linea.indexOf("*/");
								linea=linea.substring(indice+2);
								cerro=true;
							}
						}
					}
					lineaFinal=lineaFinal.concat(linea);
					codigoCompleto.add(lineaFinal);
				}
				else
					codigoCompleto.add(linea);
			}
			lector.close();
//			for(int i=0;i<codigoCompleto.size();i++)
//				System.out.println(codigoCompleto.get(i));
			return codigoCompleto;
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al leer");
			e.printStackTrace();
			return codigoCompleto;
		}
	}
	public int fanIn(ArrayList<String> codigoCompleto, String metodo) {
		int fanIN=0;
		String linea;
		String[] lineaSplitted;
		if(metodo.split(" ").length>=2)
			metodo=metodo.split(" ")[1];
		for(int i=0; i<codigoCompleto.size();i++) {
			linea=codigoCompleto.get(i).trim();
			lineaSplitted=linea.trim().split(" ");
			if(!(lineaSplitted[0].equals("public") || lineaSplitted[0].equals("private")) && linea.contains(metodo)) 
				fanIN++;
		}
		return fanIN;
	}
	
   public int fanOut(ArrayList<String> metodoCompleto) {
		int fanOUT=0;
		String linea = new String();
		String substring =  new String();
		for(int i = 1;i < metodoCompleto.size() ; i++) {
			linea = metodoCompleto.get     (i).trim();
			
			for(int j = 0 ; j < linea.length() ; j ++) {
				if(linea.charAt(j)=='(') {
					substring = linea.substring(0,j);
					int contador = j-1;
					//skipeo espacios
					while(substring.charAt(contador)==' ') {
						contador--;						
					}
					//ya no hay espacios
					int principioCadena=contador;
					while(esLetraONumero(substring.charAt(principioCadena)) && principioCadena!=0) {
						principioCadena--;						
					}
					if(!esLetraONumero(substring.charAt(principioCadena))) {
						principioCadena++;
					}
					String palabraEncontrada = substring.substring(principioCadena,contador+1).trim();
					if(!palabraEncontrada.equals("catch") && !palabraEncontrada.equals("if") && !palabraEncontrada.equals("for") && !palabraEncontrada.equals("while") && !palabraEncontrada.equals("switch")) {
						fanOUT++;
					}
				}
			}
		}
		return fanOUT;
	}
   public String ArrayListToString(ArrayList<String> metodoCompleto) {
	   String codigo=new String();
	   for(String line : metodoCompleto)
		   codigo+=line.trim()+"\n";
	   return codigo;
   }

   public static boolean esLetraONumero(char a){
	   if((a>=48 && a<=57) || (a>=65 && a<=90) || (a>=97 && a<=122)) {
		   return true;
	   }
	   else return false;
   };
   
   public void halstead(String methodName) {
		  String[] methodNameParsedArray = methodName.split("\\s+");
		  String methodNameParsed = methodNameParsedArray[methodNameParsedArray.length-1];
		  
		  //System.out.println(methodNameParsed);

			String fileAsString = readLineByLineJava8(path);
			
			//System.out.println(fileAsString);
		   //String fileAsString = readLineByLineJava8(path);
		   ASTParser parser = ASTParser.newParser(AST.JLS3);
		   parser.setSource(fileAsString.toCharArray());
		   parser.setKind(ASTParser.K_COMPILATION_UNIT);
		   parser.setResolveBindings(true);
		   final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		   
		   IProblem[] problems = cu.getProblems();		
			for(IProblem problem : problems) {
				// Ignore some error because of the different versions.			
	           if (problem.getID() == 1610613332) 		 // 1610613332 = Syntax error, annotations are only available if source level is 5.0
	               continue;       
	           else if (problem.getID() == 1610613329) // 1610613329 = Syntax error, parameterized types are only available if source level is 5.0
	               continue;
	           else if (problem.getID() == 1610613328) // 1610613328 = Syntax error, 'for each' statements are only available if source level is 5.0
	               continue;
	           else 
	           {
	           	// quit compilation if 
	   	        System.out.println("CompilationUnit problem Message " + problem.getMessage() + " \t At line= "+problem.getSourceLineNumber() + "\t Problem ID="+ problem.getID());            	
	   	        
	   	        System.out.println("The program will quit now!");
	   	        //System.exit(1);
	           }
		    }

		   ASTVisitorMod visitor = new ASTVisitorMod();
		   
		   cu.accept(new ASTVisitor() {
				public boolean visit(SingleVariableDeclaration node) {
					if (!visitor.variables.contains(node.getName().toString()))
					{
						visitor.variables.add(node.getName().toString());
					}
					
					return true;
				}
				
				public boolean visit(VariableDeclarationFragment node) {		
					
					if (!visitor.variables.contains(node.getName().toString()))
					{
						visitor.variables.add(node.getName().toString());
					}
					
					return true;
				}
		   });
		   
		   cu.accept(new ASTVisitor() {
			   public boolean visit(MethodDeclaration node) {
				  // System.out.println("nombremetodo : " + node.getName().toString());
				  // System.out.println("methodName : " + methodNameParsed);
				   if(node.getName().toString().equals(methodNameParsed)) {
					  // System.out.println("entre aca");
					   node.accept(visitor);
				   }
				   return true;
			   }
		   });

		   //System.out.println("Cantidad de operandos : " + visitor.names.toString());
		   //System.out.println("Cantidad de operadores : " + visitor.oprt.toString());


		   int DistOperators = visitor.oprt.size();
		   int DistOperands = visitor.names.size();
		   int OperatorCount=0;
		   int OperandCount=0;
		   int TotalOperators=0;
		   int TotalOperands=0;

		   OperatorCount=0;
		   for (int f : visitor.oprt.values()) {				
			   OperatorCount+= f;			
		   }
		   TotalOperators+=OperatorCount;

		   OperandCount=0;	
		   for (int f : visitor.names.values()) {
			   OperandCount += f;
		   }
		   TotalOperands+=OperandCount;


		   volumen = (TotalOperators+TotalOperands)*(Math.log(DistOperators+DistOperands)/Math.log(2));
		   
		   double dificultad = ((double)DistOperators/2) * ((double)TotalOperands/(double)DistOperands);
		   
		   esfuerzo = dificultad * volumen;

		   longitud = (TotalOperators+TotalOperands);
		   
			
	   		  System.out.println("variables : " + visitor.variables);
			  System.out.println("distintosOperadores : " + DistOperators);
			  System.out.println("totalOperadores : " + TotalOperators);
			  System.out.println("distintosOperandos : " + DistOperands);
			  System.out.println("totalOperandos : " + TotalOperands);
			  System.out.println("esfuerzo : " + esfuerzo); 
			  System.out.println("volumen : "+ volumen); 
			  System.out.println("longitud : " + longitud);
			  System.out.println("dificultad : " + dificultad);
			 
	   	   }
	   
   
	private static String readLineByLineJava8(String filePath)
    {
     //   StringBuilder contentBuilder = new StringBuilder();
 
     //   try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
     //   {
     //       stream.forEach(s -> contentBuilder.append(s).append("\n"));
     //   }
     //   catch (IOException e) 
     //   {
     //       e.printStackTrace();
     //   }
 
     //   return contentBuilder.toString();
		
		
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last new line separator
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stringBuilder.toString();
    }
	
	public int getCantidadLineasBlanco() {
		return cantidadLineasBlanco;
	}


	public void setCantidadLineasBlanco(int cantidadLineasBlanco) {
		this.cantidadLineasBlanco = cantidadLineasBlanco;
	}

	public int getCantidadLineasComentadas() {
		return cantidadLineasComentadas;
	}


	public void setCantidadLineasComentadas(int cantidadLineasComentadas) {
		this.cantidadLineasComentadas = cantidadLineasComentadas;
	}


	public int getCantidadTotalLineas() {
		return cantidadTotalLineas;
	}
	
	public void setCantidadTotalLineas(int cantidadTotalLineas) {
		this.cantidadTotalLineas = cantidadTotalLineas;
	}
	public int getCantidadSoloCodigo() {
		return cantidadSoloCodigo;
	}
	
	
	public void setCantidadSoloCodigo(int cantidadSoloCodigo) {
		this.cantidadSoloCodigo = cantidadSoloCodigo;
	}
	public double getProcentajeComentarios() {
		return procentajeComentarios;
	}


	public void setProcentajeComentarios(double procentajeComentarios) {
		this.procentajeComentarios = procentajeComentarios;
	}
	
	public ArrayList<String> getMetodos() {
		return metodos;
	}

	public void setMetodos(ArrayList<String> metodos) {
		this.metodos = metodos;
	}

	public double getVolumen() {
		return volumen;
	}
	
	public double getLongitud() {
		return longitud;
	}
	
	public double getEsfuerzo() {
		return esfuerzo;
	}

}
