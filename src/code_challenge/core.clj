(ns code-challenge.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :refer [response]])
  (:gen-class))

(defn digit-sum
  [n]
  ((fn rec-sum [s n]
    (if (zero? n)
      s
      (rec-sum
        (+ s (mod n 10))
        (quot n 10))))
   0 (if (neg? n) (* -1 n) n)))

(defn calc-result
  [data]
  (let [vals (get-in data [:address :values])
        sum  (apply + vals)
        res  (digit-sum sum)]
    res))

(defn handler
  [data]
  (let [res (calc-result data)]
    {:status 200
     :body {:result res}}))

(defroutes app-routes
  (POST "/" req (handler (:body req)))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (->
    app-routes
    (wrap-json-body {:keywords? true :bigdecimals? true})
    wrap-json-response))

(defn -main []
  (jetty/run-jetty app {:port 3000}))
