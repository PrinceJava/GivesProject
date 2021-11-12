package com.javaproject.javaprojectthree.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// ALL ONE BIG METHOD
// loos for Authorization in header and takes the entire header
// assumes JWT and username are null, then does an if NOT NUL statement to parse
// Looks for "Bearer " string than takes the substring starting at 7 for the JWT
// takes the JWT, sends it to JwtUtils.extractUserName(JWT) to assign username value


@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService){this.userDetailsService = userDetailsService;}

    private JWTUtils jwtUtils;

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils){this.jwtUtils = jwtUtils;}

    //When any api will be called this method will be called first and this will extract
    // Token from header pass to JWT Util calls for token details extraction
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        final String AuthorizationHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // HERE IS WHERE I NEED TO CREATE THE ROLES LIST IN COLLECTION
        //Checking if Bearer is present in Header or not because When sending api request with
        // token bearer should be present in that
        if (AuthorizationHeader != null && AuthorizationHeader.startsWith("Bearer ")) {
            jwt = AuthorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        // POSSIBLE TODO IS UPDATE THIS TO INCLUDE ROLE IN HEADER
        //Token is being passed to JwtUtil class for details extraction
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // only place to call the UserDetailsService interface method and sets UserDetails to it
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken
                        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
