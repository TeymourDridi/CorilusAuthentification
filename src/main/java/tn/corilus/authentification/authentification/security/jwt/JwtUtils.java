package tn.corilus.authentification.authentification.security.jwt;


import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import tn.corilus.authentification.authentification.service.UserDetailsImpl;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${warskoder.app.jwtSecret}")
  private String jwtSecret;

  @Value("${warskoder.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${warskoder.app.jwtCookieName}")
  private String jwtCookie;

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername(),userPrincipal.getAuthorities());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).domain("reacterrordetectiondeployement.azurewebsites.net").path("/").maxAge( 24 * 60 * 60).httpOnly(false).sameSite("None").secure(true).build();
    return cookie;
  }

  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
    return cookie;
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String generateTokenFromUsername(String username, Collection<? extends GrantedAuthority> authorities) {
    List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}
