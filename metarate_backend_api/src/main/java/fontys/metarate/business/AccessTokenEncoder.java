package fontys.metarate.business;

import fontys.metarate.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
