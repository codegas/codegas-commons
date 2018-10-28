package org.codegas.service.spock

import org.codegas.service.test.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.inject.Inject

/**
 * Base class of all Tests that use JPA for persistence
 */
@ContextConfiguration(["classpath*:META-INF/spring/test-context.xml"])
abstract class PersistenceTest extends Specification {

   @Inject
   protected TestEntityManager testEntityManager
}
