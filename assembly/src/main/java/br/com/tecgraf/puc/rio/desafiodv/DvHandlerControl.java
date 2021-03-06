package br.com.tecgraf.puc.rio.desafiodv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Paths;

/**
 * Classe de controle para os processamento de digito verificador
 *
 */
public class DvHandlerControl {
	
	/**
	 * Executa o comando informado a partir dos parâmetros
	 * @param param Parameter parâmetros informados
	 * @throws Exception
	 */
	public void execute(Parameter param) throws Exception {
		
		String opt = param.getCommand();
		switch (opt) {
			case "-G": {
				generateMatriculasWithDv(param.getInFilePathName(), param.getOuFilePathName());
				break;
			}
			
			case "-V": {
				validateMatriculaWithDv(param.getInFilePathName(), param.getOuFilePathName());
				break;
			}
			default: {
			
			}
		}
	}
	
	/**
	 * Executa o processamento de geração de arquivo com matriculas com DV
	 * @param inputFile String contendo o path do arquivo com as matriculas sem DV
	 * @param outputFile String contendo o path do arquivo a ser gerado com as matriculas com DV
	 * @throws Exception
	 */
	private void generateMatriculasWithDv(String inputFile, String outputFile) throws Exception {
		
		String in = Paths.get(inputFile).toAbsolutePath().normalize().toString();
		String out = Paths.get(outputFile).toAbsolutePath().normalize().toString();
		
		try (Reader reader = new InputStreamReader(new FileInputStream(in), "UTF-8");
			 BufferedReader linhas = new BufferedReader(reader);
			 Writer writer = new OutputStreamWriter(new FileOutputStream(out), "UTF-8");
			 BufferedWriter	linhasWrite = new BufferedWriter(writer)) {
			
			String matricula = null;
			
		    while ((matricula = linhas.readLine()) != null) {
		        String dv = Matricula.calculateDv(matricula);
		        if (dv != null) {
		        	linhasWrite.write(matricula + "-" + dv);
		        	linhasWrite.newLine();
		        } else {
		        	linhasWrite.write(matricula + "- matricula inválida");
		        	linhasWrite.newLine();
		        }
		    }
			
			
		} catch (IOException io) {
			System.err.println("Erro na geração do arquivo de matriculas com DV");
			throw io;
		} 
	}
	
	/**
	 * Executa o processmento de validação dos DV's em um arquivo. Gera um arquivo com
	 * o resultado do processamento informando se a matricula está com o dv correto ou errado
	 * @param inputFile String contendo o path do arquivo com as matriculas com DV para validar
	 * @param outputFile String contendo o path do arquivo com as matriculas com DV validadas
	 * @throws Exception
	 */
	private void validateMatriculaWithDv(String inputFile, String outputFile) throws Exception {
		
		String in = Paths.get(inputFile).toAbsolutePath().normalize().toString();
		String out = Paths.get(outputFile).toAbsolutePath().normalize().toString();
		
		try (Reader reader = new InputStreamReader(new FileInputStream(in), "UTF-8");
				 BufferedReader linhas = new BufferedReader(reader);
				 Writer writer = new OutputStreamWriter(new FileOutputStream(out), "UTF-8");
				 BufferedWriter	linhasWrite = new BufferedWriter(writer)) {
				
				String matricula = null;
				
			    while ((matricula = linhas.readLine()) != null) {
			    	
			    	if (Matricula.isValidDv(matricula)) {
			    		linhasWrite.write(matricula + " verdadeiro");
			        	linhasWrite.newLine();
			    	} else {
			    		linhasWrite.write(matricula + " falso");
			        	linhasWrite.newLine();
			    	}
			    }
				
				
			} catch (IOException io) {
				System.err.println("Erro na geração do arquivo de matriculas com DV");
				throw io;
			} 

	}

}
