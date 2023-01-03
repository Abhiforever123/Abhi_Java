package com.test.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadDataFromFile {

	
	public static void readDataFromTextBufferReader(String str) throws IOException
	{
		FileReader fr = new FileReader("path to the file");
		
		BufferedReader br = new BufferedReader(fr);
		
		while(br.readLine()!=null)
		{
			System.out.println(br.readLine());
		}
		
		br.close();
		
		
		
	}
	
	public static void readUsingScanner() throws FileNotFoundException
	{
		File file = new File("path to the file");
		
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine())
			System.out.println(scanner.hasNextLine());
		
	}
	
	public static void main(String[] args) {
		
	}
}
