package vacunasuy.componentecentral.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RespuestaAccessTokenDTO {
	
	private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String id_token;

}
