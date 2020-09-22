package com.ict.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCSV2 {
	    public static void main(String[] args){
	        //출력 스트림 생성
	        BufferedWriter bufWriter = null;
	        try{
	        	//csv파일 읽기 
/////////////////이거 위치 바뀌면 읽어오기 전에 새롭게 버퍼로 밀어버려서 원본 데이터 삭제됨 ㅇㅇ 조심!/////////////////////
	        	List<List<String>> allData = readCSV();
	        	
	            bufWriter = Files.newBufferedWriter(Paths.get("C:\\\\upload\\\\ex.csv"),Charset.forName("UTF-8"));
	            
	            
	            for(List<String> newLine : allData){
	                List<String> list = newLine;
	                for(String data : list){
	                    bufWriter.write(data);
	                    bufWriter.write(",");
	                }
	                //추가하기
	                bufWriter.write("주소");
	                //개행코드추가
	                bufWriter.newLine();
	            }
	        }catch(FileNotFoundException e) {
	            e.printStackTrace();
	        }catch(IOException e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(bufWriter != null){
	                    bufWriter.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    public static List<List<String>> readCSV(){
	        //반환용 리스트
	        List<List<String>> ret = new ArrayList<List<String>>();
	        BufferedReader br = null;
	        
	        try{
	            br = Files.newBufferedReader(Paths.get("C:\\\\upload\\\\ex.csv"));
	            //Charset.forName("UTF-8");
	            String line = "";
	            
	            while((line = br.readLine()) != null){
	                //CSV 1행을 저장하는 리스트
	                List<String> tmpList = new ArrayList<String>();
	                String array[] = line.split(",");
	                //배열에서 리스트 반환
	                tmpList = Arrays.asList(array);
	                System.out.println(tmpList);
	                ret.add(tmpList);
	            }
	        }catch(FileNotFoundException e){
	            e.printStackTrace();
	        }catch(IOException e){
	            e.printStackTrace();
	        }finally{
	            try{
	                if(br != null){
	                    br.close();
	                }
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	        return ret;
	    }

}
