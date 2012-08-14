(ns lalea.models.drill)
(load "/lalea/config")

(defentity drill
    (table :drills))


(defn load-by-user-id
  [user-id]
  (select drill
    (where {:user_id [= user-id]})))
