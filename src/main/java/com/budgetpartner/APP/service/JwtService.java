package com.budgetpartner.APP.service;

import com.budgetpartner.APP.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(final Usuario usuario){
        return buildToken(usuario, jwtExpiration);
    }

    public String generateTokenRefresh(final Usuario usuario){
        return buildToken(usuario, refreshExpiration);
    }

    //Genera nuevo token de cualquier tipo
    private String buildToken(final Usuario usuario, final long expiration){
        return Jwts.builder()
                .id(usuario.getId().toString())
                .claims(Map.of("name", usuario.getNombre()))
                .subject(usuario.getEmail())
                .expiration(new Date(System.currentTimeMillis()+  expiration))
                .signWith(getSignInKey())
                .compact();
    }

    //Generador de claves codificadas
    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public  String extractEmailUsuario(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();//Info pública del token
        return jwtToken.getSubject();
    }

    public boolean isTokenValid(final String token, final Usuario usuario){
        final String nombreUsuario = extractEmailUsuario(token);
        return (nombreUsuario.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(final String token){

        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.getExpiration();
    }


}
