package control.proof;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;



public class Proof {
	
	private static int totall=0;
	private static int diff=0;
	private static String ProofTablName=null;
	private static String currentId="";
	
	//记录每个测试contentID对应的正确率
	private static Map map=new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> user_dict=null;

	
	public static HashMap<String, ArrayList<String>> compared(Map<String, String> comparedMap,
			Map<String, String> testMap, ArrayList<String> userDict){
		user_dict=userDict;
		String lineCompared="";
		String lineTest="";
		for(Entry<String,String> en:comparedMap.entrySet()){
			currentId=en.getKey();
			lineCompared=en.getValue();
			lineTest=testMap.get(currentId);
			contrast(stopWord(lineCompared),stopWord(lineTest),lineCompared);
			//将修改的最后一个缓存数据保存入数据库
			UpdateArticle.flush(ProofTablName);
		}
		return (HashMap<String, ArrayList<String>>) map;
	}
	
	
	public static void setProofTableName(String tablename){
		ProofTablName=tablename;
	}
	
	public static HashMap<String, ArrayList<String>> compared(String currentid,
			String lineCompared,String lineTest){
		currentId=currentid;
		if(currentid.contains("108646"))
			System.out.println();
		contrast(stopWord(lineCompared),stopWord(lineTest),lineCompared);
		//showResult();
		return (HashMap<String, ArrayList<String>>) map;
	}
	
	
	@SuppressWarnings("unchecked")
	private static void contrast(String lineCompared, String lineTest, String comparedCopy) {
		// TODO Auto-generated method stub
		ArrayList<String> errorWord=null;	
		
		//去除BOM-UTF8的BOM头
		if((int)lineCompared.charAt(0)==65279){
			lineCompared=lineCompared.substring(1);
		}
		if((int)lineTest.charAt(0)==65279){
			lineTest=lineTest.substring(1);
		}
		
		
		String elementDone[] = lineCompared.split(" ");
		String elementTest[] = lineTest.split(" ");
		String key="";
		String error="";
		//统计标准库所有单词总量
		totall+=elementDone.length;
		//i辅助遍历elementDone; j辅助遍历elementTest
		int i=0;
		int j=0;
		while(i<elementDone.length || j<elementTest.length){
			//如果当前比较的词相等
			if(i<elementDone.length && j<elementTest.length){
				if(elementDone[i].equals(elementTest[j])){
					i++;
					j++;
				}else{
						//如果不相等
						//记录出错的分词
						errorWord=new ArrayList<String>();
						
						//Show.setString(elementDone[i]);
						
						String doneCh = "";
						String testCh = "";
						doneCh=elementDone[i];
						
						
						
						
						/*if((i-1>=0))	key=elementDone[i-1]+","+elementDone[i];
						ele	key=elementDone[i];*/
						key=elementDone[i];
						error=elementTest[j];
						
						
						testCh=elementTest[j];
						String str="";
						
						while(!testCh.equals(doneCh) && (testCh.contains(doneCh) || doneCh.contains(testCh))){
							diff++;
							//对其i和j
							
							if(testCh.contains(doneCh)){
								if((i+1)>=elementDone.length){
									j++;
									break; 
								}else {
									String tmp=elementDone[++i];
									
									str=doneCh;
									doneCh+=tmp;
									str+=" "+tmp;
									key=key+" & "+tmp;
									/*if((i+1)<elementDone.length)	key=key+","+str+","+elementDone[(i+1)];
									else key=key+","+str;*/
								}
							}else{
								if((j+1)>=elementTest.length){
									i++;
									break;
								}else {
									String tmp=elementTest[++j];
									
									str=testCh;
									testCh+=tmp;
									str+=" "+tmp;
									
									//errorWord.add(str);
									error+=" & "+tmp;
								}
							}
						}
						if(testCh.equals(doneCh)){
							if(user_dict.contains(error)) {
								UpdateArticle.setArticle(currentId, comparedCopy,error);
							}
							errorWord.add(error);
							i++;
							j++;
						}else {
							System.out.println("3");
							diff++;
							i++;
							j++;
						}
						
						/*if(start-1>=1)
							key=elementDone[start-2]+","+elementDone[start-1]+","+key;
						if(end<elementDone.length)
							key=key+","+elementDone[end];*/
						if(errorWord.size()==0)
							System.out.println();
						map.put(currentId+","+key, errorWord);
				}
			}else if(i<elementDone.length){
				//System.out.println("1");
				i++;
				totall++;
				diff++;
			}else if(j<elementTest.length){
				//System.out.println("2");
				j++;
				totall++;
				diff++;
			}
		}
	}
	
	private static String stopWord(String str) {
		return str.replaceAll("“", " ").replaceAll("”", " ").replaceAll("，", " ").replaceAll("\\.", " ").replaceAll(",", " ").replaceAll("：", " ").replaceAll("．", " ").replaceAll("？", " ").replaceAll("；", " ").replaceAll("\\s+", " ");
	}
	
	
	private static void showResult() {
		System.out.println("总共词汇有（包含重复）："+totall);
		System.out.println("正确率有："+(double)(totall-diff)/totall);
		
		//PrintWriter pw=new PrintWriter(new File("participle/reportZZ.txt"));
		
		//Show.show();
		
		Set<Entry<String,ArrayList<String>>> set = map.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()){
			Entry<String,ArrayList<String>> en=(Entry<String, ArrayList<String>>) it.next();
			System.out.println(en.getKey()+"\t"+en.getValue());
		}
	}

}
