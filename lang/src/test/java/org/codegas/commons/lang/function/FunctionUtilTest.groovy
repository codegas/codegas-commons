package org.codegas.commons.lang.function

import spock.lang.Specification

import java.util.function.BiFunction
import java.util.function.Function

class FunctionUtilTest extends Specification {

   def "Functions can be memoized"() {
      given:
      int callCount = 0
      Function<Character, Integer> function = FunctionUtil.memoize(new Function<Character, Integer>() {
         @Override
         Integer apply(Character character) {
            callCount++
            return character.charValue()
         }
      })

      expect:
      function.apply((char)'a') == 97
      function.apply((char)'b') == 98
      function.apply((char)'a') == 97

      and:
      callCount == 2
   }

   def "BiFunctions can be memoized"() {
      given:
      int callCount = 0
      BiFunction<Integer, Integer, TestSum> biFunction = FunctionUtil.memoize(new BiFunction<Integer, Integer, TestSum>() {
         @Override
         TestSum apply(Integer t1, Integer t2) {
            callCount++
            return new TestSum(t1 + t2);
         }
      })

      expect:
      biFunction.apply(4, 5).getSum() == 9
      biFunction.apply(5, 4).getSum() == 9
      biFunction.apply(4, 5).getSum() == 9
      biFunction.apply(4, 6).getSum() == 10

      and:
      callCount == 3
   }

   def "Invertible BiFunctions can be memoized"() {
      given:
      int callCount = 0
      BiFunction<Integer, Integer, TestSum> biFunction = FunctionUtil.inverseMemoize(new BiFunction<Integer, Integer, TestSum>() {
         @Override
         TestSum apply(Integer t1, Integer t2) {
            callCount++
            return new TestSum(t1 + t2);
         }
      })

      expect:
      biFunction.apply(4, 5).getSum() == 9
      biFunction.apply(5, 4).getSum() == 9
      biFunction.apply(4, 5).getSum() == 9
      biFunction.apply(4, 6).getSum() == 10

      and:
      callCount == 2
   }

   private static final class TestSum implements Invertible<TestSum> {

      private final int sum

      public TestSum(int sum) {
         this.sum = sum
      }

      @Override
      public TestSum invert() {
         return new TestSum(sum)
      }

      @Override
      boolean equals(o) {
         if (this.is(o)) {
            return true
         }

         if (getClass() != o.class) {
            return false
         }

         TestSum testSum = (TestSum)o
         return sum == testSum.sum
      }

      @Override
      int hashCode() {
         return sum
      }

      int getSum() {
         return sum
      }
   }
}
