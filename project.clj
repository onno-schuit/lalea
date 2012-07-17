(defproject lalea "0.1.0-SNAPSHOT"
            :description "Language Learner Aid"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.3.0-beta3"]
                           [org.clojure/java.jdbc "0.2.3"]
                           [mysql/mysql-connector-java "5.1.6"]
                           [korma "0.3.0-beta11"]]
            :main lalea.server)

