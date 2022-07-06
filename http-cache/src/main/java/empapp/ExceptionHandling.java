package empapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionHandling implements ProblemHandling {

    @ExceptionHandler
    ResponseEntity<Problem> handleNotFoundException(
            NotFoundException exception,
            NativeWebRequest request) {
        return create(Status.NOT_FOUND, exception, request);
    }

    @ExceptionHandler
    ResponseEntity<Problem> handleVersionMismatchException(
            VersionMismatchException exception,
            NativeWebRequest request) {
        return create(Status.PRECONDITION_FAILED, exception, request);
    }

}
