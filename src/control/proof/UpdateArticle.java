package control.proof;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import service.FileLoad2DB;

public class UpdateArticle {
	private static String articleName="";
	private static String article="";
	private static String error="";
	private static String textid="";
	
	private static ArrayList<Character> stopword=new ArrayList<Character>();//停用词
	
	
	static{
		stopword.add('“');
		stopword.add('“');
		stopword.add('”');
		stopword.add('，');
		stopword.add(',');
		stopword.add('：');
		stopword.add('．');
		stopword.add('？');
		stopword.add('；');
		stopword.add(' ');
		stopword.add('．');
		stopword.add('﻿');		
		stopword.add('\n');
	}
	
	
	public static void setArticle(String articleID,String articles, String errors) {
		
		error=errors;//出现的错误
		if(article.equals("")){
			article=articles;//问题内容
			textid=articleID;
		}
		update();//articles,errors
	}
	
	public static void flush(String tablename){
		if(textid.equals("") && article.equals(""))
			return;
		saveFile(tablename);
		//articleName="";
		textid="";
		article="";
		error="";
	}

	public static void saveFile(String tablename) {
		//存入nlpir_word_segment表
		FileLoad2DB.update(textid,article,tablename,"content");
	}

	private static String gettxtcount(int i,int len) {
		if(len==4)
			return ""+i;
		else
			return "0"+gettxtcount(i,len+1);
	}

	private static void update() {
		//error，article
		String tmp[]=error.split("");
		StringBuilder sb=new StringBuilder(article);
		int i=0;
		int start=0;
		int end=0;
		for(int j=0;j<sb.length();j++){
			if(isStopword(sb.charAt(j))){
				continue;
			}else{
				if(tmp[i].equals(sb.charAt(j)+"")){
					if(i==0)
						start=j;
					if((i+1)<tmp.length)
						i++;
					else{
						end=j;
						//article=article.substring(0, start)+error+article.substring(end+1);
						article=article.substring(0, start)+error+article.substring(end+1);
						sb=new StringBuilder(article);
						j=j-(end-start)+tmp.length;
						//System.out.println(start);
						//System.out.println(end);
						//System.out.println(article.substring(start,end+1));
						i=0;
						continue;
					}
				}else{
					i=0;
				}
			}
		}
	}

	private static boolean isStopword(char charAt) {
		// TODO Auto-generated method stub
		if(stopword.contains(charAt))
			return true;
		else
			return false;
	}
	
	
}
