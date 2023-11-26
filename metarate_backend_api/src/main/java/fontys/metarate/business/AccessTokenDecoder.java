package fontys.metarate.business;

import fontys.metarate.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
