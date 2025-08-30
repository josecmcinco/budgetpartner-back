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

/**
 * Servicio encargado de la generación y validación de tokens JWT.
 * Permite crear tokens de acceso y refresh, validar su vigencia
 * y extraer información del usuario.
 */
@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    /**
     * Genera un token de acceso para un usuario.
     *
     * @param usuario usuario para el cual se genera el token
     * @return token JWT de acceso
     */
    public String generateToken(final Usuario usuario){
        return buildToken(usuario, jwtExpiration);
    }


    /**
     * Genera un token de refresh para un usuario.
     *
     * @param usuario usuario para el cual se genera el token
     * @return token JWT de refresh
     */
    public String generateTokenRefresh(final Usuario usuario){
        return buildToken(usuario, refreshExpiration);
    }


    //Construye un token JWT con la información del usuario y tiempo de expiración
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


    /**
     * Extrae el email del usuario a partir del token.
     *
     * @param token JWT válido
     * @return email del usuario contenido en el token
     */
    public  String extractEmailUsuario(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();//Info pública del token
        return jwtToken.getSubject();
    }

    /**
     * Valida si un token corresponde a un usuario y no está expirado.
     *
     * @param token   JWT a validar
     * @param usuario usuario a comparar
     * @return true si el token es válido y corresponde al usuario
     */
    public boolean isTokenValid(final String token, final Usuario usuario){
        final String nombreUsuario = extractEmailUsuario(token);
        return (nombreUsuario.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    /**
     * Comprueba si un token JWT ha expirado.
     *
     * @param token JWT a comprobar
     * @return true si el token ha expirado
     */
    public boolean isTokenExpired(final String token){

        return  extractExpiration(token).before(new Date());
    }

    // Extrae la fecha de expiración de un token JWT
    private Date extractExpiration(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return jwtToken.getExpiration();
    }


}
