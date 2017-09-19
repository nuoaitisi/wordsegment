package control;

import java.util.Map;

import control.method.Operate;
import code.NLPIR;
import service.FileLoad2DB;

public class Participle {
	
	static boolean isload=true;
	
	static{
		if(!isload){
			//加载Compare到数据库compared上
			Operate.upLoad("NewArticle/", "compared");
			//加载Test到数据库test上
			Operate.upLoad("participleOutput2divide/", "test");
		}
	}
	
	public static void main(String[] args) {
		
		Map<String, String> comparedMap=null;
		
		
		
		boolean isrun=false;
		if(isrun){
			
			/*
			 * 1、生成原文章
			 */
			//获取分词后的文章
			comparedMap = FileLoad2DB.getFile("compared");
			//存入original_text表
			FileLoad2DB.save(comparedMap,"original_text",true);
			
			/*
			 * NLPIR分词
			 */
			Operate.Segment(new NLPIR(),"original_text","nlpir_word_segment");
			
			/*
			 * 对原文章进行矫正
			 */
			Operate.Proof("compared_correct","userdict_words","jieba_word_segment_userdict");
			Operate.Proof("compared_correct","userdict_words","nlpir_word_segment_userdict");
			Operate.Proof("compared_correct","userdict_words","thulac_word_segment_userdict");
		}
		
		
		
		Operate.ComparedToDiff("compared_correct", "nlpir_word_segment_userdict","nlpir_diff_userdict");
		Operate.ComparedToDiff("compared_correct", "jieba_word_segment_userdict","jieba_diff_userdict");
		Operate.ComparedToDiff("compared_correct", "thulac_word_segment_userdict","thulac_diff_userdict");
	}
	
}
