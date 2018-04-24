package org.codegas.commons.lang.value

import spock.lang.Specification

class EmailTest extends Specification {

    def "Invalid Emails cannot be created"() {
        when:
        Email.fromString(email)

        then:
        thrown(IllegalArgumentException)

        where:
        email << ["", "a", "a@a."]
    }

    def "Valid Emails can be created"() {
        when:
        Email.fromString(email)

        then:
        notThrown(Exception)

        where:
        email << ["a@a.net", "someone@somedomain.com"]
    }
}
