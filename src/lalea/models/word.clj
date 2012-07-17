(ns lalea.models.word
  (:use [korma.core]
        [korma.db]
        [lalea.config]))

(defentity word
    (table :words))
