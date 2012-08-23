(ns lalea.models.drill 
  (:require [noir.validation :as vali]))
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


(defn is-owner? [provided-user-id owner-id]
  (if (and owner-id provided-user-id)
    (= (Integer. provided-user-id) (Integer. owner-id))))


(defn valid? [{:keys [label user_id]}]
  (vali/rule (vali/has-value? label)
             [:label "Please fill in a name for your list"])
  (vali/rule (number? (Integer. user_id))
             [:user_id "User id not found"])
  (not (vali/errors?)))


(defn save
  [mutated-drill]
  (when (valid? mutated-drill)
    (if (mutated-drill :id)
      (do
        (update drill
          (set-fields {:label (mutated-drill :label)})
          (where {:id [= (mutated-drill :id)] :user_id [= (mutated-drill :user_id)]}))
        (mutated-drill :id))
      (do
        (insert drill
          (values {:label (mutated-drill :label) :user_id (mutated-drill :user_id)}))))))


(defn destroy
  [obsolete-drill]
  (delete drill
    (where {:id [= (obsolete-drill :id)] :user_id [= (obsolete-drill :user_id)]})))
