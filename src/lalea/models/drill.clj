(ns lalea.models.drill
  (:use [korma.core]
        [korma.db])
  (:require [lalea.config :as config]))

(defentity drill
    (table :drills))

(defn test2
  []
  (println "test2"))

