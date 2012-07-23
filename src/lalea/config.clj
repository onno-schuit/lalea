(ns lalea.config
  (:use [korma.core])
  (:require [lalea.test :as test]
            [korma.db :as db]))


(def test-result "Okay!")

(defn show-test-result
  []
  (println test-result))

(db/defdb db
    (db/mysql {:host "localhost"
               :port "3306"
               :delimiters "`"
               :db "lalea"
               :user "root"
               :password "paarse" }))

(test/no-can-do "This is impossible")
