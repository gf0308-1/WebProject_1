package my.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class LoginUtil {
	private final static String K_CLIENT_ID = "f33eeb657bbf55d41c518f3585c89574";
	// 이런식으로 REDIRECT_URI를 써넣는다.
	private final static String K_REDIRECT_URI = "http://localhost:8080/weather/test";

	private final static String K_REDIRECT_LOGOUT = "http://localhost:8080/weather/logout";

	public static String getAuthorizationUrl(HttpSession session) {
		String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?" + "client_id=" + K_CLIENT_ID + "&redirect_uri="
				+ K_REDIRECT_URI + "&response_type=code";

		return kakaoUrl;
	}

	public static JsonNode getAccessToken(String autorize_code) {
		final String RequestUrl = "https://kauth.kakao.com/oauth/token";
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", K_CLIENT_ID));
		// REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", K_REDIRECT_URI));
		// 리다이렉트 URI
		postParams.add(new BasicNameValuePair("code", autorize_code));
		// 로그인 과정중 얻은 code 값 final
		HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);

		JsonNode returnNode = null;
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode;
	}
	// 카카오로그인한 유저의 정보 얻는 메소드
	public static JsonNode getKakaoUserInfo(JsonNode accessToken) {
		final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		// add header
		post.addHeader("Authorization", "Bearer " + accessToken);
		JsonNode returnNode = null;
		try {
			final HttpResponse response = client.execute(post);
			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// clear resources
		}
		return returnNode;
	}

	/*
	 * public static void Logout() throws Exception{ final String kakaoUrl =
	 * "https://kauth.kakao.com/oauth/logout?client_id="+K_CLIENT_ID+
	 * "&logout_redirect_uri="+K_REDIRECT_LOGOUT;
	 * 
	 * URL url = new URL(kakaoUrl); HttpURLConnection conn = (HttpURLConnection
	 * )url.openConnection();
	 * 
	 * 
	 * //POST방식으로 요청하기 위해 setDoOutput을 true로 설정/출력에 URL 연결을 사용하려면 DoOutput를 true로 설정
	 * conn.setRequestMethod("GET"); }
	 */

	// 로그 아웃 처리 메소드
	public static void Logout(String accToken) {
		try {
			final String R_LogOut_Url = "https://kapi.kakao.com/v1/user/logout";
			HttpPost post = new HttpPost(R_LogOut_Url);

			post.addHeader("Authorization", "Bearer " + accToken); // 로그아웃 request

			final String R_UnLink_Url = "https://kapi.kakao.com/v1/user/unlink"; // 연결 해제 request
			final HttpClient client = HttpClientBuilder.create().build();
			post = new HttpPost(R_UnLink_Url);
			post.addHeader("Authorization", "Bearer " + accToken);

			final HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				System.out.println(body);
			} else {
				System.out.println("응답 에러 : " + response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}