package fontys.metarate.business.accesstoken;

import fontys.metarate.business.exception.InvalidAccessTokenException;
import fontys.metarate.business.impl.AccessTokenEncoderDecoderImpl;
import fontys.metarate.domain.AccessToken;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessTokenEncoderDecoderTest {
    private static final String TEST_SUBJECT = "testUser";
    private static final List<String> TEST_ROLES = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
    private static final Long TEST_USER_ID = 123L;

    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;
    private Key key;

    @BeforeEach
    void setUp() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String keyString = Encoders.BASE64.encode(key.getEncoded());
        accessTokenEncoderDecoder = new AccessTokenEncoderDecoderImpl(keyString);
    }


    @Test
    void testEncode() {
        // Test data
        AccessToken accessToken = AccessToken.builder()
                .subject(TEST_SUBJECT)
                .roles(TEST_ROLES)
                .userId(TEST_USER_ID)
                .build();

        // Encode the access token
        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);

        // Assert
        assertNotNull(encodedToken);
    }

    @Test
    void testDecode_ValidToken() {
        // Test data
        AccessToken accessToken = AccessToken.builder()
                .subject(TEST_SUBJECT)
                .roles(TEST_ROLES)
                .userId(TEST_USER_ID)
                .build();

        // Encode the access token
        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);

        // Decode the access token
        AccessToken decodedToken = accessTokenEncoderDecoder.decode(encodedToken);

        // Assertions
        assertEquals(accessToken.getSubject(), decodedToken.getSubject());
        assertEquals(accessToken.getRoles(), decodedToken.getRoles());
        assertEquals(accessToken.getUserId(), decodedToken.getUserId());
    }

    @Test
    void testDecode_InvalidToken() {
        // Invalid token with tampered signature
        String invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiaWQiOjEyM30.XN76rdhf-9i3Dp4z0eZbLfg4VB7QlT0h3K-4bldRBz8";

        // Assert
        assertThrows(InvalidAccessTokenException.class, () -> accessTokenEncoderDecoder.decode(invalidToken));
    }
}
