package com.app.my_security.configuration;

import com.app.my_security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       final String requestTokenHeader=request.getHeader("Authorization");
       final Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        log.info("Request token from header has been created: ");
        String username=null;
        String jwtToken=null;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer"))
        {
            jwtToken=requestTokenHeader.substring(7);
            try{
                username=jwtUtils.extractUsername(jwtToken);
            }catch (ExpiredJwtException jwtException)
            {
              log.error("JwtAuthenticationFilter :: doFilterInternal :: Token has been expired: or Error due to: {}", jwtException.getMessage());
            }
            catch (Exception e)
            {
                log.error("JwtAuthenticationFilter :: doFilterInternal :: Exception found due to: {}", e.getMessage());
            }
        }
        else {
            log.error("JwtAuthenticationFilter :: doFilterInternal :: Invalid Token or Bad Credentials : Not start with bearer token.");
        }


        if(username!=null && authentication==null)
        {
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            if(jwtUtils.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
        }
        }
        else {
            log.error("JwtAuthenticationFilter >> doFilterInternal >> Invalid Token or bad credentials : Not start with bearer");
        }
         filterChain.doFilter(request, response);
    }
}
