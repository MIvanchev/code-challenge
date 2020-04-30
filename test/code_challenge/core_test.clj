(ns code-challenge.core-test
  (:require [clojure.test :refer [deftest are]]
            [code-challenge.core :refer [digit-sum calc-result]]))

(deftest digit-sum-tests
  (are [a b] (= a (digit-sum b))
    8 611
    8 -611
    0 0
    1 100000
    7 4030
    47 -3413200998341))

(deftest calc-result-tests
  (are [a b] (= a (calc-result b))
    8 {:address {:values [74, 117, 115, 116, 79, 110]}}
    0 {:address {:values []}}
	0 {:address {:values [0 0 0]}
    12 {:address {:values [9 -40 -53]}}}))
