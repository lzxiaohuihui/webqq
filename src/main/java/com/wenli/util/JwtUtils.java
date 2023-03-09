package com.wenli.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	private static final Long EXPIRE = 30 * 24 * 3600 * 1000L; //设置一天时间
	private static final String SECRET = "wzomg"; //用于signature（签名）部分解密

	//生成token
	public static String createJwt(String username) {
		Assert.notNull(username, "用户名不能为空");
		return Jwts.builder().setSubject(username)
				.claim("username", username)
				.setIssuedAt(new Date())
				.setId(UUID.randomUUID().toString())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
				.signWith(SignatureAlgorithm.HS256, SECRET).compact();
	}

	//解析token
	public static Claims parseJwt(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
	}

	public static void main(String[] args) {
		// System.out.println(createJwt("1851347833"));
		// String aa = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI4OTg5IiwidXNlcklkIjo4OTg5LCJ1c2VybmFtZSI6ImFhYSIsImlhdCI6MTYxNDc2NzY5NCwianRpIjoiZmIwYjU5ZjUtZGMwNy00YmY0LTg0MjEtOGY5ODNlMDQyODE1IiwiZXhwIjoxNjE0ODU0MDk1fQ.JeCJeID1RDM2Gah0A0AQo5xPxTH3fEDMR3maSxqn7A0";
		String token = "eyJhbGciOiJIUzI1NiJ9" +
				".eyJzdWIiOiIxODUxMzQ3ODMzIiwidXNlcm5hbWUiOiIxODUxMzQ3ODMzIiwiaWF0IjoxNjY4NjAxOTc3LCJqdGkiOiJiY2QyZWM4YS0yYTllLTQ3YmYtOGIyNy1kMTFkMjhhN2EyOTgiLCJleHAiOjE2NzExOTM5Nzd9.T6gSUqlXNTkhaRY24c6FVKi0oz4xJ46FEpOwNcUkOEs";
		System.out.println(parseJwt(token).get("username"));
	}

	//删除token
	public void removeToken(String token) {
	}
}