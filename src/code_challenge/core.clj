(ns code-challenge.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.adapter.jetty :as jetty]
            [ring.util.http-response :refer [bad-request]]
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
    (if (not= data :error)
      {:status 200 :body {:result res}}
      (bad-request "The JSON payload has an invalid structure!"))))

(defn wrap-json-data [handler]
  (fn [request]
    (let [data (get-in request [:body :address :values])]
      (if (or (nil? data) (not (every? integer? data)))
        (handler (assoc request :body :error))
        (handler request)))))

(defroutes app-routes
  (POST "/" req (handler (:body req)))
  (route/not-found (bad-request "Invalid service invoked.")))

(def app
  (->
    app-routes
    wrap-json-data
    (wrap-json-body {:keywords? true :bigdecimals? true})
    wrap-json-response))

(defn -main [& args]
  (let [port (Integer/valueOf (or (System/getenv "PORT") "3000"))]
    (jetty/run-jetty app {:port port})))
