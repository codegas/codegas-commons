package org.codegas.service.spock

import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(["classpath*:META-INF/spring/test-context.xml"])
abstract class IntegrationTest extends PersistenceTest {

}
