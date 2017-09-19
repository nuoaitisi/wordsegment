package luan;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import strategy.wordsegment.WordSegmentContext;
import code.NLPIR;
import Util.Util;

public class Test2 {
	
	private static Set<String> words=new HashSet<String>();
	
	public static void main(String[] args) {

		/* 对words文件与stop文件
		  Util.distinct(new File("diff/stop.txt"));
		  Util.distinct(new File("diff/words.txt"));
		  */
		Util.distinct(new File("diff/userdict.txt"));
		
		
		//读取tmp中对疑似字典进行再次分词，如果不能分说明是真的词典，否则还需考虑
		String lines=Util.getContent(new File("diff/stop.txt"));
		//Collection<String> lines.split("\n").
		//WordSegmentContext wordSegemntContxt=new WordSegmentContext(new NLPIR());
		Set<String> stop=new HashSet<String>();
		for(String str:lines.split("\n")){
			stop.add(str);
		}

		lines=Util.getContent(new File("diff/words.txt"));
		Set<String> words=new HashSet<String>();
		for(String str:lines.split("\n")){
			words.add(str);
		}
		
		
		for(String key:words){
			if(stop.contains(key)){
				System.out.println(key);
			}
		}
	}

}
