module empapp {
    requires spring.core;
    requires spring.beans;
    requires org.slf4j;
    requires org.hibernate.orm.core;
    requires spring.aop;

    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.boot;
    requires static lombok;
    requires java.persistence;
    requires io.swagger.v3.oas.annotations;
    requires ehcache;
    requires spring.web;
    requires spring.data.jpa;
    requires modelmapper;
    requires spring.tx;

    // Caused by: java.lang.IllegalAccessException: class org.springframework.context.annotation.ConfigurationClassEnhancer$BeanFactoryAwareMethodInterceptor (in module spring.context) cannot access class empapp.EmployeesApplication$$EnhancerBySpringCGLIB$$d6b68b4 (in module empapp) because module empapp does not export empapp to module spring.context
    //	at java.base/jdk.internal.reflect.Reflection.newIllegalAccessException(Reflection.java:392) ~[na:na]
    exports empapp to modelmapper, spring.beans, spring.context, spring.web;

    // module empapp does not open empapp to module spring.core
    opens empapp to spring.core, ehcache, org.hibernate.orm.core;
}
