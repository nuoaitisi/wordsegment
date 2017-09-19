package control;

import java.util.Map;

import Proxy.TraversalFileMapProxy;
import service.FileLoad2DB;
import code.CWordSeg.CLibrary;
import code.NLPIR;

public class NLPIRParticiple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*//获取文本内容
		Map<String, String> comparedMap = FileLoad2DB.getFile("compared");
		Map<String, String> testMap = FileLoad2DB.getFile("test");
		//通过NLPIR进行分词
		TraversalFileMapProxy fileproxy=new TraversalFileMapProxy(testMap);
		int i=0;
		while(fileproxy.hasNext()){
			String[] content = fileproxy.getNext();
			NLPIR nlpir=new NLPIR();
			nlpir.addUserDict("file//dict//userdict.txt");
			//nlpir.participle(content[1]);
			nlpir.participle("区间");
		}*/
		
	}

}
