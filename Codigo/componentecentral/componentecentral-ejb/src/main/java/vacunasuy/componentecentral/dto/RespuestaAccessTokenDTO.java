package vacunasuy.componentecentral.dto;

import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaAccessTokenDTO {
	
	private String access_token;
    private String refresh_token;
    private String token_type;
    private String expires_in;
    private String id_token;

}
