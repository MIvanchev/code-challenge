(ns code-challenge.core-test
  (:require [clojure.test :refer [deftest is are]]
            [ring.mock.request :as mock]
            [code-challenge.core :refer [digit-sum
                                         calc-result
                                         wrap-json-data]]))

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
    0 {:address {:values [0 0 0]}}
    12 {:address {:values [9 -40 -53]}}))

(deftest wrap-json-data-tests
  (let [wrapper (wrap-json-data identity)
        req     (mock/request :post "/")
        req-err (assoc req :body :error)]
    (letfn [(is-invalid-data? [body]
              (is (= req-err (wrapper (assoc req :body body)))))
            (is-valid-data? [body]
              (is (= (assoc req :body body))))]
      (is-invalid-data? nil)
      (is-invalid-data? {})
      (is-invalid-data? {:address {:values "foo"}})
      (is-invalid-data? {:addresss {:colorKeys ["A" "G" "Z"]
                                    :values [3 7 9]}
                         :meta {:digits 33
                                :processingPattern "d{5}+[a-z&$§]"}})
      (is-invalid-data? {:address {:colorKeys ["A" "G" "Z"]
                                   :values [5 100 4 3 true]}
                         :meta {:digits 33
                                :processingPattern "d{5}+[a-z&$§]"}})
      (is-valid-data? {:address {:colorKeys ["V" "G" "D"]
                                 :values []}})
      (is-valid-data? {:address {:colorKeys ["R" "G" "B"]
                                 :values [6 900 3101 74447]}
                       :meta {:digits 33
                              :processingPattern "d{5}+[a-z&$§]"}}))))
