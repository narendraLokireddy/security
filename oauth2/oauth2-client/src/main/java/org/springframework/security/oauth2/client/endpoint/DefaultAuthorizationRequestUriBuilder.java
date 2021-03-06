/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.client.endpoint;

import org.springframework.security.oauth2.core.endpoint.AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.AuthorizationRequestUriBuilder;
import org.springframework.security.oauth2.core.endpoint.OAuth2Parameter;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Set;

/**
 * The default implementation of an {@link AuthorizationRequestUriBuilder},
 * which internally uses a {@link UriComponentsBuilder} to construct the <i>OAuth 2.0 Authorization Request</i>.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see AuthorizationRequestUriBuilder
 * @see AuthorizationRequest
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-4.1.1">Section 4.1.1 Authorization Code Grant Request</a>
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-4.2.1">Section 4.2.1 Implicit Grant Request</a>
 */
public class DefaultAuthorizationRequestUriBuilder implements AuthorizationRequestUriBuilder {

	@Override
	public URI build(AuthorizationRequest authorizationRequest) {
		Set<String> scopes = authorizationRequest.getScopes();
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
			.fromUriString(authorizationRequest.getAuthorizationUri())
			.queryParam(OAuth2Parameter.RESPONSE_TYPE, authorizationRequest.getResponseType().getValue())
			.queryParam(OAuth2Parameter.CLIENT_ID, authorizationRequest.getClientId())
			.queryParam(OAuth2Parameter.SCOPE, StringUtils.collectionToDelimitedString(scopes, " "))
			.queryParam(OAuth2Parameter.STATE, authorizationRequest.getState());
		if (authorizationRequest.getRedirectUri() != null) {
			uriBuilder.queryParam(OAuth2Parameter.REDIRECT_URI, authorizationRequest.getRedirectUri());
		}

		return uriBuilder.build().encode().toUri();
	}
}
