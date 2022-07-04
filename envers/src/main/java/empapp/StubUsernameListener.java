package empapp;

import org.hibernate.envers.RevisionListener;

public class StubUsernameListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        if (revisionEntity instanceof EmployeeRevisionEntity) {
            EmployeeRevisionEntity employeeRevisionEntity = (EmployeeRevisionEntity) revisionEntity;
            employeeRevisionEntity.setUsername("admin");
        }
    }
}
