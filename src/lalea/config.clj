(ns lalea.config
  (:use [korma.core]
        [korma.db]))

(defdb db
    (mysql {:host "localhost"
            :port "3306"
            :delimiters "`"
            :db "lalea"
            :user "root"
            :password "paarse" }))
