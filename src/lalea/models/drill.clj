(ns lalea.models.drill)
(load-file "./src/lalea/config.clj")

(defentity drill
    (table :drills))


(defn load-by-user-id
  [user-id]
  (select drill
    (where {:user_id [= user-id]})))
