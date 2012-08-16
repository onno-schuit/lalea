(ns lalea.models.word)

(load-file "./src/lalea/config.clj")

(defentity word
    (table :words))


(defn load-by-drill-id
  [drill-id]
  (select word
    (where {:drill_id [= drill-id]})))


(defn save
  [{:keys [label meaning drill_id] :as mutated-word-pair}]
  (if (mutated-word-pair :id)
    (do
      (update word
        (set-fields {:label label :meaning meaning})
        (where {:id [= (mutated-word-pair :id)]})))
    (do
      (insert word
        (values {:label label :meaning meaning :drill_id drill_id})))))
