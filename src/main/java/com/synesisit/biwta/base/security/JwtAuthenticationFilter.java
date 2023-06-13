package com.synesisit.biwta.base.security;

import com.synesisit.biwta.base.helper.AppConstants;
import com.synesisit.biwta.base.payload.ApiResponse;
import com.synesisit.biwta.base.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private ArrayList authorities;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                List<String> authorities = tokenProvider.getAuthorities(jwt);
                logger.info(String.valueOf(userId));
                logger.info(authorities.toString());

//                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void authenticationFromACL(HttpServletRequest request, String jwt){
        if(!ObjectUtils.isEmpty(jwt)){
            String client_url = AppConstants.AUTH_MODULE_URL+"api/auth/user/jwt";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+jwt);
            HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
            ResponseEntity<?> result = restTemplate.exchange(client_url, HttpMethod.GET,
                    jwtEntity, ApiResponse.class);

            if(!ObjectUtils.isEmpty(result.getBody())){
                    /*UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) result.getBody();
                    SecurityContextHolder.getContext().setAuthentication(authentication);*/
                ApiResponse apiResponse = (ApiResponse) result.getBody();
                Map authenticationData = (Map) apiResponse.getData();
                Map authentic = (Map) authenticationData.get("authentication");
                //Map auth = (Map) authentic.get("authentication");
                Map userPrinciple = (Map) authentic.get("principal");

                if(!ObjectUtils.isEmpty(userPrinciple)){
                    Integer userPrincipalId = (Integer) userPrinciple.get("id");
                    String userPrincipalName = (String) userPrinciple.get("name");
                    String userPrincipalUserName = (String) userPrinciple.get("username");
                    authorities = (ArrayList) authentic.get("authorities");
                    Set<GrantedAuthority> grantedAuthorities = UserPrincipal.convertToGrantedAuthority(authorities);
                    UserDetails userDetails = new UserPrincipal((long) userPrincipalId,userPrincipalName, userPrincipalUserName, "", "", grantedAuthorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    //UsernamePasswordAuthenticationToken aaa = new UsernamePasswordAuthenticationToken();
                }
            }
        }
    }

}
