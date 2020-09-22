package my.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

import my.vo.ResVO;

public class InfoUtil {
	

		
	public static ResVO getInfo(String pk) throws IOException {
		//식당정보 json obj로 가져오기
		final String resURL = "https://map.naver.com/v5/api/sites/summary/"+pk+"?lang=ko"; //음식 url

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpGet get = new HttpGet(resURL); //get방식으로 보냄
	
		//JsonNode returnNode = null;
		JsonNode returnNode = null;
		
		final HttpResponse response = client.execute(get);

		//JSON 형태 반환값 처리
		ObjectMapper mapper = new ObjectMapper();
		
		//반환 되는 JSON이 담기는 곳
		returnNode = mapper.readTree(response.getEntity().getContent());
						
		List<Map<String, Object>> list = mapper.readValue(returnNode.get("menus"), new TypeReference<List<Map<String,Object>>>() {});
		
		
		
		ResVO vo = new ResVO();
		if (returnNode.get("imageURL") == null || returnNode.get("imageURL").size() < 0) {
			vo.setUrl("resources/img/notfound.jpg");
		}
		
			
		
		//url 이미지 특수문자 " 없애기
		String url = returnNode.get("imageURL").toString();
		vo.setUrl(url.substring(1, url.lastIndexOf("\"")));
		vo.setId(returnNode.get("id").toString());
		vo.setName(returnNode.get("name").toString());
		vo.setBizhourInfo(returnNode.get("bizhourInfo").toString());
		vo.setPhone(returnNode.get("phone").toString());
		vo.setAddress(returnNode.get("address").toString());
			
		List<String> list2 = new ArrayList<String>();
		for(int i = 0 ; i <list.size();i++) {
			list2.add(i,list.get(i).get("name").toString());
		}		
		String menuList = list2.toString();
		vo.setMenu(menuList.substring(1, menuList.lastIndexOf("]")));
		
		
		return vo;		
	}
	
}
