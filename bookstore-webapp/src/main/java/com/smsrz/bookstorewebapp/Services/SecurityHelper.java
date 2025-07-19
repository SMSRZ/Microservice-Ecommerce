package com.smsrz.bookstorewebapp.Services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public SecurityHelper(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    }

    public String getAccessToken(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(! (authentication instanceof OAuth2AuthenticationToken oauthToken)){
            return null;
        }
        OAuth2AuthorizedClient  client = oAuth2AuthorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),oauthToken.getName());

        return client.getAccessToken().getTokenValue();
    }
}
