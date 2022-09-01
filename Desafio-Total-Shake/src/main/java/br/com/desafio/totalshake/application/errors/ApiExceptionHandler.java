package br.com.desafio.totalshake.application.errors;

import br.com.desafio.totalshake.application.errors.exceptions.ItemInexistenteException;
import br.com.desafio.totalshake.application.errors.exceptions.PedidoInexistenteException;
import br.com.desafio.totalshake.application.errors.exceptions.QuantidadeInvalidaException;
import br.com.desafio.totalshake.application.errors.exceptions.StatusInvalidoException;
import br.com.desafio.totalshake.application.errors.response.ErroCampoResponseDTO;
import br.com.desafio.totalshake.application.errors.response.ErroStatusPedidoDTO;
import br.com.desafio.totalshake.application.errors.response.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex ,
                                                                                      WebRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                CodInternoErroApi.AP001.getMensagem(),
                CodInternoErroApi.AP001.getCodigo(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map(errorField -> new ErroCampoResponseDTO(
                                errorField.getDefaultMessage(),
                                errorField.getField()
                        ))
                        .collect(Collectors.toList())
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
    @ExceptionHandler(PedidoInexistenteException.class)
    public ResponseEntity<ExceptionResponseDTO> handlePedidoInexistenteException(PedidoInexistenteException ex, WebRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getCodInternoErro()
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ItemInexistenteException.class)
    public ResponseEntity<ExceptionResponseDTO> handleItemInexistenteException(ItemInexistenteException ex, WebRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                ex.getCodInternoErro()
        );
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(QuantidadeInvalidaException.class)
    public ResponseEntity<ExceptionResponseDTO> handleQuantidadeInvalidaException(QuantidadeInvalidaException ex, WebRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getCodInternoErro()
        );
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<ExceptionResponseDTO> handleStatusInvalidoException(StatusInvalidoException ex, WebRequest request) {
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                ex.getCodInterno(),
                new ErroStatusPedidoDTO(ex.getStatus())
        );
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
