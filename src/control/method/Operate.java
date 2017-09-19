package control.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import code.NLPIR;
import control.contrast.Contrast;
import control.proof.Proof;
import service.FileLoad2DB;
import strategy.Interface.WordSegmentStrategy;
import strategy.wordsegment.WordSegmentContext;
import Proxy.TraversalFileMapProxy;
import Util.Log;

public class Operate {
	
	static Map<String, String> comparedMap=null;
	static Map<String, String> compared_copy=null;
	static Map<String, String> contentMap=null;
	static Map<String, String> participleResult=null;
	static HashMap<String, ArrayList<String>> diffmap=null;
	static Map<String, String> testMap = null;
	static ArrayList<String> userDictList=null;
	//代理迭代遍历Map
	static TraversalFileMapProxy fileproxy=null;
	static String keyValue[]=null;
	static int bath=300;
	static int bathIndex=1;
	
	/**
	 * 
	 * <p>Title:</p>
	 * <p>Description：分别比较手工标注结果comparedTableName，与某分词工具分词结果testWordSegmentTableName，
	 * 					将二者的差异存入diffTableName中</p>
	 * <p>Company：17zuoye</p>
	 * @author Administrator
	 * @data comparedTableName：手工标注结果
	 * @data testWordSegmentTableName：某分词工具分词结果
	 * @data comparedTableName：comparedTableName与testWordSegmentTableName差异所保存的数据库表名
	 */
	public static void ComparedToDiff(String comparedTableName,String testWordSegmentTableName, String diffTableName){
		//通过比较nlpir_word_segment_userdict和compared对应文档分词结果生成nlpir_diff_userdic
		comparedMap=FileLoad2DB.getFile(comparedTableName);
		participleResult=FileLoad2DB.getFile(testWordSegmentTableName);
		//代理迭代遍历Map
		fileproxy=new TraversalFileMapProxy(comparedMap);
		keyValue=null;
		Log.setStart(comparedMap.size());
		
		diffmap=new HashMap<String, ArrayList<String>>();
		bath=300;
		bathIndex=1;
		while(fileproxy.hasNext()){
			keyValue=fileproxy.getNext();
			diffmap.putAll( Contrast.compared(keyValue[0],keyValue[1], participleResult.get(keyValue[0])) );
			if(++bathIndex > bath){
				FileLoad2DB.save(diffmap,diffTableName);
				bathIndex=1;
				diffmap.clear();
			}
			Log.tip();
		}
		if(bathIndex!=1)
			FileLoad2DB.save(diffmap,diffTableName);
		FileLoad2DB.close();
	}
	
	
	/**
	 * 
	 * <p>Title:</p>
	 * <p>Description：迭代fileDirPath路径下的所有文件录入到数据库tableName中</p>
	 * <p>Company：17zuoye</p>
	 * @author Administrator
	 * @data fileDirPath：迭代的文件所在文件下路径
	 * @data tableName：存入的数据库表名
	 */
	public static void upLoad(String fileDirPath, String tableName) {
		FileLoad2DB.FileUp(fileDirPath,tableName);
	}


	/**
	 * 
	 * <p>Title:</p>
	 * <p>Description：通过分词结果segmentReferenceTableName与手工标注ProofTableName进行对比，
	 * 				        如果存在差异，并且该差异在自定义用户字典userDictTableName中存在，
	 * 				        则说明，手工标注出错，对手工标注进行更正</p>
	 * <p>Company：17zuoye</p>
	 * @author Administrator
	 * @data ProofTableName：待更正的手工标注结果
	 * @data userDictTableName：自定义用户字典
	 * @data segmentReferenceTableName：分词结果
	 */
	public static void Proof(String ProofTableName, String userDictTableName, String segmentReferenceTableName) {
		//获得手工标注文档
		comparedMap=FileLoad2DB.getFile(ProofTableName);
		//获得用户字典
		userDictList = FileLoad2DB.getFile(userDictTableName,FileLoad2DB.COL_TWO);
		//获得结巴分词结果
		testMap = FileLoad2DB.getFile(segmentReferenceTableName);
		
		//原文矫正入口函数
		Proof.setProofTableName(ProofTableName);
		Proof.compared(comparedMap, testMap, userDictList);
	}
	
	public static void Segment(WordSegmentStrategy segmentStrategy, String originalText, String segmentSaveTableName){
		/*
		 * 4、使用NLPIR进行分词
		 */
		//获得原文档
		contentMap=FileLoad2DB.getFile(originalText);
		//初始化分词工具
		WordSegmentContext wordSegemntContxt=new WordSegmentContext(segmentStrategy);
		//代理迭代遍历Map
		fileproxy=new TraversalFileMapProxy(contentMap);
		String keyValue[]=null;
		participleResult=new HashMap<String, String>();
		while(fileproxy.hasNext()){
			keyValue=fileproxy.getNext();
			//文档分词，并将结果存入participleResult中
			participleResult.put(keyValue[0], wordSegemntContxt.cut(keyValue[1],false));
		}
		//分词结束，退出
		wordSegemntContxt.close();
		//存入nlpir_word_segment表
		FileLoad2DB.save(participleResult,segmentSaveTableName,false);
	}
	
	

}

