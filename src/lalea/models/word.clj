(ns lalea.models.word
  (:require [lalea.models.drill :as drill]
            [noir.validation :as vali]
            [clojure.string :as str]))

(load-file "./src/lalea/config.clj")

(defentity word
    (table :words))


(defn load-by-drill-id
  [drill-id]
  (select word
    (where {:drill_id [= drill-id]})))


(defn load-by-id
  [id]
  (first (select word (where {:id [= id]}))))


(defn is-owner? [word user-id]
  (first (drill/load-by-id-and-user-id (:drill_id word) user-id)))


(defn valid? [{:keys [label meaning user_id drill_id]}]
  (vali/rule (vali/has-value? label)
             [:label "Please fill in a word or phrase"])
  (vali/rule (vali/has-value? meaning)
             [:label "Please fill in the meaning or translation"])
  (vali/rule (number? (Integer. user_id))
             [:user_id "User id not found"])
  (vali/rule (number? (Integer. drill_id))
             [:drill_id "Drill id not found"])
  (not (vali/errors?)))


(defn save
  [{:keys [label meaning drill_id] :as mutated-word-pair}]
  (when (valid? mutated-word-pair)
    (if (mutated-word-pair :id)
      (do
        (update word
          (set-fields {:label label :meaning meaning})
          (where {:id [= (mutated-word-pair :id)]})))
      (do
        (insert word
          (values {:label label :meaning meaning :drill_id drill_id}))))))


(defn destroy
  [id]
  (delete word
    (where {:id [= id]})))


(defn correct? [id provided-meaning]
  (= (:meaning (load-by-id id))))
