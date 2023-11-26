package fontys.metarate.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private Long id;
    @NotBlank
    @Size(min = 2, max = 200)
    private String username;
    @NotBlank
    private String profilePic;
}
