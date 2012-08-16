(ns lalea.models.drill)
(load-file "./src/lalea/config.clj")

(defentity drill
    (table :drills))


(defn load-by-user-id
  [user-id]
  (select drill
    (where {:user_id [= user-id]})))


(defn load-by-id-and-user-id
  [id user-id]
  (first (select drill
    (where {:user_id [= user-id] :id [= id]}))))


(defn save
  [mutated-drill]
  (if (mutated-drill :id)
    (do
      (update drill
        (set-fields {:label (mutated-drill :label)})
        (where {:id [= (mutated-drill :id)] :user_id [= (mutated-drill :user_id)]})))
    (do
      (insert drill
        (values {:label (mutated-drill :label) :user_id (mutated-drill :user_id)})))))


(defn destroy
  [obsolete-drill]
  (delete drill
    (where {:id [= (obsolete-drill :id)] :user_id [= (obsolete-drill :user_id)]})))
