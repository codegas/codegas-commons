package org.codegas.commons.lang.collection

import spock.lang.Specification

class CollectionUtilTest extends Specification {

   def "A Collection can be reversed in to a new List"() {
      given:
      def original = [2, 3, 5, 7]

      when:
      def result = CollectionUtil.reverse(original)

      then:
      original != result
      result == [7, 5, 3, 2]
   }

   def "Two Collections can be concatenated in to a new List"() {
      expect:
      expectedResult == CollectionUtil.concat(collection1, collection2)

      where:
      expectedResult | collection1 | collection2
      []             | []          | []
      [1]            | [1]         | []
      [1]            | []          | [1]
      [1,2]          | [1]         | [2]
   }

   def "A Collection can be sorted in to a new List"() {
      given:
      def original = [1, 6, 2, 5, 4, 3]

      when:
      def result = CollectionUtil.sort(original)

      then:
      result != original

      and:
      result == [1, 2, 3, 4, 5, 6]
   }

   def "Collections can be permuted"() {
      expect:
      expectedResult == CollectionUtil.permute(collection)

      where:
      collection | expectedResult
      []         | [[]]
      [1]        | [[1]]
      [1, 2]     | [[1, 2], [2, 1]]
      [1, 2, 3]  | [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 2, 1], [3, 1, 2]]
   }
}
