package vetclinic.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class CustomResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;

    public ResponseEntity<CustomResponse<T>> generateResponseEntity(){
        return new ResponseEntity<>(this, status);
    }
}
