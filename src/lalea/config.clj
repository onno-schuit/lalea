(ns lalea.config
  (:use [korma.core]
        [korma.db]
        [lalea.test]))

(def test-result "Okay!")

(defn show-test-result
  []
  (println test-result))

(defdb db
    (mysql {:host "localhost"
            :port "3306"
            :delimiters "`"
            :db "lalea"
            :user "root"
            :password "paarse" }))
