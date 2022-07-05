package empapp;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class VersionMissmatchException extends AbstractThrowableProblem {

    public VersionMissmatchException() {
        super(URI.create("employees/version-mismatch"),
                "Version mismatch",
                Status.PRECONDITION_FAILED,
                String.format("Version mismatch"));
    }
}
