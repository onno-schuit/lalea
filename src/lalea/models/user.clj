(ns lalea.models.user
  (:use [korma.core]
        [korma.db]
        [lalea.config]))

(defentity user
    (table :users))

(defn test
  []
  (println "Hello from test!"))
