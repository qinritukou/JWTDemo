package com.example.jwtdemo.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JWTUtil {

    fun extractUsername(token: String): String {
        return extractClaim(token, Function { obj: Claims -> obj.subject })
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Function { obj: Claims -> obj.expiration })
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        return createToken(userDetails.username)
    }

    private fun createToken(subject: String): String {
        val claims = Jwts.claims().setIssuedAt(Date())
                .setSubject(subject)
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    companion object {
        const val SECRET_KEY = "secret"
    }
}