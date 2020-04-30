(defproject code-challenge "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.8.0"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [ring/ring-mock "0.4.0"]
                 [metosin/ring-http-response "0.9.1"]
                 [compojure "1.6.1"]]
  :plugins [[lein-cljfmt "0.6.7"]]
  :repl-options {:init-ns code-challenge.core}
  :main code-challenge.core
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "code-challenge-standalone.jar"
  :min-lein-version "2.0.0")
