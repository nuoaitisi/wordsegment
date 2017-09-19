package Proxy;

import java.util.ArrayList;
import java.util.Map;

/**
 * 提供对Map的迭代代理
 * @author Administrator
 *
 */
public class TraversalFileMapProxy {
	private Map<String, String> testMap = null;
	private ArrayList<String> keys=null;
	private int keysNum=0;
	private static int keysIndex=-1; 
	

	public TraversalFileMapProxy(Map<String, String> testMap) {
		// TODO Auto-generated method stub
		this.testMap=testMap;
		keys=new ArrayList<String>();
		for(String key:testMap.keySet()){
			keys.add(key);
		}
		keysNum=keys.size();
		keysIndex=-1;
	}
	
	/**
	 * 
	 * <p>Title:返回当前迭代的数据</p>
	 * <p>Description：</p>
	 * <p>Company：17zuoye</p>
	 * @author Administrator
	 * @data arg1：key; arg2：content
	 */
	public String[] getNext() {
		keysIndex++;
		return new String[]{keys.get(keysIndex),testMap.get(keys.get(keysIndex))};
	}
	
	public boolean hasNext(){
		if(keysNum-keysIndex>=2)
			return true;
		else 
			return false;
	} 
	
}
