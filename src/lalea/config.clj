(use 'korma.core)
(require '[korma.db :as db])

(db/defdb db
    (db/mysql {:host "localhost"
               :port "3306"
               :delimiters "`"
               :db "lalea"
               :user "root"
               :password "paarse" }))
